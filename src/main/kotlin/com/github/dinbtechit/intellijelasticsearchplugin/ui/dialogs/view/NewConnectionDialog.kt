package com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.view

import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ElasticSearchConfig
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.controller.NewDialogController
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.model.PropertyChangeModel
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.view.leftcontent.LeftMenuPanel
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.view.rightcontent.RightContentPanel
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.components.JBLabel
import com.intellij.ui.layout.panel
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import com.jetbrains.rd.framework.base.deepClonePolymorphic
import java.awt.*
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.Border
import javax.swing.border.MatteBorder


class NewConnectionDialog(private val project: Project) : DialogWrapper(true) {

    private var model = PropertyChangeModel()
    private val controller = NewDialogController(model)
    private val oneSplitter = OnePixelSplitter(false, .3f)

    init {
        title = "New Elasticsearch Connections"
        init()
       // subscribeToListeners()
    }

    override fun createContentPaneBorder(): Border {
        return MatteBorder(0, 0, 1, 0, Color.decode("#2F3233"))
    }

    override fun doOKAction() {
        if (okAction.isEnabled) {
            val config = ElasticSearchConfig.getInstance(project)
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
        oneSplitter.apply {
            val leftMenuPanel = LeftMenuPanel(controller)
            val rightPanel = RightContentPanel(model)
            firstComponent = leftMenuPanel
            secondComponent = rightPanel
            splitterProportionKey = "com.github.dinbtechit.elasticsearch.dialogSplitKey"
            minimumSize = Dimension(780, 550)
            preferredSize = Dimension(850, 500)

        }
        val rootDialogPanel = panel { }.apply {
            layout = BorderLayout(0, 0)
            preferredSize = Dimension(850, 650)
            minimumSize = Dimension(850, 650)
            add(oneSplitter, BorderLayout.CENTER)
            border = MatteBorder(0, 0, 1, 0, Color.decode("#2F3233"))
        }

        return rootDialogPanel
    }

    private fun subscribeToListeners() {
        // Subscribe To List Selection Events
        model.addPropertyChangeListener {
            if (it.newValue is Int && it.newValue == -1) {
                oneSplitter.secondComponent = EmptyPanel()
            } else {
                oneSplitter.secondComponent = RightContentPanel(model)

            }
        }
    }

    inner class EmptyPanel: JPanel() {
        init {
            layout = GridBagLayout()
            add(JBLabel("Add or Modify Connection", UIUtil.ComponentStyle.SMALL), GridBagConstraints().apply {
                gridx = 0
                gridy = 0
                gridwidth = 10
                gridheight = 1
                weightx = 1.0
                weighty = 1.0
            })
        }
    }
}
