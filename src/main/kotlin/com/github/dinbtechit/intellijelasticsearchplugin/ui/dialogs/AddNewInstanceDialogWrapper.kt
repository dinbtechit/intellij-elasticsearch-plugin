package com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs

import com.github.dinbtechit.intellijelasticsearchplugin.actions.RefreshAction
import com.github.dinbtechit.intellijelasticsearchplugin.actions.newdialog.AddAction
import com.github.dinbtechit.intellijelasticsearchplugin.actions.newdialog.DeleteAction
import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ConnectionInfo
import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ElasticSearchConfig
import com.github.dinbtechit.intellijelasticsearchplugin.shared.ProjectUtils
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.ui.ColoredListCellRenderer
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBPanel
import com.intellij.ui.layout.panel
import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager
import icons.ElasticsearchIcons
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Insets
import javax.swing.JComponent
import javax.swing.JList
import javax.swing.JTextField


class AddNewInstanceDialogWrapper(
    private val project: Project
) : DialogWrapper(true) {

    init {
        title = "New Elasticsearch"
        init()
    }

    override fun doOKAction() {
        if (okAction.isEnabled) {
            val config = ElasticSearchConfig().getInstance(project)
            /*config.state.connections.add(
                ConnectionInfo(name = "some randome")
            )*/
            for (connection in config.state.connections) {
                println(connection)
            }
            close(OK_EXIT_CODE)
        }
    }

    override fun createCenterPanel(): JComponent {
        val config = ElasticSearchConfig().getInstance(ProjectUtils.currentProject)
        val connectionInfo = ConnectionInfo()
        val listOfESCollections = JBList(config.state.connections)

        val leftPanel = JBPanel<SimpleToolWindowPanel>(BorderLayout())
        val mainPanel = JBPanel<SimpleToolWindowPanel>(
            GridLayoutManager(
                2,
                1,
                Insets(1, 1, 1, 1),
                0,
                0
            )
        )
        val oneSplitter = OnePixelSplitter(false, .3f)
        oneSplitter.apply {
            dividerWidth = 2
            firstComponent = ESPanel()
            val name = JTextField(connectionInfo.name)
            secondComponent = mainPanel.apply {
                add(name, GridConstraints().apply {
                    row = 1
                    hSizePolicy = GridConstraints.SIZEPOLICY_WANT_GROW
                    fill = GridConstraints.FILL_BOTH
                })
            }
            splitterProportionKey = "com.github.dintechit.elasticsearch.dialogSplitKey"
        }

        val rootDialogPanel = panel {

        }.apply {
            preferredSize = Dimension(850, 650)
            minimumSize = Dimension(850, 650)
            add(oneSplitter, GridConstraints().apply {
                row = 1
                fill = GridConstraints.FILL_BOTH
            })
        }
        return rootDialogPanel
    }
}

class ESPanel : SimpleToolWindowPanel(true, true) {

    init {
        initUI()
    }

    fun initUI() {
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
        val config = ElasticSearchConfig().getInstance(ProjectUtils.currentProject)
        val listOfESCollections = JBList(config.state.connections)
        listOfESCollections.cellRenderer = ConnectionListCellRenderer()
        setContent(listOfESCollections)
    }

    internal class ConnectionListCellRenderer: ColoredListCellRenderer<ConnectionInfo>(){
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
