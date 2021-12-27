package com.github.dinbtechit.es.ui.toolwindow.tree.nodes

import com.github.dinbtechit.es.actions.DuplicateAction
import com.github.dinbtechit.es.actions.RefreshAction
import com.github.dinbtechit.es.actions.popup.ConnectAction
import com.github.dinbtechit.es.actions.popup.DisconnectAction
import com.github.dinbtechit.es.models.ESAlias
import com.github.dinbtechit.es.models.ESIndex
import com.github.dinbtechit.es.models.ElasticsearchDocument
import com.github.dinbtechit.es.services.state.ConnectionInfo
import com.github.dinbtechit.es.ui.toolwindow.tree.ElasticsearchTree
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.DefaultActionGroup
import icons.ElasticsearchIcons

class ElasticsearchConnectionTreeNode(connectionInfo: ConnectionInfo) :
    ElasticsearchTreeNode<ConnectionInfo>(icon = ElasticsearchIcons.logo_16px, connectionInfo) {

    var isConnected = false


    fun connect() {
        println("Connecting...")
        isConnected = true
        createESNodes(this)
    }

    fun refresh() {
        println("Refreshing...")
    }

    fun disconnect() {
        println("Disconnecting...")
        isConnected = false
    }

    fun buildPopMenuItems(tree: ElasticsearchTree): DefaultActionGroup {
        val popupMenuItems = DefaultActionGroup()
        if (!isConnected) popupMenuItems.add(ConnectAction()) else {
            popupMenuItems.add(DisconnectAction().apply {
                registerCustomShortcutSet(this.shortcutSet, tree)
            })
        }
        popupMenuItems.add(RefreshAction())
        popupMenuItems.addSeparator()
        popupMenuItems.add(DuplicateAction())
        return popupMenuItems
    }

    private fun createESNodes(connectionNode: ElasticsearchConnectionTreeNode) {

        connectionNode.add(
            ElasticsearchTreeNode(
                icon = ElasticsearchIcons.esIndices,
                data = ElasticsearchDocument.Types.INDICES,
                childIcon = AllIcons.Nodes.DataTables,
                childData = listOf(
                    ESIndex(index = "index1", health = "green", storeSize = "300 Kb"),
                    ESIndex(index = "index2", health = "green", storeSize = "300 Kb"),
                    ESIndex(index = "index3", health = "green", storeSize = "300 Kb"),
                    ESIndex(index = "index4", health = "green", storeSize = "300 Kb"),
                    ESIndex(index = "index5", health = "green", storeSize = "300 Kb"),
                )
            )
        )

        connectionNode.add(
            ElasticsearchTreeNode(
                icon = ElasticsearchIcons.esAliases,
                data = ElasticsearchDocument.Types.ALIAS,
                childIcon = AllIcons.Nodes.DataTables,
                childData = listOf(
                    ESAlias("alias1"),
                    ESAlias("alias2")
                )
            )
        )

        connectionNode.add(
            ElasticsearchTreeNode(
                icon = ElasticsearchIcons.esTemplates,
                data = ElasticsearchDocument.Types.TEMPLATES,
                childIcon = AllIcons.Nodes.DataSchema,
                childData = listOf(
                    ESAlias("template1"),
                    ESAlias("template2")
                )
            )
        )

        connectionNode.add(
            ElasticsearchTreeNode(
                icon = ElasticsearchIcons.esPipelines,
                data = ElasticsearchDocument.Types.INGEST_PIPELINES,
                childIcon = AllIcons.Nodes.TabPin,
            )
        )
    }

}