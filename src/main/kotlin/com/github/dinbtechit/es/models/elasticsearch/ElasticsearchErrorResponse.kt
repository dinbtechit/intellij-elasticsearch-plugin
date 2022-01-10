package com.github.dinbtechit.es.models.elasticsearch

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ElasticsearchErrorResponse(
    @JsonProperty("error") val error: Error,
    val status: Int
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Error(
        @JsonProperty("root_cause") val rootCause: List<RootCause>,
        val type: String,
        val reason: String
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        data class RootCause(val type: String, val reason: String)
    }
}
