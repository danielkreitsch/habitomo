import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "3.2.0"
  id("io.spring.dependency-management") version "1.1.4"
  kotlin("jvm") version "1.9.20"
  kotlin("plugin.spring") version "1.9.20"
  kotlin("plugin.jpa") version "1.9.20"
  id("maven-publish")
  id("com.diffplug.spotless") version "6.8.0"
}

group = "com.danielkreitsch"

version = "0.0.1-SNAPSHOT"

java { sourceCompatibility = JavaVersion.VERSION_17 }

repositories { mavenCentral() }

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
  implementation("org.jraf:klibnotion-jvm:1.10.0")
  implementation("com.google.code.gson:gson:2.9.1")
  runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.security:spring-security-test")
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs += "-Xjsr305=strict"
      jvmTarget = "17"
    }
  }
  withType<Test> { useJUnitPlatform() }
  jar { enabled = false }
  named("build") { finalizedBy("copyFiles") }
  register<Copy>("copyFiles") {
    from("Dockerfile")
    from("build/libs")
    into("../../dist/apps/backend")
  }
}

tasks.withType<Test> { useJUnitPlatform() }

springBoot {
  buildInfo()
  mainClass = "com.danielkreitsch.habitatomo.backend.ApplicationKt"
}

publishing {
  publications { create<MavenPublication>("mavenJava") { artifact(tasks.getByName("bootJar")) } }
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
  format("misc") {
    target("*.gradle.kts", "*.md", ".gitignore")
    trimTrailingWhitespace()
    indentWithSpaces()
    endWithNewline()
  }
  kotlin { ktfmt() }
  kotlinGradle {
    target("*.gradle.kts")
    ktfmt()
  }
}
