package com.ep.challenge.clients

import com.ep.challenge.config.AppConfig
import com.ep.challenge.models.Document
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Configuration
class RestTemplateClient(private val appConfig: AppConfig) {

    private val logger = KotlinLogging.logger {}

    private val restTemplate = RestTemplate()

    fun getDocuments() : List<Document> {
        val response: HttpEntity<Array<Document>>?
         try {
                 response = restTemplate.getForEntity(
                     UriComponentsBuilder.fromHttpUrl(appConfig.endpoint).build().toUri(),
                     Array<Document>::class.java
                 )
         } catch (ex: Exception) {
             logger.error("Exception occurred while fetching the Documents from API", ex)
             throw ex
         }
         return response?.body?.asList() ?: emptyList()
    }
}
