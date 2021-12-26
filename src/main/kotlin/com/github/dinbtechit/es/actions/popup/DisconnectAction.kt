package com.github.dinbtechit.es.actions.popup

import com.github.dinbtechit.es.ui.toolwindow.tree.ElasticsearchTree
import com.github.dinbtechit.es.ui.toolwindow.tree.nodes.ElasticsearchConnectionTreeNode
import com.github.dinbtechit.es.ui.toolwindow.models.ElasticsearchTreeModel
import com.github.dinbtechit.es.ui.toolwindow.models.TreeDataKey
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.KeyboardShortcut
import com.intellij.openapi.actionSystem.ShortcutSet
import java.awt.event.KeyEvent
import javax.swing.KeyStroke

class DisconnectAction : AnAction(
    "Disconnect", "Disconnect elasticsearch connection", AllIcons.Actions.Suspend
) {
    init {
        shortcutSet = ShortcutSet {
            arrayOf(
                KeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_F2, KeyEvent.CTRL_DOWN_MASK), null)
            )
        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        val tree = e.getData(TreeDataKey.TREE_MODEL) as ElasticsearchTree
        if (!tree.isSelectionEmpty
            && tree.selectionPaths[0].lastPathComponent is ElasticsearchConnectionTreeNode
        ) {
            val connectionTreeNode = tree.selectionPaths[0].lastPathComponent as ElasticsearchConnectionTreeNode
            connectionTreeNode.disconnect()
            connectionTreeNode.removeAllChildren()
            val model = tree.model as ElasticsearchTreeModel
            model.reload(connectionTreeNode)
        }

    }
}
