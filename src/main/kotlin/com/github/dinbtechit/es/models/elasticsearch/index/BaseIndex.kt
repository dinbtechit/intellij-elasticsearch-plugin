package com.github.dinbtechit.es.models.elasticsearch.index


import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchRequest

class BaseIndex(
    override val path: String
): AbstractElasticsearchRequest(path = "$path?format=json&pretty") {
}