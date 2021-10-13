package com.github.dinbtechit.intellijelasticsearchplugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class DuplicateAction: AnAction() {
    companion object {
        const val ID = "com.github.dinbtechit.intellijelasticsearchplugin.actions.DuplicateAction"
    }

    override fun actionPerformed(e: AnActionEvent) {
        println("duplicate Clicked")
    }
}
