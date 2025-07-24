package com.mcdodik.sql.linter.extractor

import com.mcdodik.sql.linter.methods.SqlMethodInfo
import com.mcdodik.sql.linter.methods.SqlParameter
import io.github.detekt.psi.fileName
import java.io.InputStream
import java.text.ParseException
import java.util.concurrent.ConcurrentHashMap
import javax.xml.parsers.DocumentBuilderFactory
import org.jetbrains.kotlin.psi.KtFile
import org.w3c.dom.NodeList

object SqlExtractor {

    private val parsedStatements: MutableMap<String, List<SqlMethodInfo>> = ConcurrentHashMap()

    fun extractSqlForKtFile(ktFile: KtFile): List<SqlMethodInfo> {
        val packagePath = ktFile.packageFqNameByTree.asString().replace('.', '/')
        val fileBaseName = ktFile.fileName.removeSuffix(".kt")
        val resourcePath = "$packagePath/$fileBaseName.xml"

        Printer.pprintln("Searching XML: $resourcePath")

        val inputStream: InputStream = XmlFinderAggregator.findXmlByPackageName(resourcePath)
            ?: return emptyList()

        return parsedStatements.getOrPut(resourcePath) {
            parseMappedStatements(resourcePath, inputStream)
        }
    }

    private fun parseMappedStatements(resourcePath: String, inputStream: InputStream): List<SqlMethodInfo> {
        val configuration = org.apache.ibatis.session.Configuration()
        val mapperBuilder = org.apache.ibatis.builder.xml.XMLMapperBuilder(
            inputStream,
            configuration,
            resourcePath,
            configuration.sqlFragments
        )
        mapperBuilder.parse()

        return configuration.mappedStatements
            .asSequence()
            .filterIsInstance<org.apache.ibatis.mapping.MappedStatement>()
            .map { ms ->
                val boundSql = ms.getBoundSql(emptyMap<String, Any>())
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
