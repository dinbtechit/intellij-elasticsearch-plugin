package com.github.dinbtechit.es.ui.dialogs.view.rightcontent

import com.github.dinbtechit.es.services.state.ConnectionInfo
import com.github.dinbtechit.es.ui.dialogs.DialogModelController
import com.github.dinbtechit.es.ui.dialogs.model.DialogViewType
import com.intellij.ui.HyperlinkLabel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTabbedPane
import java.awt.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.*
import javax.swing.border.MatteBorder

class RightContentPanel(
    viewType: DialogViewType,
    private val controller: DialogModelController) : JPanel() {

    // Fields
    private val nameTextField: JTextField = JTextField()
    private val nameLabel = JLabel()

    // Containers
    private val topPanel = JPanel()
    private val tabbedPanel = JBTabbedPane()
    private val bottomPanel = JPanel()

    // Style/Constrains
    private val gridBagConstraints = GridBagConstraints()

    init {
        layout = BorderLayout(0, 0)
        initUIComponents()
        subscribeToEvents();
        if (viewType == DialogViewType.NEW) {
            controller.addConnection()
        }
    }

    private fun initUIComponents() {

        /* Start of Top panel*/
        // Top Panel
        topPanel.apply {
            border = BorderFactory.createEmptyBorder(8, 10, 15, 12)
            preferredSize = Dimension(500, 70)
            layout = GridBagLayout()
            gridBagConstraints.insets = Insets(2, 2, 0, 10)

            // NameLabel
            add(nameLabel.apply {
                text = "Name:"
                horizontalTextPosition = SwingConstants.LEFT
                preferredSize = Dimension(50, 30)
            }, gridBagConstraints.apply {
                gridx = 0
                gridy = 0
                gridwidth = 1
                weightx = 0.0
                weighty = 0.5
                anchor = GridBagConstraints.LINE_START
                fill = GridBagConstraints.BOTH
            })

            // NameTextField
            add(nameTextField.apply {
                minimumSize = Dimension(400, 30)
                preferredSize = Dimension(400, 30)
                addKeyListener(object : KeyAdapter() {
                    override fun keyReleased(e: KeyEvent?) {
                        controller.changeName(nameTextField.text)
                    }
                })
            }, gridBagConstraints.apply {
                gridx = 1
                gridy = 0
                gridwidth = 1
                weightx = 1.0
                fill = GridBagConstraints.NONE
            })
        }

        add(topPanel, BorderLayout.NORTH)
        /* End of Top panel*/

        // Start of Tabbed Panel
        tabbedPanel.apply {
            // Content Starts
            // 1. Configuration Tab
            name = "tabbedPanel"
            add("Configuration", JBScrollPane(ConfigurationTabPanel(controller)).apply {
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

        bottomPanel.apply {
            preferredSize = Dimension(Int.MAX_VALUE, 50)
            isVisible = tabbedPanel.selectedIndex == 0
            border = MatteBorder(1, 0, 1, 0, Color.decode("#2F3233"))

            add(HyperlinkLabel("Test connection"))
        }
        add(bottomPanel, BorderLayout.SOUTH)
    }

    private fun subscribeToEvents() {
        controller.addPropertyChangeListener {
            if (it.newValue is ConnectionInfo) {
                val newValue = it.newValue as ConnectionInfo
                nameTextField.text = newValue.name
                nameTextField.repaint()
            }
        }
    }
}
