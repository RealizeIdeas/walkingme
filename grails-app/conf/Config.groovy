// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
        all: '*/*',
        atom: 'application/atom+xml',
        css: 'text/css',
        csv: 'text/csv',
        form: 'application/x-www-form-urlencoded',
        html: ['text/html', 'application/xhtml+xml'],
        js: 'text/javascript',
        json: ['application/json', 'text/json'],
        multipartForm: 'multipart/form-data',
        rss: 'application/rss+xml',
        text: 'text/plain',
        xml: ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
//grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']
grails.resources.adhoc.patterns = []

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

basePath = System.properties['base.dir']
google.places.apiKey = "AIzaSyA71BFIm8HQtw4izv80TzFCIZscoRMPSN4"
environments {
    development {
        grails.logging.jul.usebridge = true
        grails.plugins.springsocial.facebook.clientId = "101900146642118"
        grails.plugins.springsocial.facebook.clientSecret = "31a92a00e7144bb3b4d45e8253f4d5a0"
        grails.serverURL = "http://localhost:8080/walkingme"
        foursquare.clientId = "ZHX144RHEIHRBYKQUF4HTAJCOWNTNH5FMVQKTZ23RCWK4IQ2"
        foursquare.clientSecret = "LO1P1YLGAUX5L0AC5J5BC2TWYY2JNLVZI5APHEGBKJYMQF00"
    }
    test {
        grails.plugins.springsocial.facebook.clientId = "476389125745449"
        grails.plugins.springsocial.facebook.clientSecret = "e6477ad7ddd32b61537b407390c230e0"
        basePath = System.properties['catalina.base'] + "/webapps/ROOT"
        grails.serverURL = "http://walkingme.realizeideas.net"
        foursquare.clientId = "ZHX144RHEIHRBYKQUF4HTAJCOWNTNH5FMVQKTZ23RCWK4IQ2"
        foursquare.clientSecret = "LO1P1YLGAUX5L0AC5J5BC2TWYY2JNLVZI5APHEGBKJYMQF00"
    }
    production {
        grails.logging.jul.usebridge = false
        grails.serverURL = "http://www.walkingme.com"
        grails.plugins.springsocial.facebook.clientId = "466216803419591"
        grails.plugins.springsocial.facebook.clientSecret = "6d8369422272df8185d04b9300554668"
        basePath = System.properties['catalina.base'] + "/webapps/ROOT"
        foursquare.clientId = "ZHX144RHEIHRBYKQUF4HTAJCOWNTNH5FMVQKTZ23RCWK4IQ2"
        foursquare.clientSecret = "LO1P1YLGAUX5L0AC5J5BC2TWYY2JNLVZI5APHEGBKJYMQF00"
    }

}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console appender:
    //
    appenders {
        rollingFile name: 'file', maxFileSize: 5120000, file: "${basePath}/logs/walkingme.log", maxBackupIndex: 10, layout: pattern(conversionPattern: '%d [%t] %-5p (%c) - %m%n'), encoding: 'UTF-8'
        console name: 'stdout', layout: pattern(conversionPattern: '%d [%t] %-5p (%c) - %m%n'), encoding: 'UTF-8'
    }

    info 'org.codehaus.groovy.grails.web.servlet'        // controllers
//        info 'net.realizeideas.walkingme.search'        // API search
//    debug 'org.hibernate.SQL'              // SQL logging
//    debug 'org.apache.http'              // HTTP logging
    error 'org.codehaus.groovy.grails.web.pages',          // GSP
            'org.codehaus.groovy.grails.web.sitemesh',       // layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping',        // URL mapping
            'org.codehaus.groovy.grails.commons',            // core / classloading
            'org.codehaus.groovy.grails.plugins',            // plugins
            'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'

    root {
        info 'stdout', 'file'
        additivity = true
    }


}

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'net.realizeideas.walkingme.authentication.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'net.realizeideas.walkingme.authentication.UserRole'
grails.plugins.springsecurity.authority.className = 'net.realizeideas.walkingme.authentication.Role'
grails.plugins.springsecurity.requestMap.className = 'net.realizeideas.walkingme.authentication.Requestmap'
grails.plugins.springsecurity.securityConfigType = grails.plugins.springsecurity.SecurityConfigType.Requestmap
grails.plugins.springsecurity.rememberMe.alwaysRemember = true


grails.plugin.databasemigration.updateOnStart = true
grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.groovy']

springMobile {
    deviceResolver = "wurfl"
}

