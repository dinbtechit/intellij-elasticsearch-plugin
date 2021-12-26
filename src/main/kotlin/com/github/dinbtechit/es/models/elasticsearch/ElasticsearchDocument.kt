package com.github.dinbtechit.es.models

sealed class ElasticsearchDocument(open val displayName:String) {
    enum class Types(val value: String) {
        ALIAS("Aliases"),
        INDICES("Indices"),
        INGEST_PIPELINES("Ingest-Pipelines"),
        TEMPLATES("Templates")
    }
}

data class ESAlias(
    val alias: String = "",
    val index: String = "",
    val filter: String = "",
    val routingIndex: String = "",
    val routingSearch: String = "",
    val isWriteIndex: String = ""
): ElasticsearchDocument(displayName = alias)


data class ESIndex(
    val health: String = "",
    val status: String = "",
    val index: String = "",
    val uuid: String = "",
    val pri: String = "",
    val rep: String = "",
    val docsCount: String = "",
    val docsDeleted: String = "",
    val storeSize: String = "",
    val priStoreSize: String = ""
): ElasticsearchDocument(displayName = index)

data class ESIngestPipeline(override val displayName: String): ElasticsearchDocument(displayName)
data class ESTemplate(override val displayName: String): ElasticsearchDocument(displayName)
