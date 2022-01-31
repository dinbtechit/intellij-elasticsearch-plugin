package com.github.dinbtechit.es.models.elasticsearch

import okhttp3.RequestBody


abstract class AbstractElasticsearchRequest(
    val url: String = "",
    open val path: String = "",
    open val method: String = "GET",
    val header: Map<String, String>? = null,
    val queryParams: Map<String, String>? = null,
    var body: RequestBody? = null
) {
}


abstract class AbstractElasticsearchResponse<T : ElasticsearchDocument>(
    open val documents: List<T> = listOf()
) {
}