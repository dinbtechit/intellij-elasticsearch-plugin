package com.github.dinbtechit.es.ui.editor.components

import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.components.panels.NonOpaquePanel
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.ColumnInfo
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.ListTableModel
import java.awt.BorderLayout
import java.awt.Color
import javax.swing.JPanel
import javax.swing.table.TableColumn
import javax.swing.table.TableModel

class EditorContent : JPanel() {

    // Component
    private val splitter = OnePixelSplitter(true, 0.01f)
    private val editorQueryPanel = EditorQueryPanel()
    private val editorBodyPanel = JPanel()

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
        return editorBodyPanel.apply {
            layout = BorderLayout(0, 0)
            background = Color.decode("#2B2B2B")
            val tableModel = EditorTableModel()
            val table = JBTable(tableModel).apply {
                columnSelectionAllowed = false
                isStriped = true
                border = JBUI.Borders.empty()
                dragEnabled = false
            }
            add(table, BorderLayout.NORTH)
        }
    }


}

class EditorTableModel : ListTableModel<String>() {}