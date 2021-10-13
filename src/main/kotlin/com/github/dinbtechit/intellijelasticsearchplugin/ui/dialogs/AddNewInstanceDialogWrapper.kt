package com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs

import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ConnectionInfo
import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ElasticSearchConfig
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import java.awt.Dimension
import java.awt.Toolkit
import javax.swing.JComponent


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
            config.state.connections.add(
                ConnectionInfo(name = "some randome")
            )
            for (connection in config.state.connections) {
                println(connection)
            }
            close(OK_EXIT_CODE)
        }
    }

    override fun createCenterPanel(): JComponent {
        val connectionInfo = ConnectionInfo()
        val screenSize: Dimension = Toolkit.getDefaultToolkit().getScreenSize()
        println("screenSize: $screenSize")
        val dialogPanel = panel {
        }.apply {
            preferredSize = Dimension(850, 650)
            minimumSize = Dimension(850, 650)
        }
        return dialogPanel
    }
}
