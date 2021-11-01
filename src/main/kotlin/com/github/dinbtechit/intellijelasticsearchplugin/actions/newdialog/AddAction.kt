package com.github.dinbtechit.intellijelasticsearchplugin.actions.newdialog

import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ConnectionInfo
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.controller.NewDialogController
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.model.PropertyChangeModel
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import javax.swing.DefaultListModel
import javax.swing.Icon

class AddAction(
    private val icon: Icon,
    private val controller: NewDialogController
) : AnAction("New", "Create new connection", icon) {

    companion object {
        const val ID = "com.github.dinbtechit.intellijelasticsearchplugin.actions.AddAction"
    }

    override fun actionPerformed(e: AnActionEvent) {
        val allConnections = e.getData(PropertyChangeModel.DataKey.ES_CONNECTIONS) as DefaultListModel<ConnectionInfo>
        var nextNameIndex = allConnections.elements().toList().stream()
            .map { a -> a.name }
            .filter {
                it.matches(Regex("^@localhost([0-9]+)?$"))
            }.count()
        val lastValue = allConnections.elements().toList().stream()
            .map { a -> a.name }
            .filter {
                it.matches(Regex("^@localhost([0-9]+)?$"))
            }.findFirst().orElse("")

        val nextName = if (nextNameIndex.compareTo(0) == 0) {
            "@localhost"
        } else {
            if ("@localhost$nextNameIndex" == lastValue) {
                nextNameIndex++; "@localhost$nextNameIndex"
            } else "@localhost$nextNameIndex"
        }
        val connectionInfo = ConnectionInfo().apply {
            name = nextName
        }
        allConnections.add(0, connectionInfo)
        controller.selectConnectionInfo(0)
    }
}
