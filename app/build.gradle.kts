import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// Define versions for clarity

plugins {
    // Apply the Spring Boot plugin
    id("org.springframework.boot") version "3.3.5"
    // Apply the Spring Dependency Management plugin
    id("io.spring.dependency-management") version "1.1.7"
    // Apply the Kotlin JVM and Spring plugins
    kotlin("jvm")
    kotlin("plugin.spring") version "2.2.20"
}

group = "com.dealtech" // Your project's group
java.sourceCompatibility = JavaVersion.VERSION_21 // Spring Boot 3+ requires Java 17+

repositories {
    mavenCentral()
}

// This is the core of dependency management for Spring Boot
dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        mavenBom("com.google.cloud:libraries-bom:26.32.0")

    }
}

dependencies {
    // Core Spring Boot dependency for web applications (REST APIs, etc.)
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Jackson module for proper Kotlin JSON serialization/deserialization
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Standard Kotlin libraries
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Google Cloud Storage Client
    implementation("com.google.cloud:google-cloud-storage")

    // Dependencies for testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

}

// Configure the Kotlin compiler for Spring
kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// This task is provided by the Spring Boot plugin to create an executable JAR
tasks.getByName<BootJar>("bootJar") {
    mainClass.set("com.dealtech.IntA.MainKt") // IMPORTANT: Set this to your main class file
}