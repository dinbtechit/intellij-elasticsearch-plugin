package com.github.dinbtechit.es.models.elasticsearch.cat


import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchRequest

class CatClusterReq(
    override val path: String = "/?format=json&pretty"
): AbstractElasticsearchRequest(path = path) {
}