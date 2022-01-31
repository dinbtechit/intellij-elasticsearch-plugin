package com.github.dinbtechit.es.actions.editorToolbar

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.openapi.actionSystem.impl.ActionButtonWithText
import com.intellij.util.ui.JBDimension
import java.beans.PropertyChangeEvent

var size = 10

class SetRowSizeAction : ActionButtonWithText(
    SizeValueAction(), Presentation("$size Rows"),
    "ElasticsearchEditor", JBDimension(50, 30)
) {
    companion object {
        var Action: AnAction? = null
    }

    init {
        Action = action
    }

    override fun presentationPropertyChanged(e: PropertyChangeEvent) {
        super.presentationPropertyChanged(e)
    }
}


class SizeValueAction : DefaultActionGroup("RowSize", true) {

    override fun useSmallerFontForTextInToolbar(): Boolean = true
    init {
        add(Size10())
        add(Size100())
        add(Size500())
        add(Size10000())
    }
}


class Size10 : AnAction("10 (Default)", "", null) {
    override fun actionPerformed(e: AnActionEvent) {

    }

}

class Size100 : AnAction("100", "", null) {
    override fun actionPerformed(e: AnActionEvent) {
        SetRowSizeAction.Action!!.templatePresentation.text = "100"
    }

}

class Size500 : AnAction("500", "", null) {
    override fun actionPerformed(e: AnActionEvent) {
        SetRowSizeAction.Action!!.templatePresentation.text = "500"
    }

}

class Size10000 : AnAction("10,000", "", null) {
    override fun actionPerformed(e: AnActionEvent) {
        SetRowSizeAction.Action!!.templatePresentation.text = "10,000"
    }

}
