package com.github.dinbtechit.es.actions.editorToolbar

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class InsertRowAction : AnAction(
    "Insert", "Insert Row", AllIcons.General.Add
) {

    override fun actionPerformed(e: AnActionEvent) {
        println("Refresh Index")
    }

}
