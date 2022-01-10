package com.github.dinbtechit.es.actions.popup.index

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CloseIndexAction : AnAction(
    "Close", "Close index", null
) {

    override fun actionPerformed(e: AnActionEvent) {
        println("Close Index")
    }

}
