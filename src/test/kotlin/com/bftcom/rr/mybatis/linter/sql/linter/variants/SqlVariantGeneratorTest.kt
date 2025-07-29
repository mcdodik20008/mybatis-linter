package com.bftcom.rr.mybatis.linter.sql.linter.variants

import com.mcdodik.sql.linter.mybatis.vatiants.SqlVariantGenerator
import java.io.File
import org.apache.ibatis.builder.xml.XMLMapperBuilder
import org.apache.ibatis.mapping.MappedStatement
import org.apache.ibatis.session.Configuration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SqlVariantGeneratorTest {

    @Test
    fun test() {
        // given
        val resourcePath = "/com/mcdodik/zbatis/FindUsers.xml"
        val resourceUrl = javaClass.getResource(resourcePath)
        requireNotNull(resourceUrl) { "Could not find test resource: $resourcePath" }

        val inputStream = resourceUrl.openStream()
        val configuration = Configuration()
        val builder = XMLMapperBuilder(inputStream, configuration, resourceUrl.path, configuration.sqlFragments)
        builder.parse()

        val ms = configuration.mappedStatements
            .first { it.id.endsWith("findUsers") } as MappedStatement

        val variants = SqlVariantGenerator.generateFromMappedStatement(ms, File(resourceUrl.path))

        assertEquals(8, variants.size)
    }

    @Test
    fun test2() {
        val resourcePath = "/com/mcdodik/zbatis/FindUsers.xml"
        val resourceUrl = javaClass.getResource(resourcePath)
        requireNotNull(resourceUrl) { "Could not find test resource: $resourcePath" }

        val inputStream = resourceUrl.openStream()
        val configuration = Configuration()
        val builder = XMLMapperBuilder(inputStream, configuration, resourceUrl.path, configuration.sqlFragments)
        builder.parse()

        val ms = configuration.mappedStatements
            .first { it.id.endsWith("findEsto") } as MappedStatement

        val variants = SqlVariantGenerator.generateFromMappedStatement(ms, File(resourceUrl.path))

        assertEquals(128, variants.size)
    }
}

