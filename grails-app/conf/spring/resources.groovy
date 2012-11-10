// Place your Spring DSL code here
beans = {
    xmlns context: "http://www.springframework.org/schema/context"
    context.'component-scan'('base-package': "net.realizeideas.walkingme.authentication.oauth.facebook")
    xmlns util:"http://www.springframework.org/schema/util"

        /*Places Search API*/
    util.list(id: 'placesSearchExecutors'){
       ref(bean:'googlePlacesSearchService')
   }

}
