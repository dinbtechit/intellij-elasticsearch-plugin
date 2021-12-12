package com.github.dinbtechit.es.ui.dialogs.view

import com.github.dinbtechit.es.services.state.ConnectionInfo
import com.github.dinbtechit.es.ui.dialogs.DialogModelController
import com.github.dinbtechit.es.ui.dialogs.model.DialogViewType
import com.github.dinbtechit.es.ui.dialogs.view.leftcontent.LeftMenuPanel
import com.github.dinbtechit.es.ui.dialogs.view.rightcontent.RightContentPanel
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.components.JBLabel
import com.intellij.ui.layout.panel
import com.intellij.util.ui.UIUtil
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.GridBagLayout
import java.awt.event.ActionEvent
import javax.swing.Action
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.border.Border
import javax.swing.border.MatteBorder


class NewConnectionDialog(
    viewType: DialogViewType = DialogViewType.VIEW_PROPERTIES,
    private val selectedConnectionInfo: ConnectionInfo? = null,
    project: Project) : DialogWrapper(true) {


    private var controller = DialogModelController()
    private val oneSplitter = OnePixelSplitter(false, .3f)

    // Components
    private val emptyPanel = EmptyPanel()
    private val leftMenuPanel = LeftMenuPanel(controller = controller)
    private val rightPanel = RightContentPanel(viewType, controller = controller)
    private var applyAction: ApplyAction = ApplyAction()

    init {
        title = "Elasticsearch Connections"
        init()
        subscribeToListeners()
    }

    override fun createContentPaneBorder(): Border {
        return MatteBorder(0, 0, 1, 0, Color.decode("#2F3233"))
    }

    override fun doOKAction() {
        if (okAction.isEnabled) {
            controller.saveConnectionChanges()
            close(OK_EXIT_CODE)
        }
    }

    internal fun doApplyAction(e: ActionEvent) {
        if (applyAction.isEnabled) {
            controller.saveConnectionChanges()
        }
    }

    override fun createCenterPanel(): JComponent {
        oneSplitter.apply {
            firstComponent = leftMenuPanel
            emptyPanel.isVisible = false
            secondComponent = rightPanel
            splitterProportionKey = "com.github.dinbtechit.elasticsearch.dialogSplitKey"
            minimumSize = Dimension(780, 550)
            preferredSize = Dimension(850, 500)

        }
        val rootDialogPanel = panel { }.apply {
            layout = BorderLayout(0, 0)
            preferredSize = Dimension(950, 650)
            minimumSize = Dimension(950, 650)
            add(oneSplitter, BorderLayout.CENTER)
            border = MatteBorder(0, 0, 1, 0, Color.decode("#2F3233"))
        }

        if (selectedConnectionInfo != null) {
            controller.selectConnectionInfo(selectedConnectionInfo)
        } else controller.selectConnectionInfo(0)

        return rootDialogPanel
    }

    private fun subscribeToListeners() {
        // Subscribe To List Selection Events
        controller.addPropertyChangeListener {
            if (it.propertyName == DialogModelController.EventType.UNSELECTED.name
                || it.propertyName == DialogModelController.EventType.SELECTED.name
            ) {
                if (it.newValue is Int && it.newValue == -1) {
                    oneSplitter.secondComponent = emptyPanel
                    emptyPanel.revalidate()
                    emptyPanel.repaint()
                    emptyPanel.isVisible = true
                } else {
                    oneSplitter.secondComponent = rightPanel
                    rightPanel.revalidate()
                    rightPanel.repaint()
                    rightPanel.isVisible = true
                }
            } else if (it.propertyName == DialogModelController.EventType.ENABLE_APPLY_ACTION.name) {
                if (it.newValue is MutableList<*>) {
                    applyAction.isEnabled = true
                }
            } else if (it.propertyName == DialogModelController.EventType.DISABLE_APPLY_ACTION.name) {
                applyAction.isEnabled = false
            }

        }
    }

    inner class EmptyPanel : JPanel() {

        val label = JBLabel("Add or modify connection").apply {
            componentStyle = UIUtil.ComponentStyle.SMALL
            fontColor = UIUtil.FontColor.BRIGHTER
        }

        init {
            layout = GridBagLayout()
            add(label)
        }
    }

    override fun createActions(): Array<Action> {
        return super.createActions().plus(applyAction)
    }

    private inner class ApplyAction : DialogWrapperAction("Apply") {
        init {
            isEnabled = false
        }

        override fun doAction(e: ActionEvent?) {
            doApplyAction(e!!)
        }
    }

}
