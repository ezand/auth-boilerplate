# Firebase functions

## Init

```bash
npm i -g firebase-tools
firebase login
firebase init functions
```

## .env

[Environment variables](https://firebase.google.com/docs/functions/config-env?gen=2nd)

```bash
BACKEND_URL=https://your-api.example.com
```

## Secrets

```bash
firebase functions:secrets:set BACKEND_API_KEY
```
