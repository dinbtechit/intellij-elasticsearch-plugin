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

    fun getCounter(): ConnectionInfo {
        return model!!.getConnectionInfo()
    }

    fun selectConnectionInfo(i: ConnectionInfo) {
        model!!.setConnectionInfo(i)
    }

    fun unselectConnectionInfo() {
        model!!.unselectCollectionInfo()
    }
}



