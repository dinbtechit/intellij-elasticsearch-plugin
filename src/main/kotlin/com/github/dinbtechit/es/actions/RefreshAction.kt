package com.github.dinbtechit.es.actions

import com.github.dinbtechit.es.shared.ProjectUtil
import com.github.dinbtechit.es.ui.toolwindow.service.TreeModelController
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service

class RefreshAction : AnAction(
    "Refresh", "Refresh Elasticsearch connection", AllIcons.Actions.Refresh
) {
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
    }

    override fun actionPerformed(e: AnActionEvent) {
        println("Refresh Clicked")
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = this.isEnabled
    }
}
