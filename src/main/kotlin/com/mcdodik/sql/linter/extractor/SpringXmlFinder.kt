package com.mcdodik.sql.linter.extractor

import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import javax.xml.stream.XMLStreamException

class SpringXmlFinder : AbstractXmlFinder() {

    override val logPrefix = "[Spring]"

    override fun preloadFromSource() {
        println("$logPrefix Preloading XML from Spring ResourceResolver...")
        val resolver = PathMatchingResourcePatternResolver()

        try {
            val resources = resolver.getResources("classpath*:com/**/*.xml")
            for (resource in resources) {
                println("$logPrefix addResourceToCache ${resource.uri}")
                addResourceToCache(resource.uri.toString())
            }
        } catch (e: XMLStreamException) {
            println("$logPrefix Error during Spring preload: ${e.message}")
        }
    }
}
