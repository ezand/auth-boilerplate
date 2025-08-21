import { Route, Routes } from "react-router";
import ProfilePage from "./pages/ProfilePage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import LandingPage from "./pages/LandingPage";
import useAuth from "./auth/useAuth";
import ProtectedRoute from "./ProtectedRoute";

const App = () => {
  const { userSession, onLogin, onLogout } = useAuth();

  return (
    <Routes>
      <Route path="/" element={<LandingPage />} />
      <Route path="auth">
        <Route path="register" element={<RegisterPage />} />
        <Route path="login" element={<LoginPage onLogin={onLogin} />} />
      </Route>
      <Route path="profile">
        <Route
          path="me"
          element={
            <ProtectedRoute userSession={userSession}>
              <ProfilePage onLogout={onLogout} />
            </ProtectedRoute>
          }
        />
      </Route>
      <Route path="*" element={<div>No route!</div>} />
    </Routes>
  );
};

export default App;
