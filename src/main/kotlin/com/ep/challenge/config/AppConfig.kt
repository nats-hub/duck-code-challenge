package com.ep.challenge.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "document-api")
data class AppConfig (
    var endpoint: String = ""
)