package com.github.dinbtechit.es.actions.editorToolbar.secondGroup

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PopupAction
import com.intellij.openapi.actionSystem.ex.ComboBoxAction
import icons.ElasticsearchIcons

class EditorSettingsAction : AnAction(
    "View", "Change view", AllIcons.General.Gear
), PopupAction {

    override fun actionPerformed(e: AnActionEvent) {
        println("Refresh Index")
    }

}
