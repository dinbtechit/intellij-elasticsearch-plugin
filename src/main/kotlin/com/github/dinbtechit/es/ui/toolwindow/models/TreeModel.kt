package com.github.dinbtechit.es.ui.toolwindow.models

import com.github.dinbtechit.es.models.ESAlias
import com.github.dinbtechit.es.models.ESIndex
import com.github.dinbtechit.es.models.ElasticsearchDocument
import com.github.dinbtechit.es.services.state.ConnectionInfo
import com.intellij.icons.AllIcons
import icons.ElasticsearchIcons
import javax.swing.Icon
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

class ElasticsearchTreeModel(
    connectionInfos: List<ConnectionInfo>,
    rootNode: ElasticsearchTreeNode<String> = ElasticsearchTreeNode(null, "Elasticsearch")
) : DefaultTreeModel(rootNode) {

    init {
        for (connection in connectionInfos) {
            rootNode.add(ElasticsearchTreeNode(ElasticsearchIcons.logo_16px, connection).apply {
                createESNodes(this)
            })
        }
    }

    private fun createESNodes(connectionNode: ElasticsearchTreeNode<ConnectionInfo>) {

        connectionNode.add(
            ElasticsearchTreeNode(
                icon = ElasticsearchIcons.esIndices,
                data = ElasticsearchDocument.Types.INDICES,
                childIcon = AllIcons.Nodes.DataTables,
                childData = listOf(
                    ESIndex("index1", "green", "300 Kb"),
                    ESIndex("index2", "green", "300 Kb"),
                    ESIndex("index3", "green", "300 Kb"),
                    ESIndex("index4", "green", "300 Kb"),
                    ESIndex("index5", "green", "300 Kb"),
                    ESIndex("index6", "green", "300 Kb"),
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

class ElasticsearchTreeNode<T>(
    val icon: Icon?,
    val data: T,
    val childIcon: Icon? = null,
    val childData: List<T>? = null
) : DefaultMutableTreeNode() {
    init {
        if (childIcon != null && childData != null && childData.isNotEmpty()) {
            for (d in childData) add(ElasticsearchTreeNode(childIcon, d))
        }
    }

    data class Empty(val emptyString: String)
}

