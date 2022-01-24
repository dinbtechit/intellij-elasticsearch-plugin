package com.github.dinbtechit.es.models.elasticsearch.index

import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchRequest

class Search(
    name: String
) : AbstractElasticsearchRequest(path = "${name}/_search") {
}