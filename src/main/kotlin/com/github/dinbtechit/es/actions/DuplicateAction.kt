package com.github.dinbtechit.es.actions

import com.github.dinbtechit.es.models.elasticsearch.cat.CatAliasesRequest
import com.github.dinbtechit.es.models.elasticsearch.cat.CatIndicesRequest
import com.github.dinbtechit.es.models.elasticsearch.cat.CatIngestPipelinesRequest
import com.github.dinbtechit.es.models.elasticsearch.cat.CatTemplatesRequest
import com.github.dinbtechit.es.services.ElasticsearchHttpClient
import com.github.dinbtechit.es.services.state.ConnectionInfo
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

    // inline fun <S> ObjectMapper.readValue(s: String): List<S> = this.readValue(s, object : TypeReference<List<S>>() {})

    override fun actionPerformed(e: AnActionEvent) {
        println("duplicate Clicked")
    }

    override fun update(e: AnActionEvent) {
        if (e.getData(TreeDataKey.TREE_MODEL) != null) {
            val tree = e.getData(TreeDataKey.TREE_MODEL) as ElasticsearchTree
            e.presentation.isEnabled = !tree.isSelectionEmpty
        }
    }
}
