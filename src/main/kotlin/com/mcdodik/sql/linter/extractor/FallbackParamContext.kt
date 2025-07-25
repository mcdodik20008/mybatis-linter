package com.mcdodik.sql.linter.extractor

class FallbackParamContext(
    override val entries: Set<Map.Entry<String, Any?>> = emptySet<Map.Entry<String, Any?>>(),
    override val keys: Set<String> =  emptySet<String>(),
    override val values: Collection<Any?> = emptyList<Any?>()
) : Map<String, Any?> {
    override fun isEmpty() = true
    override fun get(key: String) = null
    override fun containsKey(key: String) = true
    override fun containsValue(value: Any?) = false
    override val size: Int = 0
}
