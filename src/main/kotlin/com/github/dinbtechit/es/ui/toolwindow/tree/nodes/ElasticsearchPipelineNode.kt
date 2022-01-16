package com.github.dinbtechit.es.ui.toolwindow.tree.nodes

import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.dinbtechit.es.models.elasticsearch.ESIngestPipeline
import com.github.dinbtechit.es.models.elasticsearch.ElasticsearchDocument
import com.github.dinbtechit.es.models.elasticsearch.cat.CatIngestPipelinesRequest
import com.github.dinbtechit.es.services.ElasticsearchHttpClient
import com.github.dinbtechit.es.configuration.ConnectionInfo
import com.intellij.icons.AllIcons
import icons.ElasticsearchIcons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ElasticsearchPipelineNode : ElasticsearchTreeNode<ElasticsearchDocument.Types, ESIngestPipeline>(
    ElasticsearchIcons.esPipelines,
    data = ElasticsearchDocument.Types.INGEST_PIPELINES,
    AllIcons.RunConfigurations.Web_app
) {

    init {
        //loadIndices()
    }

    fun loadDocuments() {
        val obj = this
        CoroutineScope(Dispatchers.IO).launch {
            val client = ElasticsearchHttpClient<CatIngestPipelinesRequest>()
            val connection = if (obj.parent is ElasticsearchConnectionTreeNode)
                (obj.parent as ElasticsearchConnectionTreeNode).data else ConnectionInfo()
            val json = client.sendRequest(connection, CatIngestPipelinesRequest())
            val mapper = jacksonObjectMapper()
            val result: Map<String, Any> = mapper.readValue(json)

            for ((k, v) in result) {
                val pipeline = mapper.convertValue<ESIngestPipeline>(v)
                pipeline.displayName = k
                super.childData.add(pipeline)
            }
            loadChildren()
        }
    }

}