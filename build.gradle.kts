import io.gitlab.arturbosch.detekt.Detekt

plugins {
    base
    `java-library`
    id(Plugins.maven_publish)
    id(Plugins.java_gradle_plugin)
    kotlin(Plugins.jvm) version "1.7.10"

    id(Plugins.detekt_plugin) version Vers.detekt_plugin
}

group = ProjectGroup

repositories {
    mavenLocal()
    mavenCentral()
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
        attributes["Plugin-Class"] = "com.mcdodik.rr.mybatis.linter.SqlRuleSetProvider"
    }
}

tasks.withType<Detekt>().configureEach {
    exclude("**/dummyparams/**")
}

tasks.test {
    useJUnitPlatform()
}
