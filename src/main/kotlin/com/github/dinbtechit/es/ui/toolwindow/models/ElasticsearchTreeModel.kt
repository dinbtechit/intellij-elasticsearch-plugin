package com.github.dinbtechit.es.ui.toolwindow.models

import com.github.dinbtechit.es.services.state.ConnectionInfo
import com.github.dinbtechit.es.ui.toolwindow.tree.nodes.ElasticsearchConnectionTreeNode
import com.github.dinbtechit.es.ui.toolwindow.tree.nodes.ElasticsearchRootNode
import javax.swing.tree.DefaultTreeModel

class ElasticsearchTreeModel(
    val connectionInfos: List<ConnectionInfo>,
    val rootNode: ElasticsearchRootNode = ElasticsearchRootNode(),
    var previousRootNode: ElasticsearchRootNode? = null
) : DefaultTreeModel(rootNode) {

    init {
        for (connection in connectionInfos) {
            if (previousRootNode == null || !isNodeExist(rootNode, previousRootNode, connection)) {
                rootNode.add(ElasticsearchConnectionTreeNode(connection))
            }
        }
        this.nodeStructureChanged(rootNode)
    }

    private fun isNodeExist(
        rootNode: ElasticsearchRootNode,
        previousRootNode: ElasticsearchRootNode?, connection: ConnectionInfo
    ): Boolean {
        val iterator = previousRootNode?.children()?.asIterator()
        while (iterator!!.hasNext()) {
            val node = iterator.next()
            if (node is ElasticsearchConnectionTreeNode && node.data == connection) {
                rootNode.add(node)
                return true
            }
        }
        return false
    }
}

