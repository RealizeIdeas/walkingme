modules = {
    application {
        resource url: 'js/application.js'
    }

    'jquery-mobile-local' {
        dependsOn 'jquery'

        resource url: [dir: 'js/jquery', file: 'jquery.mobile.min.js'], disposition: 'head'
        resource url: [dir: 'css/jquery', file: 'jquery.mobile.min.css'], attrs: [media: 'screen, projection'], disposition: 'head'
    }

    jqModal {
        resource url: 'js/jqModal-r13.js', disposition: 'head'
        resource url: 'js/jqModalExtensions.js', disposition: 'head'
        resource url: 'css/jqModal.css', disposition: 'head'
    }

    tagedit {
        resource url: [dir: 'js/jquery/plugins', file: 'jquery.tagedit.js'], disposition: 'head'
        resource url: [dir: 'js/jquery/plugins', file: 'jquery.autoGrowInput.js'], disposition: 'head'
        resource url: [dir: 'css/jquery/plugins', file: 'jquery.tagedit.css'], disposition: 'head'
    }

}