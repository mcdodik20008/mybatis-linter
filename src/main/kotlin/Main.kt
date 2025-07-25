import com.mcdodik.sql.linter.extractor.FilesystemXmlFinder
import com.mcdodik.sql.linter.printer.Printer

fun main() {
    Printer.logIsOn = false
    Printer.logLevel = Printer.LogLevel.DEBUG
    val resource = FilesystemXmlFinder.findXmlByPackageName("com/mcdodik/Test.xml")
    if (resource == null) {
        Printer.pprintln("Ничего не нашел")
        return
    }
}



