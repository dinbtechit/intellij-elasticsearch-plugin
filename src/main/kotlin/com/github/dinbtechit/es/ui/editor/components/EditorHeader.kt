package com.github.dinbtechit.es.ui.editor.components


import com.github.dinbtechit.es.actions.editorToolbar.*
import com.github.dinbtechit.es.actions.editorToolbar.secondGroup.ChangeViewActionGroup
import com.github.dinbtechit.es.actions.editorToolbar.secondGroup.EditorSettingsAction
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.ui.components.panels.HorizontalLayout
import com.intellij.ui.components.panels.NonOpaquePanel

class EditorHeader : NonOpaquePanel() {

    // Components
    init {
        layout = HorizontalLayout(0)

        val firstToolbar = ActionManager.getInstance()
            .createActionToolbar("ElasticsearchEditor", DefaultActionGroup().apply {
                add(SetRowSizeAction())
                addSeparator()
                add(RefreshEditorAction())
                addSeparator()
                add(InsertRowAction())
                add(DeleteRowAction())
                add(RevertChangeAction())
                add(CommitChangesAction())
            }, true).apply {
                setTargetComponent(this@EditorHeader)
            }

        val secondToolbar = ActionManager.getInstance()
            .createActionToolbar("ElasticsearchEditor", DefaultActionGroup().apply {
                add(ChangeViewActionGroup())
                add(EditorSettingsAction())
            }, true).apply {
                setTargetComponent(this@EditorHeader)
            }

        add(firstToolbar.component, HorizontalLayout.LEFT)
        add(secondToolbar.component, HorizontalLayout.RIGHT)
    }
}