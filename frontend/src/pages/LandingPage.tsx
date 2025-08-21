import { Link } from "react-router";

const LandingPage = () => {
  return (
    <>
      <div>Welcome to the Landing Page</div>

      <div style={{ display: "flex", gap: "10px" }}>
        <Link to="/auth/register">Register</Link>
        <Link to="/auth/login">Login</Link>
        <Link to="/profile/me">Profile</Link>
      </div>
    </>
  );
};

export default LandingPage;
