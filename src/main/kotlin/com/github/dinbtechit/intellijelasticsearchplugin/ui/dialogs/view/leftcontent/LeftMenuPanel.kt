package com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.view.leftcontent

import com.github.dinbtechit.intellijelasticsearchplugin.actions.RefreshAction
import com.github.dinbtechit.intellijelasticsearchplugin.actions.newdialog.AddAction
import com.github.dinbtechit.intellijelasticsearchplugin.actions.newdialog.DeleteAction
import com.github.dinbtechit.intellijelasticsearchplugin.actions.newdialog.DuplicateAction
import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ConnectionInfo
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.DialogModelController
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.DialogModelController.EventType
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.actionSystem.Separator
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.ui.ColoredListCellRenderer
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import icons.ElasticsearchIcons
import java.awt.BorderLayout
import java.awt.Color
import java.beans.PropertyChangeEvent
import javax.swing.*
import javax.swing.border.EmptyBorder


class LeftMenuPanel(
    private val controller: DialogModelController
) : SimpleToolWindowPanel(true, true) {

    // fields
    private val addAction = AddAction(AllIcons.General.Add, controller)
    private val deleteAction = DeleteAction(AllIcons.General.Remove, controller)
    private val duplicateAction = DuplicateAction(AllIcons.Actions.Copy, controller)

    private val connectionListModel = DefaultListModel<ConnectionInfo>().apply {
        addAll(controller.getAllConnectionInfos())
    }
    val connectionsListField: JBList<ConnectionInfo> = JBList(connectionListModel).apply {
        selectedIndex = 0
    }


    init {
        initUIComponents()
        connectionListModel.elements().toList()
        subscribeToListeners()
    }

    companion object {
        val backgroundColor = Color.decode("#2F3233")
    }

    private fun initUIComponents() {

        // ToolBar
        val group = DefaultActionGroup()
        val manager = ActionManager.getInstance()
        val refreshAction = manager.getAction(RefreshAction.ID)

        group.apply {
            add(addAction)
            add(deleteAction)
            add(Separator())
            add(refreshAction)
            add(duplicateAction)
        }

        val actionToolbar = manager.createActionToolbar("Elasticsearch", group, true)
        actionToolbar.setTargetComponent(this)
        toolbar = actionToolbar.component


        // List
        connectionsListField.apply {
            background = backgroundColor
            selectionMode = ListSelectionModel.SINGLE_SELECTION
        }
        connectionsListField.cellRenderer = ConnectionListCellRenderer()

        // Seal everything in a panel
        val panel = JPanel(BorderLayout(0, 0)).apply {
            border = EmptyBorder(2, 2, 2, 4)
            background = backgroundColor
        }

        val jbScrollPane = JBScrollPane(
            connectionsListField,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        )
        panel.add(jbScrollPane, BorderLayout.CENTER)
        setContent(panel)

        connectionsListField.addListSelectionListener { selectionEvent ->
            if (!selectionEvent.valueIsAdjusting || connectionsListField.selectedIndex == -1) {
                if (connectionsListField.selectedIndex != -1) {
                    val connectionInfo = connectionListModel[connectionsListField.selectedIndex]
                    controller.selectConnectionInfo(connectionInfo)
                } else {
                    controller.unselectCollectionInfo()
                }
            }
        }
    }

    override fun getData(dataId: String): Any? {
        super.getData(dataId)
        controller.toggleApplyButton(connectionListModel.elements().toList())
        if (DialogModelController.DataKey.ES_CONNECTIONS.name == dataId) {
            return connectionListModel
        } else if (DialogModelController.DataKey.SELECTED_CONNECTION.name == dataId) {
            return connectionsListField.selectedValue
        } else if (DialogModelController.DataKey.SELECTED_CONNECTION_INDEX.name == dataId) {
            return connectionsListField.selectedIndex
        }
        return null
    }

    private fun subscribeToListeners() {
        controller.addPropertyChangeListener {
            when {
                it.propertyName.equals(EventType.ADD_CONNECTION.name) -> addConnection(it)
                it.propertyName.equals(EventType.SELECTED.name) -> selectConnection(it)
                it.propertyName.equals(EventType.UNSELECTED.name) -> unselectConnection()
                it.propertyName.equals(EventType.SAVE.name) -> saveConnection(connectionListModel.elements().toList())
                it.propertyName.equals(EventType.NAME_CHANGE.name) -> nameChange(it)
                it.propertyName.equals(EventType.UPDATE_CONNECTION_INFO.name) -> updateConnectionInfo(it)

            }
        }
    }

    private fun updateConnectionInfo(it: PropertyChangeEvent) {
        if (it.newValue is ConnectionInfo) {
            val newValue = it.newValue as ConnectionInfo
            val index = connectionListModel.indexOf(newValue)
            if (index >= 0) {
                connectionListModel.get(index).apply {
                    name = newValue.name
                    hostname = newValue.hostname
                    port = newValue.port
                    authenticationType = newValue.authenticationType
                    username = newValue.username
                    password = newValue.password
                    url = newValue.url
                }
                connectionsListField.revalidate()
                connectionsListField.repaint()
            }
        }
    }

    private fun nameChange(it: PropertyChangeEvent) {
        if (it.newValue is String) {
            val newName = it.newValue as String
            connectionListModel.get(connectionsListField.selectedIndex).name = newName
            connectionsListField.revalidate()
            connectionsListField.repaint()
        }
    }

    private fun saveConnection(connections: List<ConnectionInfo>) {
        controller.save(connections)
    }

    private fun addConnection(it: PropertyChangeEvent) {
        if (it.newValue is ConnectionInfo) {
            val allConnections = connectionListModel
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
        }
    }

    private fun selectConnection(it: PropertyChangeEvent) {
        val value = it.newValue
        when (value) {
            is ConnectionInfo -> {
                val selectedIndex = connectionListModel.indexOf(value)
                if (selectedIndex > -1) {
                    connectionsListField.selectedIndex = selectedIndex
                    DeleteAction.ENABLED = true
                    DuplicateAction.ENABLED = true
                }
            }
            is Int -> {
                if (value != -1) {
                    connectionsListField.selectedIndex = value
                    DeleteAction.ENABLED = true
                    DuplicateAction.ENABLED = true
                }
            }
        }
    }

    private fun unselectConnection() {
        val disable = false
        DeleteAction.ENABLED = disable
        DuplicateAction.ENABLED = disable
    }


    internal class ConnectionListCellRenderer : ColoredListCellRenderer<ConnectionInfo>() {
        override fun customizeCellRenderer(
            list: JList<out ConnectionInfo>,
            value: ConnectionInfo?,
            index: Int,
            selected: Boolean,
            hasFocus: Boolean
        ) {
            icon = ElasticsearchIcons.logo_16px
            append(value?.name ?: "@${value?.hostname}")
        }
    }
}
