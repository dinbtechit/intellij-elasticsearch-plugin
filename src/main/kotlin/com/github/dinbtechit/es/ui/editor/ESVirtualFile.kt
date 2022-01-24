package com.github.dinbtechit.es.ui.editor

import com.github.dinbtechit.es.configuration.ConnectionInfo
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileSystem
import java.io.InputStream
import java.io.OutputStream

open class ESVirtualFile(
    private val fileName: String = "Default",
    val connection: ConnectionInfo
) : VirtualFile(), DumbAware {

    override fun getName(): String = fileName
    override fun getFileSystem(): VirtualFileSystem = ESVirtualFileSystem()
    override fun getPath(): String = name
    override fun isWritable(): Boolean = true
    override fun isDirectory(): Boolean  = false
    override fun isValid(): Boolean = true
    override fun getParent(): VirtualFile? = null
    override fun getChildren(): Array<VirtualFile> = emptyArray()
    override fun contentsToByteArray(): ByteArray = ByteArray(0)
    override fun getTimeStamp(): Long = System.currentTimeMillis()
    override fun getLength(): Long = 0

    override fun getOutputStream(requestor: Any?, newModificationStamp: Long, newTimeStamp: Long): OutputStream {
        TODO("Not yet implemented")
    }

    override fun getInputStream(): InputStream {
        TODO("Not yet implemented")
    }

    override fun refresh(asynchronous: Boolean, recursive: Boolean, postRunnable: Runnable?) {
        TODO("Not yet implemented")
    }
}
