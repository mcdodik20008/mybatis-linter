package com.mcdodik.sql.linter.mybatis

import com.mcdodik.sql.linter.extractor.FilesystemXmlFinder
import com.mcdodik.sql.linter.methods.SqlMethodInfo
import com.mcdodik.sql.linter.methods.SqlParameter
import com.mcdodik.sql.linter.methods.SqlVariant
import com.mcdodik.sql.linter.mybatis.dummyparams.FallbackParamContext
import com.mcdodik.sql.linter.mybatis.vatiants.SqlVariantGenerator
import com.mcdodik.sql.linter.printer.Printer
import io.github.detekt.psi.fileName
import java.io.File
import java.sql.SQLException
import java.util.concurrent.ConcurrentHashMap
import org.apache.ibatis.builder.xml.XMLMapperBuilder
import org.apache.ibatis.mapping.MappedStatement
import org.apache.ibatis.session.Configuration
import org.jetbrains.kotlin.psi.KtFile

object MyBatisSqlLoader {

    private val cache: MutableMap<String, List<SqlMethodInfo>> = ConcurrentHashMap()

    fun loadSql(ktFile: KtFile): Map<String, SqlMethodInfo> {
        val resourcePath = resolveXmlPath(ktFile)
        val mapperXmlFile = FilesystemXmlFinder.findXmlByPackageName(resourcePath)
            ?: return emptyMap()

        Printer.pprintln("FOUND XML: $resourcePath}")

        return cache.getOrPut(resourcePath) {
            parseMappedStatements(resourcePath, mapperXmlFile)
        }.associateBy { it.id.substringAfterLast('.') }
    }

    private fun resolveXmlPath(ktFile: KtFile): String {
        val packagePath = ktFile.packageFqNameByTree.asString().replace('.', '/')
        val baseName = ktFile.fileName.removeSuffix(".kt")
        return "$packagePath/$baseName.xml".also {
            Printer.pprintln("Resolve xml path: $it")
        }
    }

    private fun parseMappedStatements(resourcePath: String, file: File): List<SqlMethodInfo> {
        val configuration = Configuration()
        val builder = XMLMapperBuilder(
            file.inputStream(),
            configuration,
            resourcePath,
            configuration.sqlFragments
        )
        builder.parse()

        return configuration.mappedStatements
            .asSequence()
            .filterIsInstance<MappedStatement>()
            .distinctBy { it.id }
            .map { ms -> parseStatementSafely(ms, resourcePath, file) }
            .toList()
    }

    private fun parseStatementSafely(ms: MappedStatement, resourcePath: String, file: File): SqlMethodInfo {
        return try {
            val variants: List<SqlVariant> =
                SqlVariantGenerator.generateFromMappedStatement(ms, file)

            SqlMethodInfo(
                id = ms.id,
                variants = variants,
                parameters = ms.getBoundSql(FallbackParamContext())
                    .parameterMappings
                    .map { SqlParameter(it.property, it.javaType) },
                isFallback = false
            )
        } catch (ex: SQLException) {
            Printer.pprintln("Failed to resolve SQL for $resourcePath#${ms.id}: ${ex.message}", Printer.LogLevel.ERROR)
            SqlMethodInfo(
                id = ms.id,
                variants = listOf(SqlVariant("<unresolved>", emptyMap())),
                parameters = emptyList(),
                isFallback = true
            )
        }
    }
}
