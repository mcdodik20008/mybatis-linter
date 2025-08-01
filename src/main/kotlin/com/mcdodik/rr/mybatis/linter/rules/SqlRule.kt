package com.mcdodik.rr.mybatis.linter.rules

import com.mcdodik.rr.mybatis.linter.methods.SqlMethodInfo
import com.mcdodik.rr.mybatis.linter.mybatis.MyBatisSqlLoader
import com.mcdodik.rr.mybatis.linter.printer.Printer
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Rule
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedFunction

abstract class SqlRule(config: Config) : Rule(config) {

    override fun visitKtFile(file: KtFile) {
        val sqlByMethodName = MyBatisSqlLoader.loadSql(file)

        if (sqlByMethodName.isEmpty() && fileIsMapper(file.name.lowercase())) {
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

    private fun fileIsMapper(fileName: String): Boolean =
        fileName.contains("mapper") && !fileName.contains("test")


    protected abstract fun check(function: KtNamedFunction, sqlInfo: SqlMethodInfo)

}
