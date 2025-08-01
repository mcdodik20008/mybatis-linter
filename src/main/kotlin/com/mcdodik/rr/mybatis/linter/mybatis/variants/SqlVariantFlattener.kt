package com.mcdodik.rr.mybatis.linter.mybatis.variants

import com.mcdodik.rr.mybatis.linter.methods.SqlVariant
import com.mcdodik.rr.mybatis.linter.printer.Printer

object SqlVariantFlattener {

    private val MAX_VARIANTS: Int =
        System.getProperty("sqlVariantMax")?.toIntOrNull() ?: 9000
    private var variantCount = 0

    fun flatten(root: SqlNode): List<SqlVariant> {
        variantCount = 0
        return buildVariants(root, "", emptyMap())
            .map { it.copy(sql = it.sql.trim().replace(Regex("\\s+"), " ")) }
            .distinctBy { it.sql to it.conditions }
            .also {
                Printer.pprintln("Has ${it.size} variants")
            }
    }

    private fun buildVariants(
        node: SqlNode,
        prefix: String,
        conditions: Map<String, Boolean>
    ): List<SqlVariant> {
        if (variantCount >= MAX_VARIANTS) return emptyList()

        return when (node) {
            is SqlNode.Static -> {
                variantCount++
                listOf(SqlVariant("$prefix ${node.sql}".trim(), conditions))
            }

            is SqlNode.Group -> {
                node.children.fold(listOf(SqlVariant(prefix, conditions))) { acc, child ->
                    acc.flatMap { buildVariants(child, it.sql, it.conditions) }
                        .safeTake(MAX_VARIANTS - variantCount)
                }
            }

            is SqlNode.Conditional -> {
                val trueBranch = buildVariants(SqlNode.Group(node.children), prefix, conditions + (node.test to true))
                val falseBranch = listOf(SqlVariant(prefix, conditions + (node.test to false)))
                variantCount += falseBranch.size + trueBranch.size
                (trueBranch + falseBranch).safeTake(MAX_VARIANTS - variantCount)
            }

            is SqlNode.Choice -> {
                val all = mutableListOf<SqlVariant>()
                for (branch in node.whenBranches) {
                    if (variantCount >= MAX_VARIANTS) break
                    all += buildVariants(SqlNode.Group(branch.children), prefix, conditions + (branch.test to true))
                }
                if (node.otherwise != null && variantCount < MAX_VARIANTS) {
                    all += buildVariants(
                        SqlNode.Group(node.otherwise.children),
                        prefix,
                        conditions + ("<otherwise>" to true)
                    )
                }
                variantCount += all.size
                all.safeTake(MAX_VARIANTS - variantCount)
            }
        }
    }

    private fun <T> List<T>.safeTake(n: Int): List<T> = take(n.coerceAtLeast(0))
}
