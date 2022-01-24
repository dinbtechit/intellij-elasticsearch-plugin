package com.github.dinbtechit.es.actions.editorToolbar

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import icons.ElasticsearchIcons

class PreviewQuerySubmissionAction : AnAction(
    "Preview Submit", "Preview submit", ElasticsearchIcons.DataTable.PreviewChanges
) {

    override fun actionPerformed(e: AnActionEvent) {
        println("Preview Submit")
    }

}
