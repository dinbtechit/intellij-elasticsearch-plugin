package com.github.dinbtechit.es.models.elasticsearch.cat


import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchRequest

class CatAliasesRequest(
    override val path: String = "_cat/aliases?format=json&s=alias:asc"
): AbstractElasticsearchRequest() {
}


