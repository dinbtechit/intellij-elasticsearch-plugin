package com.github.dinbtechit.es.actions.editorToolbar

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.openapi.actionSystem.impl.ActionButtonWithText
import com.intellij.util.ui.JBDimension

class SetRowSizeAction : ActionButtonWithText(
    SizeValueAction(), Presentation("0 Rows"),
    "ElasticsearchEditor", JBDimension(50, 30)
) {
}


class SizeValueAction : DefaultActionGroup("", true) {

    override fun useSmallerFontForTextInToolbar(): Boolean = true
    init {
        add(Size20())
        add(Size100())
        add(Size500())
        add(Size10000())
    }
}


class Size20 : AnAction("20", "", null) {
    override fun actionPerformed(e: AnActionEvent) {
        TODO("Not yet implemented")
    }

}

class Size100 : AnAction("100", "", null) {
    override fun actionPerformed(e: AnActionEvent) {
        TODO("Not yet implemented")
    }

}

class Size500 : AnAction("500", "", null) {
    override fun actionPerformed(e: AnActionEvent) {
        TODO("Not yet implemented")
    }

}

class Size10000 : AnAction("10,000", "", null) {
    override fun actionPerformed(e: AnActionEvent) {
        TODO("Not yet implemented")
    }

}
