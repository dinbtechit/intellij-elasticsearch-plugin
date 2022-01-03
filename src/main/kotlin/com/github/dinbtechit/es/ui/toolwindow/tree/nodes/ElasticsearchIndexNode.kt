package com.github.dinbtechit.es.ui.toolwindow.tree.nodes

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.dinbtechit.es.models.elasticsearch.ESIndex
import com.github.dinbtechit.es.models.elasticsearch.ElasticsearchDocument
import com.github.dinbtechit.es.models.elasticsearch.cat.CatIndicesRequest
import com.github.dinbtechit.es.services.ElasticsearchHttpClient
import com.github.dinbtechit.es.services.state.ConnectionInfo
import com.intellij.icons.AllIcons
import icons.ElasticsearchIcons

class ElasticsearchIndexNode : ElasticsearchTreeNode<ElasticsearchDocument.Types, ESIndex>(
    ElasticsearchIcons.esIndices,
    data = ElasticsearchDocument.Types.INDICES,
    AllIcons.Nodes.DataTables
) {

    init {
        // loadIndices()
    }

    fun loadIndices() {
        val client = ElasticsearchHttpClient<CatIndicesRequest>()
        val connection = if (this.parent is ElasticsearchConnectionTreeNode)
                (this.parent as ElasticsearchConnectionTreeNode).data else ConnectionInfo()
        val json = client.sendRequest(connection, CatIndicesRequest())
        val mapper = jacksonObjectMapper()
        super.childData = mapper.readValue(json)
        loadChildren()
    }

}