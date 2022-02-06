package com.github.dinbtechit.es.ui.toolwindow.tree.nodes

import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.dinbtechit.es.models.elasticsearch.ESIndexAlias
import com.github.dinbtechit.es.models.elasticsearch.ElasticsearchDocument
import icons.ElasticsearchIcons

class ElasticsearchIndexAliasNode :
    ElasticsearchTreeNode<ElasticsearchDocument.Types, ChildData<ESIndexAlias>>(
        ElasticsearchIcons.esAliases,
        data = ElasticsearchDocument.Types.ALIAS
    ) {

    init {
        //loadIndices()
    }

    fun loadDocuments(aliases: Map<String, Any>) {
            val mapper = jacksonObjectMapper()
            for ((k, v) in aliases) {
                val alias = mapper.convertValue<ESIndexAlias>(v)
                alias.displayName = k
                val child = ChildData(
                    icon = if (alias.isWriteIndex) {
                        ElasticsearchIcons.General.DataTable_Write
                    } else ElasticsearchIcons.General.DataTable_ReadOnly_16px,
                    alias
                )
                childData.add(child)
            }
            loadChildren()
    }

}