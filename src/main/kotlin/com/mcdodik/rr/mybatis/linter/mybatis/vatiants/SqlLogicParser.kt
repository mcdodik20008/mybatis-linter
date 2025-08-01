package com.mcdodik.rr.mybatis.linter.mybatis.vatiants

import org.w3c.dom.Element
import org.w3c.dom.Node


object SqlLogicParser {
    fun parse(node: Node): SqlNode = when (node.nodeType) {
        Node.TEXT_NODE -> {
            val text = node.textContent.trim()
            if (text.isBlank()) SqlNode.Static() else SqlNode.Static(text)
        }

        Node.ELEMENT_NODE -> {
            val el = node as Element
            when (el.tagName) {
                "if" -> {
                    val test = el.getAttribute("test")
                    SqlNode.Conditional(
                        test,
                        parseChildren(el)
                    )
                }

                "choose" -> {
                    val branches = mutableListOf<SqlNode.Branch>()
                    val whenList = el.getElementsByTagName("when")
                    for (i in 0 until whenList.length) {
                        val whenEl = whenList.item(i) as Element
                        val test = whenEl.getAttribute("test")
                        branches += SqlNode.Branch(test, parseChildren(whenEl))
                    }
                    val otherwise = el.getElementsByTagName("otherwise").item(0)
                    val otherwiseBranch = otherwise?.let { SqlNode.Branch("<otherwise>", parseChildren(it)) }
                    SqlNode.Choice(branches, otherwiseBranch)
                }

                "foreach" -> SqlNode.Static("/* foreach skipped */")

                else -> SqlNode.Group(parseChildren(el))
            }
        }

        else -> SqlNode.Static("")
    }

    private fun parseChildren(parent: Node): List<SqlNode> {
        val list = mutableListOf<SqlNode>()
        val children = parent.childNodes
        for (i in 0 until children.length) {
            val child = children.item(i)
            val parsed = parse(child)
            if (parsed !is SqlNode.Static || parsed.sql.isNotBlank()) {
                list += parsed
            }
        }
        return list
    }
}
