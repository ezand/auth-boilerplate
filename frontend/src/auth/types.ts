export type UserSession = {
  state: string;
  token: string;
  authType: "JWT" | "SESSION";
};
