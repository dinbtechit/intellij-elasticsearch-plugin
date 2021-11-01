package com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.controller

import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ConnectionInfo
import com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs.model.PropertyChangeModel

class NewDialogController() {
    private var model: PropertyChangeModel? = null

    // public PropertyChangeController() {
    // model = new PropertyChangeModel();
    // }


    // public PropertyChangeController() {
    // model = new PropertyChangeModel();
    // }
    constructor(model: PropertyChangeModel?) : this() {
        this.model = model
    }

    fun getAllConnections(): List<ConnectionInfo> {
        return model!!.getAllConnectionInfos()
    }

    fun selectConnectionInfo(i: Any?) {
        when (i) {
            is ConnectionInfo -> model!!.setConnectionInfo(i)
            is Int -> model!!.setConnectionInfo(i)
        }
    }


    fun unselectConnectionInfo() {
        model!!.unselectCollectionInfo()
    }

    fun addConnection(i: ConnectionInfo) {
        model!!.addConnection(i)
        selectConnectionInfo(i)
    }

    fun deleteConnection(i: ConnectionInfo) {
        model!!.deleteConnection(i)
    }
}



