package com.mcdodik.sql.linter.preparestatement

import java.io.InputStream
import org.apache.ibatis.builder.xml.XMLMapperBuilder
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.apache.ibatis.session.Configuration
import org.apache.ibatis.session.SqlSessionFactory

object MyBatisSqlLoader {

    fun prepare(mapperStreams: List<Pair<String, InputStream>>): SqlSessionFactory {
        val configuration = Configuration()

        for ((resourcePath, xmlStream) in mapperStreams) {
            val mapperBuilder = XMLMapperBuilder(
                xmlStream,
                configuration,
                resourcePath,
                configuration.sqlFragments
            )
            mapperBuilder.parse()
        }

        return SqlSessionFactoryBuilder().build(configuration)
    }
}
