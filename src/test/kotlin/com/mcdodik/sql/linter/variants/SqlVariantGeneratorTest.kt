package com.mcdodik.sql.linter.variants

import com.mcdodik.sql.linter.mybatis.vatiants.SqlVariantGenerator
import java.io.File
import org.apache.ibatis.builder.xml.XMLMapperBuilder
import org.apache.ibatis.mapping.MappedStatement
import org.apache.ibatis.session.Configuration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
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
            .first { it.id.endsWith("delUsers") } as MappedStatement

        // when
        val variants = SqlVariantGenerator.generateFromMappedStatement(ms, File(resourceUrl.path))

        // then
        assertEquals(8, variants.size)

        val expectedConditions = listOf(
            mapOf("login == null" to true, "date.isAfter(now())" to true, "trashBasket?.isEmpty() == true" to true),
            mapOf("login == null" to true, "date.isAfter(now())" to true, "trashBasket?.isEmpty() == true" to false),
            mapOf("login == null" to true, "date.isAfter(now())" to false, "trashBasket?.isEmpty() == true" to true),
            mapOf("login == null" to true, "date.isAfter(now())" to false, "trashBasket?.isEmpty() == true" to false),
            mapOf("login == null" to false, "date.isAfter(now())" to true, "trashBasket?.isEmpty() == true" to true),
            mapOf("login == null" to false, "date.isAfter(now())" to true, "trashBasket?.isEmpty() == true" to false),
            mapOf("login == null" to false, "date.isAfter(now())" to false, "trashBasket?.isEmpty() == true" to true),
            mapOf("login == null" to false, "date.isAfter(now())" to false, "trashBasket?.isEmpty() == true" to false),
        )

        val actualConditions = variants.map { it.conditions }

        assertTrue(actualConditions.containsAll(expectedConditions), "Some condition combinations are missing")

        variants.forEach {
            println("→ Conditions: ${it.conditions}")
            println("   SQL: ${it.sql.trim()}")
        }
    }

    @Test
    fun test2() {
        // given
        val resourcePath = "/com/mcdodik/zbatis/FindUsers.xml"
        val resourceUrl = javaClass.getResource(resourcePath)
        requireNotNull(resourceUrl) { "Could not find test resource: $resourcePath" }

        val inputStream = resourceUrl.openStream()
        val configuration = Configuration()
        val builder = XMLMapperBuilder(inputStream, configuration, resourceUrl.path, configuration.sqlFragments)
        builder.parse()

        val ms = configuration.mappedStatements
            .first { it.id.endsWith("findSiaUser") } as MappedStatement

        // when
        val variants = SqlVariantGenerator.generateFromMappedStatement(ms, File(resourceUrl.path))

        // then
        assertEquals(128, variants.size)
    }
}

