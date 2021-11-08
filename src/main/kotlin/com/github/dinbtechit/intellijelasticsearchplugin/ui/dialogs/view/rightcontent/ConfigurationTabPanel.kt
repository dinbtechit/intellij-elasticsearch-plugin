package com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.view.rightcontent

import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ConnectionInfo
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.DialogModelController
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBPasswordField
import com.intellij.uiDesigner.core.Spacer
import com.intellij.util.ui.UIUtil
import java.awt.*
import javax.swing.*


class ConfigurationTabPanel(model: DialogModelController) : JPanel() {

    // Fields
    private val hostLabel = JLabel()
    private val hostTextField = JTextField()
    private val portLabel = JLabel()
    private val portTextField = JTextField()
    private val authenticationLabel = JLabel()
    private val authTypeComboBox = ComboBox<String>(arrayOf("User & Password", "No Auth"))
    private val userLabel = JLabel()
    private val userTextField = JTextField()
    private val passwordLabel = JLabel()
    private val passwordField = JBPasswordField()
    private val urlTextField = JTextField()
    private val urlLabel = JLabel()
    private val urlHelpTextLabel = JLabel()

    // Containers
    private val contentPanel = JPanel()
    private val gridBagConstraints = GridBagConstraints()


    init {
        name = "ConfigurationTabPanel"
        layout = BorderLayout(0, 0)
        initUIComponents()
        model.addPropertyChangeListener {
            if (it.newValue is ConnectionInfo) {
                val newValue = it.newValue as ConnectionInfo
                this.hostTextField.text = newValue.hostname
                this.portTextField.text = newValue.port.toString()
                this.urlTextField.text = newValue.url
            }
        }
    }

    private fun initUIComponents() {
        contentPanel.apply {
            // Settings
            layout = GridBagLayout()
            preferredSize = Dimension(500, 300)
            border = BorderFactory.createEmptyBorder(10, 5, 8, 0)
            gridBagConstraints.insets = Insets(2, 2, 2, 2)

            add(Spacer().apply { preferredSize = Dimension(50, 50) })

            // Components
            // Host
            add(hostLabel.apply {
                text = "Host:"
                preferredSize = Dimension(50, 30)
            }, createGbc(0, 0))
            add(Box.createHorizontalStrut(300))
            add(hostTextField.apply {
                minimumSize = Dimension(300, 30)
                preferredSize = Dimension(300, 30)
            }, createGbc(1, 0).apply {
                fill = GridBagConstraints.HORIZONTAL
            })

            // Port
            add(portLabel.apply {
                text = "Port: "
                preferredSize = Dimension(50, 30)
            }, createGbc(2, 0))
            add(portTextField.apply {
                minimumSize = Dimension(100, 30)
                preferredSize = Dimension(100, 30)
            }, createGbc(3, 0))

            // Authentication
            add(authenticationLabel.apply {
                text = "Authentication: "
                preferredSize = Dimension(110, 30)
            }, createGbc(0, 1))
            add(authTypeComboBox.apply {
                minimumSize = Dimension(150, 30)
                preferredSize = Dimension(150, 30)
            }, createGbc(1, 1).apply {
                fill = GridBagConstraints.NONE
            })

            // User
            add(userLabel.apply {
                text = "User: "
                preferredSize = Dimension(50, 30)
            }, createGbc(0, 2))
            add(userTextField.apply {
                minimumSize = Dimension(300, 30)
                preferredSize = Dimension(300, 30)
            }, createGbc(1, 2))

            // password
            add(passwordLabel.apply {
                text = "Password: "
                preferredSize = Dimension(80, 30)
            }, createGbc(0, 3))
            add(passwordField.apply {
                minimumSize = Dimension(300, 30)
                preferredSize = Dimension(300, 30)
            }, createGbc(1, 3))

            // url
            add(urlLabel.apply {
                text = "Url: "
                preferredSize = Dimension(50, 30)
            }, createGbc(0, 4))
            add(urlTextField.apply {
                minimumSize = Dimension(300, 30)
                preferredSize = Dimension(300, 30)
            }, createGbc(1, 4))

            add(urlHelpTextLabel.apply {
                text = "Overrides settings above"
                preferredSize = Dimension(300, 30)
                font = UIUtil.getFont(UIUtil.FontSize.SMALL, super.getFont())
            }, createGbc(1, 5, true).apply {
                anchor = GridBagConstraints.FIRST_LINE_START
            })

        }
        add(contentPanel, BorderLayout.CENTER)
    }

    private fun createGbc(x: Int, y: Int, isLastElement: Boolean = false): GridBagConstraints {
        val westInsects = Insets(5, 0, 5, 5)
        return GridBagConstraints().apply {
            gridx = x
            gridy = y
            gridwidth = 1
            gridheight = if (isLastElement) 10 else 1
            anchor = if (x == 0) GridBagConstraints.WEST else GridBagConstraints.LINE_START
            fill = if (x == 0) GridBagConstraints.BOTH else GridBagConstraints.REMAINDER
            insets = westInsects
            weightx = if (x == 0 || x == 2) 0.1 else 1.0
            weighty = if (isLastElement) 1.0 else 0.0
        }
    }

}
