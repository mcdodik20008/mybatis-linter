package com.mcdodik.sql.linter.mybatis

import Printer
import com.mcdodik.sql.linter.extractor.FallbackParamContext
import com.mcdodik.sql.linter.extractor.FilesystemXmlFinder
import com.mcdodik.sql.linter.methods.SqlMethodInfo
import com.mcdodik.sql.linter.methods.SqlParameter
import io.github.detekt.psi.fileName
import java.io.InputStream
import java.util.concurrent.ConcurrentHashMap
import org.apache.ibatis.builder.xml.XMLMapperBuilder
import org.apache.ibatis.mapping.MappedStatement
import org.apache.ibatis.session.Configuration
import org.jetbrains.kotlin.psi.KtFile

object MyBatisSqlLoader {

    private val parsedStatements: MutableMap<String, List<SqlMethodInfo>> = ConcurrentHashMap()

    fun extractSqlForKtFile(ktFile: KtFile): List<SqlMethodInfo> {
        val packagePath = ktFile.packageFqNameByTree.asString().replace('.', '/')
        val fileBaseName = ktFile.fileName.removeSuffix(".kt")
        val resourcePath = "$packagePath/$fileBaseName.xml"

        Printer.pprintln("Searching XML: $resourcePath")

        val inputStream: InputStream = FilesystemXmlFinder.findXmlByPackageName(resourcePath)
            ?: return emptyList()

        println("📦 FOUND XML: $resourcePath from ${inputStream::class.qualifiedName}")
        return parsedStatements.getOrPut(resourcePath) {
            parseMappedStatements(resourcePath, inputStream)
        }
    }

    private fun parseMappedStatements(resourcePath: String, inputStream: InputStream): List<SqlMethodInfo> {
        val configuration = Configuration()
        val mapperBuilder = XMLMapperBuilder(
            inputStream,
            configuration,
            resourcePath,
            configuration.sqlFragments
        )
        mapperBuilder.parse()

        return configuration.mappedStatements
            .asSequence()
            .filterIsInstance<MappedStatement>()
            .distinctBy { it.id }
            .map { ms ->
                val boundSql = try {
                    ms.getBoundSql(FallbackParamContext())
                } catch (ex: Exception) {
                    Printer.pprintln("⚠️ FallbackParamContext failed: ${ex.message}")
                    return@map SqlMethodInfo(
                        id = ms.id,
                        sql = "<unresolved>",
                        parameters = emptyList(),
                        isFallback = true
                    )
                }
                SqlMethodInfo(
                    id = ms.id,
                    sql = boundSql.sql.trim(),
                    parameters = boundSql.parameterMappings.map {
                        SqlParameter(it.property, it.javaType)
                    }
                )
            }
            .toList()

    }
}