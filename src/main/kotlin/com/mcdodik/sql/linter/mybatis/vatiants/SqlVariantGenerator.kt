package com.mcdodik.sql.linter.mybatis.vatiants

import com.mcdodik.sql.linter.methods.SqlVariant
import com.mcdodik.sql.linter.printer.Printer
import java.io.File
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory
import org.apache.ibatis.mapping.MappedStatement
import org.w3c.dom.Document
import org.w3c.dom.Element


object SqlVariantGenerator {

    fun generateFromMappedStatement(
        ms: MappedStatement,
        resourcePath: String
    ): List<SqlVariant> {
        val shortId = ms.id.substringAfterLast('.')
        val xmlDoc = parseXml(resourcePath, File(ms.resource).inputStream())
            ?: return listOf(SqlVariant("<unresolved>", emptyMap()))

        val rootNode = findSqlNodeById(xmlDoc, shortId)
            ?: return listOf(SqlVariant("<not found: $shortId>", emptyMap()))

        val logicTree = SqlLogicParser().parse(rootNode)
        return SqlVariantFlattener().flatten(logicTree)
    }

    private fun parseXml(path: String, stream: InputStream?): Document? {
        return try {
            val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            builder.parse(stream)
        } catch (ex: Exception) {
            Printer.pprintln("XML parse error: $path — ${ex.message}", Printer.LogLevel.ERROR)
            null
        }
    }

    private fun findSqlNodeById(doc: Document, id: String): Element? {
        val tags = listOf("select", "insert", "update", "delete")
        for (tag in tags) {
            val nodes = doc.getElementsByTagName(tag)
            for (i in 0 until nodes.length) {
                val el = nodes.item(i) as? Element ?: continue
                if (el.getAttribute("id") == id) {
                    return el
                }
            }
        }
        return null
    }
}
