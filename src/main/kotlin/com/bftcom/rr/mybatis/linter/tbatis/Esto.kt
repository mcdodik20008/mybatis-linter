package com.bftcom.rr.mybatis.linter.tbatis

data class Esto(
    // УНСИ код (например 40.054)
    val unsiCode: String,
    // СИА код (например 4_3810)
    val siaCode: String,
    // Код региона офиса (например 40)
    val region: String,
    // Название офиса
    val name: String,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Esto) return false

        if (unsiCode != other.unsiCode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = unsiCode.hashCode()
        return result
    }
}
