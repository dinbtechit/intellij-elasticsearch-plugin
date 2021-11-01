package com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.view.leftcontent

import com.github.dinbtechit.intellijelasticsearchplugin.actions.RefreshAction
import com.github.dinbtechit.intellijelasticsearchplugin.actions.newdialog.AddAction
import com.github.dinbtechit.intellijelasticsearchplugin.actions.newdialog.DeleteAction
import com.github.dinbtechit.intellijelasticsearchplugin.actions.newdialog.DuplicateAction
import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ConnectionInfo
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.controller.NewDialogController
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.model.PropertyChangeModel
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.model.PropertyChangeModel.EventType
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
    private val controller: NewDialogController,
    private val modelListener: PropertyChangeModel
) : SimpleToolWindowPanel(true, true) {

    // fields
    val addAction = AddAction(AllIcons.General.Add, controller)
    val deleteAction = DeleteAction(AllIcons.General.Remove, controller)
    val duplicateAction = DuplicateAction(AllIcons.Actions.Copy, controller)

    val connectionListModel = DefaultListModel<ConnectionInfo>().apply {
        addAll(controller.getAllConnections())
    }
    val connectionsListField: JBList<ConnectionInfo> = JBList(connectionListModel)


    init {
        initUIComponents()
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
                    /*println("Fired Selection")*/
                } else {
                    controller.unselectConnectionInfo()
                    /*println("Nothing is Selected")*/
                }
            }
        }
    }

    override fun getData(dataId: String): Any? {
        super.getData(dataId)
        if (PropertyChangeModel.DataKey.ES_CONNECTIONS.name == dataId) {
            return connectionListModel
        } else if (PropertyChangeModel.DataKey.SELECTED_CONNECTION.name == dataId) {
            return connectionsListField.selectedValue
        } else if (PropertyChangeModel.DataKey.SELECTED_CONNECTION_INDEX.name == dataId) {
            return connectionsListField.selectedIndex
        }
        return null
    }

    private fun subscribeToListeners() {
        modelListener.addPropertyChangeListener {
            if (it.propertyName.equals(EventType.ADD_CONNECTION.name)) {
                addConnection(it)
            } else if (it.propertyName.equals(EventType.SELECTED.name)) {
                selectConnection(it)
            } else if (it.propertyName.equals(EventType.UNSELECTED.name)) {
                unselectConnection()
            }
        }
    }

    private fun addConnection(it: PropertyChangeEvent) {
        if (it.newValue is ConnectionInfo) {
            connectionListModel.add(0, it.newValue as ConnectionInfo)
            connectionsListField.selectedIndex = 0
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
