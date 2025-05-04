import { useState, FormEvent } from "react";
import { useRouter } from "next/router";
import Button from "@/components/shared/Button";
import Input from "@/components/shared/Input";
import { IoMailOutline, IoLockClosedOutline, IoPerson } from "react-icons/io5";
import { register } from "@/libs/api";
import FuriaErrorCode from "@/libs/FuriaErrorCode";
import Head from "next/head";
import UserRegisterDTO from "@/libs/users/UserRegisterDTO";

export default function RegisterPage() {
  const router = useRouter();
  const [form, setForm] = useState<UserRegisterDTO>({
    email: "",
    name: "",
    userName: "",
    password: "",
  });
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string>("");

  const handleLogin = async (e: FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    setError("");

    const response = await register(form);
    if (response.success) {
      router.push("/login");
    } else {
      setError(getErrorMessage(response.errorCode));
    }
    setIsLoading(false);
  };

  return (
    <>
      <Head>
        <title>Cadastro - Furia CS Chat</title>
      </Head>
      <div className="h-full flex items-center justify-center">
        <div className="w-full max-w-md p-8 rounded-2xl shadow-lg">
          <div className="text-center mb-8">
            <h1 className="text-3xl font-bold">Furia CS Chat</h1>
            <p className="mt-2">Criar sua conta</p>
          </div>

          <form onSubmit={handleLogin} className="space-y-6">
            <div className="space-y-4">
              <Input
                id="login-email"
                type="text"
                required
                placeholder="Email"
                className="w-full"
                variant="line"
                startElement={<IoMailOutline size={20} />}
                value={form.email}
                onChange={(e) =>
                  setForm((oldForm) => ({
                    ...oldForm,
                    email: e.target.value.trim(),
                  }))
                }
              />
              <Input
                id="login-name"
                type="text"
                required
                placeholder="Nome"
                className="w-full"
                variant="line"
                startElement={<IoPerson size={20} />}
                value={form.name}
                onChange={(e) =>
                  setForm((oldForm) => ({
                    ...oldForm,
                    name: e.target.value,
                  }))
                }
              />
              <Input
                id="login-username"
                type="text"
                required
                placeholder="Nome de usuário"
                className="w-full"
                variant="line"
                startElement={<IoPerson size={20} />}
                value={form.userName}
                onChange={(e) =>
                  setForm((oldForm) => ({
                    ...oldForm,
                    userName: e.target.value.trim(),
                  }))
                }
              />
              <Input
                id="login-password"
                type="password"
                required
                placeholder="Senha"
                variant="line"
                startElement={<IoLockClosedOutline size={20} />}
                value={form.password}
                onChange={(e) =>
                  setForm((oldForm) => ({
                    ...oldForm,
                    password: e.target.value,
                  }))
                }
              />
            </div>
            {error && (
              <div
                id="login-alert"
                className="text-red-500 text-sm text-center"
              >
                {error}
              </div>
            )}
            <Button
              type="submit"
              scheme="primary"
              id="login-submit-button"
              className="w-full bg-blue-600 hover:bg-blue-700 text-white py-2 rounded-md cursor-pointer"
              disabled={isLoading}
            >
              {isLoading ? "Criando conta..." : "Cadastrar-se"}
            </Button>
          </form>

          <div className="mt-6 text-center">
            <p className="text-zinc-600">
              Já possui uma conta?{" "}
              <Button
                as="Link"
                variant="text"
                scheme="primary"
                id="create-account-link"
                href="/login"
                className="text-blue-600 hover:text-blue-700"
              >
                Fazer login
              </Button>
            </p>
          </div>
        </div>
      </div>
    </>
  );
}

function getErrorMessage(errorCode: FuriaErrorCode): string {
  switch (errorCode) {
    case FuriaErrorCode.EMAIL_EMPTY:
      return "O e-mail não pode ser vazio";
    case FuriaErrorCode.EMAIL_INVALID:
      return "O e-mail não pode ser inválido";
    case FuriaErrorCode.EMAIL_INVALID_LENGTH:
      return "O e-mail deve possuir entre 5 a 254 caracteres";
    case FuriaErrorCode.EMAIL_ALREADY_EXISTS:
      return "Uma conta com esse e-mail já existe";
    case FuriaErrorCode.NAME_EMPTY:
      return "O nome não pode ser vazio";
    case FuriaErrorCode.NAME_INVALID:
      return "O nome não pode ser inválido";
    case FuriaErrorCode.NAME_INVALID_LENGTH:
      return "O nome deve possuir entre 1 a 50 caracteres";
    case FuriaErrorCode.USER_NAME_EMPTY:
      return "O nome de usuário não pode ser vazio";
    case FuriaErrorCode.USER_NAME_INVALID:
      return "O nome de usuário não pode ser inválido";
    case FuriaErrorCode.USER_NAME_INVALID_LENGTH:
      return "O nome de usuário deve possuir entre 3 a 40 caracteres";
    case FuriaErrorCode.USER_NAME_INVALID_CHARACTER:
      return "O nome de usuário possui caracteres inválidos";
    case FuriaErrorCode.USER_NAME_ALREADY_EXISTS:
      return "Uma conta com esse nome de usuário já existe";
    case FuriaErrorCode.PASSWORD_EMPTY:
      return "A senha não pode ser vazia";
    case FuriaErrorCode.PASSWORD_INVALID_BYTES:
      return "A senha excedeu de tamanho";
    case FuriaErrorCode.PASSWORD_INVALID_CHARACTER:
      return "A senha possui caracteres inválidos";
    case FuriaErrorCode.PASSWORD_INVALID_LENGTH:
      return "A senha deve possuir entre 8 a 72 caracteres";
  }
  return "Não foi possível fazer o cadastro.";
}
