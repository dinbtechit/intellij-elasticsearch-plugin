package com.github.dinbtechit.es.models.elasticsearch.cat


import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchRequest
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchResponse
import com.github.dinbtechit.es.models.elasticsearch.ESAlias

class CatAliasesRequest(
    override val path: String = "_cat/aliases"
): AbstractElasticsearchRequest() {
}

class CatAliasesResponse(
    override val documents: List<ESAlias>
): AbstractElasticsearchResponse<ESAlias>() {
}


