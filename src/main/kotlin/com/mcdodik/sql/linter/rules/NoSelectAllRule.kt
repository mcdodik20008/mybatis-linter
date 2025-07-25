package com.mcdodik.sql.linter.rules

import com.mcdodik.sql.linter.methods.SqlMethodInfo
import com.mcdodik.sql.linter.mybatis.MyBatisSqlLoader
import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedFunction

class NoSelectAllRule(config: Config) : Rule(config) {

    override val issue = Issue(
        id = "NoSelectAll",
        severity = Severity.CodeSmell,
        description = "Avoid using SELECT * in SQL queries",
        debt = Debt.TWENTY_MINS
    )

    override fun visitKtFile(file: KtFile) {
        val sqlByMethodName = MyBatisSqlLoader.loadSql(file)

        if (sqlByMethodName.isEmpty()) {
            println("⚠ No MyBatis SQL methods found. Skipping: ${file.name}")
            return
        }

        file.declarations
            .filterIsInstance<KtClassOrObject>() // интерфейсы и классы
            .flatMap { it.body?.declarations.orEmpty() } // методы внутри тела
            .filterIsInstance<KtNamedFunction>() // только функции
            .forEach { function ->
                val methodName = function.name ?: return@forEach
                val sqlInfo = sqlByMethodName[methodName] ?: return@forEach
                println("🔍 Matched method: $methodName → SQL: ${sqlInfo.sql.trim().take(80)}...")
                checkSelectAll(file, function, sqlInfo)
            }
    }

    private fun checkSelectAll(file: KtFile, function: KtNamedFunction, sqlInfo: SqlMethodInfo) {
        val selectAllRegex = Regex("""(?i)\bselect\s+\*""")

        selectAllRegex.findAll(sqlInfo.sql).forEach { match ->
            println("SELECT * detected in `${sqlInfo.id}`:\n${sqlInfo.sql.trim().take(100)}...")

            val className = function.containingKtFile.packageFqName.asString() + "." +
                    (function.parent as? KtClassOrObject)?.name.orEmpty()
            val methodName = function.name ?: "<unknown>"

            report(
                CodeSmell(
                    issue = issue,
                    entity = Entity.from(function, match.range.first),
                    message = "Avoid SELECT * in `$className.$methodName` (SQL id: `${sqlInfo.id}`)"
                )
            )
        }
    }
}