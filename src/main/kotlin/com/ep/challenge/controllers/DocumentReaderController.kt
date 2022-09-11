package com.ep.challenge.controllers

import com.ep.challenge.models.Document
import com.ep.challenge.models.DocumentMetaDataResponse
import com.ep.challenge.services.DocumentReaderService
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/doc/consumer/v1")
class DocumentReaderController(private val documentReaderService: DocumentReaderService) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/metadata")
    fun getDocumentMetaData() : ResponseEntity<DocumentMetaDataResponse> {
        return ResponseEntity.ok(documentReaderService.getDocumentMetadata())
    }

    @GetMapping("/filter/{category}")
    fun filterDocuments(@PathVariable category: String) : ResponseEntity<List<Document>> {
        return ResponseEntity.ok(documentReaderService.filterDocuments(category))
    }
}

