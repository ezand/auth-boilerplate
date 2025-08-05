plugins {
    kotlin("jvm") version "2.2.0"
    id("io.ktor.plugin") version "3.2.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20-Beta2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

application {
    mainModule = "com.example.ApplicationKt"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.netty)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.serialization.kotlinx.json)

    // JWT Authentication dependency
    implementation(ktorLibs.server.auth.jwt)

    implementation("ch.qos.logback:logback-classic:1.5.18")
    implementation(ktorLibs.server.sessions)
    implementation(ktorLibs.client.core)
    implementation(ktorLibs.client.cio)
    implementation(ktorLibs.client.contentNegotiation)
    testImplementation(ktorLibs.server.testHost)
    testImplementation(kotlin("test"))

    // Exposed ORM for database access
    implementation("org.jetbrains.exposed:exposed-core:1.0.0-beta-5")
    implementation("org.jetbrains.exposed:exposed-dao:1.0.0-beta-5")
    implementation("org.jetbrains.exposed:exposed-jdbc:1.0.0-beta-5")

    // PostgreSQL JDBC driver
    implementation("org.postgresql:postgresql:42.7.7")

    // HikariCP for connection pooling
    implementation("com.zaxxer:HikariCP:7.0.0")

    // BCrypt for password hashing
    implementation("org.mindrot:jbcrypt:0.4")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(24)
}
