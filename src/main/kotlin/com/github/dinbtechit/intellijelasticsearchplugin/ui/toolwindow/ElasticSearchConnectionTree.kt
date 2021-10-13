package com.github.dinbtechit.intellijelasticsearchplugin.ui.toolwindow

import com.github.dinbtechit.intellijelasticsearchplugin.models.*
import com.intellij.icons.AllIcons
import com.intellij.ui.ColoredTreeCellRenderer
import com.intellij.ui.SimpleTextAttributes
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

        val indicesUI = ESMutableTreeNode(
            icon = ElasticsearchIcons.esIndices,
            docType = ElasticsearchDocument.Types.INDICES,
            childIcon = AllIcons.Nodes.DataTables,
            documents = listOf(
                ESIndex("index1", "green", "300 Kb"),
                ESIndex("index2", "green", "300 Kb"),
                ESIndex("index3", "green", "300 Kb"),
                ESIndex("index4", "green", "300 Kb"),
                ESIndex("index5", "green", "300 Kb"),
                ESIndex("index6", "green", "300 Kb"),
            )
        )

        val aliasesUI = ESMutableTreeNode(
            icon = ElasticsearchIcons.esAliases,
            docType = ElasticsearchDocument.Types.ALIAS,
            childIcon = AllIcons.Nodes.Tag,
            documents = listOf(
                ESAlias("alias1"),
                ESAlias("alias2")
            )
        )

        val templateUI = ESMutableTreeNode(
            icon = ElasticsearchIcons.esTemplates,
            docType = ElasticsearchDocument.Types.TEMPLATES,
            childIcon = AllIcons.Nodes.DataSchema
        )

        val ingestPipelineUI = ESMutableTreeNode(
            icon = ElasticsearchIcons.esPipelines,
            docType = ElasticsearchDocument.Types.INGEST_PIPELINES,
            childIcon = AllIcons.Nodes.TabPin
        )

        ElasticsearchDocument.Types.values().forEach {
            if (it == ElasticsearchDocument.Types.INDICES) {
                rootNode.add(indicesUI.esTree)
            } else if (it == ElasticsearchDocument.Types.ALIAS) {
                rootNode.add(aliasesUI.esTree)
            } else if (it == ElasticsearchDocument.Types.INGEST_PIPELINES) {
                rootNode.add(ingestPipelineUI.esTree)
            } else if (it == ElasticsearchDocument.Types.TEMPLATES) {
                rootNode.add(templateUI.esTree)
            } else {
                rootNode.add(DefaultMutableTreeNode(it))
            }
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
            } else if (value is DefaultMutableTreeNode) {
                val obj = value.userObject;
                when (obj) {
                    is ESMutableTreeNode.Parent -> {
                        icon = obj.icon
                        append(obj.type.value)
                        append(" ${value.childCount}", SimpleTextAttributes.GRAYED_SMALL_ATTRIBUTES)
                    }
                    is ESMutableTreeNode.Children -> {
                        icon = obj.icon
                        when (obj.document) {
                            is ESIndex -> {
                                append(obj.document.name)
                                append(" ${obj.document.size}", SimpleTextAttributes.GRAY_SMALL_ATTRIBUTES)
                                append(" - Health ${obj.document.health}", SimpleTextAttributes.GRAY_SMALL_ATTRIBUTES)
                            }
                            else -> {
                                append(obj.document.name)
                            }
                        }
                    }
                    is ElasticsearchDocument.Types -> {
                        append(obj.value)
                    }
                    else -> {
                        append(obj.toString())
                    }
                }
            } else {
                append(value.toString())
            }
        }
    }
}
