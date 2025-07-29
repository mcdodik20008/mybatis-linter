package com.bftcom.rr.mybatis.linter.methods

data class SqlMethodInfo(
    val id: String,
    val variants: List<SqlVariant>,
    val parameters: List<SqlParameter>,
    val isFallback: Boolean = false
)

