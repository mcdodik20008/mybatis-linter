package com.mcdodik.rr.mybatis.linter.methods

data class SqlParameter(
    val name: String,
    val javaType: Class<*>
)
