package com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs

import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ConnectionInfo
import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ElasticSearchConfig
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.newdialog.leftcontent.LeftMenuPanel
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.newdialog.rightcontent.RightContentPanel
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.Colors
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.layout.panel
import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.border.MatteBorder


class AddNewInstanceDialogWrapper(private val project: Project) : DialogWrapper(true) {

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
        val oneSplitter = OnePixelSplitter(false, .3f)
        oneSplitter.apply {
            firstComponent = LeftMenuPanel()
            secondComponent = RightContentPanel()
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
}
