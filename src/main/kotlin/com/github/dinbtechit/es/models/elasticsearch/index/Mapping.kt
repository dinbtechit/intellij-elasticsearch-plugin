package com.github.dinbtechit.es.models.elasticsearch.index

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchRequest

class MappingReq(
    name: String
) : AbstractElasticsearchRequest(path = "${name}/_mapping") {
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Mapping(
    var name: String?,
    val type: String?,
    val properties: Mapping?
)