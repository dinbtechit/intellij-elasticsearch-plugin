package com.github.dinbtechit.es.actions.editorToolbar

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class RevertChangeAction : AnAction(
    "Revert Changes", "Revert Changes", AllIcons.Actions.Undo
) {

    override fun actionPerformed(e: AnActionEvent) {
        println("Refresh Index")
    }

}
