package icons

import com.intellij.openapi.util.IconLoader

object ElasticsearchIcons {
    @JvmField
    val Logo_Small = IconLoader.getIcon("/icons/elasticsearch-icon-13px.svg", javaClass)
    val Logo = IconLoader.getIcon("/icons/elasticsearch-icon-16px.svg", javaClass)
    val logo_grey_16px = IconLoader.getIcon("/icons/elasticsearch-icon-grey-16px.svg", javaClass)
    val logo_grey_error_16px = IconLoader.getIcon("/icons/elasticsearch-icon-grey-error-16px.svg", javaClass)

    @JvmField
    val connectionProperties = IconLoader.getIcon("/icons/connection-properties.svg", javaClass)
    @JvmField
    val NewConnection = IconLoader.getIcon("/icons/new-connection.svg", javaClass)

    val esIndices = IconLoader.getIcon("/icons/es-indices-16px.svg", javaClass)
    val esAliases = IconLoader.getIcon("/icons/es-alias-16px.svg", javaClass)
    val esPipelines = IconLoader.getIcon("/icons/es-pipeline-16px.svg", javaClass)
    val esTemplates = IconLoader.getIcon("/icons/es-template-16px.svg", javaClass)
    val pipeline = IconLoader.getIcon("/icons/pipeline-16px.svg", javaClass)

    //General
    object General {
        val DataTable_16px = IconLoader.getIcon("/icons/datatable-16px.svg", javaClass)
        val DataTable_ReadOnly_16px = IconLoader.getIcon("/icons/dataTable-readonly-16px.svg", javaClass)
        val DataTable_Shortcut = IconLoader.getIcon("/icons/dataTable-shortcut-16px.svg", javaClass)
        val DataTable_Shortcut_Readonly = IconLoader.getIcon("/icons/dataTable-shortcut-readonly-16px.svg", javaClass)
        val DataTable_Write = IconLoader.getIcon("/icons/dataTable-write-16px.svg", javaClass)
        val UpArrow = IconLoader.getIcon("/icons/arrow_up_thick.svg", javaClass)
    }

    object Action {
        val View = IconLoader.getIcon("/icons/view-16px.svg", javaClass)
    }

    object DataTable {
        val DefaultTable = IconLoader.getIcon("/icons/datatable-16px.svg", javaClass)
        val Column = IconLoader.getIcon("/icons/database/col.svg", javaClass)
        val PreviewChanges = IconLoader.getIcon("/icons/database/previewChanges.svg", javaClass)
    }

    object Index {
        val DataTable_Green_16px = IconLoader.getIcon("/icons/datatable-green-16px.svg", javaClass)
        val DataTable_Yellow_16px = IconLoader.getIcon("/icons/datatable-yellow-16px.svg", javaClass)
        val DataTable_Red_16px = IconLoader.getIcon("/icons/datatable-red-16px.svg", javaClass)
    }

}
