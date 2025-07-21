import com.mcdodik.sql.linter.extractor.XmlFinderAggregator

fun main() {
    val resource = XmlFinderAggregator.findXmlByPackageName("com/mcdodik/Test.xml")
    if (resource == null){
        println("Ничего не нашел")
        return
    }
}
