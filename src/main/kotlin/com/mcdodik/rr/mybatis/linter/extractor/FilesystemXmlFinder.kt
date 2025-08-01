package com.mcdodik.rr.mybatis.linter.extractor

import com.mcdodik.rr.mybatis.linter.printer.Printer
import com.mcdodik.rr.mybatis.linter.printer.Printer.LogLevel
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import javax.xml.stream.XMLStreamException

object FilesystemXmlFinder {

    private const val logPrefix = "[FS]"
    private val cache: MutableMap<String, File?> = ConcurrentHashMap()
    private val allowedRoots = listOf("com/mcdodik/", "com/mcdodik")

    private var initialized = false

    fun findXmlByPackageName(resourcePath: String): File? {
        if (!initialized) {
            preloadResources(File(System.getProperty("user.dir")))
            initialized = true
        }
        return cache[resourcePath].also {
            if (it == null) {
                Printer.pprintln("$logPrefix Not found in cache: $resourcePath")
            } else {
                Printer.pprintln("$logPrefix Found in cache: $resourcePath")
            }
        }
    }

    private fun preloadResources(rootDir: File) {
        Printer.pprintln("$logPrefix Preloading resources from: ${rootDir.absolutePath}")
        if (!rootDir.exists()) {
            Printer.pprintln("$logPrefix ‼ Directory not found: ${rootDir.absolutePath}", LogLevel.WARN)
            return
        }

        rootDir.walkTopDown()
            .filter { it.extension == "xml" && isSrcFile(it) }
            .forEach { file ->
                val relativePath = file.relativeTo(rootDir).invariantSeparatorsPath
                val unifiedPath = relativePath.replace("\\", "/")
                val root = allowedRoots.firstOrNull { unifiedPath.contains(it) } ?: return@forEach

                val idx = unifiedPath.indexOf(root)
                if (idx == -1) return@forEach

                val cacheKey = unifiedPath.substring(idx)
                if (cacheKey in cache) {
                    Printer.pprintln("$logPrefix Duplicate skipped: $cacheKey", LogLevel.WARN)
                    return@forEach
                }

                try {
                    cache[cacheKey] = file
                    Printer.pprintln("$logPrefix Cached: $cacheKey")
                } catch (e: XMLStreamException) {
                    Printer.pprintln("$logPrefix Error reading $cacheKey: ${e.message}", LogLevel.ERROR)
                }
            }

        Printer.pprintln("$logPrefix XML preload complete. Total cached: ${cache}")
    }

    fun isSrcFile(file: File): Boolean {
        val path = file.absolutePath.replace(File.separatorChar, '/')

        val excludedPaths = listOf(
            "/build/",
            "/out/",
            "/.idea/",
            "/.gradle/",
            "/generated/",
            "/tmp/",
            "/test-classes/"
        )

        val includedPaths = listOf(
            "/src/main/",
            "/src/shared/", // если у тебя есть общие модули
            "/resources/",  // для mapper.xml
        )

        val isExcluded = excludedPaths.any { it in path }
        val isIncluded = includedPaths.any { it in path }

        return !isExcluded && isIncluded
    }

}
