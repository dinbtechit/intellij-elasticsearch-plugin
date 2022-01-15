package com.github.dinbtechit.es.actions.editorToolbar

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import icons.ElasticsearchIcons

class CommitChangesAction : AnAction(
    "Commit Changes", "Commit Changes", ElasticsearchIcons.General.UpArrow
) {

    override fun actionPerformed(e: AnActionEvent) {
        println("Refresh Index")
    }

}
