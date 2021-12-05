package com.github.dinbtechit.es.models

sealed class ElasticsearchDocument(open val name:String) {
    enum class Types(val value: String) {
        ALIAS("Aliases"),
        INDICES("Indices"),
        INGEST_PIPELINES("Ingest-Pipelines"),
        TEMPLATES("Templates")
    }
}

data class ESAlias(override val name: String): ElasticsearchDocument(name)
data class ESIndex(override val name: String, val health: String, val size: String): ElasticsearchDocument(name)
data class ESIngestPipeline(override val name: String): ElasticsearchDocument(name)
data class ESTemplate(override val name: String): ElasticsearchDocument(name)
