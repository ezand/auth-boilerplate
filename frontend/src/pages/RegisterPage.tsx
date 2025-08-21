import RegisterForm from "../components/auth/RegisterForm";

const RegisterPage = () => {
  return (
    <>
      <h1>Register</h1>
      <RegisterForm
        onSubmit={(username, password) => {
          fetch("/api/auth/register", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({ username, password }),
          }).then((response) => {
            if (response.ok) {
              alert("Registration successful");
            } else {
              alert("Registration failed");
            }
          });
        }}
      />
    </>
  );
};

export default RegisterPage;
