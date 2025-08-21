<script setup>
definePageMeta({
    middleware: ['auth'],
})
</script>

<template>
    <h1>{{ $t('pages.profile.user-profile.title') }}</h1>
</template>
