package com.github.dinbtechit.es.ui.editor

import com.intellij.openapi.fileTypes.FileType
import icons.ElasticsearchIcons
import javax.swing.Icon

class ESFileType : FileType {
    companion object {
        @JvmField
        val INSTANCE = ESFileType()
    }
    override fun getName(): String = "ElasticsearchFile"
    override fun getDescription(): String = "Elasticsearch file type"
    override fun getDefaultExtension(): String = "esi"
    override fun getIcon(): Icon = ElasticsearchIcons.Logo
    override fun isBinary(): Boolean = false
}