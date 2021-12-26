package com.github.dinbtechit.es.models.elasticsearch.cat

import com.github.dinbtechit.es.models.ESIndex
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchRequest
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchResponse

class CatIndicesRequest(
    override val path: String = "_cat/indices"
): AbstractElasticsearchRequest(path = path) {
}

class CatIndicesResponse(
    override val documents: List<ESIndex>
): AbstractElasticsearchResponse<ESIndex>() {
}