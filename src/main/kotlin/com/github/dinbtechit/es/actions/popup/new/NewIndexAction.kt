package com.github.dinbtechit.es.actions.popup.new

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import icons.ElasticsearchIcons

class NewIndexAction : AnAction(
    "Index", "New index", ElasticsearchIcons.esIndices
) {

    override fun actionPerformed(e: AnActionEvent) {
       println("New Index")
    }

}
