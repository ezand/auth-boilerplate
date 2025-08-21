<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50">
    <div class="max-w-md w-full text-center">
      <UCard class="p-8">
        <div v-if="loading" class="space-y-4">
          <UIcon name="i-heroicons-arrow-path" class="w-8 h-8 animate-spin mx-auto text-primary-500" />
          <p class="text-gray-600">Signing you out...</p>
        </div>

        <div v-else-if="error" class="space-y-4">
          <UIcon name="i-heroicons-exclamation-triangle" class="w-8 h-8 mx-auto text-red-500" />
          <p class="text-red-600">{{ error }}</p>
          <UButton @click="handleLogout" variant="outline">
            Try Again
          </UButton>
        </div>
      </UCard>
    </div>
  </div>
</template>

<script setup lang="ts">
import { signOut } from 'firebase/auth'

definePageMeta({
  layout: false
})

const { $firebaseAuth } = useNuxtApp()
const loading = ref(true)
const error = ref('')

const handleLogout = async () => {
  loading.value = true
  error.value = ''

  try {
    await signOut($firebaseAuth)
    await navigateTo('/')
  } catch (err: unknown) {
    console.error('Logout error:', err)
    error.value = 'Failed to sign out. Please try again.'
    loading.value = false
  }
}

// Automatically sign out when page loads
onMounted(() => {
  handleLogout()
})
</script>