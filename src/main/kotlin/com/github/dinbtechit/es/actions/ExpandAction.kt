package com.github.dinbtechit.es.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.KeyboardShortcut
import com.intellij.openapi.actionSystem.ShortcutSet
import com.intellij.openapi.project.DumbAware
import java.awt.event.KeyEvent
import javax.swing.Icon
import javax.swing.KeyStroke

class ExpandAction(icon: Icon) : AnAction("Expand", "Expand elasticsearch", icon) {

    init {
        shortcutSet = ShortcutSet {
            arrayOf(
                KeyboardShortcut(KeyStroke.getKeyStroke(107, KeyEvent.CTRL_DOWN_MASK), null)
            )
        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        println("Expand Clicked")
    }

}
