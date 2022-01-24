package com.github.dinbtechit.es.ui.editor.components

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.OnePixelSplitter
import java.awt.BorderLayout
import javax.swing.JPanel

class BasePanel(val file: VirtualFile) : JPanel() {

    // Components
    private val splitter = OnePixelSplitter(true, 0.01f)
    private val editorHeader = EditorHeader()
    private val editorContent = EditorContent(file)

    init {
        layout = BorderLayout(0, 0)
        splitter.apply {
            setResizeEnabled(false)
            firstComponent = editorHeader
            secondComponent = editorContent
        }
        add(splitter, BorderLayout.CENTER)
    }
}