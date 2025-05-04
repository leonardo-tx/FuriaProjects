import Button from "@/components/shared/Button";
import Input from "@/components/shared/Input";
import { getAllMessages, getUserById, sendMessage } from "@/libs/api";
import MessageView from "@/libs/chat/MessageView";
import { socketAtom } from "@/libs/socket/atoms/socketAtom";
import { currentUserAtom } from "@/libs/users/atoms/currentUserAtom";
import { StompSubscription } from "@stomp/stompjs";
import { useAtomValue } from "jotai";
import Head from "next/head";
import { useState, useEffect, FormEventHandler, useRef } from "react";

export default function Home() {
  const currentUser = useAtomValue(currentUserAtom);
  const [messages, setMessages] = useState<MessageView[]>([]);
  const [newMessage, setNewMessage] = useState("");
  const socket = useAtomValue(socketAtom);

  const userCache = useRef<Record<number, string>>({});
  const [, forceUpdate] = useState({});
  const containerRef = useRef<HTMLDivElement | null>(null);
  const bottomRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    const container = containerRef.current;
    if (!container) return;

    const isAtBottom =
      container.scrollHeight - container.scrollTop - container.clientHeight <
      50;

    if (isAtBottom) {
      bottomRef.current?.scrollIntoView({ behavior: "smooth" });
    }
  }, [messages]);

  useEffect(() => {
    getAllMessages().then((res) => {
      if (res.success && res.data) {
        setMessages(res.data);
        res.data.forEach((msg) => {
          ensureUsernameCached(msg.userId);
        });
      }
    });
  }, []);

  useEffect(() => {
    if (socket === null) return;

    const onMessageReceived = (message: any) => {
      const body: MessageView = JSON.parse(message.body);
      setMessages((prev) => [...prev, body]);
      ensureUsernameCached(body.userId);
    };

    let subscription: StompSubscription;
    socket.onConnect = () => {
      subscription = socket.subscribe("/receiver/chat", onMessageReceived);
    };

    socket.activate();

    return () => {
      subscription.unsubscribe();
      socket.deactivate();
    };
  }, [socket]);

  const ensureUsernameCached = async (userId: number) => {
    if (userCache.current[userId] !== undefined) return;

    const res = await getUserById(userId);
    if (res.success && res.data) {
      userCache.current[userId] = `${res.data.name} (${res.data.userName})`;
      forceUpdate({});
    } else {
      userCache.current[userId] = "Usuário desconhecido";
      forceUpdate({});
    }
  };

  const handleSend: FormEventHandler<HTMLFormElement> = async (event) => {
    event.preventDefault();
    if (newMessage.trim() === "") return;

    const res = await sendMessage({ text: newMessage });
    if (res.success && res.data) {
      setNewMessage("");
    }
  };

  return (
    <>
      <Head>
        <title>Home - Furia CS Chat</title>
      </Head>
      <div className="flex h-full flex-col gap-4 p-4 justify-center items-center">
        <div
          ref={containerRef}
          className="flex flex-col gap-2 shadow-2xl w-full p-3 rounded max-w-3xl h-100 overflow-y-auto"
        >
          {messages.map((msg, index) => (
            <div
              key={index}
              className={
                "text-md flex flex-col border-1 rounded p-2 max-w-1/2 " +
                (currentUser?.id === msg.userId ? "self-end" : "self-start")
              }
            >
              {currentUser?.id !== msg.userId && (
                <span className="font-semibold">
                  {userCache.current[msg.userId] ?? msg.userId}
                </span>
              )}
              <p className="break-all">{msg.text}</p>
            </div>
          ))}
          <div ref={bottomRef} />
        </div>

        <form onSubmit={handleSend} className="flex gap-2 w-full max-w-3xl">
          <Input
            type="text"
            variant="line"
            value={newMessage}
            onChange={(e) => setNewMessage(e.target.value)}
            placeholder="Digite sua mensagem..."
            className="w-full"
          />
          <Button type="submit">Enviar</Button>
        </form>
      </div>
    </>
  );
}
