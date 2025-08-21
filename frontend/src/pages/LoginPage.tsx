import LoginForm from "../components/auth/LoginForm";

type LoginPageProps = {
  onLogin: (username: string, password: string) => void;
};

const LoginPage = ({ onLogin }: LoginPageProps) => {
  return (
    <>
      <h1>Login</h1>
      <LoginForm onSubmit={onLogin} />
    </>
  );
};

export default LoginPage;
