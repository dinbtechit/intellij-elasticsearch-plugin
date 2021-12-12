package com.github.dinbtechit.es.ui.dialogs.view.leftcontent.toolbar

import com.github.dinbtechit.es.services.state.ConnectionInfo
import com.github.dinbtechit.es.ui.dialogs.DialogModelController
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import java.util.*
import javax.swing.DefaultListModel
import javax.swing.Icon

class DuplicateAction(
    icon: Icon,
    private val controller: DialogModelController
) : AnAction(icon) {
    companion object {
        const val ID = "com.github.dinbtechit.elasticsearch.newdialog.actions.DuplicateAction"
        var ENABLED = false
    }

    override fun actionPerformed(e: AnActionEvent) {
        val duplicateConnectionIndex = e.getData(DialogModelController.DataKey.SELECTED_CONNECTION_INDEX) as Int
        val allConnections = e.getData(DialogModelController.DataKey.ES_CONNECTIONS) as DefaultListModel<ConnectionInfo>
        var count = 0
        var selectedIndex = 0
        val duplicateConnection = allConnections.get(duplicateConnectionIndex).copy().apply {
            uuid = UUID.randomUUID().toString()
        }
        val nameRegex = Regex("[\\d-]+$") // Selects the last seq
        var originalName = nameRegex.replace(duplicateConnection.name, "").trim()
        val listOfConnections = allConnections.elements().toList().toMutableList().apply {
            sortWith(
                compareBy { conn ->
                    val regex = Regex("\\D")
                    val num = regex.replace(conn.name, "")
                    if (num.isEmpty()) 0 else Integer.parseInt(num)
                }
            )
        }
        for (conn in listOfConnections) {
            if (conn.name.trim() == originalName) {
                count++
                selectedIndex = allConnections.indexOf(conn)
                originalName = "${nameRegex.replace(originalName, "").trim()} $count"
            }
        }

        if (count == 0) duplicateConnection.name = nameRegex.replace(originalName, "").trim()
        else duplicateConnection.name = originalName
        allConnections.add(selectedIndex, duplicateConnection)
        controller.selectConnectionInfo(selectedIndex)
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = ENABLED
    }
}
