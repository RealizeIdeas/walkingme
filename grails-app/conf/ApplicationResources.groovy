modules = {
    application {
        resource url:'js/application.js'
    }

    'jquery-mobile-local' {
        dependsOn 'jquery'

        resource url:[dir:'js/jquery',file:'jquery.mobile.min.js'], disposition: 'head'
        resource url:[dir:'css/jquery',file:'jquery.mobile.min.css'], attrs:[media:'screen, projection'], disposition: 'head'
    }
}