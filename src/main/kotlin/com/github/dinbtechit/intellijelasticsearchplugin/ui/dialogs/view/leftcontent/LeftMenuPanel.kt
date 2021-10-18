package com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.view.leftcontent

import com.github.dinbtechit.intellijelasticsearchplugin.actions.RefreshAction
import com.github.dinbtechit.intellijelasticsearchplugin.actions.newdialog.AddAction
import com.github.dinbtechit.intellijelasticsearchplugin.actions.newdialog.DeleteAction
import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ConnectionInfo
import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ElasticSearchConfig
import com.github.dinbtechit.intellijelasticsearchplugin.shared.ProjectUtils
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.controller.NewDialogController
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.ui.ColoredListCellRenderer
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import icons.ElasticsearchIcons
import java.awt.BorderLayout
import java.awt.Color
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.ListSelectionModel
import javax.swing.ScrollPaneConstants
import javax.swing.border.EmptyBorder


class LeftMenuPanel(private val controller: NewDialogController) : SimpleToolWindowPanel(true, true) {

    lateinit var connectionsListField: JBList<ConnectionInfo>

    init {
        initUIComponents()
    }

    companion object {
        val backgroundColor = Color.decode("#2F3233")
    }

    private fun initUIComponents() {

        // ToolBar
        val group = DefaultActionGroup()
        val manager = ActionManager.getInstance()

        val addAction = manager.getAction(AddAction.ID)
        val deleteAction = manager.getAction(DeleteAction.ID)
        val refreshAction = manager.getAction(RefreshAction.ID)

        group.apply {
            add(addAction)
            add(deleteAction)
            add(refreshAction)
        }
        val actionToolbar = manager.createActionToolbar("Elasticsearch", group, true)
        actionToolbar.setTargetComponent(this)
        toolbar = actionToolbar.component


        // List
        val config = ElasticSearchConfig.getInstance(ProjectUtils().currentProject())
        connectionsListField = JBList(config.state.connections).apply {
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
            if (!selectionEvent.valueIsAdjusting) {
                if (connectionsListField.selectedIndex != -1) {
                    val connectionInfo = config.state.connections[connectionsListField.selectedIndex]
                    controller.selectConnectionInfo(connectionInfo)
                    println("Fireed Selection")
                } else {
                    controller.unselectConnectionInfo()
                    println("Nothing is Selected")
                }
            }
        }
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
