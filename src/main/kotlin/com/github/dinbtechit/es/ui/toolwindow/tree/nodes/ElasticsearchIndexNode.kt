package com.github.dinbtechit.es.ui.toolwindow.tree.nodes

import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.dinbtechit.es.actions.popup.index.*
import com.github.dinbtechit.es.actions.popup.new.NewAliasAction
import com.github.dinbtechit.es.actions.popup.new.NewIndexAction
import com.github.dinbtechit.es.exception.ElasticsearchHttpException
import com.github.dinbtechit.es.models.elasticsearch.ESIndex
import com.github.dinbtechit.es.models.elasticsearch.ElasticsearchDocument
import com.github.dinbtechit.es.models.elasticsearch.index.BaseIndex
import com.github.dinbtechit.es.models.elasticsearch.cat.CatIndicesRequest
import com.github.dinbtechit.es.services.ElasticsearchHttpClient
import com.github.dinbtechit.es.configuration.ConnectionInfo
import com.github.dinbtechit.es.ui.toolwindow.tree.ElasticsearchTree
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.diagnostic.thisLogger
import icons.ElasticsearchIcons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ElasticsearchIndexNode : ElasticsearchTreeNode<ElasticsearchDocument.Types, ESIndex>(
    ElasticsearchIcons.esIndices,
    data = ElasticsearchDocument.Types.INDICES,
    ElasticsearchIcons.General.DataTable_16px
) {

    private val mapper = jacksonObjectMapper()

    init {
        // loadIndices()
    }

    fun loadDocuments() {
        val obj = this
        CoroutineScope(Dispatchers.Default).launch {
            val client = ElasticsearchHttpClient<CatIndicesRequest>()
            val connection = if (obj.parent is ElasticsearchConnectionTreeNode)
                (obj.parent as ElasticsearchConnectionTreeNode).data else ConnectionInfo()
            val json = client.sendRequest(connection, CatIndicesRequest())
            childData = mapper.readValue(json)
            loadChildren(buildChildPopupMenuItems())
            loadAliases()
        }
    }

    private fun loadAliases() {
        val obj = this
        for (node in children()) {
            if (node is ElasticsearchTreeNode<*, *>) {
                CoroutineScope(Dispatchers.Default).launch {
                    try {
                        val client = ElasticsearchHttpClient<BaseIndex>()
                        val connection = if (obj.parent is ElasticsearchConnectionTreeNode)
                            (obj.parent as ElasticsearchConnectionTreeNode).data else ConnectionInfo()
                        val indexName = (node.data as ESIndex).displayName
                        val json = client.sendRequest(connection, BaseIndex(indexName))
                        val result: Map<String, Any> = mapper.readValue(json)

                        val index: Map<String, Any> = mapper.convertValue(result[indexName]!!)
                        val aliases: Map<String, Any> = mapper.convertValue(index["aliases"]!!)
                        val aliasNode = ElasticsearchAliasNode()
                        aliasNode.loadDocuments(aliases)
                        node.add(aliasNode)
                    } catch (e: ElasticsearchHttpException) {
                        this.thisLogger().warn("ResponseCode=${e.body.status}, Reason=${e.body.error.reason}")
                    }
                }
            }
        }
    }

    override fun buildPopMenuItems(tree: ElasticsearchTree): DefaultActionGroup {
        val popupMenuItems = DefaultActionGroup()
        val newMenuItems = DefaultActionGroup(
            "New", mutableListOf(
                NewIndexAction(),
            )
        ).apply {
            this.templatePresentation.icon = AllIcons.General.Add
            this.isPopup = true
        }
        popupMenuItems.add(newMenuItems)
        popupMenuItems.addSeparator()
        popupMenuItems.add(ShowHiddenIndicesAction())
        popupMenuItems.add(DeleteAllDanglingIndicesAction())
        return popupMenuItems
    }

    private fun buildChildPopupMenuItems(): DefaultActionGroup {
        val popupMenuItems = DefaultActionGroup()
        popupMenuItems.add(EditDataAction())
        popupMenuItems.addSeparator()
        val newMenuItems = DefaultActionGroup(
            "New", mutableListOf(
                NewIndexAction(),
                NewAliasAction()
            )
        ).apply {
            this.templatePresentation.icon = AllIcons.General.Add
            this.isPopup = true
        }
        popupMenuItems.add(newMenuItems)
        popupMenuItems.addSeparator()
        popupMenuItems.add(CreateUpdateAliasAction())
        popupMenuItems.add(OpenIndexAction())
        popupMenuItems.add(CloseIndexAction())
        popupMenuItems.add(FreezeIndexAction())
        popupMenuItems.add(UnfreezeIndexAction())
        popupMenuItems.add(DeleteIndexAction())
        popupMenuItems.add(RolloverIndexAction())
        popupMenuItems.add(SettingsIndexAction())
        return popupMenuItems
    }


}