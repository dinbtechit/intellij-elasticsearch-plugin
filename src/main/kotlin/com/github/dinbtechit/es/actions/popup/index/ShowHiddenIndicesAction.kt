package com.github.dinbtechit.es.actions.popup.index

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction

var selected = true

class ShowHiddenIndicesAction :
    ToggleAction("Show Hidden Indices") {

    override fun isSelected(e: AnActionEvent): Boolean {
        return selected
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        selected = state
    }
}
