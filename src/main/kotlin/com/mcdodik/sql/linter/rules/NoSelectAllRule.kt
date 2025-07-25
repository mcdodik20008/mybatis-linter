package com.mcdodik.sql.linter.rules

import com.mcdodik.sql.linter.methods.SqlMethodInfo
import com.mcdodik.sql.linter.printer.Printer
import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtNamedFunction

class NoSelectAllRule(config: Config) : SqlRule(config) {

    override val issue = Issue(
        id = "NoSelectAll",
        severity = Severity.CodeSmell,
        description = "Avoid using SELECT * in SQL queries",
        debt = Debt.TWENTY_MINS
    )

    override fun check(function: KtNamedFunction, sqlInfo: SqlMethodInfo) {
        val selectAllRegex = Regex("""(?i)\bselect\s+\*""")

        selectAllRegex.findAll(sqlInfo.sql).forEach { match ->
            Printer.pprintln("SELECT * detected in `${sqlInfo.id}`:\n" +
                    "${sqlInfo.sql.trim().take(TAKE_SQL_FOR_PRINTER)}...")

            val packageName = function.containingKtFile.packageFqName
            val className = packageName.asString() + "." +
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

    companion object {
        private const val TAKE_SQL_FOR_PRINTER = 100
    }
}
