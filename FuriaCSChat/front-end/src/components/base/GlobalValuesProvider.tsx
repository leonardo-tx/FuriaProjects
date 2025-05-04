import { ReactNode } from "react";
import CurrentUserProvider from "./CurrentUserProvider";
import SocketProvider from "./SocketProvider";

interface Props {
  children: ReactNode;
}

export default function GlobalValuesProvider({ children }: Props) {
  return (
    <CurrentUserProvider>
      <SocketProvider>{children}</SocketProvider>
    </CurrentUserProvider>
  );
}
