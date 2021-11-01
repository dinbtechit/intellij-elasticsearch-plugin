package com.github.dinbtechit.intellijelasticsearchplugin.actions.newdialog

import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ConnectionInfo
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.controller.NewDialogController
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.model.PropertyChangeModel
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import javax.swing.DefaultListModel
import javax.swing.Icon

class DeleteAction(
    private val icon: Icon,
    private val controller: NewDialogController
) : AnAction("Delete", "Create new connection", icon) {

    init {
        templatePresentation
    }

    companion object {
        const val ID = "com.github.dinbtechit.intellijelasticsearchplugin.actions.DeleteAction"
        var ENABLED = false
    }

    override fun actionPerformed(e: AnActionEvent) {
        val deletedConnectionIndex = e.getData(PropertyChangeModel.DataKey.SELECTED_CONNECTION_INDEX) as Int
        val allConnections = e.getData(PropertyChangeModel.DataKey.ES_CONNECTIONS) as DefaultListModel<ConnectionInfo>
        allConnections.remove(deletedConnectionIndex)
        controller.selectConnectionInfo(
            if (deletedConnectionIndex == 0) {
                if (allConnections.size() > 0) 0 else -1
            } else deletedConnectionIndex - 1
        )
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = ENABLED
    }
}
