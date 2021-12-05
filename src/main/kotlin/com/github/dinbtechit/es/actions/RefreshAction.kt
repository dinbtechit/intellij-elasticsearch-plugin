package com.github.dinbtechit.es.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class RefreshAction : AnAction() {

    companion object {
        const val ID = "com.github.dinbtechit.es.actions.RefreshAction"
    }

    override fun actionPerformed(e: AnActionEvent) {
        println("Refresh Clicked")
    }
}
