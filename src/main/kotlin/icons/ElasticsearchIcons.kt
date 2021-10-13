package icons

import com.intellij.openapi.util.IconLoader

object ElasticsearchIcons {
    @JvmField
    val logo = IconLoader.getIcon("/icons/elasticsearch-icon-13px.svg", javaClass)
    val logo_16px = IconLoader.getIcon("/icons/elasticsearch-icon-16px.svg", javaClass)

    val esIndices = IconLoader.getIcon("/icons/es-indices-16px.svg", javaClass)
    val esAliases = IconLoader.getIcon("/icons/es-alias-16px.svg", javaClass)
    val esPipelines = IconLoader.getIcon("/icons/es-pipeline-16px.svg", javaClass)
    val esTemplates = IconLoader.getIcon("/icons/es-template-16px.svg", javaClass)

}
