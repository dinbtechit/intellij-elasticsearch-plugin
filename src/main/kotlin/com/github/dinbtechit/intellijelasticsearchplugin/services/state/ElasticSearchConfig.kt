package com.github.dinbtechit.intellijelasticsearchplugin.services.state

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.Attribute
import com.intellij.util.xmlb.annotations.Tag
import com.intellij.util.xmlb.annotations.XCollection
import java.util.*

@State(name = "org.github.dinbtechit.ElasticSearchConfig",
    storages = [Storage("elasticsearch.config.xml")])
class ElasticSearchConfig : PersistentStateComponent<ElasticSearchConfig> {

    @XCollection(propertyElementName = "connections")
    var connections = mutableListOf<ConnectionInfo>()

    companion object {
        @JvmStatic
        fun getInstance(project: Project): ElasticSearchConfig {
           return project.service()
        }
    }
    override fun getState(): ElasticSearchConfig = this

    override fun loadState(state: ElasticSearchConfig) = XmlSerializerUtil.copyBean(state, this)
}


@Tag("connection-info")
data class ConnectionInfo(
    @Attribute
    var name: String = "",
    @Attribute
    var uuid: String = UUID.randomUUID().toString(),
    @Tag
    var url: String = "",
    @Tag
    var protocol: String = "",
    @Tag
    var hostname: String = "localhost",
    @Tag
    var port: Int = 9200,
    @Tag
    var username: String = "",
    @Tag
    var password: String = ""
)


