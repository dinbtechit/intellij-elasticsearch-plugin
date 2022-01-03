package com.github.dinbtechit.es.actions.popup.new

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import icons.ElasticsearchIcons

class NewTemplateAction : AnAction(
    "Template", "New Template", ElasticsearchIcons.esTemplates
) {

    override fun actionPerformed(e: AnActionEvent) {
       println("New Template")
    }

}
