import { useState } from "react";

type RegisterFormProps = {
  onSubmit: (username: string, password: string) => void;
};

const RegisterForm = ({ onSubmit }: RegisterFormProps) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  return (
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
        <button type="submit">Register</button>
      </div>
    </form>
  );
};

export default RegisterForm;
