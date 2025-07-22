package com.mcdodik.sql.linter.extractor

import java.io.File
import java.io.InputStream
import java.util.concurrent.ConcurrentHashMap

abstract class AbstractXmlFinder {

    protected open val logPrefix = "[]"
    protected val cache: MutableMap<String, InputStream?> = ConcurrentHashMap()
    protected var initialized = false
    protected val allowedRoots = listOf(
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
    protected abstract fun preloadFromSource()

    // Общая логика добавления ресурса в кэш
    protected fun addResourceToCache(uri: String) {
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
