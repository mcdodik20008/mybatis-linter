package com.mcdodik.sql.linter.extractor

import io.github.detekt.psi.fileName
import java.io.InputStream
import java.text.ParseException
import java.util.concurrent.ConcurrentHashMap
import javax.xml.parsers.DocumentBuilderFactory
import org.jetbrains.kotlin.psi.KtFile
import org.w3c.dom.NodeList

object SqlExtractor {

    private val ktToSqlCache: MutableMap<String, List<Pair<String, String>>> = ConcurrentHashMap()

    fun extractSqlForKtFile(ktFile: KtFile): List<Pair<String, String>> {
        val packagePath = ktFile.packageFqNameByTree.asString().replace('.', '/')
        val fileBaseName = ktFile.fileName.removeSuffix(".kt")
        val resourcePath = "$packagePath/$fileBaseName.xml"

        println("Searching XML: $resourcePath")

        val inputStream: InputStream = XmlFinder.findXmlByPackageName(resourcePath)
            ?: return emptyList()

        return ktToSqlCache.getOrPut(resourcePath) {
            parseSqlBlocks(resourcePath, inputStream)
        }
    }

    private fun parseSqlBlocks(resourcePath: String, inputStream: InputStream): List<Pair<String, String>> {
        return try {
            val builderFactory = DocumentBuilderFactory.newInstance()
            val builder = builderFactory.newDocumentBuilder()
            val document = builder.parse(inputStream)

            val result = mutableListOf<Pair<String, String>>()
            val elements = listOf("select", "insert", "update", "delete")

            for (tag in elements) {
                val nodes = document.getElementsByTagName(tag)
                result.addAll(extractSqlFromNodes(nodes, resourcePath))
            }

            result
        } catch (e: ParseException) {
            println("Failed to parse XML [$resourcePath]: ${e.message}")
            emptyList()
        }
    }

    private fun extractSqlFromNodes(
        nodes: NodeList,
        resourcePath: String
    ): List<Pair<String, String>> {
        val result = mutableListOf<Pair<String, String>>()
        for (i in 0 until nodes.length) {
            val sql = nodes.item(i).textContent
            if (!sql.isNullOrBlank()) {
                val trimmed = sql.trim()
                result.add(resourcePath to trimmed)
            }
        }
        return result
    }

    fun clearCache() {
        ktToSqlCache.clear()
        XmlFinder.clearCache()
    }
}
