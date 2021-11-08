package com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs

import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ConnectionInfo
import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ElasticSearchConfig
import com.github.dinbtechit.intellijelasticsearchplugin.shared.ProjectUtils
import java.beans.PropertyChangeListener
import javax.swing.event.SwingPropertyChangeSupport


class DialogModelController {

    enum class EventType {
        SELECTED,
        UNSELECTED,
        ADD_CONNECTION,
        DELETE_CONNECTION,
        ENABLE_APPLY_ACTION,
        DUPLICATE,
        SAVE
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
        return listOfConnections
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
    }

    fun save(connections: List<ConnectionInfo>) {
        config.state.connections = connections.toMutableList()
    }

    fun duplicate(connections: List<ConnectionInfo>) {

    }

}
