package com.github.dinbtechit.es.ui.toolwindow

import com.github.dinbtechit.es.models.ESIndex
import com.github.dinbtechit.es.models.ElasticsearchDocument
import com.github.dinbtechit.es.services.state.ConnectionInfo
import com.github.dinbtechit.es.services.state.ElasticSearchConfig
import com.github.dinbtechit.es.services.state.getAllConnectionInfo
import com.github.dinbtechit.es.shared.ProjectUtils
import com.github.dinbtechit.es.ui.toolwindow.models.ElasticsearchTreeModel
import com.github.dinbtechit.es.ui.toolwindow.models.ElasticsearchTreeNode
import com.intellij.openapi.project.DumbAware
import com.intellij.ui.ColoredTreeCellRenderer
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.TreeSpeedSearch
import com.intellij.ui.speedSearch.SpeedSearchUtil
import com.intellij.ui.treeStructure.Tree
import javax.swing.JTree


class ElasticsearchTree : Tree(), DumbAware {

    private var treeSpeedSearch: TreeSpeedSearch

    init {
        val config = ElasticSearchConfig.getInstance(ProjectUtils().currentProject())
        model = ElasticsearchTreeModel(config.getAllConnectionInfo())
        setCellRenderer(ElasticsearchTreeCellRenderer())
        expandRow(0)
        isRootVisible = false
        showsRootHandles = true
        treeSpeedSearch = TreeSpeedSearch(this, { treePath ->
            val node = treePath.lastPathComponent as ElasticsearchTreeNode<*>
            when (node.data) {
                is ConnectionInfo -> node.data.name
                is ElasticsearchDocument.Types -> node.data.value
                is ElasticsearchDocument -> node.data.name
                else -> "<not found>"
            }
        }, true)
        config.addChangesListener(SettingsChanged()) {}
    }

    inner class SettingsChanged : ElasticSearchConfig.SettingsChangedListener {
        override fun settingsChanged() {
            val config = ElasticSearchConfig.getInstance(ProjectUtils().currentProject())
            model = ElasticsearchTreeModel(config.getAllConnectionInfo())
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
            if (value is ElasticsearchTreeNode<*>) {
                icon = value.icon
                when (value.data) {
                    is ElasticsearchTreeNode.Empty -> {
                        append(value.data.emptyString, SimpleTextAttributes.GRAYED_ITALIC_ATTRIBUTES)
                    }
                    is ConnectionInfo -> append(value.data.name)
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
                            append(value.data.name)
                            append(" ${value.data.size}", SimpleTextAttributes.GRAY_SMALL_ATTRIBUTES)
                            append(" - Health ${value.data.health}", SimpleTextAttributes.GRAY_SMALL_ATTRIBUTES)

                        } else append(value.data.name)
                    }
                }
            }
            SpeedSearchUtil.applySpeedSearchHighlighting(this, this, true, true)
        }

    }
}
