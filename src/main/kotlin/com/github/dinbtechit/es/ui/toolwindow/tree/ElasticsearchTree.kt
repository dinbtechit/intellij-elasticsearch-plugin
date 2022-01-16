package com.github.dinbtechit.es.ui.toolwindow.tree

import com.github.dinbtechit.es.models.elasticsearch.ESIndex
import com.github.dinbtechit.es.models.elasticsearch.ElasticsearchDocument
import com.github.dinbtechit.es.configuration.ConnectionInfo
import com.github.dinbtechit.es.configuration.ElasticSearchConfig
import com.github.dinbtechit.es.configuration.getAllConnectionInfo
import com.github.dinbtechit.es.shared.ProjectUtil
import com.github.dinbtechit.es.ui.toolwindow.models.ElasticsearchTreeModel
import com.github.dinbtechit.es.ui.toolwindow.service.TreeModelController
import com.github.dinbtechit.es.ui.toolwindow.tree.nodes.ChildData
import com.github.dinbtechit.es.ui.toolwindow.tree.nodes.ElasticsearchConnectionTreeNode
import com.github.dinbtechit.es.ui.toolwindow.tree.nodes.ElasticsearchRootNode
import com.github.dinbtechit.es.ui.toolwindow.tree.nodes.ElasticsearchTreeNode
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.components.service
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.DumbAware
import com.intellij.ui.AnimatedIcon
import com.intellij.ui.ColoredTreeCellRenderer
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.TreeSpeedSearch
import com.intellij.ui.speedSearch.SpeedSearchUtil
import com.intellij.ui.treeStructure.Tree
import com.intellij.util.ui.tree.TreeUtil
import icons.ElasticsearchIcons
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JTree
import javax.swing.SwingUtilities


@Suppress("UnstableApiUsage")
class ElasticsearchTree(
    val rootNode: ElasticsearchRootNode = ElasticsearchRootNode()
) : Tree(rootNode) {

    val loadingIcon = AnimatedIcon.Default()

    private var treeSpeedSearch: TreeSpeedSearch
    private val project = ProjectUtil.currentProject()
    private val controller = project.service<TreeModelController>()
    private val config = ElasticSearchConfig.getInstance(project)

    // Tree UI
    var esTreeModel: ElasticsearchTreeModel

    init {
        putClientProperty(AnimatedIcon.ANIMATION_IN_RENDERER_ALLOWED, true)
        model = ElasticsearchTreeModel(config.getAllConnectionInfo())
        esTreeModel = model as ElasticsearchTreeModel
        setCellRenderer(ElasticsearchTreeCellRenderer())
        expandRow(0)
        isRootVisible = false
        toggleClickCount = 0
        treeSpeedSearch = TreeSpeedSearch(this, { treePath ->
            val node = treePath.lastPathComponent as ElasticsearchTreeNode<*, *>
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


    inner class ElasticsearchTreeCellRenderer : ColoredTreeCellRenderer() {
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
                when {
                    value.isLoading.get() -> {
                        icon = loadingIcon
                        append(value.data.name)
                    }
                    value.isConnected -> {
                        toolTipText = null
                        icon = ElasticsearchIcons.Logo
                        append(value.data.name)
                    }
                    else -> {
                        icon = ElasticsearchIcons.logo_grey_16px
                        append(value.data.name, SimpleTextAttributes.GRAY_ATTRIBUTES)
                        toolTipText = "Not Connected"
                        if (value.isError) {
                            icon = ElasticsearchIcons.logo_grey_error_16px
                        }
                    }
                }
            } else if (value is ElasticsearchTreeNode<*, *>) {
                icon = value.icon
                toolTipText = null
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
                    is ChildData<*> -> {
                        icon = value.data.icon
                        when (value.data.data) {
                            is ElasticsearchDocument -> append(value.data.data.displayName)
                        }
                    }
                    is ElasticsearchDocument -> {
                        if (value.data is ESIndex) {
                            icon = when (value.data.health) {
                                ESIndex.Health.GREEN -> ElasticsearchIcons.Index.DataTable_Green_16px
                                ESIndex.Health.YELLOW -> ElasticsearchIcons.Index.DataTable_Yellow_16px
                                ESIndex.Health.RED -> ElasticsearchIcons.Index.DataTable_Red_16px
                            }
                            append(value.data.displayName)
                            append(" ${value.data.storeSize}", SimpleTextAttributes.GRAY_SMALL_ATTRIBUTES)

                        } else append(value.data.displayName)
                    }
                }
            }
            SpeedSearchUtil.applySpeedSearchHighlighting(this, this, true, true)
        }

    }

    inner class ElasticsearchTreeMouseAdaptor : MouseAdapter(), DumbAware {

        override fun mousePressed(e: MouseEvent) {
            val tree = e.component as ElasticsearchTree
            val selRow = tree.getRowForLocation(e.x, e.y)
            val treePath = tree.getPathForLocation(e.x, e.y)
            if (selRow != -1 && treePath != null && SwingUtilities.isLeftMouseButton(e) && e.clickCount == 2) {
                val selectNode = TreeUtil.getSelectedPathIfOne(tree)!!.lastPathComponent as ElasticsearchTreeNode<*, *>
                println("Double Clicked ${selectNode.data}")
                if (selectNode.data is ElasticsearchDocument) {
                    FileEditorManager.getInstance(project).openFile(selectNode.virtualFile!!, true)
                }
            }
            if (SwingUtilities.isRightMouseButton(e)) {
                val tree = e!!.component as ElasticsearchTree
                val manager = ActionManager.getInstance()
                val defaultActionGroup = DefaultActionGroup()
                val actionPopMenu = manager.createActionPopupMenu("Elasticsearch", defaultActionGroup)
                if (!tree.isSelectionEmpty) {
                    val node = tree.selectionModel.selectionPaths.first()
                        .lastPathComponent as ElasticsearchTreeNode<*, *>
                    defaultActionGroup.add(node.buildPopMenuItems(tree))
                    if (node.popupMenuItems != null) defaultActionGroup.add(node.popupMenuItems!!)
                    defaultActionGroup.addSeparator()
                }
                actionPopMenu.setTargetComponent(tree)
                actionPopMenu.component.show(tree, e.x, e.y)

            }
        }
    }
}
