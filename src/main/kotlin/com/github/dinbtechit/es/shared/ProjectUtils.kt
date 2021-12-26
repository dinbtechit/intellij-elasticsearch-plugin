package com.github.dinbtechit.es.shared

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.wm.WindowManager

object ProjectUtil {
    fun currentProject(): Project {
        val projectManager = ProjectManager.getInstance()
        val openProjects = projectManager.openProjects
        if (openProjects.isEmpty()) {
            return projectManager.defaultProject
        } else if (openProjects.size == 1) {
            return openProjects[0]
        }

        try {
            val wm = WindowManager.getInstance()
            for (project in openProjects) {
                val window = wm.suggestParentWindow(project)
                if (window != null && window.isActive) {
                    return project
                }
            }
        } catch (ignored: Exception) {
        }

        return projectManager.defaultProject
    }
}
