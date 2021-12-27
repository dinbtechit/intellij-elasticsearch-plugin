package com.github.dinbtechit.es.actions.popup

import com.github.dinbtechit.es.ui.toolwindow.models.ElasticsearchTreeModel
import com.github.dinbtechit.es.ui.toolwindow.tree.ElasticsearchTree
import com.github.dinbtechit.es.ui.toolwindow.tree.nodes.ElasticsearchConnectionTreeNode
import com.github.dinbtechit.es.ui.toolwindow.models.TreeDataKey
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class ConnectAction : AnAction(
    "Connect", "Connect elasticsearch connection", AllIcons.Actions.Execute
) {

    override fun actionPerformed(e: AnActionEvent) {
        val tree = e.getData(TreeDataKey.TREE_MODEL) as ElasticsearchTree
        if (!tree.isSelectionEmpty
            && tree.selectionPaths.first().lastPathComponent is ElasticsearchConnectionTreeNode
        ) {
            val connectionTreeNode = tree.selectionPaths.first().lastPathComponent as ElasticsearchConnectionTreeNode
            connectionTreeNode.connect()
            val model = tree.model as ElasticsearchTreeModel
            model.reload(connectionTreeNode)
        }

    }

}
