package com.github.dinbtechit.intellijelasticsearchplugin.actions

import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ElasticSearchConfig
import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ConnectionInfo
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.AddNewInstanceDialogWrapper
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class NewAction : AnAction() {
    companion object {
        const val ID = "com.github.dinbtechit.intellijelasticsearchplugin.actions.NewAction"
    }

    override fun actionPerformed(e: AnActionEvent) {
        if (AddNewInstanceDialogWrapper(e.project!!).showAndGet()) {
            println("New Clicked")
        }
    }
}
