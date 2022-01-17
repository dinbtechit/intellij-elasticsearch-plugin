package com.github.dinbtechit.es.models.elasticsearch

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class Cluster(
    val name: String,
    @JsonProperty("cluster_name") val clusterName: String,
    @JsonProperty("cluster_uuid") val clusterUUID: String,
    val version: Version
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Version(
        val number: String,
        @JsonProperty("build_type") val buildType: String,
        @JsonProperty("build_date") val buildDate: Date
    )
}