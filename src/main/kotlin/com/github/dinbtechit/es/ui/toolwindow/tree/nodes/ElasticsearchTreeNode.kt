package com.github.dinbtechit.es.ui.toolwindow.tree.nodes

import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.DumbAware
import javax.swing.Icon
import javax.swing.tree.DefaultMutableTreeNode

open class ElasticsearchTreeNode<T>(
    val icon: Icon?,
    val data: T,
    val childIcon: Icon? = null,
    val childData: List<T>? = null
) : DefaultMutableTreeNode(), DumbAware {

    open val popupMenuItems: DefaultActionGroup = DefaultActionGroup()

    init {
        if (childIcon != null && !childData.isNullOrEmpty()) {
            for (d in childData) add(ElasticsearchTreeNode(childIcon, d))
        }
    }

    data class Empty(val emptyString: String)
}