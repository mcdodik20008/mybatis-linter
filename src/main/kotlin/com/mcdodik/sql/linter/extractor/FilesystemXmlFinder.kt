package com.mcdodik.sql.linter.extractor

import Printer
import java.io.File
import java.io.InputStream
import java.util.concurrent.ConcurrentHashMap

object FilesystemXmlFinder {

    val logPrefix = "[FS]"
    val cache: MutableMap<String, InputStream?> = ConcurrentHashMap()
    var initialized = false
    val allowedRoots = listOf(
        "com/bftcom/",
        "com/mcdodik"
    )

    // Шаблонный метод для поиска XML файла
    fun findXmlByPackageName(resourcePath: String): InputStream? {
        if (!initialized) {
            preloadResources()
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

    // Шаблонный метод для загрузки ресурсов
    private fun preloadResources() {
        Printer.pprintln("$logPrefix Preloading resources...")
        // Шаги предварительной загрузки: они могут быть разными в зависимости от реализации
        preloadFromSource()
        Printer.pprintln("$logPrefix XML preload complete. Total cached: ${cache.size}")
    }

    // Абстрактный метод, который должен быть реализован в конкретных классах
    private fun preloadFromSource(){
        Printer.pprintln("$logPrefix Preloading XML from FS")
        val rootDir = File(System.getProperty("user.dir"))
        if (!rootDir.exists()) {
            Printer.pprintln("$logPrefix ‼Resource directory not found: ${rootDir.absolutePath}")
            return
        }

        rootDir.walkTopDown()
            .filter { it.extension == "xml" }
            .forEach { file ->
                val relativePath = file.relativeTo(rootDir).invariantSeparatorsPath
                addResourceToCache(relativePath)  // Добавляем файл в кэш
            }
    }

    // Общая логика добавления ресурса в кэш
    private fun addResourceToCache(uri: String) {
        val unifiedUri = uri.replace("\\", "/")
        val rootPath = allowedRoots.firstOrNull { root -> unifiedUri.contains(root) }
        if (rootPath == null) {
            Printer.pprintln("$logPrefix unifiedUri $unifiedUri не содержит allowRootPath $allowedRoots")
            return
        }
        Printer.pprintln("$logPrefix rootPath: $rootPath exists in unifiedUri: $unifiedUri")

        val idx = uri.indexOf(rootPath)
        if (idx == -1) {
            return
        }

        val path = unifiedUri.substring(idx)
        if (path !in cache) {
            try {
                cache[path] = File(unifiedUri).inputStream()
                Printer.pprintln("$logPrefix Cached: $path")
            } catch (e: InternalError) {
                Printer.pprintln("$logPrefix Error for $path: ${e.message}")
            }
        } else {
            Printer.pprintln("$logPrefix Duplicate skipped: $path")
        }
    }
}
