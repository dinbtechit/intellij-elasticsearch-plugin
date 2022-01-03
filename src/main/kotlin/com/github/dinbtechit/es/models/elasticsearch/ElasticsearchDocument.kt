package com.github.dinbtechit.es.models.elasticsearch

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

sealed class ElasticsearchDocument(open val displayName: String) {
    enum class Types(val value: String) {
        ALIAS("Aliases"),
        INDICES("Indices"),
        INGEST_PIPELINES("Ingest-Pipelines"),
        TEMPLATES("Templates")
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class ESAlias(
    val alias: String = "",
    val index: String = "",
    val filter: String = "",
    val routingIndex: String = "",
    val routingSearch: String = "",
    val isWriteIndex: String = ""
) : ElasticsearchDocument(displayName = alias)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ESIndex(
    val health: String = "",
    val status: String = "",
    val index: String = "",
    val uuid: String = "",
    val pri: String = "",
    val rep: String = "",
    @JsonProperty("docs.count") val docsCount: String = "",
    @JsonProperty("docs.deleted") val docsDeleted: String = "",
    @JsonProperty("store.size") val storeSize: String = "",
    @JsonProperty("pri.store.size") val priStoreSize: String = ""
) : ElasticsearchDocument(displayName = index)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ESTemplate(
    @JsonProperty("name") override val displayName: String) :
    ElasticsearchDocument(displayName)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ESIngestPipeline(
    override var displayName: String = "",
    val description: String = "",
    val version: String = ""
) : ElasticsearchDocument(displayName)
