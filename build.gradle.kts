import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.7.10"
	java
	kotlin("plugin.spring") version "1.7.10"
}

group = "com.ep"
version = "1.0-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_11


repositories {
	mavenCentral()
}

dependencies {
	implementation(kotlin("reflect"))
	implementation(kotlin("stdlib-jdk8"))
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation ("io.github.microutils:kotlin-logging-jvm:2.1.20")
	testImplementation("org.springframework.boot:spring-boot-starter-test:+")
	testImplementation("org.jetbrains.kotlin:kotlin-test:+")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:+")
	testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
}


tasks.test {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "11"
}


