package com.github.dinbtechit.es.models.elasticsearch.cat

import com.github.dinbtechit.es.models.elasticsearch.ESTemplate
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchRequest
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchResponse

class CatTemplatesRequest(
    override val path: String = "_cat/templates?format=json&pretty"
) : AbstractElasticsearchRequest() {
}

class CatTemplateResponse(
    override val documents: List<ESTemplate>
): AbstractElasticsearchResponse<ESTemplate>() {
}