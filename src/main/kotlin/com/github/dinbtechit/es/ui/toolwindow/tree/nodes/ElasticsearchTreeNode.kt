package com.github.dinbtechit.es.ui.toolwindow.tree.nodes

import com.github.dinbtechit.es.configuration.ConnectionInfo
import com.github.dinbtechit.es.models.elasticsearch.ElasticsearchDocument
import com.github.dinbtechit.es.ui.editor.ESVirtualFile
import com.github.dinbtechit.es.ui.toolwindow.tree.ElasticsearchTree
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.DumbAware
import java.util.concurrent.atomic.AtomicBoolean
import javax.swing.Icon
import javax.swing.tree.DefaultMutableTreeNode

open class ElasticsearchTreeNode<P, C>(
    val icon: Icon?,
    val data: P,
    val childIcon: Icon? = null,
    var childData: MutableList<C> = mutableListOf()
) : DefaultMutableTreeNode(), DumbAware {

    open var popupMenuItems: DefaultActionGroup? = null
    var virtualFile: ESVirtualFile? = null
    open var isLoading: AtomicBoolean = AtomicBoolean(false)

    init {
        loadChildren()
    }

    fun loadChildren(childPopup: DefaultActionGroup? = null) {
        if (childData.isNotEmpty()) {
            for (d in childData) add(ElasticsearchTreeNode<C, String>(childIcon, d).apply {
                popupMenuItems = childPopup
                if (d is ElasticsearchDocument) virtualFile = ESVirtualFile(d.displayName, ConnectionInfo())
            })
        }
    }


    fun getConnectionNode(treeNode: ElasticsearchTreeNode<*, *>?): ConnectionInfo? {
        if (treeNode is ElasticsearchTreeNode<*, *>) {
            val parentPath = treeNode.parent as ElasticsearchTreeNode<*, *>
            if (parentPath.data is String && treeNode.data is ConnectionInfo) return treeNode.data
            return getConnectionNode(parentPath)
        }
        return null
    }

    open fun buildPopMenuItems(tree: ElasticsearchTree): DefaultActionGroup = DefaultActionGroup()
    data class Empty(val emptyString: String)
}

data class ChildData<T>(val icon: Icon?, val data: T)