package com.github.dinbtechit.es.actions.popup.index

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateUpdateTemplateAction : AnAction(
    "Create/Update Template", "Edit index data", AllIcons.Modules.EditFolder
) {

    override fun actionPerformed(e: AnActionEvent) {
       println("New alias")
    }

}
