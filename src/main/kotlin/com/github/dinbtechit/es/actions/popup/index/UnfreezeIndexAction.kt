package com.github.dinbtechit.es.actions.popup.index

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class UnfreezeIndexAction : AnAction(
    "Unfreeze", "Unfreeze index", null
) {

    override fun actionPerformed(e: AnActionEvent) {
        println("Unfreeze Index")
    }

}
