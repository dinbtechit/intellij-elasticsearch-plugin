package com.github.dinbtechit.intellijelasticsearchplugin.actions.newdialog

import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.AddNewInstanceDialogWrapper
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class DeleteAction : AnAction() {
    companion object {
        const val ID = "com.github.dinbtechit.intellijelasticsearchplugin.actions.DeleteAction"
    }

    override fun actionPerformed(e: AnActionEvent) {
        println("Delete Clicked")
    }
}
