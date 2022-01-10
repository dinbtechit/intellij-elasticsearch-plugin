package com.github.dinbtechit.es.actions.popup.index

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class DeleteIndexAction : AnAction(
    "Delete", "Delete index", AllIcons.General.Remove
) {

    override fun actionPerformed(e: AnActionEvent) {
        println("New alias")
    }

}
