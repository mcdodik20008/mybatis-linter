package com.mcdodik.sql.linter.mybatis.vatiants

import com.mcdodik.sql.linter.methods.SqlVariant

class SqlVariantFlattener {
    fun flatten(root: SqlNode): List<SqlVariant> {
        return buildVariants(root, "", emptyMap())
            .map { it.copy(sql = it.sql.trim().replace(Regex("\\s+"), " ")) }
            .distinctBy { it.sql to it.conditions }
    }

    private fun buildVariants(
        node: SqlNode,
        prefix: String,
        conditions: Map<String, Boolean>
    ): List<SqlVariant> {
        return when (node) {
            is SqlNode.Static -> listOf(SqlVariant("$prefix ${node.sql}".trim(), conditions))

            is SqlNode.Group -> {
                node.children.fold(listOf(SqlVariant(prefix, conditions))) { acc, child ->
                    acc.flatMap { buildVariants(child, it.sql, it.conditions) }
                }
            }

            is SqlNode.Conditional -> {
                val trueBranch = buildVariants(SqlNode.Group(node.children), prefix, conditions + (node.test to true))
                val falseBranch = listOf(SqlVariant(prefix, conditions + (node.test to false)))
                trueBranch + falseBranch
            }

            is SqlNode.Choice -> {
                val all = mutableListOf<SqlVariant>()
                node.whenBranches.forEach { branch ->
                    all += buildVariants(SqlNode.Group(branch.children), prefix, conditions + (branch.test to true))
                }
                if (node.otherwise != null) {
                    all += buildVariants(
                        SqlNode.Group(node.otherwise.children),
                        prefix,
                        conditions + ("<otherwise>" to true)
                    )
                }
                all
            }
        }
    }
}
