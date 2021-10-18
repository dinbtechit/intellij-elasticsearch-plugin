package com.github.dinbtechit.intellijelasticsearchplugin.services

import com.intellij.openapi.project.Project
import com.github.dinbtechit.intellijelasticsearchplugin.MyBundle
import com.github.dinbtechit.intellijelasticsearchplugin.services.state.ElasticSearchConfig

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
