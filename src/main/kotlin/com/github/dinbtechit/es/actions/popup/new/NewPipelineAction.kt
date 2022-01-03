package com.github.dinbtechit.es.actions.popup.new

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import icons.ElasticsearchIcons

class NewPipelineAction : AnAction(
    "Pipeline", "New pipeline", ElasticsearchIcons.esPipelines
) {

    override fun actionPerformed(e: AnActionEvent) {
       println("New Pipeline")
    }

}
