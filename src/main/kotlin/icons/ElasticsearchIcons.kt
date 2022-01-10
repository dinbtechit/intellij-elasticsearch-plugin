package icons

import com.intellij.openapi.util.IconLoader

object ElasticsearchIcons {
    @JvmField
    val logo = IconLoader.getIcon("/icons/elasticsearch-icon-13px.svg", javaClass)
    val logo_16px = IconLoader.getIcon("/icons/elasticsearch-icon-16px.svg", javaClass)
    val logo_grey_16px = IconLoader.getIcon("/icons/elasticsearch-icon-grey-16px.svg", javaClass)
    val logo_grey_error_16px = IconLoader.getIcon("/icons/elasticsearch-icon-grey-error-16px.svg", javaClass)

    @JvmField
    val connectionProperties = IconLoader.getIcon("/icons/connection-properties.svg", javaClass)
    @JvmField
    val newconnection = IconLoader.getIcon("/icons/new-connection.svg", javaClass)

    val esIndices = IconLoader.getIcon("/icons/es-indices-16px.svg", javaClass)
    val esAliases = IconLoader.getIcon("/icons/es-alias-16px.svg", javaClass)
    val esPipelines = IconLoader.getIcon("/icons/es-pipeline-16px.svg", javaClass)
    val esTemplates = IconLoader.getIcon("/icons/es-template-16px.svg", javaClass)

    //General
    object General {
        val DataTable_16px = IconLoader.getIcon("/icons/datatable-16px.svg", javaClass)
        val DataTable_ReadOnly_16px = IconLoader.getIcon("/icons/dataTable-readonly-16px.svg", javaClass)
    }

    object Index {
        val DataTable_Green_16px = IconLoader.getIcon("/icons/datatable-green-16px.svg", javaClass)
        val DataTable_Yellow_16px = IconLoader.getIcon("/icons/datatable-yellow-16px.svg", javaClass)
        val DataTable_Red_16px = IconLoader.getIcon("/icons/datatable-red-16px.svg", javaClass)
    }

}
