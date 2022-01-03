package com.github.dinbtechit.es.actions

import com.github.dinbtechit.es.shared.ProjectUtil
import com.github.dinbtechit.es.ui.toolwindow.models.TreeDataKey
import com.github.dinbtechit.es.ui.toolwindow.service.TreeModelController
import com.github.dinbtechit.es.ui.toolwindow.tree.ElasticsearchTree
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.KeyboardShortcut
import com.intellij.openapi.actionSystem.ShortcutSet
import com.intellij.openapi.components.service
import java.awt.event.KeyEvent
import javax.swing.KeyStroke

class RefreshAction : AnAction(
    "Refresh", "Refresh Elasticsearch connection", AllIcons.Actions.Refresh
) {
    var isEnabled = false
    val controller = ProjectUtil.currentProject().service<TreeModelController>()

    init {
        shortcutSet = ShortcutSet {
            arrayOf(
                KeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_F5, KeyEvent.CTRL_DOWN_MASK), null)
            )
        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        println("Refresh Clicked")
    }

    override fun update(e: AnActionEvent) {
        if (e.getData(TreeDataKey.TREE_MODEL) != null) {
            val tree = e.getData(TreeDataKey.TREE_MODEL) as ElasticsearchTree
            e.presentation.isEnabled = !tree.isSelectionEmpty
        }
    }
}
