package com.mcdodik.sql.linter.mybatis.dummyparams

class UniversalOgnlValue : Map<String, Any?>, CharSequence {
    // OGNL-safe методы
    fun startsWith(prefix: String) = false
    fun endsWith(suffix: String) = false
    fun toLowerCase() = this
    fun toUpperCase() = this
    fun contains(value: Any?) = false
    fun intValue() = 0
    fun longValue() = 0L
    fun booleanValue() = false
    override fun toString() = ""

    // Map API (для #{param.map["x"]})
    override val entries: Set<Map.Entry<String, Any?>> get() = emptySet()
    override val keys: Set<String> get() = emptySet()
    override val values: Collection<Any?> get() = emptyList()
    override val size: Int get() = 0
    override fun isEmpty() = true
    override fun containsKey(key: String): Boolean = true
    override fun containsValue(value: Any?) = false
    override fun get(key: String): Any? = this

    // CharSequence API (для param.name[0])
    override val length: Int get() = 0
    override fun get(index: Int): Char = ' '
    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence = this
}
