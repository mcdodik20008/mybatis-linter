package com.mcdodik.sql.linter.extractor

import java.io.File
import java.io.InputStream
import java.util.concurrent.ConcurrentHashMap
import javax.xml.stream.XMLStreamException
import org.springframework.core.io.support.PathMatchingResourcePatternResolver

object XmlFinder {

    private val cache: MutableMap<String, InputStream?> = ConcurrentHashMap()
    private var initialized = false
    private val allowedRoots = listOf(
        "com/bftcom/",
        "com/mcdodik"
    )

    fun findXmlByPackageName(resourcePath: String): InputStream? {
        if (!initialized) {
            preloadFromSpring()
            preloadFromFilesystem()
            println("XML preload complete. Total cached: ${cache.size}")
            initialized = true
        }

        return cache[resourcePath].also {
            if (it == null) {
                println("Not found in cache: $resourcePath")
            } else {
                println("Found in cache: $resourcePath")
            }
        }
    }

    private fun preloadFromSpring() {
        println("Preloading XML from Spring ResourceResolver...")
        val resolver = PathMatchingResourcePatternResolver()

        try {
            val resources = resolver.getResources("classpath*:com/**/*.xml")
            for (resource in resources) {
                addResourceToCash(resource.uri.toString(), "[Spring]")
            }
        } catch (e: XMLStreamException) {
            println("Error during Spring preload: ${e.message}")
        }
    }

    private fun preloadFromFilesystem() {
        println("Preloading XML from File System")

        // Получаем текущую рабочую директорию
        val rootDir = File(System.getProperty("user.dir"))

        if (!rootDir.exists()) {
            println("‼Resource directory not found: ${rootDir.absolutePath}")
            return
        }

        rootDir.walkTopDown()
            .filter { it.extension == "xml" }
            .forEach { file ->
                val relativePath = file.relativeTo(rootDir).invariantSeparatorsPath
                addResourceToCash(relativePath, "[FS]")
            }
    }

    private fun addResourceToCash(uri: String, logPrefix: String = "[]") {
        val rootPath = getAllowRootPathFromUri(uri)
        if (rootPath == null) {
            return
        }

        val idx = uri.indexOf(rootPath)
        if (idx == -1) {
            return
        }

        val path = uri.substring(idx)
        if (path !in cache) {
            try {
                cache[path] = File(uri).inputStream()
                println("$logPrefix Cached: $path")
            } catch (e: Exception) {
                println("$logPrefix Error for $path: ${e.message}")
            }
        } else {
            println("$logPrefix Duplicate skipped: $path")
        }
    }

    private fun getAllowRootPathFromUri(uri: String): String? = allowedRoots.firstOrNull { root -> uri.contains(root) }

    fun clearCache() {
        cache.clear()
        initialized = false
    }
}
