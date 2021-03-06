package com.github.dinbtechit.es.models.elasticsearch.cat

import com.github.dinbtechit.es.models.elasticsearch.ESIngestPipeline
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchRequest
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchResponse

class CatIngestPipelinesRequest(
    override val path: String = "_ingest/pipeline?format=json&pretty"
): AbstractElasticsearchRequest() {
}

class CatIngestPipelinesResponse(
    override val documents: List<ESIngestPipeline>
): AbstractElasticsearchResponse<ESIngestPipeline>() {
}