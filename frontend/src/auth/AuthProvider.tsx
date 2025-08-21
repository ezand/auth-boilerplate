import { PropsWithChildren, useState } from "react";
import { UserSession } from "./types";
import AuthContext from "./AuthContext";

const AuthProvider = ({ children }: PropsWithChildren) => {
  const [userSession, setUserSession] = useState<UserSession | null>(null);

  const handleLogin = (username: string, password: string) => {
    fetch("/api/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
    }).then(async (response) => {
      if (response.ok) {
        const token = await response.json().then((data) => data.token);
        setUserSession({ state: "authenticated", token, authType: "JWT" });
      } else {
        alert("Login failed");
      }
    });
  };

  const handleLogout = () => {
    fetch("/api/auth/logout").then(() => {
      setUserSession(null);
    });
  };

  return (
    <AuthContext.Provider
      value={{ userSession, onLogin: handleLogin, onLogout: handleLogout }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;
