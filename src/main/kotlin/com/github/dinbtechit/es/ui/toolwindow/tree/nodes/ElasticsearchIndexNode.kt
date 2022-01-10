package com.github.dinbtechit.es.ui.toolwindow.tree.nodes

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.dinbtechit.es.actions.popup.index.*
import com.github.dinbtechit.es.actions.popup.new.NewAliasAction
import com.github.dinbtechit.es.actions.popup.new.NewIndexAction
import com.github.dinbtechit.es.models.elasticsearch.ESIndex
import com.github.dinbtechit.es.models.elasticsearch.ElasticsearchDocument
import com.github.dinbtechit.es.models.elasticsearch.cat.CatIndicesRequest
import com.github.dinbtechit.es.services.ElasticsearchHttpClient
import com.github.dinbtechit.es.services.state.ConnectionInfo
import com.github.dinbtechit.es.ui.toolwindow.tree.ElasticsearchTree
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.DefaultActionGroup
import icons.ElasticsearchIcons

class ElasticsearchIndexNode : ElasticsearchTreeNode<ElasticsearchDocument.Types, ESIndex>(
    ElasticsearchIcons.esIndices,
    data = ElasticsearchDocument.Types.INDICES,
    ElasticsearchIcons.General.DataTable_16px
) {

    init {
        // loadIndices()
    }

    fun loadDocuments() {
        val client = ElasticsearchHttpClient<CatIndicesRequest>()
        val connection = if (this.parent is ElasticsearchConnectionTreeNode)
                (this.parent as ElasticsearchConnectionTreeNode).data else ConnectionInfo()
        val json = client.sendRequest(connection, CatIndicesRequest())
        val mapper = jacksonObjectMapper()
        childData = mapper.readValue(json)
        loadChildren(buildChildPopupMenuItems())
        loadAliases()
    }

    private fun loadAliases() {
        for (node in children()) {
            if (node is ElasticsearchTreeNode<*, *>) {
                val aliasNode = ElasticsearchAliasNode()
                aliasNode.loadDocuments((node.data as ESIndex).displayName)
                node.add(aliasNode)
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