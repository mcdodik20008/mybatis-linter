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
                println("$logPrefix Not found in cache: $resourcePath")
            } else {
                println("$logPrefix Found in cache: $resourcePath")
            }
        }
    }

    // Шаблонный метод для загрузки ресурсов
    private fun preloadResources() {
        println("$logPrefix Preloading resources...")
        // Шаги предварительной загрузки: они могут быть разными в зависимости от реализации
        preloadFromSource()
        println("$logPrefix XML preload complete. Total cached: ${cache.size}")
    }

    // Абстрактный метод, который должен быть реализован в конкретных классах
    protected abstract fun preloadFromSource()

    // Общая логика добавления ресурса в кэш
    protected fun addResourceToCache(uri: String) {
        val unifiedUri = uri.replace("\\", "/")
        val rootPath = allowedRoots.firstOrNull { root -> unifiedUri.contains(root) }
        if (rootPath == null) {
            println()
            return
        }
        println("$logPrefix rootPath: $rootPath exists in unifiedUri: $unifiedUri")

        val idx = uri.indexOf(rootPath)
        if (idx == -1) {
            return
        }

        val path = unifiedUri.substring(idx)
        if (path !in cache) {
            try {
                cache[path] = File(unifiedUri).inputStream()
                println("$logPrefix Cached: $path")
            } catch (e: Exception) {
                println("$logPrefix Error for $path: ${e.message}")
            }
        } else {
            println("$logPrefix Duplicate skipped: $path")
        }
    }
}
