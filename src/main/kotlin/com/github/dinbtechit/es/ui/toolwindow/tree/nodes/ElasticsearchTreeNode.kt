package com.github.dinbtechit.es.ui.toolwindow.tree.nodes

import com.github.dinbtechit.es.ui.toolwindow.tree.ElasticsearchTree
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.DumbAware
import javax.swing.Icon
import javax.swing.tree.DefaultMutableTreeNode

open class ElasticsearchTreeNode<P, C>(
    val icon: Icon?,
    val data: P,
    val childIcon: Icon? = null,
    var childData: MutableList<C> = mutableListOf()
) : DefaultMutableTreeNode(), DumbAware {

    open var popupMenuItems: DefaultActionGroup? = null

    init {
        loadChildren()
    }

    fun loadChildren(childPopup: DefaultActionGroup? = null) {
        if (childData.isNotEmpty()) {
            for (d in childData) add(ElasticsearchTreeNode<C, String>(childIcon, d).apply {
                popupMenuItems = childPopup
            })
        }
    }

    open fun buildPopMenuItems(tree: ElasticsearchTree): DefaultActionGroup = DefaultActionGroup()
    data class Empty(val emptyString: String)
}

data class ChildData<T>(val icon: Icon?, val data: T)