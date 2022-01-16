package com.github.dinbtechit.es.ui.toolwindow.tree.nodes

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.dinbtechit.es.models.elasticsearch.ESTemplate
import com.github.dinbtechit.es.models.elasticsearch.ElasticsearchDocument
import com.github.dinbtechit.es.models.elasticsearch.cat.CatTemplatesRequest
import com.github.dinbtechit.es.services.ElasticsearchHttpClient
import com.github.dinbtechit.es.configuration.ConnectionInfo
import com.github.dinbtechit.es.ui.toolwindow.tree.ElasticsearchTree
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.DefaultActionGroup
import icons.ElasticsearchIcons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ElasticsearchTemplateNode : ElasticsearchTreeNode<ElasticsearchDocument.Types, ESTemplate>(
    ElasticsearchIcons.esTemplates,
    data = ElasticsearchDocument.Types.TEMPLATES,
    AllIcons.FileTypes.JsonSchema
) {

    init {
        //loadIndices()
    }

    fun loadDocuments() {
        CoroutineScope(Dispatchers.IO).launch {
            val client = ElasticsearchHttpClient<CatTemplatesRequest>()
            val connection = if (this@ElasticsearchTemplateNode.parent is ElasticsearchConnectionTreeNode)
                (this@ElasticsearchTemplateNode.parent as ElasticsearchConnectionTreeNode).data else ConnectionInfo()
            val json = client.sendRequest(connection, CatTemplatesRequest())
            val mapper = jacksonObjectMapper()
            super.childData = mapper.readValue(json)
            loadChildren()
        }
    }

    override fun buildPopMenuItems(tree: ElasticsearchTree): DefaultActionGroup {
        return super.buildPopMenuItems(tree)
    }

}