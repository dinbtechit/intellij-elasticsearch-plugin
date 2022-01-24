package com.github.dinbtechit.es.actions.editorToolbar

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class StopQueryProgressAction : AnAction(
    "Stop Refresh", "Suspend request", AllIcons.Actions.Suspend
) {

    override fun actionPerformed(e: AnActionEvent) {
        println("Stop refresh")
    }

}
