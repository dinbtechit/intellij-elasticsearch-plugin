package com.github.dinbtechit.intellijelasticsearchplugin.actions.newdialog

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class AddAction : AnAction() {
    companion object {
        const val ID = "com.github.dinbtechit.intellijelasticsearchplugin.actions.AddAction"
    }

    override fun actionPerformed(e: AnActionEvent) {
        println("Add Clicked")
    }
}
