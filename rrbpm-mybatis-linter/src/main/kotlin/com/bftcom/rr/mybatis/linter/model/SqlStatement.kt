package com.bftcom.rr.mybatis.linter.model

data class SqlStatement(
    val id: String,
    val type: String,
    val sql: String,
    val sourceFile: String,
    val lineNumber: Int
)