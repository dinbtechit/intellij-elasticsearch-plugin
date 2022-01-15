package com.github.dinbtechit.es.ui.editor

import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.fileEditor.FileEditorStateLevel

class ESFileEditorState(editor: FileEditorState) : FileEditorState {
    init {

    }

    override fun canBeMergedWith(otherState: FileEditorState, level: FileEditorStateLevel): Boolean = false
}