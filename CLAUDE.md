# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Firebase Authentication boilerplate with:
- **Nuxt 4** frontend (`/web`) - Primary frontend with SSR support
- **Ktor** API server (`/api`) - Kotlin backend with Firebase token validation
- **React + Vite** frontend (`/frontend`) - Alternative frontend implementation

Current working directory is `/web` (Nuxt frontend).

## Development Commands

### Web Frontend (Nuxt 4) - Current Directory
- **Development**: `bun run dev` (runs on http://localhost:3000)
- **Build**: `bun run build`
- **Type checking**: `bun run typecheck`
- **Generate static**: `bun run generate`
- **Preview**: `bun run preview`

### API Server (from /api directory)
- **Development**: `./gradlew run` (runs on http://localhost:3001)
- **Tests**: `./gradlew test`
- **Build**: `./gradlew build`
- **Build fat JAR**: `./gradlew buildFatJar`

### Linting
- **Web**: Uses @nuxt/eslint with custom config in `eslint.config.mjs`
- **API**: Kotlin code style enforced by Gradle

## Architecture

### Authentication Flow
1. **Frontend**: VueFire handles Firebase client authentication with session cookies
2. **Middleware**: `app/middleware/auth.ts` protects routes using `getCurrentUser()`
3. **Backend**: Ktor validates Firebase tokens via Firebase Admin SDK
4. **Protected Routes**: Use `authenticate("firebase")` in Ktor routing

### Key Files
- `nuxt.config.ts` - VueFire configuration with Firebase settings
- `app/middleware/auth.ts` - Route protection middleware
- `api/src/main/kotlin/Security.kt` - Firebase Admin SDK setup
- `api/src/main/kotlin/Routing.kt` - API routes with `/api/me` protected endpoint

### Firebase Integration
- Uses `nuxt-vuefire` module for client-side Firebase
- Server-side validation through Firebase Admin SDK in Ktor
- Session cookies enabled for SSR compatibility
- Admin credentials via `FIREBASE_ADMIN_FILE` environment variable

## Environment Requirements

### Frontend (.env in /web)
```
FIREBASE_API_KEY=...
FIREBASE_AUTH_DOMAIN=...
FIREBASE_PROJECT_ID=...
FIREBASE_STORAGE_BUCKET=...
FIREBASE_MESSAGING_SENDER_ID=...
FIREBASE_APP_ID=...
FIREBASE_MEASUREMENT_ID=...
```

### Backend
- `FIREBASE_ADMIN_FILE` pointing to Firebase Admin SDK JSON
- Default API port: 3001
- Admin JSON typically in `api/src/main/resources/firebase-admin.json`

## Development Workflow

1. Start API: `cd ../api && ./gradlew run`
2. Start web frontend: `bun run dev`
3. Always run `bun run typecheck` before commits
4. API serves protected routes under `/api/*` namespace