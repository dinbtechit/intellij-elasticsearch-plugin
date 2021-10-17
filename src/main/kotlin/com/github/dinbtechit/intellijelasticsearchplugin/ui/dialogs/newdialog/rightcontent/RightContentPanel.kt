package com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.newdialog.rightcontent

import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTabbedPane
import com.intellij.ui.components.panels.NonOpaquePanel
import com.intellij.uiDesigner.core.Spacer
import java.awt.*
import javax.swing.*

class RightContentPanel : NonOpaquePanel() {

    // Fields
    private val nameTextField: JTextField = JTextField()
    private val nameLabel = JLabel()

    // Containers
    private val topPanel = JPanel()
    private val tabbedPanel = JBTabbedPane()

    // Style/Constrains
    private val gridBagConstraints =  GridBagConstraints()

    init {
        layout = BorderLayout(0, 0)
        initUIComponents()
    }

    private fun initUIComponents() {

        /* Start of Top panel*/
        // Top Panel
        topPanel.apply {
            border = BorderFactory.createEmptyBorder(8, 12, 8, 12)
            preferredSize = Dimension(500, 50)
            layout = GridBagLayout()

            add(Spacer())

            // NameLabel
            add(nameLabel.apply {
                text = "Name:"
                horizontalTextPosition = SwingConstants.LEFT
                preferredSize = Dimension(50, 18)
            }, gridBagConstraints.apply {
                gridx = 0
                gridy = 0
                weightx = 0.5
                weighty = 0.5
                anchor = GridBagConstraints.LINE_START
                insets = Insets(0, 0, 0, 5)
            })

            // NameTextField
            add(nameTextField.apply {
                minimumSize = Dimension(350, 30)
                preferredSize = Dimension(350, 30)
            }, gridBagConstraints.apply {
                gridx = 1
                gridy = 0
                gridwidth = 3
                weightx = 3.0
                insets = Insets(0, 0, 0, 0)
            })
        }

        add(topPanel, BorderLayout.NORTH)
        /* End of Top panel*/

        // Start of Tabbed Panel
        tabbedPanel.apply {
            // Style

            // Content Starts
            // 1. Configuration Tab
            add("Configuration", JBScrollPane(ConfigurationTabPanel()).apply {
                border = BorderFactory.createEmptyBorder()
                verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
                horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
            })

            // 2. SSL Tab
            add("SSL", JBScrollPane(JPanel()).apply {
                border = BorderFactory.createEmptyBorder()
                verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
                horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
            })
        }
        add(tabbedPanel, BorderLayout.CENTER)
        // End of Tabbed Panel
    }
}
