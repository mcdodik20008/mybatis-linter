package com.bftcom.rr.mybatis.linter.printer

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
        WARN(-1),
        ERROR(-1)
    }
}
