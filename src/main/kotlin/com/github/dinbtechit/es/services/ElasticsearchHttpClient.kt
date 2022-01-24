package com.github.dinbtechit.es.services

import com.github.dinbtechit.es.configuration.ConnectionInfo
import com.github.dinbtechit.es.exception.ElasticsearchHttpException
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchRequest
import com.intellij.openapi.components.Service
import okhttp3.OkHttpClient
import okhttp3.Request

@Service
class ElasticsearchHttpClient<in R : AbstractElasticsearchRequest> : OkHttpClient() {
    suspend fun sendRequest(connectionInfo: ConnectionInfo, request: R): String {
        val httpRequest = Request.Builder()
            .method(request.method, request.body)
            .url("${connectionInfo.url}/${request.path}")
            .build()
        val res = this.newCall(httpRequest).execute()
        if (res.code != 200) throw ElasticsearchHttpException(res.body!!.string())
        return res.body!!.string()
    }

}