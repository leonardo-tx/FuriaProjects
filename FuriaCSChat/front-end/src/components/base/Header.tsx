import { useRouter } from "next/router";
import styles from "./styles/Header.module.css";
import Button from "../shared/Button";
import BrandIcon from "@/assets/icon.svg";
import { IoLogInOutline, IoLogOutOutline, IoPerson } from "react-icons/io5";
import { logout } from "@/libs/api";
import { useAtom } from "jotai";
import { currentUserAtom } from "@/libs/users/atoms/currentUserAtom";

interface NavItem {
  text: string;
  id: string;
  href: string;
}

const navItems: NavItem[] = [];

export default function Header() {
  const router = useRouter();
  const [currentUser, setCurrentUser] = useAtom(currentUserAtom);

  const handleLogout = async () => {
    const response = await logout();

    if (response.success) {
      setCurrentUser(null);
      router.push("/home");
    } else {
      throw new Error("Falha ao realizar logout");
    }
  };

  return (
    <header className={styles.header}>
      <Button
        as="Link"
        scheme="primary"
        variant="text"
        id="logo-home-anchor"
        className="text-3xl font-semibold tracking-wide"
        href="/home"
      >
        <BrandIcon style={{ fill: "black" }} height={70} width={70} />
      </Button>
      <nav className="flex items-center gap-6 text-md font-semibold">
        {navItems.map((item, i) => (
          <Button
            as="Link"
            scheme="primary"
            variant="text"
            key={i}
            id={item.id}
            href={item.href}
          >
            {item.text}
          </Button>
        ))}
      </nav>
      <div className="flex gap-6 text-base font-semibold">
        {currentUser !== null ? (
          <Button
            onClick={handleLogout}
            scheme="primary"
            variant="text"
            id="logout-button"
            className="cursor-pointer"
          >
            <IoLogOutOutline size={20} />
            Logout
          </Button>
        ) : (
          <>
            <Button as="Link" variant="outline" id="login-button" href="/login">
              <IoLogInOutline size={20} />
              Entrar
            </Button>
            <Button as="Link" id="signup-button" href="/register">
              <IoPerson size={20} />
              Criar Conta
            </Button>
          </>
        )}
      </div>
    </header>
  );
}
