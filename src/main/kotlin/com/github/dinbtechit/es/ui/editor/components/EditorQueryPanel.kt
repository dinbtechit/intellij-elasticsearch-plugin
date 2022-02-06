package com.github.dinbtechit.es.ui.editor.components

import com.github.dinbtechit.es.shared.ProjectUtil
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.colors.EditorFontType
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.ui.EditorTextField
import com.intellij.util.ui.JBEmptyBorder
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import javax.swing.JPanel

class EditorQueryPanel : JPanel() {

    private val project = ProjectUtil.currentProject()
    private val fileType = PlainTextFileType.INSTANCE
    private val editor = EditorTextField(project, fileType).apply {
    }

    init {
        layout = BorderLayout(0, 0)
        background = if (EditorColorsManager.getInstance().isDarkEditor) Color.decode("#2B2B2B") else super.getBackground()
        editor.apply {
            setOneLineMode(true)
            val colorsScheme = EditorColorsManager.getInstance().globalScheme
            val font: Font = colorsScheme.getFont(EditorFontType.PLAIN)
            setFont(font)
            background =  if (EditorColorsManager.getInstance().isDarkEditor) Color.decode("#2B2B2B") else super.getBackground()
            border = JBEmptyBorder(7, 7, 7, 7)
            this.setPlaceholder("Search")
            this.setShowPlaceholderWhenFocused(true)
            this.addSettingsProvider {
                it.scrollPane.border = JBEmptyBorder(0)
            }

        }
        add(editor, BorderLayout.CENTER)
    }
}