package com.github.dinbtechit.es.actions.popup.new

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import icons.ElasticsearchIcons

class NewAliasAction : AnAction(
    "Alias", "Alias", ElasticsearchIcons.esAliases
) {

    override fun actionPerformed(e: AnActionEvent) {
       println("New alias")
    }

}
