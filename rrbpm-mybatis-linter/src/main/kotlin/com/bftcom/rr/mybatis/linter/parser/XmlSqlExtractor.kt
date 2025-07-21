package com.bftcom.rr.mybatis.linter.parser

import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

object XmlSqlExtractor {
    fun extract(file: File): List<SqlStatement> {
        val doc = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()
            .parse(file)

        val root = doc.documentElement
        val sqlNodes = listOf("select", "insert", "update", "delete")
        val statements = mutableListOf<SqlStatement>()

        for (type in sqlNodes) {
            val nodeList = root.getElementsByTagName(type)
            for (i in 0 until nodeList.length) {
                val node = nodeList.item(i)
                val id = node.attributes?.getNamedItem("id")?.nodeValue ?: "unknown"
                val sql = node.textContent.trim()
                val lineNumber = (node as? org.apache.xerces.dom.DeferredElementImpl)?.getLineNumber() ?: -1

                statements.add(SqlStatement(id, type, sql, file.name, lineNumber))
            }
        }

        return statements
    }
}