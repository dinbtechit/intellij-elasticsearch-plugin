package com.github.dinbtechit.intellijelasticsearchplugin.ui.toolwindow

import com.github.dinbtechit.intellijelasticsearchplugin.models.ESIndex
import com.github.dinbtechit.intellijelasticsearchplugin.models.ElasticsearchDocument
import com.github.dinbtechit.intellijelasticsearchplugin.models.Indices
import com.intellij.icons.AllIcons
import com.intellij.ui.ColoredTreeCellRenderer
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.components.JBLabel
import com.intellij.ui.treeStructure.SimpleTree
import com.intellij.ui.treeStructure.Tree
import icons.ElasticsearchIcons
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

class ElasticSearchConnectionTree(connectionName: String) {

    val tree: Tree

    init {
        val rootNode = DefaultMutableTreeNode(connectionName)
        val treeModel = DefaultTreeModel(rootNode)
        createESNodes(rootNode)
        tree = SimpleTree(treeModel)
        tree.cellRenderer = ElasticsearchTreeCellRenderer()
        tree.isRootVisible = true
        tree.showsRootHandles = true
    }

    private fun createESNodes(rootNode: DefaultMutableTreeNode) {
        ElasticsearchDocument.Types.values().forEach {
            val esTreeNode = DefaultMutableTreeNode(it.value)
            if (it == ElasticsearchDocument.Types.INDICES) {
                val dummyIndices = listOf<ESIndex>(
                    ESIndex("index1", "green", "300 Kb")
                )
                for (node in Indices(dummyIndices).getIndicesNodes()) {
                    esTreeNode.add(node)
                }
            }
            rootNode.add(esTreeNode)
        }
    }

    class ElasticsearchTreeCellRenderer : ColoredTreeCellRenderer() {
        override fun customizeCellRenderer(
            tree: JTree,
            value: Any?,
            selected: Boolean,
            expanded: Boolean,
            leaf: Boolean,
            row: Int,
            hasFocus: Boolean
        ) {
            if (!leaf && row == 0) {
                icon = ElasticsearchIcons.logo_16px
                append(value.toString())
                append("(Connected)", SimpleTextAttributes.GRAYED_ITALIC_ATTRIBUTES)
            } else if (!leaf && row > 0){
                icon = ElasticsearchIcons.esIndices
                append(value.toString())
            } else {
                icon = AllIcons.Nodes.DataTables
                append(value.toString())
            }

        }
    }
}
