package com.github.dinbtechit.es.actions.popup.index

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class DeleteAllDanglingIndicesAction : AnAction(
    "Delete All Dangling Indices", "Delete all dangling indices", AllIcons.Actions.DeleteTag
) {

    override fun actionPerformed(e: AnActionEvent) {
        println("New alias")
    }

}
