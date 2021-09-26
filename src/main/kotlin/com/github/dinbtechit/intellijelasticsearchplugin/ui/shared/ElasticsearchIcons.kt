package com.github.dinbtechit.intellijelasticsearchplugin.ui.shared

import com.intellij.ui.IconManager
import javax.swing.Icon

object ElasticsearchIcons {
    private fun load(path: String): Icon {
        return IconManager.getInstance().getIcon(path, ElasticsearchIcons::class.java)
    }

    val ELATICSEARCH_ICON = load("/images/elasticsearch-icon-13px.svg")
}
