package com.github.dinbtechit.es.ui.toolwindow.tree.nodes

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.dinbtechit.es.configuration.ConnectionInfo
import com.github.dinbtechit.es.models.elasticsearch.ESAlias
import com.github.dinbtechit.es.models.elasticsearch.ESIndexAlias
import com.github.dinbtechit.es.models.elasticsearch.ElasticsearchDocument
import com.github.dinbtechit.es.models.elasticsearch.cat.CatAliasesRequest
import com.github.dinbtechit.es.services.ElasticsearchHttpClient
import com.jetbrains.rd.util.first
import icons.ElasticsearchIcons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ElasticsearchAliasNode :
    ElasticsearchTreeNode<ElasticsearchDocument.Types, ESAlias>(
        ElasticsearchIcons.esAliases,
        data = ElasticsearchDocument.Types.ALIAS,
        childIcon = ElasticsearchIcons.General.DataTable_Shortcut_Readonly
    ) {


    fun loadDocuments() {
        CoroutineScope(Dispatchers.IO).launch {
            val client = ElasticsearchHttpClient<CatAliasesRequest>()
            val connection = if (this@ElasticsearchAliasNode.parent is ElasticsearchConnectionTreeNode) {
                (this@ElasticsearchAliasNode.parent as ElasticsearchConnectionTreeNode).data
            } else ConnectionInfo()
            val json = client.sendRequest(connection, CatAliasesRequest())
            val mapper = jacksonObjectMapper()
            val groupByAliasName = mapper.readValue<MutableList<ESAlias>>(json).groupBy { it.alias }
            childData = groupByAliasName.entries.map { ESAlias(it.key) }.toMutableList()
            loadChildren()
            loadAssociatedIndices(groupByAliasName)
        }
    }

    private fun loadAssociatedIndices(groupByAliasName: Map<String, List<ESAlias>>) {
        for (node in children()) {
            if (node is ElasticsearchTreeNode<*, *>) {
                val indexNode = ElasticsearchAliasIndicesNode()
                val data = node.data
                if (data is ESAlias) {
                    indexNode.loadDocuments(groupByAliasName[data.displayName]!!)
                }
                node.add(indexNode)
            }
        }
    }
}