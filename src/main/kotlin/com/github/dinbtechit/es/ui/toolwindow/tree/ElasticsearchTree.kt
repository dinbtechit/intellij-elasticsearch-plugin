package com.github.dinbtechit.es.ui.toolwindow.tree

import com.github.dinbtechit.es.actions.NewAction
import com.github.dinbtechit.es.actions.ViewPropertiesAction
import com.github.dinbtechit.es.models.ESIndex
import com.github.dinbtechit.es.models.ElasticsearchDocument
import com.github.dinbtechit.es.services.state.ConnectionInfo
import com.github.dinbtechit.es.services.state.ElasticSearchConfig
import com.github.dinbtechit.es.services.state.getAllConnectionInfo
import com.github.dinbtechit.es.shared.ProjectUtil
import com.github.dinbtechit.es.ui.toolwindow.models.ElasticsearchTreeModel
import com.github.dinbtechit.es.ui.toolwindow.service.TreeModelController
import com.github.dinbtechit.es.ui.toolwindow.tree.nodes.ElasticsearchConnectionTreeNode
import com.github.dinbtechit.es.ui.toolwindow.tree.nodes.ElasticsearchTreeNode
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.components.service
import com.intellij.openapi.project.DumbAware
import com.intellij.ui.ColoredTreeCellRenderer
import com.intellij.ui.JBColor
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.TreeSpeedSearch
import com.intellij.ui.speedSearch.SpeedSearchUtil
import com.intellij.ui.treeStructure.Tree
import com.intellij.util.ui.UIUtil
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JTree
import javax.swing.SwingUtilities


class ElasticsearchTree : Tree(), DumbAware {

    private var treeSpeedSearch: TreeSpeedSearch
    private val project = ProjectUtil.currentProject()
    private val controller = project.service<TreeModelController>()
    private val config = ElasticSearchConfig.getInstance(project)

    init {
        model = ElasticsearchTreeModel(config.getAllConnectionInfo())
        setCellRenderer(ElasticsearchTreeCellRenderer())
        expandRow(0)
        isRootVisible = false
        treeSpeedSearch = TreeSpeedSearch(this, { treePath ->
            val node = treePath.lastPathComponent as ElasticsearchTreeNode<*>
            when (node.data) {
                is ConnectionInfo -> node.data.name
                is ElasticsearchDocument.Types -> node.data.value
                is ElasticsearchDocument -> node.data.displayName
                else -> "<not found>"
            }
        }, true)
        config.addChangesListener(SettingsChanged(this)) {}
        addTreeSelectionListener {
            controller.selectedTree(it)
        }
        addMouseListener(ElasticsearchTreeMouseAdaptor())

    }

    inner class SettingsChanged(val tree: ElasticsearchTree) : ElasticSearchConfig.SettingsChangedListener {
        override fun settingsChanged() {
            val currentModel = tree.model as ElasticsearchTreeModel
            model = ElasticsearchTreeModel(config.getAllConnectionInfo(), previousRootNode = currentModel.rootNode)


        }
    }


    private class ElasticsearchTreeCellRenderer : ColoredTreeCellRenderer() {
        override fun customizeCellRenderer(
            tree: JTree,
            value: Any?,
            selected: Boolean,
            expanded: Boolean,
            leaf: Boolean,
            row: Int,
            hasFocus: Boolean
        ) {
            if (value is ElasticsearchConnectionTreeNode) {
                icon = value.icon
                if (value.isConnected) {
                    append(value.data.name)
                    toolTipText = null
                } else {
                    toolTipText = "Not Connected"
                    append(
                        value.data.name, SimpleTextAttributes(
                            SimpleTextAttributes.STYLE_WAVED,
                            UIUtil.getLabelForeground(), JBColor.RED
                        )
                    )
                }
            } else if (value is ElasticsearchTreeNode<*>) {
                icon = value.icon
                when (value.data) {
                    is ElasticsearchTreeNode.Empty -> {
                        append(value.data.emptyString, SimpleTextAttributes.GRAYED_ITALIC_ATTRIBUTES)
                    }
                    is ElasticsearchDocument.Types -> {
                        if (value.childCount == 0) {
                            append(value.data.value, SimpleTextAttributes.GRAYED_ITALIC_ATTRIBUTES)
                        } else {
                            append(value.data.value)
                            append(" ${value.childCount}", SimpleTextAttributes.GRAYED_SMALL_ATTRIBUTES)
                        }
                    }
                    is ElasticsearchDocument -> {
                        if (value.data is ESIndex) {
                            append(value.data.displayName)
                            append(" ${value.data.storeSize}", SimpleTextAttributes.GRAY_SMALL_ATTRIBUTES)
                            append(" - Health ${value.data.health}", SimpleTextAttributes.GRAY_SMALL_ATTRIBUTES)

                        } else append(value.data.displayName)
                    }
                }
            }
            SpeedSearchUtil.applySpeedSearchHighlighting(this, this, true, true)
        }

    }

    inner class ElasticsearchTreeMouseAdaptor : MouseAdapter(), DumbAware {

        override fun mousePressed(e: MouseEvent?) {
            if (SwingUtilities.isRightMouseButton(e)) {
                val tree = e!!.component as ElasticsearchTree
                val manager = ActionManager.getInstance()
                val defaultActionGroup = DefaultActionGroup()
                val actionPopMenu = manager.createActionPopupMenu("Elasticsearch", defaultActionGroup)
                if (!tree.isSelectionEmpty) {
                    val node = tree.selectionModel.selectionPaths.first().lastPathComponent as ElasticsearchTreeNode<*>
                    if (node is ElasticsearchConnectionTreeNode) {
                        defaultActionGroup.addAll(node.buildPopMenuItems(tree))
                        defaultActionGroup.addSeparator()
                    }
                }
                defaultActionGroup.add(manager.getAction(NewAction.ID))
                val viewPropertiesAction = manager.getAction(ViewPropertiesAction.ID) as ViewPropertiesAction
                if (viewPropertiesAction.isEnabled) defaultActionGroup.add(manager.getAction(ViewPropertiesAction.ID))
                actionPopMenu.setTargetComponent(tree)
                actionPopMenu.component.show(tree, e.x, e.y)

            }
        }
    }
}
