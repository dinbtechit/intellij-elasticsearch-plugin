package com.github.dinbtechit.es.ui.dialogs.view.rightcontent

import com.github.dinbtechit.es.services.state.ConnectionInfo
import com.github.dinbtechit.es.ui.dialogs.DialogModelController
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBPasswordField
import com.intellij.uiDesigner.core.Spacer
import com.intellij.util.ui.UIUtil
import org.apache.http.client.utils.URIBuilder
import java.awt.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.net.MalformedURLException
import java.net.URI
import java.net.URL
import javax.swing.*


class ConfigurationTabPanel(private val controller: DialogModelController) : JPanel() {

    // Fields
    private val hostLabel = JLabel()
    private val hostTextField = JTextField()
    private val portLabel = JLabel()
    private val portTextField = JTextField()
    private val authenticationLabel = JLabel()
    private val authTypeComboBox = ComboBox<String>(arrayOf("User & Password", "No Auth"))
    private val showPasswordCheckBox = JBCheckBox("Show password")
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
                DialogModelController.EventType.POPULATE_CONFIGURATION.name -> {
                    if (it.newValue is ConnectionInfo) {
                        val newValue = it.newValue as ConnectionInfo
                        connectionInfo = newValue
                        this.hostTextField.text = newValue.hostname
                        this.portTextField.text = if (newValue.port != -1) newValue.port.toString() else ""
                        this.userTextField.text = newValue.username
                        if (newValue.password != null && newValue.password!!.isNotEmpty()) {
                            this.passwordField.text = String(newValue.password!!)
                            this.passwordField.setPasswordIsStored(true)
                        } else {
                            this.passwordField.text = ""
                            this.passwordField.setPasswordIsStored(false)
                        }
                        this.urlTextField.text = newValue.url
                        this.authTypeComboBox.selectedIndex = newValue.authenticationType
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
            border = BorderFactory.createEmptyBorder(10, 5, 8, 10)
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
                name = "hostTextField"
                addKeyListener(AddKeyListener())
            }, createGbc(1, 0).apply {
                weightx = 0.95
                fill = GridBagConstraints.HORIZONTAL
            })

            // Port
            add(JPanel().apply {
                layout = GridBagLayout()
                add(portLabel.apply {
                    text = "Port: "
                }, createGbc(0, 0))
                add(portTextField.apply {
                    name = "portTextField"
                    minimumSize = Dimension(100, 30)
                    preferredSize = Dimension(100, 30)
                    addKeyListener(AddKeyListener())
                }, createGbc(1, 0).apply {
                    anchor = GridBagConstraints.LINE_START
                })
            }, createGbc(3, 0).apply {
                insets = Insets(0, 10, 0, 0)
                weightx = 0.05
                anchor = GridBagConstraints.LINE_START
            })

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
            add(showPasswordCheckBox.apply {
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
                name = "urlTextField"
                addKeyListener(AddKeyListener())
            }, createGbc(1, 5, width = 3, fill = GridBagConstraints.HORIZONTAL))

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

            val valid = when {
                e != null -> {
                    (e.keyCode in 48..57) // number keys
                            || (e.keyCode in 65..90) // letter keys
                            || (e.keyCode in 96..111) // numpad keys
                            || (e.keyCode in 192..192) // ;=,-./` (in orde
                            || (e.keyCode == 8) // backspace
                            || (e.keyCode == 127) // delete
                }
                else -> false
            }

            /*if (valid.not()) return*/

            if (e?.component?.name == "portTextField" || e?.component?.name == "hostTextField") {
                var currentUrl: URI? = null
                fun buildUrl(urlStr: String) = URIBuilder().apply {
                    val url = URL(urlStr)
                    scheme = url.protocol
                    host = url.host.trim()
                    port = url.port
                    path = url.path.trim()
                }.build()

                currentUrl = try {
                    buildUrl(urlTextField.text)
                } catch (e: MalformedURLException) {
                    null
                }
                if (currentUrl == null) {
                    currentUrl = buildUrl("http://localhost:9200")
                }
                val newUrl = URIBuilder().apply {
                    scheme = currentUrl!!.scheme
                    host = hostTextField.text.trim()
                    if (!portTextField.text.isNullOrEmpty()) {
                        port = Integer.valueOf(portTextField.text) ?: -1
                    } else port = -1
                }
                urlTextField.text = newUrl.build().toString()

            } else if (e?.component?.name == "urlTextField") {
                val url = try {
                    URL(urlTextField.text)
                } catch (e: Exception) {
                    null
                }
                if (url != null) {
                    hostTextField.text = url.host
                    portTextField.text = if (url.port != -1) url.port.toString() else ""
                } else {
                    hostTextField.text = ""
                    portTextField.text = ""
                }
            }
            updateConnectionInfo()
        }
    }

    private fun updateConnectionInfo() {
        controller.updateConnectionInfo(
            connectionInfo.apply {
                hostname = hostTextField.text
                port = try {
                    Integer.valueOf(portTextField.text)
                } catch (e: NumberFormatException) {
                    -1
                }
                authenticationType = authTypeComboBox.selectedIndex
                username = userTextField.text
                password = if (passwordField.password.size > 0) passwordField.password else null
                url = urlTextField.text
            }
        )
    }

    private fun changeAuthFieldVisibility(isVisible: Boolean) {
        userLabel.isVisible = isVisible
        userTextField.isVisible = isVisible
        passwordLabel.isVisible = isVisible
        passwordField.isVisible = isVisible
        showPasswordCheckBox.isVisible = isVisible
        userTextField.repaint()
        passwordField.repaint()
        showPasswordCheckBox.repaint()
        updateConnectionInfo()
    }

    private fun createGbc(
        x: Int, y: Int, width: Int = 1, height: Int = 1,
        fill: Int? = null,
        insets: Insets? = null,
        isLastElement: Boolean = false
    ): GridBagConstraints {
        val westInsects = Insets(5, 0, 5, 5)
        return GridBagConstraints().apply {
            this.gridx = x
            this.gridy = y
            this.gridwidth = width
            this.gridheight = if (isLastElement) 10 else height
            this.anchor = if (x == 0) GridBagConstraints.WEST else GridBagConstraints.LINE_START
            this.fill = fill ?: if (x == 0) GridBagConstraints.BOTH else GridBagConstraints.REMAINDER
            this.insets = insets ?: westInsects
            this.weightx = if (x == 0 || x == 2) 0.1 else 1.0
            this.weighty = if (isLastElement) 1.0 else 0.0
        }
    }

}
