<script setup>
const { locales, locale } = useI18n()

const defaultLocale = "nb-NO"
const router = useRouter()
const route = useRoute()
const user = useCurrentUser()

onMounted(() => {
  watch(user, (user, prevUser) => {
    if (prevUser && !user) {
      // user logged out
      router.push('/login')
    } else if (user && typeof route.query.redirect === 'string') {
      // user logged in
      router.push(route.query.redirect)
    }
  })
})
</script>

<template>
  <div>
    <NuxtRouteAnnouncer />
    <UApp :locale="locales[locale] || defaultLocale">
      <NuxtPage />
    </UApp>
  </div>
</template>
