import io.gitlab.arturbosch.detekt.Detekt
import kotlin.properties.ReadOnlyProperty

plugins {
    base
    `java-library`
    id(Plugins.maven_publish)
    id(Plugins.java_gradle_plugin)
    kotlin(Plugins.jvm) version "1.7.10"

    id(Plugins.detekt_plugin) version Vers.detekt_plugin
    id(Plugins.release_plugin_id) version Vers.release_plugin
}

apply {
    plugin(Plugins.idea)
}

group = ProjectGroup

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
    implementation(Libs.mybatis)

    implementation(Libs.detekt)

    // Для тестов
    testImplementation(Libs.detekt_test)
    testImplementation(Libs.kotest_core)
    testImplementation(Libs.kotest_junit)
    testImplementation(Libs.kotest_jupiter)
    testRuntimeOnly(Libs.kotest_jupiter_engie)
}

java {
    withSourcesJar()
}

tasks.jar {
    manifest {
        attributes["Plugin-Class"] = "com.bftcom.rr.mybatis.linter.SqlRuleSetProvider"
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
