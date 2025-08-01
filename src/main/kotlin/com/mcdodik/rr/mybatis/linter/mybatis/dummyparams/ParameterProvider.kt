package com.mcdodik.rr.mybatis.linter.mybatis.dummyparams

import java.time.LocalDateTime

object ParameterProvider {
    fun sampleValue(javaType: Class<*>?): Any? = when (javaType) {
        String::class.java -> "sample"
        Int::class.java, Integer::class.javaPrimitiveType -> 0
        Long::class.java, java.lang.Long.TYPE -> 0L
        Boolean::class.java, java.lang.Boolean.TYPE -> false
        LocalDateTime::class.java -> LocalDateTime.now()
        else -> null
    }
}