package com.mcdodik.sql.linter.methods

data class SqlMethodInfo(
    val id: String,
    val sql: String,
    val parameters: List<SqlParameter>,
    val isFallback: Boolean = false
)

