package com.github.dinbtechit.intellijelasticsearchplugin.ui.toolwindow

import com.github.dinbtechit.intellijelasticsearchplugin.ui.shared.ElasticsearchIcons
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import javax.swing.JPanel

class ElasticsearchFactory : ToolWindowFactory{
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val component = MyComponent(project);
        val content = toolWindow.contentManager.factory.createContent(component,
            null, false);
        content.putUserData(ToolWindow.SHOW_CONTENT_ICON, true)
        toolWindow.setIcon(ElasticsearchIcons.ELATICSEARCH_ICON);
        toolWindow.contentManager.addContent(content)
        toolWindow.contentManager.setSelectedContent(content)
    }
}

class MyComponent(project: Project) : JPanel(), Disposable {
    override fun dispose() {
        TODO("Not yet implemented")
    }

}
