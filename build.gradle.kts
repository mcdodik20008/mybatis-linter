plugins {
    `java-library`
    id("maven-publish")
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.spring") version "1.7.10"
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
}

group = "com.mcdodik"
version = "1.0.3.21"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-core:5.3.21")

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("io.gitlab.arturbosch.detekt:detekt-api:1.22.0")
    testImplementation("io.gitlab.arturbosch.detekt:detekt-test:1.22.0")

    // Для тестов
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

java {
    withSourcesJar()
}

tasks.jar {
    manifest {
        attributes["Plugin-Class"] = "com.mcdodik.sql.linter.SqlRuleSetProvider"
    }
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
