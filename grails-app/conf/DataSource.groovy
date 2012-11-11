dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect'
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            url = "jdbc:mysql://localhost:3306/walkingme"
            username = "root"
            password = "root"
        }
    }
    test {
        dataSource {
            url = "jdbc:mysql://127.0.0.1:3306/walkingme?useUnicode=yes&characterEncoding=UTF-8"
            username = "bbdashboard"
            password = "bbdashboard23"
            pooled = true
            properties {
                maxActive = -1
                minEvictableIdleTimeMillis = 1800000
                timeBetweenEvictionRunsMillis = 1800000
                numTestsPerEvictionRun = 3
                testOnBorrow = true
                testWhileIdle = true
                testOnReturn = true
                validationQuery = "SELECT 1"
            }
        }
    }
    production {
//        def envVar = System.env.VCAP_SERVICES
//        def credentials = envVar ? grails.converters.JSON.parse(envVar)["mysql-5.1"][0]["credentials"] : null
//
//        dataSource {
//            pooled = true
//            dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect'
//            driverClassName = "com.mysql.jdbc.Driver"
//            url = credentials ? "jdbc:mysql://${credentials.hostname}:${credentials.port}/${credentials.name}?useUnicode=yes&characterEncoding=UTF-8" : ""
//            username = credentials ? credentials.username : ""
//            password = credentails ? credentials.password : ""
//        }
        dataSource {
            url = "jdbc:mysql://127.0.0.1:3306/walkingme_prod?useUnicode=yes&characterEncoding=UTF-8"
            username = "bbdashboard"
            password = "bbdashboard"
            pooled = true
            properties {
                maxActive = -1
                minEvictableIdleTimeMillis = 1800000
                timeBetweenEvictionRunsMillis = 1800000
                numTestsPerEvictionRun = 3
                testOnBorrow = true
                testWhileIdle = true
                testOnReturn = true
                validationQuery = "SELECT 1"
            }
        }
    }
}
