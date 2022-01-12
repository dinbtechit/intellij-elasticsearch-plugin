package com.github.dinbtechit.es.ui.editor

import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileSystem
import icons.ElasticsearchIcons
import java.io.InputStream
import java.io.OutputStream
import javax.swing.Icon

open class ESVirtualFile(private val fileName: String = "Default") : VirtualFile(), DumbAware {

    override fun getName(): String = fileName

    override fun getFileSystem(): VirtualFileSystem {
        return ESVirtualFileSystem()
    }

    override fun getPath(): String {
        return name
    }

    override fun isWritable(): Boolean {
        return true
    }

    override fun isDirectory(): Boolean {
        return false
    }

    override fun isValid(): Boolean {
        return true
    }

    override fun getParent(): VirtualFile? {
        return null
    }

    override fun getChildren(): Array<VirtualFile> {
        return emptyArray()
    }

    override fun getOutputStream(requestor: Any?, newModificationStamp: Long, newTimeStamp: Long): OutputStream {
        TODO("Not yet implemented")
    }

    override fun contentsToByteArray(): ByteArray {
        return ByteArray(0)
    }

    override fun getTimeStamp(): Long {
        return System.currentTimeMillis()
    }

    override fun getLength(): Long {
        return  0
    }

    override fun refresh(asynchronous: Boolean, recursive: Boolean, postRunnable: Runnable?) {
        TODO("Not yet implemented")
    }

    override fun getInputStream(): InputStream {
        TODO("Not yet implemented")
    }
}