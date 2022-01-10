package com.github.dinbtechit.es.actions.popup.index

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class FreezeIndexAction : AnAction(
    "Freeze", "Freeze index", null
) {

    override fun actionPerformed(e: AnActionEvent) {
        println("Freeze Index")
    }

}
