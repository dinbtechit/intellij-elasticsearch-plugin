package com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.view.rightcontent

import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ConnectionInfo
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.DialogModelController
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBPasswordField
import com.intellij.uiDesigner.core.Spacer
import com.intellij.util.ui.UIUtil
import java.awt.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.*


class ConfigurationTabPanel(private val controller: DialogModelController) : JPanel() {

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

    // other variables
    private var connectionInfo = ConnectionInfo()

    init {
        name = "ConfigurationTabPanel"
        layout = BorderLayout(0, 0)
        initUIComponents()
        controller.addPropertyChangeListener {
            when (it.propertyName) {
                DialogModelController.EventType.SELECTED.name -> {
                    if (it.newValue is ConnectionInfo) {
                        val newValue = it.newValue as ConnectionInfo
                        connectionInfo = newValue
                        this.hostTextField.text = newValue.hostname
                        this.portTextField.text = newValue.port.toString()
                        this.userTextField.text = newValue.username
                        if (newValue.password != null && newValue.password!!.isNotEmpty()) {
                            this.passwordField.text = String(newValue.password!!)
                            this.passwordField.setPasswordIsStored(true)
                        } else {
                            this.passwordField.text = ""
                            this.passwordField.setPasswordIsStored(false)
                        }
                        this.authTypeComboBox.selectedIndex = newValue.authenticationType
                        this.urlTextField.text = newValue.url

                    }
                }
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
                addKeyListener(AddKeyListener())
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
                addKeyListener(AddKeyListener())
            }, createGbc(3, 0))

            // Authentication
            add(authenticationLabel.apply {
                text = "Authentication: "
                preferredSize = Dimension(110, 30)
            }, createGbc(0, 1))
            add(authTypeComboBox.apply {
                minimumSize = Dimension(150, 30)
                preferredSize = Dimension(150, 30)
                addItemListener {
                    if (it.item == "No Auth") {
                        changeAuthFieldVisibility(false)
                    } else {
                        changeAuthFieldVisibility(true)
                    }
                }
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
                addKeyListener(AddKeyListener())
            }, createGbc(1, 2))

            // password
            add(passwordLabel.apply {
                text = "Password: "
                preferredSize = Dimension(80, 30)
            }, createGbc(0, 3))
            add(passwordField.apply {
                minimumSize = Dimension(300, 30)
                preferredSize = Dimension(300, 30)
                addKeyListener(AddKeyListener())
            }, createGbc(1, 3))
            add(JBCheckBox("Show password").apply {
                val checkbox = this
                addActionListener {
                    if (checkbox.isSelected) {
                        passwordField.echoChar = 0.toChar()
                    } else {
                        passwordField.echoChar = 0x2022.toChar()
                    }
                }
            }, createGbc(1, 4))

            // url
            add(urlLabel.apply {
                text = "Url: "
                preferredSize = Dimension(50, 30)
            }, createGbc(0, 5))
            add(urlTextField.apply {
                minimumSize = Dimension(300, 30)
                preferredSize = Dimension(300, 30)
            }, createGbc(1, 5))

            add(urlHelpTextLabel.apply {
                text = "Overrides settings above"
                preferredSize = Dimension(300, 30)
                font = UIUtil.getFont(UIUtil.FontSize.MINI, super.getFont())
                foreground = UIUtil.getLabelDisabledForeground().brighter()
            }, createGbc(1, 6, isLastElement = true).apply {
                anchor = GridBagConstraints.FIRST_LINE_START
                insets = Insets(-10, 0, 0, 0)
            })

        }
        add(contentPanel, BorderLayout.CENTER)
    }


    inner class AddKeyListener : KeyAdapter() {
        override fun keyReleased(e: KeyEvent?) {
            println(e?.component)
            updateConnectionInfo()
        }
    }

    private fun updateConnectionInfo() {
        controller.updateConnectionInfo(
            connectionInfo.apply {
                hostname = hostTextField.text
                port =
                    Integer.valueOf(portTextField.text)
                        ?: throw NumberFormatException("Port has to be a valid number")
                authenticationType = authTypeComboBox.selectedIndex
                username = userTextField.text
                password = passwordField.password
                url = urlTextField.text
            }
        )
    }

    private fun changeAuthFieldVisibility(isVisible: Boolean) {
        userLabel.isVisible = isVisible
        userTextField.isVisible = isVisible
        passwordLabel.isVisible = isVisible
        passwordField.isVisible = isVisible
        updateConnectionInfo()
        userTextField.repaint()
        passwordField.repaint()
    }

    private fun createGbc(
        x: Int, y: Int, width: Int = 1, height: Int = 1,
        isLastElement: Boolean = false
    ): GridBagConstraints {
        val westInsects = Insets(5, 0, 5, 5)
        return GridBagConstraints().apply {
            gridx = x
            gridy = y
            gridwidth = width
            gridheight = if (isLastElement) 10 else height
            anchor = if (x == 0) GridBagConstraints.WEST else GridBagConstraints.LINE_START
            fill = if (x == 0) GridBagConstraints.BOTH else GridBagConstraints.REMAINDER
            insets = westInsects
            weightx = if (x == 0 || x == 2) 0.1 else 1.0
            weighty = if (isLastElement) 1.0 else 0.0
        }
    }

}
