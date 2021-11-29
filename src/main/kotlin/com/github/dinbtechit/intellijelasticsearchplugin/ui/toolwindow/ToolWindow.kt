package com.github.dinbtechit.intellijelasticsearchplugin.ui.toolwindow

import com.github.dinbtechit.intellijelasticsearchplugin.actions.*
import com.intellij.icons.AllIcons
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.actionSystem.KeyboardShortcut
import com.intellij.openapi.actionSystem.ShortcutSet
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
import java.awt.event.KeyEvent
import javax.swing.JPanel
import javax.swing.KeyStroke
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
        val collapseTreeAction = CollapseAction(AllIcons.Actions.Collapseall).apply {
            this.registerCustomShortcutSet(this.shortcutSet, toolWindow.component)
        }
        val expandTreeAction = ExpandAction(AllIcons.Actions.Expandall).apply {
            this.registerCustomShortcutSet(this.shortcutSet, toolWindow.component)
        }

        toolbar.apply {
            add(newAction)
            add(duplicateAction)
            addSeparator()
            add(refreshAction)
            add(viewPropertiesAction)
            addSeparator()
            add(collapseTreeAction)
            add(expandTreeAction)
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

        val esTree = ElasticsearchTree()
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

    override fun dispose() {
    }

}
