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
    <div
        class="min-h-screen flex items-center justify-center bg-gradient-to-br from-slate-50 to-indigo-100 dark:from-slate-900 dark:to-indigo-900 p-4">
        <div class="max-w-md w-full">
            <UCard class="shadow-2xl bg-white/80 dark:bg-slate-900/90 backdrop-blur-sm">
                <template #header>
                    <div class="text-center">
                        <div
                            class="w-12 h-12 bg-gradient-to-r from-blue-500 to-indigo-600 rounded-full mx-auto mb-2 flex items-center justify-center">
                            <svg class="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
                            </svg>
                        </div>
                        <h2
                            class="text-3xl font-light tracking-tight bg-gradient-to-r from-gray-900 to-gray-600 dark:from-white dark:to-gray-300 bg-clip-text text-transparent">
                            {{ $t('pages.login.title') }}
                        </h2>
                        <p class="text-gray-600 dark:text-gray-300 text-sm font-light mt-2">{{
                            $t('pages.login.subHeader') }}
                        </p>
                    </div>
                </template>

                <div class="px-6 pb-6">
                    <form class="space-y-5" @submit.prevent="handleUsernameAndPasswordLogin">
                        <div v-if="error"
                            class="bg-red-50 dark:bg-red-900/50 border border-red-200 dark:border-red-800 text-red-700 dark:text-red-300 px-4 py-3 rounded-lg text-sm">
                            {{ error }}
                        </div>

                        <div class="space-y-4">
                            <div>
                                <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                                    {{ $t('pages.login.emailInput.label') }}
                                </label>
                                <UInput v-model="email" class="w-full" size="lg" type="email"
                                    :placeholder="$t('pages.login.emailInput.placeholder')" :disabled="loading"
                                    required />
                            </div>

                            <div>
                                <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                                    {{ $t('pages.login.passwordInput.label') }}
                                </label>
                                <UInput v-model="password" class="w-full" size="lg" type="password"
                                    :placeholder="$t('pages.login.passwordInput.placeholder')" :disabled="loading"
                                    required />
                            </div>
                        </div>

                        <UButton type="submit" size="lg" block :loading="loading" :disabled="!email || !password"
                            class="bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700 shadow-lg hover:shadow-xl transition-all">
                            {{ $t('pages.login.signInButton.label') }}
                        </UButton>

                        <div class="flex items-center my-6">
                            <div class="flex-grow border-t border-gray-200 dark:border-gray-700" />
                            <span
                                class="mx-4 text-xs text-gray-500 dark:text-gray-400 uppercase font-light tracking-wider">
                                {{ $t('pages.login.orContinueWith') }}
                            </span>
                            <div class="flex-grow border-t border-gray-200 dark:border-gray-700" />
                        </div>

                        <GoogleSignInButton :loading="loading" :handle-google-login="handleGoogleLogin" />
                    </form>
                </div>
            </UCard>
        </div>
    </div>
</template>
