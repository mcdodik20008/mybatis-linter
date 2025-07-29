package com.bftcom.rr.mybatis.linter.methods

data class SqlVariant(
    val sql: String,
    val conditions: Map<String, Boolean>
)
