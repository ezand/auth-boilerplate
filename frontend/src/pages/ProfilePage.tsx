import { Link, useNavigate } from "react-router";
import useAuth from "../auth/useAuth";

type ProfilePageProps = {
  onLogout: () => void;
};

const ProfilePage = ({ onLogout }: ProfilePageProps) => {
  const { userSession } = useAuth();
  const navigate = useNavigate();

  return (
    <>
      {userSession ? (
        <>
          <div>Welcome to your profile!</div>
          <button
            onClick={() => {
              onLogout();
              navigate("/");
            }}
          >
            Log out
          </button>
        </>
      ) : (
        <div>
          Please <Link to="/auth/login">log in</Link> to view your profile.
        </div>
      )}
    </>
  );
};

export default ProfilePage;
