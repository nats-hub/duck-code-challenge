package com.ep.challenge.models

import java.math.BigDecimal
import java.math.BigInteger

data class DocumentMetaDataResponse(
    val totalDocuments: Long,
    val deletedDocuments: Long,
    val totalSizeOfDocuments: Long,
    val averageSizeOfDocuments: Double
)
