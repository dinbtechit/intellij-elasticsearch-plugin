package com.github.dinbtechit.es.actions

import com.github.dinbtechit.es.models.ElasticsearchDocument
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchRequest
import com.github.dinbtechit.es.models.elasticsearch.AbstractElasticsearchResponse
import com.github.dinbtechit.es.services.ElasticsearchHttpClient
import com.github.dinbtechit.es.ui.toolwindow.models.TreeDataKey
import com.github.dinbtechit.es.ui.toolwindow.tree.ElasticsearchTree
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DuplicateAction : AnAction(
    "Elasticsearch: Duplicate", "Duplicate elasticsearch connection", AllIcons.Actions.Copy
) {

    companion object {
        const val ID = "com.github.dinbtechit.es.actions.DuplicateAction"
    }

    var isEnabled = false


    init {
        this.addTextOverride("Elasticsearch", "Duplicate")
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
        if (e.getData(TreeDataKey.TREE_MODEL) != null) {
            val tree = e.getData(TreeDataKey.TREE_MODEL) as ElasticsearchTree
            e.presentation.isEnabled = !tree.isSelectionEmpty
        }
    }
}
