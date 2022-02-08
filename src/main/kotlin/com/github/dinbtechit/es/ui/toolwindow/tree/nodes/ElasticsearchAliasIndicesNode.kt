package com.github.dinbtechit.es.ui.toolwindow.tree.nodes

import com.github.dinbtechit.es.models.elasticsearch.ESAlias
import com.github.dinbtechit.es.models.elasticsearch.ElasticsearchDocument
import icons.ElasticsearchIcons

class ElasticsearchAliasIndicesNode :
    ElasticsearchTreeNode<ElasticsearchDocument.Types, ChildData<ESAlias>>(
        ElasticsearchIcons.esIndices,
        data = ElasticsearchDocument.Types.INDICES
    ) {

    fun loadDocuments(indices: List<ESAlias>) {
            for (index in indices) {
                val child = ChildData(
                    icon = if (index.isWriteIndex) {
                        ElasticsearchIcons.General.DataTable_Write
                    } else ElasticsearchIcons.General.DataTable_ReadOnly_16px,
                    index.apply {
                        displayName = this.index!!
                    }
                )
                childData.add(child)
            }
            childData.sortByDescending { it.data.index }
            loadChildren()
    }

}