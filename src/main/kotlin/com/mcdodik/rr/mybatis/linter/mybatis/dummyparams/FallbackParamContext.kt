package com.mcdodik.rr.mybatis.linter.mybatis.dummyparams

import java.time.LocalDateTime

/**
 * Provides default values for OGNL expressions when resolving mapper SQL.
 */

class FallbackParamContext : Map<String, Any?> {
    override val entries: Set<Map.Entry<String, Any?>> get() = emptySet()
    override val keys: Set<String> get() = emptySet()
    override val values: Collection<Any?> get() = emptyList()
    override val size: Int get() = 0
    override fun isEmpty() = true
    override fun containsKey(key: String) = true
    override fun containsValue(value: Any?) = false
    override fun get(key: String): Any? = when (key.lowercase()) {
        "limit", "offset" -> 1
        "id", "userid" -> 1L
        "status" -> "ACTIVE"
        "date" -> LocalDateTime.now()
        else -> UniversalOgnlValue()
    }
    val date: LocalDateTime = LocalDateTime.now()
    fun now(): LocalDateTime = LocalDateTime.now()
    val status: String = "ACTIVE"
    val userId: Long = 1
}