package com.github.dinbtechit.es.actions.popup.index

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateUpdateAliasAction : AnAction(
    "Create/Update Alias", "Create or update alias", AllIcons.Modules.EditFolder
) {

    override fun actionPerformed(e: AnActionEvent) {
       println("Create/Update alias")
    }

}
