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

plugins {
    `kotlin-dsl`
}
