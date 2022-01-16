package com.github.dinbtechit.es.actions.editorToolbar.secondGroup

import com.intellij.openapi.actionSystem.*
import icons.ElasticsearchIcons

class ChangeViewActionGroup : DefaultActionGroup(
    "View", true
) {

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
        e.presentation.icon = ElasticsearchIcons.Action.View
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
}
