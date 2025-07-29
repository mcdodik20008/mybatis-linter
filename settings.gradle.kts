pluginManagement {
    if (System.getenv("nexusUrl") != null) {
        repositories {
            mavenLocal()
            maven(url = uri(System.getenv("nexusUrl")))
        }
    } else {
        repositories {
            mavenLocal()
//            maven(url = uri("https://plugins.gradle.org/m2/"))
            maven(url = "https://nexus.supercode.ru/repository/rosreestr-public/")
        }
    }
}

rootProject.name = "rr-mybatis-linter"
