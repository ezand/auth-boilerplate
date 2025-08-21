<template>
    <div class="min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
        <div class="max-w-md w-full space-y-8">
            <div>
                <h2 class="mt-6 text-center text-3xl font-extrabold text-gray-900">
                    Sign in to your account
                </h2>
            </div>

            <UCard class="p-6">
                <form class="space-y-6" @submit.prevent="handleLogin">
                    <div v-if="error" class="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded">
                        {{ error }}
                    </div>

                    <div>
                        <UFormGroup label="Email address" name="email" required>
                            <UInput v-model="email" type="email" placeholder="Enter your email" :disabled="loading"
                                required />
                        </UFormGroup>
                    </div>

                    <div>
                        <UFormGroup label="Password" name="password" required>
                            <UInput v-model="password" type="password" placeholder="Enter your password"
                                :disabled="loading" required />
                        </UFormGroup>
                    </div>

                    <div>
                        <UButton type="submit" block :loading="loading" :disabled="!email || !password">
                            Sign in
                        </UButton>
                    </div>

                    <div class="relative">
                        <div class="absolute inset-0 flex items-center">
                            <div class="w-full border-t border-gray-300" />
                        </div>
                        <div class="relative flex justify-center text-sm">
                            <span class="px-2 bg-white text-gray-500">Or continue with</span>
                        </div>
                    </div>

                    <div class="space-y-3">
                        <UButton variant="outline" block :loading="googleLoading" @click="signInWithGoogle">
                            <template #leading>
                                <svg class="w-5 h-5" viewBox="0 0 24 24">
                                    <path fill="currentColor"
                                        d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z" />
                                    <path fill="currentColor"
                                        d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z" />
                                    <path fill="currentColor"
                                        d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z" />
                                    <path fill="currentColor"
                                        d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z" />
                                </svg>
                            </template>
                            Continue with Google
                        </UButton>

                        <UButton variant="outline" block :loading="githubLoading" @click="signInWithGitHub">
                            <template #leading>
                                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
                                    <path
                                        d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z" />
                                </svg>
                            </template>
                            Continue with GitHub
                        </UButton>
                    </div>
                </form>
            </UCard>
        </div>
    </div>
</template>

<script setup lang="ts">
import { signInWithEmailAndPassword, signInWithPopup, GoogleAuthProvider, GithubAuthProvider } from 'firebase/auth'

definePageMeta({
    layout: false
})

const { $firebaseAuth } = useNuxtApp()
const route = useRoute()

const email = ref('')
const password = ref('')
const error = ref('')
const loading = ref(false)
const googleLoading = ref(false)
const githubLoading = ref(false)

const redirectPath = computed(() =>
    typeof route.query.redirect === 'string' ? route.query.redirect : '/'
)

const handleLogin = async () => {
    if (!email.value || !password.value) return

    loading.value = true
    error.value = ''

    try {
        console.log('firebase auth', $firebaseAuth, email.value, password.value)
        await signInWithEmailAndPassword($firebaseAuth, email.value, password.value)
        await navigateTo(redirectPath.value)
    } catch (err: unknown) {
        console.error('Login error:', err)
        const firebaseError = err as { code: string }
        error.value = getErrorMessage(firebaseError.code)
    } finally {
        loading.value = false
    }
}

const signInWithGoogle = async () => {
    googleLoading.value = true
    error.value = ''

    try {
        const provider = new GoogleAuthProvider()
        await signInWithPopup($firebaseAuth, provider)
        await navigateTo(redirectPath.value)
    } catch (err: unknown) {
        console.error('Google sign in error:', err)
        const firebaseError = err as { code: string }
        error.value = getErrorMessage(firebaseError.code)
    } finally {
        googleLoading.value = false
    }
}

const signInWithGitHub = async () => {
    githubLoading.value = true
    error.value = ''

    try {
        const provider = new GithubAuthProvider()
        await signInWithPopup($firebaseAuth, provider)
        await navigateTo(redirectPath.value)
    } catch (err: unknown) {
        console.error('GitHub sign in error:', err)
        const firebaseError = err as { code: string }
        error.value = getErrorMessage(firebaseError.code)
    } finally {
        githubLoading.value = false
    }
}

const getErrorMessage = (errorCode: string): string => {
    switch (errorCode) {
        case 'auth/user-not-found':
            return 'No user found with this email address'
        case 'auth/wrong-password':
            return 'Incorrect password'
        case 'auth/invalid-email':
            return 'Invalid email address'
        case 'auth/user-disabled':
            return 'This account has been disabled'
        case 'auth/too-many-requests':
            return 'Too many failed attempts. Please try again later'
        case 'auth/popup-closed-by-user':
            return 'Sign-in was cancelled'
        case 'auth/cancelled-popup-request':
            return 'Only one popup request is allowed at a time'
        default:
            return 'An error occurred during sign in. Please try again'
    }
}

// Redirect if already logged in
onMounted(async () => {
    const user = await getCurrentUser()
    if (user) {
        await navigateTo(redirectPath.value)
    }
})
</script>
