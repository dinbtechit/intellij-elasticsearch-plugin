package com.github.dinbtechit.intellijelasticsearchplugin.actions.newdialog

import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.controller.NewDialogController
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import javax.swing.Icon

class DuplicateAction(
    private val icon: Icon,
    private val controller: NewDialogController): AnAction(icon) {
    companion object {
        const val ID = "com.github.dinbtechit.elasticsearch.newdialog.actions.DuplicateAction"
        var ENABLED = false
    }

    override fun actionPerformed(e: AnActionEvent) {
        println("duplicate Clicked")
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = ENABLED
    }
}
