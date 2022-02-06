package com.github.dinbtechit.es.ui.editor.components

import ESTable
import ESTableUI
import com.github.dinbtechit.es.shared.ProjectUtil
import com.github.dinbtechit.es.ui.editor.ESVirtualFile
import com.github.dinbtechit.es.ui.editor.components.table.RowNumberTable
import com.github.dinbtechit.es.ui.editor.components.table.action.ColumnFilterActionGroup
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionPopupMenu
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.progress.runBackgroundableTask
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.ListPopupStep
import com.intellij.openapi.ui.popup.PopupChooserBuilder
import com.intellij.openapi.ui.popup.util.BaseListPopupStep
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.PopupMenuListenerAdapter
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.panels.HorizontalLayout
import com.intellij.ui.popup.ActionPopupStep
import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBUI
import kotlinx.coroutines.*
import java.awt.BorderLayout
import java.awt.Color
import java.util.concurrent.atomic.AtomicBoolean
import javax.swing.JPanel
import javax.swing.ScrollPaneConstants
import javax.swing.SwingUtilities

class EditorContent(
    val file: VirtualFile
) : JPanel() {

    // Component
    private val splitter = OnePixelSplitter(true, 0.01f)
    private val editorQueryPanel = EditorQueryPanel()
    private val editorBodyPanel = JPanel()
    var isLoading = AtomicBoolean(true)


    init {
        layout = BorderLayout(0, 0)
        background = Color.decode("#2B2B2B")
        splitter.apply {
            setResizeEnabled(false)
            firstComponent = editorQueryPanel
            secondComponent = createEditorBody()
        }
        add(splitter, BorderLayout.CENTER)
    }

    private fun createEditorBody(): JPanel {

        val toolBar = ActionManager.getInstance()
            .createActionToolbar(
                "ElasticsearchEditor",
                DefaultActionGroup().apply {
                    add(ColumnFilterActionGroup())
                },
                true
            )
        toolBar.setTargetComponent(this)

        return JPanel().apply {
            background = Color.decode("#2B2B2B")
            layout = BorderLayout(0, 0)
            runBackgroundableTask("${file.name} Loading...", ProjectUtil.currentProject(), true) {
                runBlocking {
                    val vFile = file as ESVirtualFile
                    val tableModel = vFile.getContentAsTable()
                    SwingUtilities.invokeLater {
                        val table = ESTable(tableModel).apply {
                            rowSelectionAllowed = false
                            cellSelectionEnabled = true
                            isFocusable = false
                        }
                        val scrollPanel = JBScrollPane(
                            table,
                            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
                        ).apply {
                            background = EditorColorsManager.getInstance().schemeForCurrentUITheme.defaultBackground
                            //val rowTable = RowHeaderTable(table)
                            val rowTable = RowNumberTable(table)
                            setRowHeaderView(rowTable)

                            val panel = JPanel(HorizontalLayout(0, HorizontalLayout.FILL)).apply {
                                background = ESTableUI.getResultTableHeaderColor()
                                border = JBUI.Borders.customLine(ESTableUI.getTableGridColor(), 1)
                                /*val popup = JBPopupFactory.getInstance().createListPopup(ActionPopupStep(

                                ))*/
                                val p = this
                                add(toolBar.component.apply {
                                    size = JBDimension(p.size.width, p.size.height)
                                }, HorizontalLayout.CENTER)

                            }
                            setCorner(JBScrollPane.UPPER_LEFT_CORNER, panel)
                        }
                        // Add Components
                        add(scrollPanel, BorderLayout.CENTER)
                    }
                }
            }

        }
    }
}