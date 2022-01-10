package com.github.dinbtechit.es.actions.popup.index

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction


class ShowHiddenIndicesAction : ToggleAction("Show Hidden Indices") {
   companion object {
       var isSelected = false
   }

    override fun isSelected(e: AnActionEvent): Boolean {
        return isSelected
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        isSelected = state
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = true
    }
}
