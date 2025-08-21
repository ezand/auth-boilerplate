import { createContext } from "react";

type UserSession = {
  state: string;
  token: string;
  authType: "JWT" | "SESSION";
};

const AuthContext = createContext<{
  userSession: UserSession | null;
  onLogin: (username: string, password: string) => void;
  onLogout: () => void;
}>({
  userSession: null,
  onLogin: () => {},
  onLogout: () => {},
});

export default AuthContext;
