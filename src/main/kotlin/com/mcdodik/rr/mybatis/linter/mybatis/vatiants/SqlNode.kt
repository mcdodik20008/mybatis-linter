package com.mcdodik.rr.mybatis.linter.mybatis.vatiants

sealed class SqlNode {
    data class Static(val sql: String = "") : SqlNode()
    data class Group(val children: List<SqlNode>) : SqlNode()
    data class Conditional(val test: String, val children: List<SqlNode>) : SqlNode()
    data class Choice(val whenBranches: List<Branch>, val otherwise: Branch?) : SqlNode()
    data class Branch(val test: String, val children: List<SqlNode>)
}
