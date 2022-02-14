package com.github.dinbtechit.es.models.elasticsearch

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

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
    val alias: String = "",
    val index: String? = "",
    val filter: String? = "",
    @JsonProperty("routing.index") val routingIndex: String? = "",
    @JsonProperty("routing.search") val routingSearch: String? = "",
    @JsonDeserialize(using = WriteIndexBooleanSerializer::class)
    @JsonProperty("is_write_index") val isWriteIndex: Boolean = false
) : ElasticsearchDocument(displayName = alias) {
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class ESIndexAlias(
    override var displayName: String = "",
    val filter: String = "",
    @JsonProperty("routing.index") val routingIndex: String = "",
    @JsonProperty("routing.search") val routingSearch: String = "",
    @JsonDeserialize(using = WriteIndexBooleanSerializer::class)
    @JsonProperty("is_write_index") val isWriteIndex: Boolean = true
) : ElasticsearchDocument(displayName) {}

private class WriteIndexBooleanSerializer : JsonDeserializer<Boolean>() {
    override fun deserialize(p0: JsonParser?, p1: DeserializationContext?): Boolean {
        return when (p0!!.valueAsString.toLowerCase()) {
            "true" -> true
            "false" -> false
            else -> true
        }
    }

}

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
) : ElasticsearchDocument(displayName)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ESIngestPipeline(
    override var displayName: String = "",
    val description: String = "",
    val version: String = ""
) : ElasticsearchDocument(displayName)
