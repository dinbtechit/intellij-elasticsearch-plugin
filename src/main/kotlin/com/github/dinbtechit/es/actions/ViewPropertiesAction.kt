package com.github.dinbtechit.es.actions

import com.github.dinbtechit.es.services.state.ConnectionInfo
import com.github.dinbtechit.es.ui.dialogs.view.NewConnectionDialog
import com.github.dinbtechit.es.ui.toolwindow.models.ElasticsearchTreeNode
import com.github.dinbtechit.es.ui.toolwindow.models.TreeDataKey
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class ViewPropertiesAction : AnAction() {
    companion object {
        const val ID = "com.github.dinbtechit.es.actions.ViewPropertiesAction"
        var ENABLED = false
    }

    override fun actionPerformed(e: AnActionEvent) {
        val model = e.getData(TreeDataKey.TREE_MODEL)?.selectionModel
        val treePath = model?.selectionPaths?.toList()?.first()
        fun getTopLevelNode(treePath: ElasticsearchTreeNode<*>?): ElasticsearchTreeNode<*>? {
            if (treePath is ElasticsearchTreeNode<*>) {
                val currentPath = treePath
                val parentPath = currentPath.parent as ElasticsearchTreeNode<*>
                if (parentPath.data is String) return currentPath
                return getTopLevelNode(parentPath)
            }
            return null
        }

        val connectionInfo = try {
            getTopLevelNode(treePath?.lastPathComponent as ElasticsearchTreeNode<*>?)?.data as ConnectionInfo
        } catch (e: Exception) {
            null
        }

        val dialog = NewConnectionDialog(project = e.project!!, selectedConnectionInfo = connectionInfo)
        if (dialog.showAndGet()) {
            println("View properties Clicked")
        }
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = ENABLED
    }
}
