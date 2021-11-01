package com.github.dinbtechit.intellijelasticsearchplugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class ViewPropertiesAction: AnAction() {
    companion object {
        const val ID = "com.github.dinbtechit.intellijelasticsearchplugin.actions.ViewPropertiesAction"
    }

    override fun actionPerformed(e: AnActionEvent) {
        println("View Properties Clicked")
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = false
    }
}
