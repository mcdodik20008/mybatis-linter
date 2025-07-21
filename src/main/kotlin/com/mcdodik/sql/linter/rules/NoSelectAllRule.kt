package com.mcdodik.sql.linter.rules

import com.mcdodik.sql.linter.extractor.SqlExtractor
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.api.CodeSmell
import org.jetbrains.kotlin.psi.KtFile

class NoSelectAllRule(
    config: Config
) : Rule(config) {

    override val issue = Issue(
        id = "NoSelectAll",
        severity = Severity.CodeSmell,
        description = "Avoid using SELECT * in SQL queries",
        debt = Debt.TWENTY_MINS
    )

    override fun visitKtFile(file: KtFile) {
        val sqlBlocks = SqlExtractor.extractSqlForKtFile(file)

        sqlBlocks.forEach { (xmlFile, sql) ->
            Regex("""(?i)\bselect\s+\*""").findAll(sql).forEach { match ->
                report(
                    CodeSmell(
                        issue,
                        Entity.from(file, match.range.first),
                        message = "Avoid SELECT * in $xmlFile"
                    )
                )
            }
        }
    }
}
