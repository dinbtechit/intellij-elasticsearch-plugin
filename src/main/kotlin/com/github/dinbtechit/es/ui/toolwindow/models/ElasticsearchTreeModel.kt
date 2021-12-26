package com.github.dinbtechit.es.ui.toolwindow.models

import com.github.dinbtechit.es.services.state.ConnectionInfo
import com.github.dinbtechit.es.ui.toolwindow.tree.nodes.ElasticsearchConnectionTreeNode
import com.github.dinbtechit.es.ui.toolwindow.tree.nodes.ElasticsearchTreeNode
import javax.swing.tree.DefaultTreeModel

class ElasticsearchTreeModel(
    connectionInfos: List<ConnectionInfo>,
    rootNode: ElasticsearchTreeNode<String> = ElasticsearchTreeNode(null, "Elasticsearch")
) : DefaultTreeModel(rootNode) {

    init {
        for (connection in connectionInfos) {
            rootNode.add(ElasticsearchConnectionTreeNode(connection))
        }
    }
}

