package com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.model

import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ConnectionInfo
import java.beans.PropertyChangeListener

import javax.swing.event.SwingPropertyChangeSupport


class PropertyChangeModel {

    companion object {
        const val CHANGED = "property change model updated"
    }

    private var connectionInfo: ConnectionInfo = ConnectionInfo()
    private var pcs: SwingPropertyChangeSupport = SwingPropertyChangeSupport(this)

    fun addPropertyChangeListener(l: PropertyChangeListener?) {
        pcs.addPropertyChangeListener(l)
    }

    fun getConnectionInfo(): ConnectionInfo {
        return connectionInfo
    }

    fun setConnectionInfo(i: ConnectionInfo) {
        val oldValue = connectionInfo
        connectionInfo = i
        pcs.firePropertyChange(CHANGED, oldValue, i)
    }

    fun unselectCollectionInfo(){
        val oldValue = connectionInfo
        pcs.firePropertyChange(CHANGED, oldValue, -1)
    }
}
