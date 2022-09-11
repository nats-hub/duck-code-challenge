package com.ep.challenge.models

data class Document(
    val id: Long,
    val name: String,
    val size: Long,
    val type: String,
    val categories: List<String>,
    val deleted: Boolean,
    val createdAt: String,
    val modifiedAt: String
)

