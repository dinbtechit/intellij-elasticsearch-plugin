package com.github.dinbtechit.es.ui.editor

import com.intellij.ide.FileIconProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import icons.ElasticsearchIcons
import javax.swing.Icon

class ESFileIconProvider : FileIconProvider {

    override fun getIcon(file: VirtualFile, flags: Int, project: Project?): Icon? {
        return when (file) {
            is ESVirtualFile -> ElasticsearchIcons.logo
            else -> null
        }
    }
}