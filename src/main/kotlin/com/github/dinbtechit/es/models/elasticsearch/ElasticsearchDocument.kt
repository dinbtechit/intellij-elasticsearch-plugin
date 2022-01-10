package com.github.dinbtechit.es.models.elasticsearch

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue

sealed class ElasticsearchDocument(open var displayName: String) {
    enum class Types(val value: String) {
        ALIAS("Aliases"),
        INDICES("Indices"),
        INGEST_PIPELINES("Ingest-Pipelines"),
        TEMPLATES("Templates")
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class ESAlias(
    override var displayName: String = "",
    val filter: String = "",
    @JsonProperty("routing.index") val routingIndex: String = "",
    @JsonProperty("routing.search") val routingSearch: String = "",
    @JsonProperty("is_write_index") val isWriteIndex: Boolean = false
) : ElasticsearchDocument(displayName)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ESIndex(
    val health: Health = Health.GREEN,
    val status: String = "",
    val index: String = "",
    val uuid: String = "",
    val pri: String = "",
    val rep: String = "",
    @JsonProperty("docs.count") val docsCount: String = "",
    @JsonProperty("docs.deleted") val docsDeleted: String = "",
    @JsonProperty("store.size") val storeSize: String = "",
    @JsonProperty("pri.store.size") val priStoreSize: String = ""
) : ElasticsearchDocument(displayName = index) {
    enum class Health(@get:JsonValue val color: String) {
        GREEN("green"),
        YELLOW("yellow"),
        RED("red");

        companion object {
            @JsonCreator
            fun fromString(color: String): Health = valueOf(color.toUpperCase())
        }
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class ESTemplate(
    @JsonProperty("name") override var displayName: String,
    @JsonProperty("index_patterns") val indexPatterns: String
) :
    ElasticsearchDocument(displayName)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ESIngestPipeline(
    override var displayName: String = "",
    val description: String = "",
    val version: String = ""
) : ElasticsearchDocument(displayName)
