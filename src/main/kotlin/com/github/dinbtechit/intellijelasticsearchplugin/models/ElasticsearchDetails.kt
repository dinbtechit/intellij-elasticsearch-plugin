package com.github.dinbtechit.intellijelasticsearchplugin.models

data class ElasticsearchDetails(
    val name: String,
    val url: String,
    val port: Int,
    val data: Map<ElasticsearchDocument.Types, List<ElasticsearchDocument>>
)
