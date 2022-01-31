package com.github.dinbtechit.es.ui.editor.components.table.action

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.ui.AnimatedIcon
import icons.ElasticsearchIcons

class ColumnFilterActionGroup : DefaultActionGroup(
    "ColumnFilter", true
), DumbAware, StartupActivity.DumbAware {

    var isTableSelected = true
    var isJsonSelected = false


    init {
        add(TableView())
        add(JsonView())
    }

    private fun toggleSelection(action: String) {
        when (action) {
            "Table" -> {
                isTableSelected = true
                isJsonSelected = false
            }
            "Json" -> {
                isTableSelected = false
                isJsonSelected = true
            }
        }
    }


    override fun update(e: AnActionEvent) {
        e.presentation.isMultipleChoice = true
        e.presentation.icon = AllIcons.General.Filter
    }

    inner class TableView : ToggleAction("Table") {
        override fun isSelected(e: AnActionEvent): Boolean {
            return isTableSelected
        }

        override fun setSelected(e: AnActionEvent, state: Boolean) {
            toggleSelection(this.templatePresentation.text)
        }

    }

    inner class JsonView : ToggleAction("Json") {
        override fun isSelected(e: AnActionEvent): Boolean {
            return isJsonSelected
        }

        override fun setSelected(e: AnActionEvent, state: Boolean) {
            toggleSelection(this.templatePresentation.text)
        }

    }

    override fun runActivity(project: Project) {
        templatePresentation.icon = AnimatedIcon()
    }
}
