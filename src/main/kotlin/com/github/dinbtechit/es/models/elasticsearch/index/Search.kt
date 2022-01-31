package com.github.dinbtechit.es.models.elasticsearch.index

import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchRequest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class Search(
    name: String
) : AbstractElasticsearchRequest(
    method = "POST",
    path = "${name}/_search"
) {
    init {
        val mediaType = "application/json; charset=iso-8859-1".toMediaType()
        val jsonStr = """
            {
                "size" : 60
            }
        """.trimIndent()
        body = jsonStr.toRequestBody(mediaType)
    }
}