package com.github.dinbtechit.es.actions.popup.index

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class SettingsIndexAction : AnAction(
    "Settings", "Settings index", AllIcons.General.Settings
) {

    override fun actionPerformed(e: AnActionEvent) {
       println("Rollover")
    }

}
