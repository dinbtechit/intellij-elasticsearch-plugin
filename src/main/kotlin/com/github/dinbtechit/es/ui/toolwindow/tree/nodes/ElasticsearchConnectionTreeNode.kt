package com.github.dinbtechit.es.ui.toolwindow.tree.nodes

import com.github.dinbtechit.es.actions.DuplicateAction
import com.github.dinbtechit.es.actions.NewAction
import com.github.dinbtechit.es.actions.RefreshAction
import com.github.dinbtechit.es.actions.ViewPropertiesAction
import com.github.dinbtechit.es.actions.popup.ConnectAction
import com.github.dinbtechit.es.actions.popup.DisconnectAction
import com.github.dinbtechit.es.actions.popup.new.NewAliasAction
import com.github.dinbtechit.es.actions.popup.new.NewIndexAction
import com.github.dinbtechit.es.actions.popup.new.NewPipelineAction
import com.github.dinbtechit.es.actions.popup.new.NewTemplateAction
import com.github.dinbtechit.es.notification.NotificationID
import com.github.dinbtechit.es.configuration.ConnectionInfo
import com.github.dinbtechit.es.shared.ProjectUtil
import com.github.dinbtechit.es.ui.toolwindow.tree.ElasticsearchTree
import com.intellij.icons.AllIcons
import com.intellij.ide.actions.DeleteAction
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.actionSystem.Separator
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.progress.runBackgroundableTask
import icons.ElasticsearchIcons
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean

class ElasticsearchConnectionTreeNode(
    val connectionInfo: ConnectionInfo
) : ElasticsearchTreeNode<ConnectionInfo, ElasticsearchTreeNode<*, *>>(
    icon = ElasticsearchIcons.Logo,
    connectionInfo
) {

    private val project = ProjectUtil.currentProject()


    var isConnected = false
    var isLoading = AtomicBoolean(false)
    var isError = false


    fun connect(tree: ElasticsearchTree) {
        isLoading.set(true)
        val node = this
        CoroutineScope(Dispatchers.Default).launch {
            runBackgroundableTask(
                "Elasticsearch: Connecting to ${connectionInfo.name}...",
                com.github.dinbtechit.es.shared.ProjectUtil.currentProject(),
                false
            ) { progressIndicator ->
                progressIndicator.isIndeterminate = true
                try {
                    createESNodes(node)
                    isConnected = true
                    println("Connected...")
                } catch (e: Exception) {
                    this.thisLogger().warn("Unable to Connect to " +
                            "Elasticsearch instance - ${connectionInfo.name}", e)

                    val notificationContent = """
                        Unable to connect to ${connectionInfo.name} 
                        due to ${e.message}.
                        Try again or check if the cluster is available.
                    """.trimIndent()
                    NotificationGroupManager.getInstance().getNotificationGroup(NotificationID.ConnectionNotification)
                        .createNotification(notificationContent, NotificationType.ERROR)
                        .notify(project)

                    node.removeAllChildren()
                    isError = true
                } finally {
                    isLoading.set(false)
                }
            }
        }
    }

    fun refresh() {
        println("Refreshing...")
    }

    fun disconnect() {
        println("Disconnecting...")
        isConnected = false
    }

    override fun buildPopMenuItems(tree: ElasticsearchTree): DefaultActionGroup {
        val popupMenuItems = DefaultActionGroup()
        val manager = ActionManager.getInstance()
        if (!isConnected) popupMenuItems.add(ConnectAction()) else {
            popupMenuItems.add(DisconnectAction().apply {
                registerCustomShortcutSet(this.shortcutSet, tree)
            })
        }
        popupMenuItems.add(RefreshAction())
        popupMenuItems.addSeparator()

        val newMenuItems = DefaultActionGroup(
            "New", mutableListOf(
                manager.getAction(NewAction.ID),
                Separator(),
                NewIndexAction(),
                NewAliasAction(),
                NewTemplateAction(),
                NewPipelineAction()
            )
        ).apply {
            this.templatePresentation.icon = AllIcons.General.Add
            this.isPopup = true
        }

        popupMenuItems.add(newMenuItems)
        popupMenuItems.add(DuplicateAction())
        popupMenuItems.add(DeleteAction())
        popupMenuItems.addSeparator()
        val viewPropertiesAction = manager.getAction(ViewPropertiesAction.ID) as ViewPropertiesAction
        if (viewPropertiesAction.isEnabled) popupMenuItems.add(viewPropertiesAction)
        return popupMenuItems
    }

    private fun createESNodes(connectionNode: ElasticsearchConnectionTreeNode) {
        connectionNode.removeAllChildren()
        // Index
        val index = ElasticsearchIndexNode()
        connectionNode.add(index)
        index.loadDocuments()

        // Template
        val template = ElasticsearchTemplateNode()
        connectionNode.add(template)
        template.loadDocuments()

        // Pipeline
        val pipeline = ElasticsearchPipelineNode()
        connectionNode.add(pipeline)
        pipeline.loadDocuments()
    }

}