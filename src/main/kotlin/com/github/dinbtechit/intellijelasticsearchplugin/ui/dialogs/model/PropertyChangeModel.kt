package com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.model

import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ConnectionInfo
import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ElasticSearchConfig
import com.github.dinbtechit.intellijelasticsearchplugin.shared.ProjectUtils
import java.beans.PropertyChangeListener
import javax.swing.event.SwingPropertyChangeSupport


class PropertyChangeModel {

    enum class EventType {
        SELECTED,
        UNSELECTED,
        ADD_CONNECTION,
        DELETE_CONNECTION
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

    fun getAllConnectionInfos(): MutableList<ConnectionInfo> {
        return listOfConnections
    }

    fun setConnectionInfo(i: Any) {
        val oldValue = -1
        pcs.firePropertyChange(EventType.SELECTED.name, oldValue, i)
    }

    fun unselectCollectionInfo() {
        val oldValue = connectionInfo
        pcs.firePropertyChange(EventType.UNSELECTED.name, oldValue, -1)
    }

    fun addConnection(i: ConnectionInfo) {
        val oldValue = connectionInfo
        listOfConnections.add(0, i)
        pcs.firePropertyChange(EventType.ADD_CONNECTION.name, oldValue, i)
    }

    fun deleteConnection(i: ConnectionInfo) {
        val oldValue = connectionInfo
        listOfConnections.remove(i)
        pcs.firePropertyChange(EventType.DELETE_CONNECTION.name, oldValue, i)
    }


}
