package com.ep.challenge.services

import com.ep.challenge.clients.RestTemplateClient
import com.ep.challenge.models.Document
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
class DocumentReaderServiceTest {

    @Mock
    private lateinit var restTemplateClient: RestTemplateClient

    @InjectMocks
    private lateinit var service: DocumentReaderService

    companion object {
        val documentList = listOf(
            Document(id = 1, name="doc1", deleted = false, modifiedAt = "10/08/2022 18:20",
            size = 100, type = "PDF", createdAt = "10/08/2022 18:20", categories = listOf("test-1")),
            Document(id = 2, name="doc2", deleted = true, modifiedAt = "10/08/2022 18:20",
            size = 200, type = "PDF", createdAt = "10/08/2022 18:20", categories = listOf("test-2"))
        )
    }

    @Test
    fun `Should throw 500-Error when no documents found` () {
        whenever(restTemplateClient.getDocuments())
            .thenReturn(emptyList())
        val exception = assertFailsWith<HttpClientErrorException> {
            service.getDocumentMetadata()
        }
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.statusCode)
    }

    @Test
    fun `Should return document metadata when documents found` () {
        whenever(restTemplateClient.getDocuments())
            .thenReturn(documentList)
        val documentMetadata = service.getDocumentMetadata()
        assertEquals(2, documentMetadata.totalDocuments)
        assertEquals(300, documentMetadata.totalSizeOfDocuments)
        assertEquals(150.0, documentMetadata.averageSizeOfDocuments)
        assertEquals(1, documentMetadata.deletedDocuments)
    }

    @Test
    fun `Should return filtered documents when filtered by category` () {
        val category = "test-1"
        whenever(restTemplateClient.getDocuments())
            .thenReturn(documentList)
        val docs = service.filterDocuments(category)
        assertTrue(docs?.size == 1)
        assertEquals(docs?.stream()?.filter{ it.categories.contains(category)}?.count(), 1)
    }
}