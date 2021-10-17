package com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.newdialog.rightcontent

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBPasswordField
import com.intellij.uiDesigner.core.Spacer
import com.intellij.util.ui.UIUtil
import java.awt.*
import javax.swing.Box
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField


class ConfigurationTabPanel : JPanel() {

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
        layout = BorderLayout(0, 0)
        initUIComponents()
    }

    private fun initUIComponents() {
        contentPanel.apply {
            // Settings
            layout = GridBagLayout()
            preferredSize = Dimension(500, 300)
            gridBagConstraints.insets = Insets(2, 2, 2, 2)

            add(Spacer())

            // Components
            // Host
            add(hostLabel.apply {
                text = "Host:"
                preferredSize = Dimension(50, 30)
            }, gridBagConstraints.apply {
                gridx = 0
                gridy = 0
                gridwidth = 1
                weightx = 0.5
                weighty = 0.0
                anchor = GridBagConstraints.FIRST_LINE_START
            })
            add(Box.createHorizontalStrut(300))
            add(hostTextField.apply {
                minimumSize = Dimension(300, 30)
                preferredSize = Dimension(300, 30)
            }, gridBagConstraints.apply {
                gridx = 1
                gridy = 0
                anchor = GridBagConstraints.LINE_START
            })

            // Port
            add(portLabel.apply {
                text = "Port: "
                preferredSize = Dimension(50, 30)
            }, gridBagConstraints.apply {
                gridx = 2
                gridy = 0
            })
            add(portTextField.apply {
                minimumSize = Dimension(100, 30)
                preferredSize = Dimension(100, 30)
            }, gridBagConstraints.apply {
                gridx = 3
                gridy = 0
            })

            // Authentication
            add(authenticationLabel.apply {
                text = "Authentication: "
                preferredSize = Dimension(110, 30)
            }, gridBagConstraints.apply {
                gridx = 0
                gridy = 1
            })
            add(authTypeComboBox.apply {
                minimumSize = Dimension(150, 30)
                preferredSize = Dimension(150, 30)
            }, gridBagConstraints.apply {
                gridx = 1
                gridy = 1
            })

            // User
            add(userLabel.apply {
                text = "User: "
                preferredSize = Dimension(50, 30)
            }, gridBagConstraints.apply {
                gridx = 0
                gridy = 2
            })
            add(userTextField.apply {
                minimumSize = Dimension(300, 30)
                preferredSize = Dimension(300, 30)
            }, gridBagConstraints.apply {
                gridx = 1
                gridy = 2
            })

            // password
            add(passwordLabel.apply {
                text = "Password: "
                preferredSize = Dimension(80, 30)
            }, gridBagConstraints.apply {
                gridx = 0
                gridy = 3
            })
            add(passwordField.apply {
                minimumSize = Dimension(300, 30)
                preferredSize = Dimension(300, 30)
            }, gridBagConstraints.apply {
                gridx = 1
                gridy = 3
            })

            // url
            add(urlLabel.apply {
                text = "Url: "
                preferredSize = Dimension(50, 30)
            }, gridBagConstraints.apply {
                gridx = 0
                gridy = 4
            })
            add(urlTextField.apply {
                minimumSize = Dimension(300, 30)
                preferredSize = Dimension(300, 30)
            }, gridBagConstraints.apply {
                gridx = 1
                gridy = 4
            })

            add(urlHelpTextLabel.apply {
                text = "Overrides settings above"
                preferredSize = Dimension(300, 30)
                font = UIUtil.getFont(UIUtil.FontSize.SMALL, super.getFont())
            }, gridBagConstraints.apply {
                gridx = 1
                gridy = 5
                gridheight = 10
                weighty = 1.0
                anchor = GridBagConstraints.FIRST_LINE_START
            })

        }
        add(contentPanel, BorderLayout.CENTER)
    }

    private fun createGbc(x: Int, y: Int): GridBagConstraints {
        val WEST_INSETS = Insets(5, 0, 5, 5)
        val EAST_INSETS = Insets(5, 5, 5, 0)
        return GridBagConstraints().apply {
            gridx = x
            gridy = y
            gridwidth = 1
            gridheight = 1
            anchor = if (x == 0) GridBagConstraints.WEST else GridBagConstraints.EAST
            fill = if (x == 0) GridBagConstraints.BOTH else GridBagConstraints.HORIZONTAL
            insets = if (x == 0) WEST_INSETS else EAST_INSETS
            weightx = if (x == 0) 0.1 else 1.0
            weighty = 1.0
        }
    }

}
