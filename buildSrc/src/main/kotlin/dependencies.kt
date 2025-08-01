object Vers {
    const val mybatis = "3.5.9"

    const val kotest_core = "5.8.0"
    const val kotest_junit = "5.8.0"
    const val kotest_jupiter = "5.8.2"
    const val kotest_jupiter_engie = "5.8.2"
}

object Plugins {
    const val jvm = "jvm"
    const val idea = "idea"
    const val java_gradle_plugin = "org.gradle.java-gradle-plugin"
    const val maven_publish = "maven-publish"
}

object Libs {
    const val mybatis = "org.mybatis:mybatis:${Vers.mybatis}"

    const val kotest_core = "io.kotest:kotest-assertions-core:${Vers.kotest_core}"
    const val kotest_junit = "io.kotest:kotest-runner-junit5:${Vers.kotest_junit}"
    const val kotest_jupiter = "org.junit.jupiter:junit-jupiter-api:${Vers.kotest_jupiter}"
    const val kotest_jupiter_engie = "org.junit.jupiter:junit-jupiter-engine:${Vers.kotest_jupiter_engie}"
}

const val ProjectGroup = "com.mcdodik.rr.mybatis.linter"