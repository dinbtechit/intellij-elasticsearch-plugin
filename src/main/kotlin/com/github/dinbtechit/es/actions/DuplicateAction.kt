package com.github.dinbtechit.es.actions

import com.github.dinbtechit.es.models.ElasticsearchDocument
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchRequest
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchResponse
import com.github.dinbtechit.es.services.ElasticsearchHttpClient
import com.github.dinbtechit.es.shared.ProjectUtil
import com.github.dinbtechit.es.ui.toolwindow.service.TreeModelController
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DuplicateAction : AnAction() {


    companion object {
        const val ID = "com.github.dinbtechit.es.actions.DuplicateAction"
    }

    var isEnabled = false
    val controller = ProjectUtil.currentProject().service<TreeModelController>()

    init {
        controller.subscribe {
            if (it.propertyName == TreeModelController.EventType.TREE_NODE_SELECTED.name) {
                this.isEnabled = true
            } else if (it.propertyName == TreeModelController.EventType.TREE_NODE_UNSELECTED.name) {
                this.isEnabled = false
            }
        }
    }


    override fun actionPerformed(e: AnActionEvent) {
        println("duplicate Clicked")
        val client = ElasticsearchHttpClient<AbstractElasticsearchRequest,
                AbstractElasticsearchResponse<ElasticsearchDocument>>()
        CoroutineScope(Dispatchers.IO).launch {
            client.run()
        }
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = this.isEnabled
    }
}
