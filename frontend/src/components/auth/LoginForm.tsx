import { useState } from "react";

type LoginFormProps = {
  onSubmit: (username: string, password: string) => void;
};

const LoginForm = ({ onSubmit }: LoginFormProps) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  return (
    <>
      <form
        onSubmit={(e) => {
          onSubmit(username, password);
          e.preventDefault();
        }}
      >
        <div>
          <div>Username:</div>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div>
          <div>Password:</div>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <div>
          <button type="submit">Login</button>
        </div>
      </form>
      <div>
        <button
          onClick={async () => {
            window.location.href =
              "/api/auth/google/login?redirectUrl=http://localhost:8080/home";
          }}
        >
          Google Login
        </button>
        <button
          onClick={async () => {
            await fetch("/api/auth/logout");
          }}
        >
          Google Logout
        </button>
      </div>
    </>
  );
};

export default LoginForm;
