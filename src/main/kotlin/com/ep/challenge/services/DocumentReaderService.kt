package com.ep.challenge.services

import com.ep.challenge.clients.RestTemplateClient
import com.ep.challenge.config.AppConfig
import com.ep.challenge.models.Document
import com.ep.challenge.models.DocumentMetaDataResponse
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import java.util.stream.Collectors

@Service
class DocumentReaderService(
    private val restTemplateClient: RestTemplateClient) {

    private val logger = KotlinLogging.logger {}

    fun getDocumentMetadata() : DocumentMetaDataResponse {
        val documents = restTemplateClient.getDocuments()
        val totalDocuments = documents.size
        if(totalDocuments == 0)
            throw HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "No documents found")
        logger.info("getDocumentMetadata :: totalDocuments found $totalDocuments")
        val deletedDocuments = documents.stream().filter {
            it.deleted
        }.count()
        val totalSizeOfDocuments = documents.stream()
            .mapToLong { it.size }.summaryStatistics().sum
        val averageSizeOfDocuments = documents.stream()
            .mapToLong { it.size }.summaryStatistics().average

        return DocumentMetaDataResponse(
            totalDocuments = totalDocuments.toLong(),
            deletedDocuments = deletedDocuments,
            totalSizeOfDocuments = totalSizeOfDocuments,
            averageSizeOfDocuments = averageSizeOfDocuments
        )
    }

    fun filterDocuments(category: String): List<Document>? =
        restTemplateClient.getDocuments().stream().filter { compareCategory(it.categories, category) }
            .collect(Collectors.toList())

    private fun compareCategory(
        categories: List<String>?,
        category: String
    ) = categories?.any { it.equals(category, true) } ?: false
}