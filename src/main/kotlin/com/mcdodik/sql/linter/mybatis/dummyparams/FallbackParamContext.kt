package com.mcdodik.sql.linter.mybatis.dummyparams

class FallbackParamContext : Map<String, Any?> {
    override val entries: Set<Map.Entry<String, Any?>> get() = emptySet()
    override val keys: Set<String> get() = emptySet()
    override val values: Collection<Any?> get() = emptyList()
    override val size: Int get() = 0
    override fun isEmpty() = true
    override fun containsKey(key: String) = true
    override fun containsValue(value: Any?) = false
    override fun get(key: String): Any? = UniversalOgnlValue()
}
