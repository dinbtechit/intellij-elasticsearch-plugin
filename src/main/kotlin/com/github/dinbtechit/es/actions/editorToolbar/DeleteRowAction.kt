package com.github.dinbtechit.es.actions.editorToolbar

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class DeleteRowAction : AnAction(
    "Delete", "Delete row", AllIcons.General.Remove
) {

    override fun actionPerformed(e: AnActionEvent) {
        println("Refresh Index")
    }

}
