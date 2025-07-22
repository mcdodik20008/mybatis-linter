package com.mcdodik.sql.linter.extractor

import java.io.File

class FilesystemXmlFinder : AbstractXmlFinder() {

    override val logPrefix = "[FS]"

    override fun preloadFromSource() {
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
}
