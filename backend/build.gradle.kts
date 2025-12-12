plugins {
	kotlin("jvm") version "2.2.21"
	kotlin("plugin.spring") version "2.2.21"
	id("org.springframework.boot") version "4.0.0"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "aoc"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

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

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.4")
    testImplementation("com.tngtech.archunit:archunit-junit5:1.3.0")
    testImplementation("io.mockk:mockk:1.13.16")
    testImplementation("io.github.hakky54:logcaptor:2.10.1")

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
