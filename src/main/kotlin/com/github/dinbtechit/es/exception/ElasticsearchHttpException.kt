package com.github.dinbtechit.es.exception

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.dinbtechit.es.models.elasticsearch.ElasticsearchErrorResponse

class ElasticsearchHttpException(
    responseBody: String
): Exception(){
    val body: ElasticsearchErrorResponse

    init {
        val mapper = jacksonObjectMapper()
        body = mapper.readValue(responseBody)
    }
}