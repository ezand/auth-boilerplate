<script setup>
definePageMeta({
    middleware: ['auth'],
})
</script>

<template>
    <div>
        <h1>{{ $t('pages.profile.user-profile.title') }}</h1>
        <UButton to="/">Home</UButton>
    </div>
</template>
