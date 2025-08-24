/**
 * Import function triggers from their respective submodules:
 *
 * import {onCall} from "firebase-functions/v2/https";
 * import {onDocumentWritten} from "firebase-functions/v2/firestore";
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

import { setGlobalOptions } from "firebase-functions";
import * as admin from "firebase-admin";
import * as functionsV1 from "firebase-functions/v1";
// import * as logger from "firebase-functions/logger";
import { defineString } from "firebase-functions/params";

// Start writing functions
// https://firebase.google.com/docs/functions/typescript

// For cost control, you can set the maximum number of containers that can be
// running at the same time. This helps mitigate the impact of unexpected
// traffic spikes by instead downgrading performance. This limit is a
// per-function limit. You can override the limit for each function using the
// `maxInstances` option in the function's options, e.g.
// `onRequest({ maxInstances: 5 }, (req, res) => { ... })`.
// NOTE: setGlobalOptions does not apply to functions using the v1 API. V1
// functions should each use functions.runWith({ maxInstances: 10 }) instead.
// In the v1 API, each function can only serve one request per container, so
// this will be the maximum concurrent request count.
setGlobalOptions({ maxInstances: 10 });

admin.initializeApp();

// Parameterized config (backend base URL)
const BACKEND_URL = defineString("BACKEND_URL");

export const onAuthUserCreated = functionsV1
  .region("us-central1")
  .runWith({
    secrets: ["BACKEND_API_KEY"],
    timeoutSeconds: 30,
    memory: "256MB",
  })
  .auth.user()
  .onCreate(async (user) => {
    const payload = {
      uid: user.uid,
      email: user.email ?? null,
      emailVerified: user.emailVerified ?? false,
      displayName: user.displayName ?? null,
      photoURL: user.photoURL ?? null,
      phoneNumber: user.phoneNumber ?? null,
      providerIds: (user.providerData || []).map((p) => p.providerId),
      createdAt: new Date().toISOString(),
    };

    const url = `${BACKEND_URL.value()}/internal/api/firebase/users`;
    const headers = {
      "Content-Type": "application/json",
      "X-Idempotency-Key": user.uid,
      Authorization: `Bearer ${process.env.BACKEND_API_KEY}`,
    };

    const res = await fetch(url, {
      method: "POST",
      headers: headers,
      body: JSON.stringify(payload),
    });

    if (!res.ok) {
      const text = await res.text();
      throw new Error(`Backend ${res.status}: ${text}`);
    }
  });

export const onAuthUserDeleted = functionsV1
  .region("us-central1")
  .runWith({
    secrets: ["BACKEND_API_KEY"],
    timeoutSeconds: 30,
    memory: "256MB",
  })
  .auth.user()
  .onDelete(async (user) => {
    const url = `${BACKEND_URL.value()}/internal/api/firebase/users/${
      user.uid
    }`;
    const headers = {
      Authorization: `Bearer ${process.env.BACKEND_API_KEY}`,
    };

    const res = await fetch(url, {
      method: "DELETE",
      headers: headers,
    });

    if (!res.ok) {
      const text = await res.text();
      throw new Error(`Backend ${res.status}: ${text}`);
    }
  });
