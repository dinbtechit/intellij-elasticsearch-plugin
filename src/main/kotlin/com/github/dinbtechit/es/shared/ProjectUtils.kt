package com.github.dinbtechit.es.shared

import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project

class ProjectUtils {

    fun currentProject(): Project {
        val dataContext = DataManager.getInstance().dataContextFromFocusAsync.blockingGet(2000)
        return dataContext?.getData(CommonDataKeys.PROJECT) as Project
    }
}

