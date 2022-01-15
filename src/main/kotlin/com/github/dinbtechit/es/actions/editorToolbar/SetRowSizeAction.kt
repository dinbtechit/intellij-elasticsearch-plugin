package com.github.dinbtechit.es.actions.editorToolbar

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.openapi.actionSystem.impl.ActionButtonWithText
import java.awt.Dimension

class Size0 : AnAction("0 Rows", "", null) {
    override fun actionPerformed(e: AnActionEvent) {
        TODO("Not yet implemented")
    }

}

class Size10 : AnAction("10 Rows", "", null) {
    override fun actionPerformed(e: AnActionEvent) {
        TODO("Not yet implemented")
    }

}

class SizeValueAction : DefaultActionGroup("", true){
    init {
        add(Size0())
        add(Size10())
    }
}

class SetRowSizeAction : ActionButtonWithText(
    SizeValueAction(), Presentation("0 Rows"),
    "ElasticsearchEditor", Dimension(50, 30)
) {

    override fun actionPerformed(e: AnActionEvent) {
        println("Refresh Index")
    }

}
