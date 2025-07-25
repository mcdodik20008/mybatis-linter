package com.mcdodik.sql.linter.mybatis

import com.mcdodik.sql.linter.extractor.FilesystemXmlFinder
import com.mcdodik.sql.linter.methods.SqlMethodInfo
import com.mcdodik.sql.linter.methods.SqlParameter
import com.mcdodik.sql.linter.mybatis.dummyparams.FallbackParamContext
import com.mcdodik.sql.linter.printer.Printer
import io.github.detekt.psi.fileName
import java.io.InputStream
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
        val inputStream = FilesystemXmlFinder.findXmlByPackageName(resourcePath)
            ?: return emptyMap()

        Printer.pprintln("FOUND XML: $resourcePath from ${inputStream::class.qualifiedName}")

        return cache.getOrPut(resourcePath) {
            parseMappedStatements(resourcePath, inputStream)
        }.associateBy { it.id.substringAfterLast('.') }
    }

    private fun resolveXmlPath(ktFile: KtFile): String {
        val packagePath = ktFile.packageFqNameByTree.asString().replace('.', '/')
        val baseName = ktFile.fileName.removeSuffix(".kt")
        return "$packagePath/$baseName.xml".also {
            Printer.pprintln("Resolve xml path: $it")
        }
    }

    private fun parseMappedStatements(resourcePath: String, inputStream: InputStream): List<SqlMethodInfo> {
        val configuration = Configuration()
        val builder = XMLMapperBuilder(
            inputStream,
            configuration,
            resourcePath,
            configuration.sqlFragments
        )
        builder.parse()

        return configuration.mappedStatements
            .asSequence()
            .filterIsInstance<MappedStatement>()
            .distinctBy { it.id }
            .map { ms -> parseStatementSafely(ms, resourcePath) }
            .toList()
    }

    private fun parseStatementSafely(ms: MappedStatement, resourcePath: String): SqlMethodInfo {
        return try {
            val boundSql = ms.getBoundSql(FallbackParamContext())
            SqlMethodInfo(
                id = ms.id,
                sql = boundSql.sql.trim(),
                parameters = boundSql.parameterMappings.map {
                    SqlParameter(it.property, it.javaType)
                },
                isFallback = false
            )
        } catch (ex: SQLException) {
            Printer.pprintln("Failed to resolve SQL for $resourcePath#${ms.id}: ${ex.message}", Printer.LogLevel.ERROR)
            SqlMethodInfo(
                id = ms.id,
                sql = "<unresolved>",
                parameters = emptyList(),
                isFallback = true
            )
        }
    }
}
