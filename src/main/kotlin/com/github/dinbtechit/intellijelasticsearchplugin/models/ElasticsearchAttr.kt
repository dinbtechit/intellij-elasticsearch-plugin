package com.github.dinbtechit.intellijelasticsearchplugin.models

import javax.swing.Icon

data class ElasticsearchAttr(
    val categories: Map<ElasticsearchDocument.Types, ElasticsearchCategory>
)

data class ElasticsearchCategory(
    val icon: Icon,
    val items: List<ElasticsearchDocument.Types>
)
