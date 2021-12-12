package com.github.dinbtechit.es.services

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class ElasticsearchHttpClient: OkHttpClient() {

    suspend fun run() {
        val request = Request.Builder()
            .url("http://sportsrpt-dinesh.int.spsc-np.cloud.gracenote.com:9200/_cat/indices")
            .build()

        this.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            for ((name, value ) in response.headers) {
                println("$name: $value")
            }
            println(response.body!!.string())
        }
    }

}