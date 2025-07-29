package com.mcdodik.sql.linter.rules

import com.mcdodik.sql.linter.methods.SqlMethodInfo
import com.mcdodik.sql.linter.mybatis.MyBatisSqlLoader
import com.mcdodik.sql.linter.printer.Printer
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Rule
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedFunction

abstract class SqlRule(config: Config)  : Rule(config) {

    override fun visitKtFile(file: KtFile) {
        val sqlByMethodName = MyBatisSqlLoader.loadSql(file)

        if (sqlByMethodName.isEmpty()) {
            Printer.pprintln("No MyBatis SQL methods found. Skipping: ${file.name}", Printer.LogLevel.WARN)
            return
        }

        file.declarations
            .filterIsInstance<KtClassOrObject>() // интерфейсы и классы
            .flatMap { it.body?.declarations.orEmpty() } // методы внутри тела
            .filterIsInstance<KtNamedFunction>() // только функции
            .forEach { function ->
                val methodName = function.name ?: return@forEach
                val sqlInfo = sqlByMethodName[methodName] ?: return@forEach
                check(function, sqlInfo)
            }
    }

    protected abstract fun check(function: KtNamedFunction, sqlInfo: SqlMethodInfo)

}
