import com.mcdodik.sql.linter.extractor.XmlFinderAggregator

fun main() {
    Printer.logIsOn = true
    Printer.logLevel = Printer.LogLevel.DEBUG
    val resource = XmlFinderAggregator.findXmlByPackageName("com/mcdodik/Test.xml")
    if (resource == null){
        Printer.pprintln("Ничего не нашел")
        return
    }
}


object Printer {
    var logIsOn = false
    var logLevel = LogLevel.INFO

    fun pprintln(value: Any?, /*logLevel: LogLevel = LogLevel.INFO*/){
        println(value)
    }

    enum class LogLevel(val weigth: Int){
        INFO(0),
        DEBUG(1),
        ERROR(-1)
    }
}
