import "./App.css";
import LoginForm from "./components/auth/LoginForm";
import RegisterForm from "./components/auth/RegisterForm";

function App() {
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
      <br />
      <hr />
      <h1>Login</h1>
      <LoginForm
        onSubmit={(username, password) => {
          console.log(username, password);
          fetch("/api/auth/login", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({ username, password }),
          }).then(async (response) => {
            if (response.ok) {
              const token = await response.json().then((data) => data.token);
              fetch("/api/profile", {
                headers: {
                  Authorization: `Bearer ${token}`,
                },
              }).then(async (profileResponse) => {
                alert((await profileResponse.json()).message);
              });
            } else {
              alert("Login failed");
            }
          });
        }}
      />
    </>
  );
}

export default App;
