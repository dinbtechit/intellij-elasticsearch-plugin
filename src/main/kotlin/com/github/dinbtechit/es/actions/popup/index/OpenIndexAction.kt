package com.github.dinbtechit.es.actions.popup.index

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class OpenIndexAction : AnAction(
    "Open", "Open index", null
) {

    override fun actionPerformed(e: AnActionEvent) {
        println("Open Index")
    }

}
