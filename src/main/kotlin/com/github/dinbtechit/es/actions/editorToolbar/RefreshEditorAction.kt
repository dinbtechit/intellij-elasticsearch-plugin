package com.github.dinbtechit.es.actions.editorToolbar

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class RefreshEditorAction : AnAction(
    "Refresh", "Refresh index", AllIcons.Actions.Refresh
) {

    override fun actionPerformed(e: AnActionEvent) {
        println("Refresh Index")
    }

}
