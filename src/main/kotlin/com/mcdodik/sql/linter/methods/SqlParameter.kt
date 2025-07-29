package com.mcdodik.sql.linter.methods

data class SqlParameter(
    val name: String,
    val javaType: Class<*>
)
