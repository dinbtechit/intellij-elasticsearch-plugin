package com.github.dinbtechit.intellijelasticsearchplugin.ui.toolwindow

import com.github.dinbtechit.intellijelasticsearchplugin.actions.DuplicateAction
import com.github.dinbtechit.intellijelasticsearchplugin.actions.NewAction
import com.github.dinbtechit.intellijelasticsearchplugin.actions.RefreshAction
import com.github.dinbtechit.intellijelasticsearchplugin.actions.ViewPropertiesAction
import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ElasticSearchConfig
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.actionSystem.DefaultActionGroup
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
) : SimpleToolWindowPanel(true, true), Disposable {

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
        val toolbar = DefaultActionGroup()
        val manager = ActionManager.getInstance()
        val newAction = manager.getAction(NewAction.ID)
        val refreshAction = manager.getAction(RefreshAction.ID)
        val duplicateAction = manager.getAction(DuplicateAction.ID)
        val viewPropertiesAction = manager.getAction(ViewPropertiesAction.ID)

        toolbar.apply {
            add(newAction)
            add(duplicateAction)
            addSeparator()
            add(refreshAction)
            add(viewPropertiesAction)
        }
        return toolbar
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

        val esTree = ElasticSearchConnectionTree("@Elasticsearch - Dev")
        val jbScrollPane = JBScrollPane(
            esTree.tree,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        )
        panel.add(jbScrollPane, GridConstraints().apply {
            row = 1
            fill = GridConstraints.FILL_BOTH
        })
        return panel
    }

    private fun initToolbar(toolbar: JPanel) {
        val manager = ActionManager.getInstance()
        val newAction = manager.getAction(NewAction.ID)
        val refreshAction = manager.getAction(RefreshAction.ID)
        val duplicateAction = manager.getAction(DuplicateAction.ID)


        val toolbarActionGroup = DefaultActionGroup().apply {
            add(newAction)
            add(duplicateAction)
            addSeparator()
            add(refreshAction)
        }

        val actionToolbar = manager.createActionToolbar(ActionPlaces.TOOLWINDOW_TITLE, toolbarActionGroup, true)
        actionToolbar.setTargetComponent(this)
        toolbar.add(actionToolbar.component)
    }

    override fun dispose() {
    }

}
