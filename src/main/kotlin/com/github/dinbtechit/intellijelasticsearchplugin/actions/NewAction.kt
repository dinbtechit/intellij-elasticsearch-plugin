package com.github.dinbtechit.intellijelasticsearchplugin.actions

import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.view.NewConnectionDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class NewAction : AnAction() {
    companion object {
        const val ID = "com.github.dinbtechit.intellijelasticsearchplugin.actions.NewAction"
    }

    override fun actionPerformed(e: AnActionEvent) {
        if (NewConnectionDialog(e.project!!).showAndGet()) {
            println("New Clicked")
        }
    }
}
