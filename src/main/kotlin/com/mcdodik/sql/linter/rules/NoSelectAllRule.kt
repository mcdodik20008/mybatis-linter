package com.mcdodik.sql.linter.rules

import com.mcdodik.sql.linter.mybatis.MyBatisSqlLoader
import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
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
        val sqlBlocks = MyBatisSqlLoader.extractSqlForKtFile(file)
        if (sqlBlocks.isEmpty()){
            return
        }

        for (info in sqlBlocks) {
            if (info.sql.isNotBlank()){
                println(info.sql)
            }
        }

        sqlBlocks.forEach { (xmlFile, sql) ->
            Regex("""(?i)\bselect\s+\*""").findAll(sql).forEach { match ->
                println("boolshitSql: $sql")
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
