package com.github.dinbtechit.es.configuration

import com.github.dinbtechit.es.ui.dialogs.Constants
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.openapi.Disposable
import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import com.intellij.remoteServer.util.CloudConfigurationUtil
import com.intellij.util.EventDispatcher
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.Attribute
import com.intellij.util.xmlb.annotations.Tag
import com.intellij.util.xmlb.annotations.XCollection
import java.util.*

@State(name = "org.github.dinbtechit.ElasticSearchConfig", storages = [Storage("elasticsearch.config.xml")])
class ElasticSearchConfig : PersistentStateComponent<ElasticSearchConfig> {

    private val changeEventDispatcher = EventDispatcher.create(SettingsChangedListener::class.java)

    @XCollection(propertyElementName = "connections")
    var connections: MutableList<ConnectionInfoState> = mutableListOf()
        set(value) {
            field = value
            changeEventDispatcher.multicaster.settingsChanged()
        }

    companion object {
        @JvmStatic
        fun getInstance(project: Project): ElasticSearchConfig {
            return project.service()
        }
    }

    override fun getState(): ElasticSearchConfig = this

    override fun loadState(state: ElasticSearchConfig) = XmlSerializerUtil.copyBean(state, this)

    fun addChangesListener(listener: SettingsChangedListener, disposable: Disposable) =
        changeEventDispatcher.addListener(listener, disposable)

    interface SettingsChangedListener : EventListener {
        fun settingsChanged()
    }
}


fun ElasticSearchConfig.getAllConnectionInfo(): MutableList<ConnectionInfo> {
    val connectionInfos = mutableListOf<ConnectionInfo>()
    for (c in connections) {
        val newConnectionInfo = ConnectionInfo()
        val credentialAttributes =
            CloudConfigurationUtil.createCredentialAttributes("${Constants.CREDS_SERVICE_NAME} - ${c.uuid}", c.uuid)
        if (credentialAttributes != null) {
            val credentials = PasswordSafe.instance.get(credentialAttributes)
            connectionInfos.add(newConnectionInfo.apply {
                uuid = c.uuid
                name = c.name
                hostname = c.hostname
                port = c.port
                authenticationType = c.authenticationType
                username = c.username
                password = credentials?.password?.toCharArray()
                url = c.url
            })
        }
    }
    return connectionInfos
}


@Tag("connection-info")
data class ConnectionInfoState(
    @Attribute
    var name: String = "",
    @Attribute
    var uuid: String = UUID.randomUUID().toString(),
    @Tag
    var url: String = "http://localhost:9200",
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
    var url: String = "http://localhost:9200",
    var protocol: String = "",
    var hostname: String = "localhost",
    var port: Int = 9200,
    var authenticationType: Int = 0,
    var username: String = "",
    var password: CharArray? = null
) {


    override fun hashCode(): Int {
        return uuid.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConnectionInfo

        if (name != other.name) return false
        if (uuid != other.uuid) return false
        if (url != other.url) return false
        if (protocol != other.protocol) return false
        if (hostname != other.hostname) return false
        if (port != other.port) return false
        if (authenticationType != other.authenticationType) return false
        if (username != other.username) return false
        if (password != null) {
            if (other.password == null) return false
            if (!password.contentEquals(other.password)) return false
        } else if (other.password != null) return false

        return true
    }
}


