package com.github.dinbtechit.es.models.elasticsearch

import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class IndicesCollection(
    val took: Int,
    @JsonProperty("timed_out") val timeOut: Boolean,
    @JsonProperty("_shards") val shards: Shards,
    val hits: Hits
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Shards(
        val total: Int,
        val successful: Int,
        val skipped: Int,
        val failed: Int
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Hits(
        val total: Total,
        @JsonProperty("max_score") val maxScore: Double,
        val hits: List<Hits>
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Total(
            val value: Int,
            val relation: String
        )

        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Hits(
            @JsonProperty("_index") val index: String,
            @JsonProperty("_type") val type: String,
            @JsonProperty("_id") val id: String,
            @JsonProperty("_score") val score: Double,
            @JsonProperty("_source") val source: HashMap<String, Any?>

        ) {
            @JsonAnySetter
            fun setSource(key: String, value: Any?) {
                source[key] = value
            }
        }
    }
}

data class IndicesSettings(
    val name: String
)