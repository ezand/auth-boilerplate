import {
  GoogleAuthProvider,
  signInWithEmailAndPassword,
  signInWithPopup,
  type Auth,
} from "firebase/auth";

const getErrorMessage = (errorCode: string): string => {
  switch (errorCode) {
    case "auth/user-not-found":
      return "No user found with this email address";
    case "auth/wrong-password":
      return "Incorrect password";
    case "auth/invalid-email":
      return "Invalid email address";
    case "auth/user-disabled":
      return "This account has been disabled";
    case "auth/too-many-requests":
      return "Too many failed attempts. Please try again later";
    case "auth/popup-closed-by-user":
      return "Sign-in was cancelled";
    case "auth/cancelled-popup-request":
      return "Only one popup request is allowed at a time";
    default:
      return "An error occurred during sign in. Please try again";
  }
};

type UsernameAndPasswordSignIn = {
  provider: "usernameAndPassword";
  username: Ref<string>;
  password: Ref<string>;
};

type GoogleSignIn = {
  provider: "google";
};

type SignIn = (UsernameAndPasswordSignIn | GoogleSignIn) & {
  firebaseAuth: Auth | null;
  loading: Ref<boolean>;
  error: Ref<string>;
  redirectPath: Ref<string | undefined>;
};

export const signIn = async (signIn: SignIn) => {
  const { redirectPath, loading, error, provider, firebaseAuth } = signIn;

  console.log("auth", firebaseAuth);

  loading.value = true;
  error.value = "";

  if (firebaseAuth) {
    try {
      switch (provider) {
        case "usernameAndPassword":
          console.log("GOT HERE!!");
          await signInWithEmailAndPassword(
            firebaseAuth,
            signIn.username.value,
            signIn.password.value
          );
          break;
        case "google":
          await signInWithPopup(firebaseAuth, new GoogleAuthProvider());
          break;
      }
      await navigateTo(redirectPath.value || "/");
    } catch (err: unknown) {
      console.error("Login error:", err);
      const firebaseError = err as { code: string };
      error.value = getErrorMessage(firebaseError.code);
    } finally {
      loading.value = false;
    }
  }
};
