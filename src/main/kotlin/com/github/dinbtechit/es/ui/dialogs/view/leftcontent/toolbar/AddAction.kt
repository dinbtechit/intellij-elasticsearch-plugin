package com.github.dinbtechit.es.ui.dialogs.view.leftcontent.toolbar

import com.github.dinbtechit.es.services.state.ConnectionInfo
import com.github.dinbtechit.es.ui.dialogs.DialogModelController
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import javax.swing.DefaultListModel
import javax.swing.Icon

class AddAction(
    icon: Icon,
    private val controller: DialogModelController
) : AnAction("New", "Create new connection", icon) {

    override fun actionPerformed(e: AnActionEvent) {
        val allConnections = e.getData(DialogModelController.DataKey.ES_CONNECTIONS) as DefaultListModel<ConnectionInfo>
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
