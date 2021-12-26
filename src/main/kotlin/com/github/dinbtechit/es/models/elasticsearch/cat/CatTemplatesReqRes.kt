package com.github.dinbtechit.es.models.elasticsearch.cat

import com.github.dinbtechit.es.models.ESTemplate
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchRequest
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchResponse

class CatTemplatesRequest(
    override val path: String = "_cat/templates"
) : AbstractElasticsearchRequest() {
}

class CatTemplateResponse(
    override val documents: List<ESTemplate>
): AbstractElasticsearchResponse<ESTemplate>() {
}