package com.bftcom.rr.mybatis.linter.mybatis.vatiants

import com.bftcom.rr.mybatis.linter.methods.SqlVariant
import com.bftcom.rr.mybatis.linter.printer.Printer
import java.io.File
import javax.management.modelmbean.XMLParseException
import javax.xml.parsers.DocumentBuilderFactory
import org.apache.ibatis.mapping.MappedStatement
import org.w3c.dom.Document
import org.w3c.dom.Element


object SqlVariantGenerator {

    fun generateFromMappedStatement(
        ms: MappedStatement,
        file: File
    ): List<SqlVariant> {
        val shortId = ms.id.substringAfterLast('.')
        val xmlDoc = parseXml(file)
        val rootNode = findSqlNodeById(xmlDoc, shortId)

        if (rootNode == null) {
            return listOf(SqlVariant("<not found: $shortId>", emptyMap()))
        }

        val logicTree = SqlLogicParser.parse(rootNode)
        return SqlVariantFlattener.flatten(logicTree)
    }

    private fun parseXml(file: File?): Document? {
        return try {
            val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            builder.parse(file?.inputStream())
        } catch (ex: XMLParseException) {
            Printer.pprintln(
                "XML parse error: ${file?.absolutePath ?: "filePath is null"} — ${ex.message}",
                Printer.LogLevel.ERROR
            )
            null
        }
    }

    private fun findSqlNodeById(doc: Document?, id: String): Element? {
        val tags = if (doc != null) {
            listOf("select", "insert", "update", "delete")
        } else {
            emptyList()
        }
        for (tag in tags) {
            val nodes = doc!!.getElementsByTagName(tag)
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
