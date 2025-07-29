import com.bftcom.rr.mybatis.linter.mybatis.MyBatisSqlLoader
import com.bftcom.rr.mybatis.linter.printer.Printer
import java.io.File
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.environment.setIdeaIoUseFallback
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer
import org.jetbrains.kotlin.config.CommonConfigurationKeys
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.psi.KtPsiFactory

fun main() {
    Printer.logIsOn = true
    // Нужно для запуска вне IDEA
    setIdeaIoUseFallback()

    val disposable = Disposer.newDisposable()

    val configuration = CompilerConfiguration().apply {
        put(CommonConfigurationKeys.MODULE_NAME, "my-linter")
        // Чтобы видеть классы JDK
        val javaHome = File(System.getProperty("java.home"))
        val jdkClasses = javaHome.walkTopDown()
            .filter { it.extension == "jar" && it.name.contains("rt") || it.name.contains("classes") }
            .toList()
        jdkClasses.forEach { add(CLIConfigurationKeys.CONTENT_ROOTS, JvmClasspathRoot(it)) }
    }

    val environment = KotlinCoreEnvironment.createForProduction(
        disposable,
        configuration,
        EnvironmentConfigFiles.JVM_CONFIG_FILES
    )

    val project = environment.project

    val file = File("src/main/kotlin/com/bftcom/rr/mybatis/linter/tbatis/FindUsers.kt")
    val text = file.readText()
    val ktFile = KtPsiFactory(project).createFile(file.name, text)

    println("KtFile created: ${ktFile.name}")

    MyBatisSqlLoader.loadSql(ktFile).forEach {
        it.value.variants.forEach {
            println(it.sql)
        }
    }
}
