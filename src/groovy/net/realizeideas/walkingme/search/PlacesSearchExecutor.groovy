package net.realizeideas.walkingme.search

/**
 * Common interface to execute venue search to external API (google, bing, foursquare,...)
 * or internal (our Solr index)
 *
 * @author Michael Astreiko
 */
public interface PlacesSearchExecutor {

    SearchResult search(Query query)
}