package com.github.dinbtechit.intellijelasticsearchplugin.services.state

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.Attribute
import com.intellij.util.xmlb.annotations.Tag
import com.intellij.util.xmlb.annotations.XCollection
import java.util.*

@State(
    name = "org.github.dinbtechit.ElasticSearchConfig",
    storages = [Storage("elasticsearch.config.xml", roamingType = RoamingType.PER_OS)]
)
class ElasticSearchConfig : PersistentStateComponent<ElasticSearchConfig> {

    @XCollection(propertyElementName = "connections")
    var connections = mutableListOf<ConnectionInfoState>()

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
data class ConnectionInfoState(
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
    var authenticationType: Int = 0,
    @Tag
    var username: String = ""
)

data class ConnectionInfo(
    var name: String = "",
    var uuid: String = UUID.randomUUID().toString(),
    var url: String = "",
    var protocol: String = "",
    var hostname: String = "localhost",
    var port: Int = 9200,
    var authenticationType: Int = 0,
    var username: String = "",
    var password: CharArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ConnectionInfo
        if (uuid != other.uuid) return false
        return true
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }
}


