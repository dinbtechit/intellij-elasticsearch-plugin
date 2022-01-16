package com.github.dinbtechit.es.actions

import com.github.dinbtechit.es.configuration.ConnectionInfo
import com.github.dinbtechit.es.shared.ProjectUtil
import com.github.dinbtechit.es.ui.dialogs.view.NewConnectionDialog
import com.github.dinbtechit.es.ui.toolwindow.tree.nodes.ElasticsearchTreeNode
import com.github.dinbtechit.es.ui.toolwindow.models.TreeDataKey
import com.github.dinbtechit.es.ui.toolwindow.service.TreeModelController
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.KeyboardShortcut
import com.intellij.openapi.actionSystem.ShortcutSet
import com.intellij.openapi.components.service
import com.intellij.openapi.project.DumbAware
import java.awt.event.KeyEvent
import javax.swing.KeyStroke

class ViewPropertiesAction : AnAction(), DumbAware {
    companion object {
        const val ID = "com.github.dinbtechit.es.actions.ViewPropertiesAction"
    }

    var isEnabled = false
    val controller = ProjectUtil.currentProject().service<TreeModelController>()

    init {
        controller.subscribe {
            if (it.propertyName == TreeModelController.EventType.TREE_NODE_SELECTED.name) {
                this.isEnabled = true
            } else if (it.propertyName == TreeModelController.EventType.TREE_NODE_UNSELECTED.name) {
                this.isEnabled = false
            }
        }

        shortcutSet = ShortcutSet {
            arrayOf(
                KeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_DOWN_MASK), null)
            )
        }

    }

    override fun actionPerformed(e: AnActionEvent) {
        val model = e.getData(TreeDataKey.TREE_MODEL)?.selectionModel
        val treePath = model?.selectionPaths?.toList()?.first()
        fun getTopLevelNode(treePath: ElasticsearchTreeNode<*, *>?): ElasticsearchTreeNode<*, *>? {
            if (treePath is ElasticsearchTreeNode<*, *>) {
                val currentPath = treePath
                val parentPath = currentPath.parent as ElasticsearchTreeNode<*, *>
                if (parentPath.data is String) return currentPath
                return getTopLevelNode(parentPath)
            }
            return null
        }

        val connectionInfo = try {
            getTopLevelNode(treePath?.lastPathComponent as ElasticsearchTreeNode<*, *>?)?.data as ConnectionInfo
        } catch (e: Exception) {
            null
        }

        val dialog = NewConnectionDialog(project = e.project!!, selectedConnectionInfo = connectionInfo)
        if (dialog.showAndGet()) {
            println("View properties Clicked")
        }
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = this.isEnabled
    }
}
