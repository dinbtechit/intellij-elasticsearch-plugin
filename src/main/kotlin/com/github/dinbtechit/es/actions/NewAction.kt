package com.github.dinbtechit.es.actions

import com.github.dinbtechit.es.ui.dialogs.model.DialogViewType
import com.github.dinbtechit.es.ui.dialogs.view.NewConnectionDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class NewAction : AnAction() {
    companion object {
        const val ID = "com.github.dinbtechit.es.actions.NewAction"
    }

    override fun actionPerformed(e: AnActionEvent) {
        if (NewConnectionDialog(DialogViewType.NEW, project = e.project!!).showAndGet()) {
            println("New Clicked")
        }

    }
}
