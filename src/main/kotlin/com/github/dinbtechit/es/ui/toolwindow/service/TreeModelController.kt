package com.github.dinbtechit.es.ui.toolwindow.service

import com.github.dinbtechit.es.ui.toolwindow.tree.ElasticsearchTree
import com.intellij.openapi.components.Service
import java.beans.PropertyChangeListener
import javax.swing.event.SwingPropertyChangeSupport
import javax.swing.event.TreeSelectionEvent

@Service
class TreeModelController {

    private var pcs: SwingPropertyChangeSupport = SwingPropertyChangeSupport(this)

    enum class EventType {
        TREE_NODE_SELECTED,
        TREE_NODE_UNSELECTED
    }

    fun subscribe(l: PropertyChangeListener?) {
        pcs.addPropertyChangeListener(l)
    }

    fun selectedTree(event: TreeSelectionEvent) {
        when(val source = event.source) {
            is ElasticsearchTree -> {
                val count = source.selectionCount
                if (count > 0) {
                    pcs.firePropertyChange(EventType.TREE_NODE_SELECTED.name, -1, source)
                } else {
                    pcs.firePropertyChange(EventType.TREE_NODE_UNSELECTED.name, -1, source)
                }
            }
        }
    }
}