/* Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.realizeideas.walkingme.authentication.oauth.facebook

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.social.connect.ConnectionFactory
import org.springframework.social.facebook.connect.FacebookConnectionFactory
import org.springframework.util.Assert
import grails.plugins.springsocial.SpringSocialUtils

@Configuration
class FacebookConfig {
  @Bean
  ConnectionFactory facebookConnectionFactory() {
    println "Configuring SpringSocial Facebook"
    def facebookConfig = SpringSocialUtils.config.facebook
    String clientId = facebookConfig.clientId ?: ""
    String clientSecret = facebookConfig.clientSecret ?: ""
    Assert.hasText(clientId, "The Facebook clientId is necessary, please add to the Config.groovy as follows: grails.plugins.springsocial.facebook.clientId='yourClientId'")
    Assert.hasText(clientSecret, "The Facebook clientSecret is necessary, please add to the Config.groovy as follows: grails.plugins.springsocial.facebook.clientSecret='yourClientSecret'")
    new FacebookConnectionFactory(clientId, clientSecret)
  }
}
