<script setup lang="ts">
definePageMeta({
    layout: false
})

const { $firebaseAuth } = useNuxtApp()
const route = useRoute()

const email = ref('')
const password = ref('')
const error = ref('')
const loading = ref(false)

const redirectPath = computed(() =>
    typeof route.query.redirect === 'string' ? route.query.redirect : '/'
)

// Check auth state before rendering, redirect if already logged in.
const user = await getCurrentUser()
if (user) {
    await navigateTo(redirectPath.value)
}

const handleUsernameAndPasswordLogin = async () => {
    if (!email.value || !password.value) return
    await signIn({
        provider: "usernameAndPassword",
        firebaseAuth: $firebaseAuth,
        username: email,
        password: password,
        loading,
        error,
        redirectPath
    })
}

const handleGoogleLogin = async () => {
    await signIn({
        provider: "google",
        firebaseAuth: $firebaseAuth,
        loading,
        error,
        redirectPath
    })
}

</script>

<template>
    <div class="min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
        <div class="max-w-md w-full space-y-8">
            <div>
                <h2 class="mt-6 text-center text-3xl font-extrabold text-gray-900">
                    Sign in to your account
                </h2>
            </div>

            <UCard class="p-6">
                <form class="space-y-6" @submit.prevent="handleUsernameAndPasswordLogin">
                    <div v-if="error" class="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded">
                        {{ error }}
                    </div>

                    <div>
                        <UInput v-model="email" type="email" placeholder="Enter your email" :disabled="loading"
                            required />
                    </div>

                    <div>
                        <UInput v-model="password" type="password" placeholder="Enter your password" :disabled="loading"
                            required />
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
                        <GoogleSignInButton :loading="loading" :handle-google-login="handleGoogleLogin" />
                    </div>
                </form>
            </UCard>
        </div>
    </div>
</template>
