package com.github.dinbtechit.es.models.elasticsearch.cat


import com.fasterxml.jackson.annotation.JsonAnySetter
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchRequest
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchResponse
import com.github.dinbtechit.es.models.elasticsearch.ESIndex

class CatIndicesRequest(
    override val path: String = "_cat/indices?v&s=index:asc&format=json"
): AbstractElasticsearchRequest(path = path) {
}

class CatIndicesResponse(
    @JsonAnySetter override val documents: List<ESIndex>
): AbstractElasticsearchResponse<ESIndex>() {
}