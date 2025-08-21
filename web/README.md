# Nuxt Minimal Starter

Look at the [Nuxt documentation](https://nuxt.com/docs/getting-started/introduction) to learn more.

## Setup

Make sure to install dependencies:

```bash
# npm
npm install

# pnpm
pnpm install

# yarn
yarn install

# bun
bun install
```

## Development Server

Start the development server on `http://localhost:3000`:

```bash
# npm
npm run dev

# pnpm
pnpm dev

# yarn
yarn dev

# bun
bun run dev
```

## Authentication

This project uses Firebase Authentication for user management.
Make sure to set up your Firebase project and add the necessary configuration to your
environment variables.

### .env

```bash
FIREBASE_API_KEY=...
FIREBASE_AUTH_DOMAIN=...
FIREBASE_PROJECT_ID=...
FIREBASE_STORAGE_BUCKET=...
FIREBASE_MESSAGING_SENDER_ID=...
FIREBASE_APP_ID=...
FIREBASE_MEASUREMENT_ID=...
GOOGLE_APPLICATION_CREDENTIALS=...
```

### firebase-admin.json

```json
{
  "type": "...",
  "project_id": "...",
  "private_key_id": "...",
  "private_key": "...",
  "client_email": "...",
  "client_id": "...",
  "auth_uri": "...",
  "token_uri": "...",
  "auth_provider_x509_cert_url": "...",
  "client_x509_cert_url": "...",
  "universe_domain": "..."
}
```

## Production

Build the application for production:

```bash
# npm
npm run build

# pnpm
pnpm build

# yarn
yarn build

# bun
bun run build
```

Locally preview production build:

```bash
# npm
npm run preview

# pnpm
pnpm preview

# yarn
yarn preview

# bun
bun run preview
```

## Resources

- Check out the [deployment documentation](https://nuxt.com/docs/getting-started/deployment) for more information.
- [Nuxt documentation](https://nuxt.com/docs/4.x/getting-started/introduction)
- [VueFire documentation](https://vuefire.vuejs.org/nuxt/getting-started.html)
- [Nuxt VueFire documentation](https://vuefire.vuejs.org/nuxt/getting-started.html)
- [Nuxt i18n documentation](https://i18n.nuxtjs.org/docs/getting-started)
- [Nuxt UI documentation](https://ui.nuxt.com/)
- [Reka UI documentation](https://reka-ui.com/)
