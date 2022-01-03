package com.github.dinbtechit.es.services

import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchRequest
import com.github.dinbtechit.es.services.state.ConnectionInfo
import com.intellij.openapi.components.Service
import okhttp3.OkHttpClient
import okhttp3.Request

@Service
class ElasticsearchHttpClient<in R : AbstractElasticsearchRequest> : OkHttpClient() {
    fun sendRequest(connectionInfo: ConnectionInfo, request: R): String {
        val httpRequest = Request.Builder()
            .url("${connectionInfo.url}/${request.path}?format=json&pretty")
            .build()
        val res = this.newCall(httpRequest).execute()
        return res.body!!.string()
    }

}