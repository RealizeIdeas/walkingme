grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.work.dir = "target"
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "info" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

        mavenRepo "http://maven.springframework.org/release"
        mavenRepo "http://maven.springframework.org/snapshot"
        mavenRepo "http://maven.springframework.org/milestone"
        //Spring Social Google <id>spring.social.google</id>
        mavenRepo "https://github.com/GabiAxel/maven/raw/master"
        mavenRepo "https://artifacts.alfresco.com/nexus"
        mavenRepo "http://maven.mse.jhu.edu/m2repository/"

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        runtime 'mysql:mysql-connector-java:5.1.20'
        compile("org.codehaus.groovy.modules.http-builder:http-builder:0.5.2") {
            excludes "antlr", "appengine-api", "commons-collections", "commons-logging", "groovy", "xercesImpl", "httpclient"
        }
        compile "org.codehaus.jackson:jackson-mapper-asl:1.9.9"
        compile "org.springframework.data:spring-data-jpa:1.1.0.RELEASE"
        compile "org.springframework:spring-oxm:3.1.2.RELEASE"
        compile "com.google.api-client:google-api-client:1.10.3-beta"
        compile("org.springframework.social:spring-social-core:1.1.0.BUILD-SNAPSHOT",
                "org.springframework.social:spring-social-web:1.1.0.BUILD-SNAPSHOT",
                "org.springframework.social:spring-social-facebook:1.1.0.BUILD-SNAPSHOT",
                "org.springframework.social:spring-social-twitter:1.0.2.RELEASE",
                "org.springframework.social:spring-social-google:1.0.0.M1"
        ) { transitive = false }
        // Workaround to resolve dependency issue with aws-java-sdk and http-builder (dependent on httpcore:4.0)
        build 'org.apache.httpcomponents:httpcore:4.1'
        build 'org.apache.httpcomponents:httpclient:4.1.1'
        runtime 'org.apache.httpcomponents:httpcore:4.1'
        runtime 'org.apache.httpcomponents:httpclient:4.1.1'
        compile 'commons-httpclient:commons-httpclient:3.1'
        compile 'uk.ac.shef.wit:simmetrics:1.6.2'
    }

    plugins {
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.7.2"
        runtime ":jquery-ui:1.8.24"
        runtime ":resources:1.1.6"

        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.4"

        build ":tomcat:$grailsVersion"

        runtime ":database-migration:1.0"

        compile ':cache:1.0.0'
        runtime ":spring-security-core:1.2.7.3"
        compile(":spring-social-core:0.1.31") {
            excludes "spring-security-core", "spring-social-core", "spring-social-web", "jackson-mapper-asl"
        }

        compile(":spring-social-twitter:0.1.31") { // keept to avoid compilation problem and right spring loading order
            excludes "spring-security-core", "spring-social-twitter"
        }

        compile ":spring-mobile:0.4"
    }
}
