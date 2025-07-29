import io.gitlab.arturbosch.detekt.Detekt
import kotlin.properties.ReadOnlyProperty

plugins {
    `java-library`
    id("maven-publish")
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.spring") version "1.7.10"
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
    id("net.researchgate.release") version "3.0.2"
}

group = "com.mcdodik"

repositories {
    if (System.getenv("nexusUrl") != null) {
        repositories {
            mavenLocal()
            maven(url = uri(System.getenv("nexusUrl")))
        }
    } else {
        repositories {
            mavenLocal()
            maven(url = "https://nexus.supercode.ru/repository/rosreestr-public/")
        }
    }
}

dependencies {
    implementation("org.springframework:spring-core:5.3.21")

    implementation("org.mybatis:mybatis:3.5.9")
    implementation("org.mybatis:mybatis-spring:2.0.7")

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

tasks.withType<Detekt>().configureEach {
    exclude("**/dummyparams/**")
}

tasks.test {
    useJUnitPlatform()
}

/**
 * Project configuration by properties and environment
 */
fun envConfig() = ReadOnlyProperty<Any, String?> { _, property ->
    if (ext.has(property.name)) {
        ext[property.name] as? String
    } else {
        System.getenv(property.name)
    }
}

val repositoryUser by envConfig()
val repositoryPassword by envConfig()
val repositoryUrl by envConfig()

publishing {
    publications {
        //Internal repository setup
        repositories {
            maven {
                url = uri("$repositoryUrl")
                if (url.scheme.startsWith("http", true)) {
                    credentials {
                        username = "$repositoryUser"
                        password = "$repositoryPassword"
                    }
                }
            }
        }

        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

release {
    if (System.getenv("pushReleaseVersionBranch") != null) {
        git {
            requireBranch.set("develop")
            pushReleaseVersionBranch.set(System.getenv("pushReleaseVersionBranch"))
        }
    } else {
        git {
            requireBranch.set("develop")
        }
    }
}
