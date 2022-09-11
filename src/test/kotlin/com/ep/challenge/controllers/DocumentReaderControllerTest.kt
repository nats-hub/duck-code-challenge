package com.ep.challenge.controllers

import com.ep.challenge.models.DocumentMetaDataResponse
import com.ep.challenge.services.DocumentReaderService
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

@ExtendWith(MockitoExtension::class)
class DocumentReaderControllerTest {

    @Mock
    private lateinit var documentReaderService: DocumentReaderService

    @InjectMocks
    private lateinit var controller: DocumentReaderController

    @Test
    fun `Should throw 404-NotFound when API is responded with not found` () {
        whenever(documentReaderService.getDocumentMetadata())
            .thenThrow(HttpClientErrorException(HttpStatus.NOT_FOUND))
        val exception = assertFailsWith<HttpClientErrorException> {
            controller.getDocumentMetaData()
        }
        assertEquals(HttpStatus.NOT_FOUND, exception.statusCode)
    }

    @Test
    fun `Should throw 500-INTERNAL_SERVER_ERROR when API is responded with an error` () {
        whenever(documentReaderService.getDocumentMetadata())
            .thenThrow(HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
        val exception = assertFailsWith<HttpClientErrorException> {
            controller.getDocumentMetaData()
        }
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.statusCode)
    }

    @Test
    fun `Should return Document Metadata when API returns the documents` () {
        val documentMetadata = DocumentMetaDataResponse(
            totalDocuments = 2,
            averageSizeOfDocuments = 100.0,
            totalSizeOfDocuments = 100,
            deletedDocuments = 1
        )
        whenever(documentReaderService.getDocumentMetadata())
            .thenReturn(documentMetadata)
        val response = controller.getDocumentMetaData()
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(documentMetadata, response.body)
    }

    @Test
    fun `Should throw 404-NotFound when API is responded with not found in filtering documents` () {
        val category = "test"
        whenever(documentReaderService.filterDocuments(category))
            .thenThrow(HttpClientErrorException(HttpStatus.NOT_FOUND))
        val exception = assertFailsWith<HttpClientErrorException> {
            controller.filterDocuments(category)
        }
        assertEquals(HttpStatus.NOT_FOUND, exception.statusCode)
    }
}