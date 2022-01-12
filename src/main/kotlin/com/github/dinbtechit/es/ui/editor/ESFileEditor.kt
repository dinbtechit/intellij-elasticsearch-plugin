package com.github.dinbtechit.es.ui.editor

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.UserDataHolderBase
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.EditorTextField
import com.intellij.ui.components.IconLabelButton
import icons.ElasticsearchIcons
import java.beans.PropertyChangeListener
import javax.swing.JComponent
import javax.swing.JPanel

class ESFileEditor(private val virtualFile: VirtualFile, project: Project) : UserDataHolderBase(), FileEditor,
    DumbAware {

    private var isActive = false

    // Components
    private val basePanel = JPanel()
    private val searchField = EditorTextField()

    init {
        basePanel.apply {
            add(IconLabelButton(ElasticsearchIcons.logo_grey_16px, {}))
            add(EditorTextField())
        }
    }

    override fun getFile(): VirtualFile? = virtualFile
    override fun getComponent(): JComponent = basePanel
    override fun getPreferredFocusedComponent(): JComponent? = basePanel
    override fun getName(): String = "My Editor"
    override fun setState(state: FileEditorState) {}
    override fun isModified(): Boolean = false
    override fun isValid(): Boolean = true
    override fun addPropertyChangeListener(listener: PropertyChangeListener) {}
    override fun removePropertyChangeListener(listener: PropertyChangeListener) {}
    override fun getCurrentLocation(): FileEditorLocation? = null
    override fun selectNotify() {
        isActive = true
    }

    override fun deselectNotify() {
        isActive = true
    }


    override fun dispose() {
        Disposer.dispose(this)
    }
}
