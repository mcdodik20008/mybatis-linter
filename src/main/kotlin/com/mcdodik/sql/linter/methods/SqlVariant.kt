package com.mcdodik.sql.linter.methods

data class SqlVariant(
    val sql: String,
    val conditions: Map<String, Boolean>
)
