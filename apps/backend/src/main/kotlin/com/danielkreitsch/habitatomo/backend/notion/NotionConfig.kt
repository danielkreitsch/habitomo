package com.danielkreitsch.habitatomo.backend.notion

import org.hibernate.validator.constraints.Length
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated

@Configuration
@ConfigurationProperties(prefix = "notion")
@Validated
class NotionConfig {
  @Length(min = 1) var accessToken: String = ""
}
