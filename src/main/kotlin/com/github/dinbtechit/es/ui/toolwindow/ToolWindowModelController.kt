package com.github.dinbtechit.es.ui.toolwindow

import java.beans.PropertyChangeListener
import javax.swing.event.SwingPropertyChangeSupport

class ToolWindowModelController {

    private var pcs: SwingPropertyChangeSupport = SwingPropertyChangeSupport(this)

    fun subscribe(l: PropertyChangeListener?) {
        pcs.addPropertyChangeListener(l)
    }



}