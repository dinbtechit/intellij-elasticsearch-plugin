package com.github.dinbtechit.es.ui.toolwindow

import com.github.dinbtechit.es.actions.*
import com.github.dinbtechit.es.ui.toolwindow.models.TreeDataKey
import com.github.dinbtechit.es.ui.toolwindow.service.TreeModelController
import com.github.dinbtechit.es.ui.toolwindow.tree.ElasticsearchTree
import com.intellij.icons.AllIcons
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.content.ContentFactory
import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager
import java.awt.Insets
import javax.swing.JPanel
import javax.swing.ScrollPaneConstants


class ElasticsearchToolWindow : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        project.thisLogger().info("Elasticsearch Plugin loaded")
        ToolWindowContents(toolWindow, project)
    }
}


class ToolWindowContents(
    private val toolWindow: ToolWindow,
    project: Project
) : SimpleToolWindowPanel(true, true), DumbAware, Disposable {

    private val controller = project.service<TreeModelController>()

    // Fields
    private val esTree = ElasticsearchTree()

    // Actions
    private val defaultActionGroup = DefaultActionGroup()
    private val manager = ActionManager.getInstance()
    private val newAction = manager.getAction(NewAction.ID)
    private val refreshAction = RefreshAction()
    private val duplicateAction = manager.getAction(DuplicateAction.ID)
    private val viewPropertiesAction = manager.getAction(ViewPropertiesAction.ID)
    private val collapseTreeAction = CollapseAction(AllIcons.Actions.Collapseall).apply {
        this.registerCustomShortcutSet(this.shortcutSet, toolWindow.component)
    }
    private val expandTreeAction = ExpandAction(AllIcons.Actions.Expandall).apply {
        this.registerCustomShortcutSet(this.shortcutSet, toolWindow.component)
    }


    init {
        val content = ContentFactory.SERVICE.getInstance().createContent(this, "", false)
        toolWindow.contentManager.addContent(content)
        // Create Toolbar
        val actionToolbar = ActionManager.getInstance()
            .createActionToolbar("Elasticsearch", buildToolBar(), true)
        actionToolbar.setTargetComponent(this)
        toolbar = actionToolbar.component
        setContent(getContentPanel())
    }

    private fun buildToolBar(): DefaultActionGroup {
        defaultActionGroup.apply {
            add(newAction)
            add(duplicateAction)
            addSeparator()
            add(refreshAction)
            add(viewPropertiesAction)
            addSeparator()
            add(collapseTreeAction)
            add(expandTreeAction)
        }
        return defaultActionGroup
    }

    private fun getContentPanel(): JPanel {

        val panel = JBPanel<SimpleToolWindowPanel>(
            GridLayoutManager(
                2,
                1,
                Insets(0, 0, 0, 0),
                0,
                0
            )
        )

        val jbScrollPane = JBScrollPane(
            esTree,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        )
        panel.add(jbScrollPane, GridConstraints().apply {
            row = 1
            fill = GridConstraints.FILL_BOTH
        })
        return panel
    }


    override fun getData(dataId: String): Any? {
        super.getData(dataId)
        if (TreeDataKey.TREE_MODEL.name == dataId) {
            return esTree
        }
        return null
    }

    override fun dispose() {

    }

}
