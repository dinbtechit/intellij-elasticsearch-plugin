package com.github.dinbtechit.es.ui.editor

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorPolicy
import com.intellij.openapi.fileEditor.FileEditorProvider
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class ESFileEditorProvider: FileEditorProvider, DumbAware {

    override fun getEditorTypeId(): String = "Elasticsearch-Editor"

    override fun accept(project: Project, file: VirtualFile): Boolean = file.fileSystem is ESVirtualFileSystem


    override fun createEditor(project: Project, file: VirtualFile): FileEditor {
        return ESFileEditor(file, project)
    }

    override fun getPolicy(): FileEditorPolicy = FileEditorPolicy.PLACE_AFTER_DEFAULT_EDITOR
}