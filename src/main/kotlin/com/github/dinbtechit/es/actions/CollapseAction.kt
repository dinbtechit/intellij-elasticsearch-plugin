package com.github.dinbtechit.es.actions

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.project.DumbAware
import java.awt.event.KeyEvent
import javax.swing.Icon
import javax.swing.KeyStroke

class CollapseAction(icon: Icon) : AnAction("Collapse", "Collapse elasticsearch", icon), DumbAware {

    init {
        shortcutSet = ShortcutSet {
            arrayOf(
                KeyboardShortcut(KeyStroke.getKeyStroke(109, KeyEvent.CTRL_DOWN_MASK), null)
            )
        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        println("Collapse Clicked")
    }

}
