plugins {
    base
    `java-library`
    id(Plugins.maven_publish)
    id(Plugins.java_gradle_plugin)
    kotlin(Plugins.jvm) version "1.7.10"
}

group = ProjectGroup

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(Libs.mybatis)

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

tasks.test {
    useJUnitPlatform()
}
