import com.mcdodik.sql.linter.extractor.XmlFinder

fun main() {
    val resource = XmlFinder.findXmlByPackageName("com/mcdodik/Test.xml")
    if (resource == null){
        println("Ничего не нашел")
        return
    }
}
