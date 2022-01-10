package com.github.dinbtechit.es.ui.toolwindow.tree.nodes

import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.dinbtechit.es.exception.ElasticsearchHttpException
import com.github.dinbtechit.es.models.elasticsearch.ESAlias
import com.github.dinbtechit.es.models.elasticsearch.ElasticsearchDocument
import com.github.dinbtechit.es.models.elasticsearch.cat.CatIndexReq
import com.github.dinbtechit.es.services.ElasticsearchHttpClient
import com.github.dinbtechit.es.services.state.ConnectionInfo
import com.intellij.icons.AllIcons
import com.intellij.openapi.diagnostic.thisLogger
import icons.ElasticsearchIcons

class ElasticsearchAliasNode :
    ElasticsearchTreeNode<ElasticsearchDocument.Types, ChildData<ESAlias>>(
        ElasticsearchIcons.esAliases,
        data = ElasticsearchDocument.Types.ALIAS
    ) {

    init {
        //loadIndices()
    }

    fun loadDocuments(indexName: String) {
        try {
            val client = ElasticsearchHttpClient<CatIndexReq>()
            val connection = if (this.parent is ElasticsearchConnectionTreeNode)
                (this.parent as ElasticsearchConnectionTreeNode).data else ConnectionInfo()
            val json = client.sendRequest(connection, CatIndexReq(indexName))
            val mapper = jacksonObjectMapper()
            val result: Map<String, Any> = mapper.readValue(json)
            val index: Map<String, Any> = mapper.convertValue(result[indexName]!!)
            val aliases: Map<String, Any> = mapper.convertValue(index["aliases"]!!)
            for ((k, v) in aliases) {
                val alias = mapper.convertValue<ESAlias>(v)
                alias.displayName = k
                val child = ChildData(
                    icon = if (alias.isWriteIndex) {
                        ElasticsearchIcons.General.DataTable_16px
                    } else ElasticsearchIcons.General.DataTable_ReadOnly_16px,
                    alias
                )
                childData.add(child)
            }
            loadChildren()
        } catch (e: ElasticsearchHttpException) {
            this.thisLogger().warn("ResponseCode=${e.body.status}, Reason=${e.body.error.reason}")
        }
    }

}