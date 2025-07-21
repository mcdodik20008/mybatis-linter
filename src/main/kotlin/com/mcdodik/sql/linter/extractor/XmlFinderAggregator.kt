package com.mcdodik.sql.linter.extractor

import java.io.InputStream

object XmlFinderAggregator {

    // Список всех поисковиков
    private val finders: List<AbstractXmlFinder> = listOf(
        //SpringXmlFinder(),
        FilesystemXmlFinder()
    )

    fun findXmlByPackageName(resourcePath: String): InputStream? {
        for (finder in finders) {
            val resourceStream = finder.findXmlByPackageName(resourcePath)
            if (resourceStream != null){
                return resourceStream
            }
        }
        return null
    }
}
