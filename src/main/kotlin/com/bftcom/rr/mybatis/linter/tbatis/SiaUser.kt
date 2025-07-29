package com.bftcom.rr.mybatis.linter.tbatis

import java.time.LocalDate

data class SiaUser(
    // Логин пользователя
    val login: String,
    // УНСИ код (например 40.054)
    val unsiEstoCode: String,
    // СИА код (например 4_0658)
    val siaEstoCode: String? = null,
    // Регион
    val region: String? = null,
    // Отдел
    val department: String? = null,
    // Должность
    val position: String? = null,
    // Статус
    val status: String? = null,
    // Отчество
    val lastName: String? = null,
    // Имя
    val firstName: String? = null,
    // Фамилия
    val secondName: String? = null,
    // Пол
    val gender: String? = null,
    // Адрес электронной почты
    val email: String? = null,
    // Действительно до
    val validUntil: LocalDate? = null,
    // Дата блокировки
    val blockingTime: LocalDate? = null,
    // имя офиса ЕСТО
    val estoName: String? = null,
    // признак системной роли
    val systemRole: Boolean = false,

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SiaUser) return false

        return login == other.login
    }

    override fun hashCode(): Int {
        return login.hashCode()
    }
}
