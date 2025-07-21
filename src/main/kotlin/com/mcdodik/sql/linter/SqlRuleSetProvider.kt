package com.mcdodik.sql.linter

import com.mcdodik.sql.linter.rules.NoSelectAllRule
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class SqlRuleSetProvider : RuleSetProvider {

    override val ruleSetId: String = "sql"

    override fun instance(config: Config): RuleSet {
        return RuleSet(
            ruleSetId,
            listOf(
                NoSelectAllRule(config)
            )
        )
    }
}
