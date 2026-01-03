import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"

    id("org.springframework.boot") version "4.0.0"
    id("io.spring.dependency-management") version "1.1.7"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring boot
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:3.0.0")

    // Spring Security
    val springSecurityVersion = "6.4.2"
    implementation("org.springframework.security:spring-security-core:$springSecurityVersion")
    implementation("org.springframework.security:spring-security-web:$springSecurityVersion")
    implementation("org.springframework.security:spring-security-config:$springSecurityVersion")

    // Testing
    // Spring boot testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("io.github.hakky54:logcaptor:2.10.1")

    // Kotlin
    testImplementation("io.mockk:mockk:1.13.16")

    val kotestVersion: String by project
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-json-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val copySourceFiles by tasks.registering(Copy::class) {
    val resourcesTarget = layout.projectDirectory.dir("src/main/resources/sourceFiles")

    from("src/main/kotlin/aoc/backend/service/year") {
        include("_*/**/*.kt")
        into("kotlin")
    }

    val goYearDir = projectDir.resolve("../go-aoc/year")
    if (goYearDir.exists()) {
        from(goYearDir) {
            include("_*/**/*.go")
            into("go")
        }
    }

    into(resourcesTarget)
}

tasks.named("processResources") {
    dependsOn(copySourceFiles)
}

tasks.bootJar {
    exclude("**/application-secrets.*", "**/application-local.*", "**/img")
}

tasks.compileKotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

if (project.hasProperty("projVersion")) {
    project.version = project.property("projVersion") as String
} else {
    project.version = "0.0.1-SNAPSHOT"
}

tasks.wrapper {
    val versionGradle: String by project
    gradleVersion = versionGradle
}
