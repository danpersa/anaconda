package com.danix.anaconda

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices
import org.zalando.stups.oauth2.spring.security.expression.ExtendedOAuth2WebSecurityExpressionHandler
import org.zalando.stups.oauth2.spring.server.TokenInfoResourceServerTokenServices

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity
@ConditionalOnProperty(prefix = "zalando.oauth2.resource", value = "enabled", matchIfMissing = true)
class ResourceServerConfiguration : ResourceServerConfigurerAdapter() {

    @Value("\${zalando.oauth2.resource.token-info-uri}")
    private lateinit var tokenInfoUri: String

    @Throws(Exception::class)
    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.resourceId("sample")
        // here is the important part to enable "hasRealm" below
        resources.expressionHandler(ExtendedOAuth2WebSecurityExpressionHandler())
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        // we only want to secure the API
        http.antMatcher("/api/**")

        // on the API we don't use sessions
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)

        // TODO enhance with more pathes and scopes if necessary
        http.authorizeRequests()
                .antMatchers("/health").anonymous()
                //				.antMatchers("/metrics", "/metrics/**", "/.well-known/schema-discovery").access("#oauth2.hasScope('uid')")
                .antMatchers("/api/**").access("#oauth2.hasScope('uid') and #oauth2.hasUidScopeAndRealm('/employees')")
                .anyRequest().anonymous()
                //				.anyRequest().access("#oauth2.hasScope('uid')");
    }

    @Bean
    fun resourceTokenServices(): ResourceServerTokenServices {
        return TokenInfoResourceServerTokenServices(tokenInfoUri)
    }
}
