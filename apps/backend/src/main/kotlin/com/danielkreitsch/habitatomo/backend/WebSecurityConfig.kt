package com.danielkreitsch.habitatomo.backend

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.config.annotation.*

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebMvcConfigurer {
  @Bean
  fun configure(http: HttpSecurity): SecurityFilterChain {
    http.cors()
    http.csrf().disable()
    return http.build()
  }

  @Bean
  fun corsConfigurationSource(): CorsConfigurationSource {
    val configuration = CorsConfiguration()
    configuration.allowedOrigins = listOf("*")
    configuration.allowedMethods = listOf("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH")
    configuration.allowCredentials = false
    configuration.allowedHeaders = listOf("Authorization", "Cache-Control", "Content-Type")
    val source = UrlBasedCorsConfigurationSource()
    source.registerCorsConfiguration("/**", configuration)
    return source
  }

  override fun addCorsMappings(registry: CorsRegistry) {
    registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
  }
}
