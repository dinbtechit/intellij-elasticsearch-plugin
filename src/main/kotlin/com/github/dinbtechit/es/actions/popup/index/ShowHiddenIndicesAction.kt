package com.github.dinbtechit.es.actions.popup.index

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction


class ShowHiddenIndicesAction :
    ToggleAction("Show Hidden Indices") {

    companion object {
        var selected = false
    }

    override fun isSelected(e: AnActionEvent): Boolean {
        return selected
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        selected = state
    }
}
