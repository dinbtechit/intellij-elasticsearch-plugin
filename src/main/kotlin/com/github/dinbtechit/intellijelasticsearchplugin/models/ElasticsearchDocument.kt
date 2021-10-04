package com.github.dinbtechit.intellijelasticsearchplugin.models

import java.util.*
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.MutableTreeNode
import javax.swing.tree.TreeNode
import kotlin.collections.ArrayList

interface ElasticsearchDocument {
    enum class Types(val value: String) {
        ALIAS("Aliases"),
        INDICES("Indices"),
        TEMPLATES("Templates"),
        INGEST_PIPELINES("Ingest-Pipelines")
    }
}

class Indices(
    val nodes: List<ESIndex>
): ElasticsearchDocument {
    fun getIndicesNodes(): List<DefaultMutableTreeNode> {
        val nodesList = ArrayList<DefaultMutableTreeNode>();
        for (node in nodes) {
            nodesList.add(DefaultMutableTreeNode(node.name))
        }
        return nodesList;
    }
}

data class ESIndex(
    val name: String,
    val health: String,
    val size: String
)

class Templates(
    val nodes: List<ESTemplate>
): ElasticsearchDocument

data class ESTemplate(
    val name: String
)

class Aliases(
    val nodes: List<ESAlias>
): ElasticsearchDocument

data class ESAlias(
    val name: String
)


class IngestPipeline(
    val nodes: List<ESIngestPipeline>
): ElasticsearchDocument

data class ESIngestPipeline(
    val name: String
)

