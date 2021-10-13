package com.github.dinbtechit.intellijelasticsearchplugin.models

import javax.swing.Icon
import javax.swing.tree.DefaultMutableTreeNode

class ESMutableTreeNode(
    icon: Icon,
    docType: ElasticsearchDocument.Types,
    childIcon: Icon,
    documents: List<ElasticsearchDocument> = listOf()
) {
    val esTree = DefaultMutableTreeNode(Parent(icon, docType))
    init {
        for (document in documents) {
            esTree.add(DefaultMutableTreeNode(Children(childIcon, document)))
        }
    }
    data class Parent(val icon: Icon, val type: ElasticsearchDocument.Types)
    data class Children(val icon: Icon, val document: ElasticsearchDocument)
}

