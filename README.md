# Authentication Boilerplate Template

A full-stack authentication template demonstrating secure Firebase Authentication integration between frontend and backend applications. Use this template to quickly bootstrap new projects with production-ready authentication patterns.

## Overview

This template provides production-ready authentication patterns with Firebase, featuring multiple frontend implementations and a robust backend API with token validation.

### Architecture

- **Frontend Options**:
  - **Nuxt 4** (`/web`) - SSR-enabled Vue.js frontend with session cookies
  - **React + Vite** (`/frontend`) - Alternative SPA implementation
- **Backend API** (`/api`) - Ktor-based Kotlin server with Firebase token validation

### Key Features

- Firebase Authentication with multiple frontend frameworks
- Server-side token validation using Firebase Admin SDK
- Session cookie support for SSR compatibility
- Protected API routes with middleware authentication
- Type-safe development with TypeScript/Kotlin
- Production-ready build configurations

## Quick Start

### Prerequisites

- Node.js and Bun
- Java 11+ (for Kotlin backend)
- Firebase project with Authentication enabled

### Setup

1. **Use this template**:
   - Click "Use this template" button on GitHub
   - Or clone: `git clone <your-new-repository-url>`
   
2. **Install dependencies**:
   ```bash
   cd your-project-name
   cd web && bun install
   ```

3. **Configure Firebase**:
   - Create `.env` file in `/web` with your Firebase config
   - Place Firebase Admin SDK JSON in `/api/src/main/resources/`
   - Set `FIREBASE_ADMIN_FILE` environment variable

4. **Start development servers**:
   ```bash
   # Terminal 1 - API Server
   cd api && ./gradlew run
   
   # Terminal 2 - Web Frontend
   cd web && bun run dev
   ```

5. **Access the application**:
   - Frontend: http://localhost:3000
   - API: http://localhost:3001

## Authentication Flow

1. User signs in via Firebase Auth on the frontend
2. Frontend receives Firebase ID token and sets session cookie
3. Protected routes use middleware to verify authentication
4. API validates Firebase tokens via Admin SDK for secure endpoints
5. Backend serves protected data to authenticated users

## Development

- **Frontend**: `bun run typecheck` before commits
- **Backend**: `./gradlew test` for API testing
- **Linting**: Configured for both frontend (@nuxt/eslint) and backend

## Environment Variables

See `CLAUDE.md` for detailed environment setup requirements.

## Project Structure

```
├── web/          # Nuxt 4 frontend (primary)
├── frontend/     # React + Vite alternative
├── api/          # Ktor Kotlin backend
└── CLAUDE.md     # Development guidance
```

This template demonstrates authentication patterns in full-stack applications with multiple frontend framework options, ready to be customized for your specific needs.