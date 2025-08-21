import { PropsWithChildren } from "react";
import { UserSession } from "./auth/types";
import { Navigate } from "react-router";

type ProtectedRouteProps = PropsWithChildren & {
  userSession: UserSession | null;
  redirectPath?: string;
};

const ProtectedRoute = ({
  userSession,
  redirectPath = "/auth/login",
  children,
}: ProtectedRouteProps) => {
  console.log(userSession);
  if (!userSession) {
    return <Navigate to={redirectPath} replace />;
  }

  return children;
};

export default ProtectedRoute;
