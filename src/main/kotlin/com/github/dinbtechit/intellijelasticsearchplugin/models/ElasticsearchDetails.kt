package com.github.dinbtechit.intellijelasticsearchplugin.models

data class ElasticsearchDetails(
    val name: String,
    val url: String,
    val port: Int,
    val attributes: List<ElasticsearchAttr>
)
