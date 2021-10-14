package com.github.dinbtechit.intellijelasticsearchplugin.shared

import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project

object ProjectUtils {
    val currentProject: Project = let {
        val dataContext = DataManager.getInstance().dataContextFromFocusAsync.blockingGet(2000)
        dataContext?.getData(CommonDataKeys.PROJECT) as Project
    }
}

