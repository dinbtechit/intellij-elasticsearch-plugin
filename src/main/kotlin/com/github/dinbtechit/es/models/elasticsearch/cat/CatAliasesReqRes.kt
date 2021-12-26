package com.github.dinbtechit.es.models.elasticsearch.cat

import com.github.dinbtechit.es.models.ESAlias
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchRequest
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchResponse

class CatAliasesRequest(
    override val path: String = "_cat/aliases"
): AbstractElasticsearchRequest() {
}

class CatAliasesResponse(
    override val documents: List<ESAlias>
): AbstractElasticsearchResponse<ESAlias>() {
}


