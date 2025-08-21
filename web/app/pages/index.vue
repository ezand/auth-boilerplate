<script setup>
const { locale, setLocale } = useI18n()
</script>

<template>
    <UCard variant="outline">
        <h1>Index page</h1>
        <div>{{ $t('pages.index.welcome') }}</div>
        <div>Locale: {{ locale }}</div>
        <div>
            <UButton @click="setLocale('en')">English</UButton>
            <UButton @click="setLocale('nb-NO')">Norsk</UButton>
        </div>
        <div>
            <UButton to="/profile/user-profile">Profile</UButton>
        </div>
        <div>
            <UButton to="/logout">Logout</UButton>
        </div>
    </UCard>
</template>
