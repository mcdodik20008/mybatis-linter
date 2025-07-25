package com.mcdodik.sql.linter.printer

import kotlin.math.log

object Printer {
    var logIsOn = false
    var logLevel = LogLevel.INFO

    fun pprintln(value: Any?, logLevel: LogLevel = LogLevel.INFO) {
        if (logIsOn || logLevel.weigth < 0) {
            println("[${logLevel}] $value")
        }
    }

    enum class LogLevel(val weigth: Int) {
        INFO(0),
        DEBUG(1),
        WARM(-1),
        ERROR(-1)
    }
}