package com.bftcom.rr.mybatis.linter.rules

import com.bftcom.rr.mybatis.linter.methods.SqlMethodInfo
import com.bftcom.rr.mybatis.linter.printer.Printer
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
        val className = resolveClassName(function)
        val methodName = function.name ?: "<unknown>"

        for (variant in sqlInfo.variants) {
            SELECT_ALL_REGEX.find(variant.sql) ?: continue

            Printer.pprintln(
                buildString {
                    appendLine("SELECT * detected in `${sqlInfo.id}`:")
                    appendLine("SQL: ${variant.sql.trim().take(TAKE_SQL_FOR_PRINTER)}...")
                    if (variant.conditions.isNotEmpty()) {
                        appendLine("Conditions: ${variant.conditions.entries.joinToString()}")
                    }
                }
            )

            report(
                CodeSmell(
                    issue = issue,
                    entity = Entity.atName(function), // можно позже привязать к SQL range
                    message = "Avoid SELECT * in `$className.$methodName` under conditions: ${variant.conditions}"
                )
            )
        }
    }

    private fun resolveClassName(function: KtNamedFunction): String {
        val packageName = function.containingKtFile.packageFqName.asString()
        val className = (function.parent as? KtClassOrObject)?.name.orEmpty()
        return "$packageName.$className"
    }

    companion object {
        private val SELECT_ALL_REGEX = Regex("""(?i)\bselect\s+\*""")
        private const val TAKE_SQL_FOR_PRINTER = 100
    }
}
