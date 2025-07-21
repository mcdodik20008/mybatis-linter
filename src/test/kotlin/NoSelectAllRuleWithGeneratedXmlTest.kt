import com.mcdodik.sql.linter.extractor.SqlExtractor
import com.mcdodik.sql.linter.rules.NoSelectAllRule
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import io.github.detekt.test.utils.compileContentForTest

class NoSelectAllRuleWithGeneratedXmlTest {

    private lateinit var testXmlFile: File

    @BeforeEach
    fun setup() {
        val xmlDir = File("src/main/resources/com/example/")
        xmlDir.mkdirs()

        testXmlFile = File(xmlDir, "Test.xml")
        testXmlFile.writeText(
            """
            <mapper>
              <select id="findAll">SELECT * FROM users</select>
            </mapper>
            """.trimIndent()
        )
    }

    @Test
    fun `should detect SELECT star in generated XML`() {
        val ktFile = compileContentForTest(
            """
            package com.example
            class Test
        """.trimIndent()
        )

        val rule = NoSelectAllRule(Config.empty)

        val findings = rule.lint(ktFile)
        assertEquals(1, findings.size)
        assertEquals("Avoid SELECT * in com/example/Test.xml", findings[0].message)
    }
}
