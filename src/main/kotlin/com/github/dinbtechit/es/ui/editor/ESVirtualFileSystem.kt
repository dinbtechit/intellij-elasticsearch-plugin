package com.github.dinbtechit.es.ui.editor

import com.intellij.openapi.vfs.NonPhysicalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileListener
import com.intellij.openapi.vfs.VirtualFileSystem
import com.intellij.openapi.vfs.newvfs.impl.NullVirtualFile

class ESVirtualFileSystem : VirtualFileSystem(), NonPhysicalFileSystem {

    override fun getProtocol(): String {
        return "elasticsearch.rest"
    }

    override fun findFileByPath(path: String): VirtualFile? {
        return null
    }

    override fun refresh(asynchronous: Boolean) {}

    override fun refreshAndFindFileByPath(path: String): VirtualFile? {
        return null
    }

    override fun addVirtualFileListener(listener: VirtualFileListener) {}

    override fun removeVirtualFileListener(listener: VirtualFileListener) {}

    override fun deleteFile(requestor: Any?, vFile: VirtualFile) {}

    override fun moveFile(requestor: Any?, vFile: VirtualFile, newParent: VirtualFile) {}

    override fun renameFile(requestor: Any?, vFile: VirtualFile, newName: String) {}

    override fun createChildFile(requestor: Any?, vDir: VirtualFile, fileName: String): VirtualFile {
        return NullVirtualFile.INSTANCE
    }

    override fun createChildDirectory(requestor: Any?, vDir: VirtualFile, dirName: String): VirtualFile {
        return NullVirtualFile.INSTANCE
    }

    override fun copyFile(
        requestor: Any?,
        virtualFile: VirtualFile,
        newParent: VirtualFile,
        copyName: String
    ): VirtualFile {
        return NullVirtualFile.INSTANCE
    }

    override fun isReadOnly(): Boolean {
        return true
    }
}