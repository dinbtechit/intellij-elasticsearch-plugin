package com.github.dinbtechit.es.models.elasticsearch

import com.github.dinbtechit.es.models.ElasticsearchDocument

abstract class AbstractElasticsearchRequest(
    val url: String = "",
    open val path: String = "",
    val header: Map<String, String>? = null,
    val queryParams: Map<String, String>? = null,
    val body: String = ""
) {
}


abstract class AbstractElasticsearchResponse<T : ElasticsearchDocument>(
    open val documents: List<T> = listOf()
) {
}