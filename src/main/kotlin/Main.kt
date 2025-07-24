import com.mcdodik.sql.linter.extractor.FilesystemXmlFinder

fun main() {
    Printer.logIsOn = false
    Printer.logLevel = Printer.LogLevel.DEBUG
    val resource = FilesystemXmlFinder.findXmlByPackageName("com/mcdodik/Test.xml")
    if (resource == null) {
        Printer.pprintln("Ничего не нашел")
        return
    }
}


object Printer {
    var logIsOn = false
    var logLevel = LogLevel.INFO

    fun pprintln(value: Any? /*logLevel: LogLevel = LogLevel.INFO*/) {
        if (logIsOn) {
            println(value)
        }
    }

    enum class LogLevel(val weigth: Int) {
        INFO(0),
        DEBUG(1),
        WARM(-1),
        ERROR(-2)
    }
}
