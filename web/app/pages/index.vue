<script setup>
const { locale } = useI18n()
</script>

<template>
    <UCard variant="outline">
        <h1>Index page</h1>
        <div>{{ $t('welcome') }}</div>
        <div>Locale: {{ locale }}</div>
        <div>
            <NuxtLink :to="$switchLocalePath('en')">English</NuxtLink>
            <NuxtLink :to="$switchLocalePath('nb-NO')">Norsk</NuxtLink>
        </div>
    </UCard>
</template>
