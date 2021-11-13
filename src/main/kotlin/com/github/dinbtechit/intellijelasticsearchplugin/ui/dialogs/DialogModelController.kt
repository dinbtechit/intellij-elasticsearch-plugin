package com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs

import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ConnectionInfo
import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ConnectionInfoState
import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ElasticSearchConfig
import com.github.dinbtechit.intellijelasticsearchplugin.shared.ProjectUtils
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.Constants.CREDS_SERVICE_NAME
import com.intellij.credentialStore.Credentials
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.openapi.application.ApplicationManager
import com.intellij.remoteServer.util.CloudConfigurationUtil.createCredentialAttributes
import java.beans.PropertyChangeListener
import javax.swing.event.SwingPropertyChangeSupport


object Constants {
    val CREDS_SERVICE_NAME = "IntelliJ Elasticsearch Plugin"
}

class DialogModelController {

    enum class EventType {
        SELECTED,
        UNSELECTED,
        ADD_CONNECTION,
        DELETE_CONNECTION,
        ENABLE_APPLY_ACTION,
        DUPLICATE,
        SAVE,
        NAME_CHANGE,
        UPDATE_CONNECTION_INFO
    }

    class DataKey {
        companion object {
            val ES_CONNECTIONS =
                com.intellij.openapi.actionSystem.DataKey.create<List<ConnectionInfo>>("connectionsList")
            val SELECTED_CONNECTION =
                com.intellij.openapi.actionSystem.DataKey.create<List<ConnectionInfo>>("selectedConnection")
            val SELECTED_CONNECTION_INDEX =
                com.intellij.openapi.actionSystem.DataKey.create<List<ConnectionInfo>>("selectedConnectionIndex")
        }
    }

    private val config = ElasticSearchConfig.getInstance(ProjectUtils().currentProject())

    private var connectionInfo: ConnectionInfo = ConnectionInfo()
    private val listOfConnections = config.state.connections
    private var pcs: SwingPropertyChangeSupport = SwingPropertyChangeSupport(this)

    fun addPropertyChangeListener(l: PropertyChangeListener?) {
        pcs.addPropertyChangeListener(l)
    }

    fun addConnection() {
        val connection = ConnectionInfo()
        pcs.firePropertyChange(EventType.ADD_CONNECTION.name, -1, connection)
    }

    fun getAllConnectionInfos(): MutableList<ConnectionInfo> {
        val connectionInfos = mutableListOf<ConnectionInfo>()
        for (c in listOfConnections) {
            val newConnectionInfo = ConnectionInfo()
            val credentialAttributes = createCredentialAttributes(CREDS_SERVICE_NAME, c.uuid)
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

    fun save(connections: List<ConnectionInfo>) {
        val tempConnections = connections.toMutableList()
        val connectionInfoState = mutableListOf<ConnectionInfoState>()
        for (conn in tempConnections) {
            val newConnectionInfoState = ConnectionInfoState()
            newConnectionInfoState.apply {
                uuid = conn.uuid
                name = conn.name
                hostname = conn.hostname
                port = conn.port
                authenticationType = conn.authenticationType
                username = conn.username
                url = conn.url
            }
            connectionInfoState.add(newConnectionInfoState)

            val credentialAttributes = createCredentialAttributes(CREDS_SERVICE_NAME, conn.uuid)
            val credentials = if (credentialAttributes != null
                && conn.password != null && conn.password!!.isNotEmpty()
                && conn.authenticationType != 1
            ) {
                Credentials(conn.username, conn.password)
            } else null

            if (credentialAttributes != null) {
                PasswordSafe.instance.set(credentialAttributes, credentials)
            }
        }
        config.state.connections = connectionInfoState
    }

    fun selectConnectionInfo(i: Any) {
        val oldValue = -1
        pcs.firePropertyChange(EventType.SELECTED.name, oldValue, i)
    }


    fun unselectCollectionInfo() {
        val oldValue = connectionInfo
        pcs.firePropertyChange(EventType.UNSELECTED.name, oldValue, -1)
    }

    fun saveConnectionChanges() {
        pcs.firePropertyChange(EventType.SAVE.name, 1, -1)
        ApplicationManager.getApplication().saveAll()
    }

    fun duplicate(connections: List<ConnectionInfo>) {
        println("Duplicate")
    }

    fun changeName(newValue: String) {
        pcs.firePropertyChange(EventType.NAME_CHANGE.name, -1, newValue)
    }

    fun updateConnectionInfo(connectionInfo: ConnectionInfo) {
        pcs.firePropertyChange(EventType.UPDATE_CONNECTION_INFO.name, -1, connectionInfo)
    }
}
