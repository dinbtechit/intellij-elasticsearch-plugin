package com.github.dinbtechit.es.ui.toolwindow.tree.nodes

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.dinbtechit.es.models.elasticsearch.ESAlias
import com.github.dinbtechit.es.models.elasticsearch.ElasticsearchDocument
import com.github.dinbtechit.es.models.elasticsearch.cat.CatAliasesRequest
import com.github.dinbtechit.es.services.ElasticsearchHttpClient
import com.github.dinbtechit.es.services.state.ConnectionInfo
import com.intellij.icons.AllIcons
import icons.ElasticsearchIcons

class ElasticsearchAliasNode : ElasticsearchTreeNode<ElasticsearchDocument.Types, ESAlias>(
    ElasticsearchIcons.esAliases,
    data = ElasticsearchDocument.Types.ALIAS,
    AllIcons.Nodes.DataTables
) {

    init {
        //loadIndices()
    }

    fun loadIndices() {
        val client = ElasticsearchHttpClient<CatAliasesRequest>()
        val connection = if (this.parent is ElasticsearchConnectionTreeNode)
            (this.parent as ElasticsearchConnectionTreeNode).data else ConnectionInfo()
        val json = client.sendRequest(connection, CatAliasesRequest())
        val mapper = jacksonObjectMapper()
        super.childData = mapper.readValue(json)
        loadChildren()
    }

}