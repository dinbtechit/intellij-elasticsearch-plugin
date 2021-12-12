package com.github.dinbtechit.es.actions

import com.github.dinbtechit.es.services.ElasticsearchHttpClient
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class DuplicateAction: AnAction() {
    companion object {
        const val ID = "com.github.dinbtechit.es.actions.DuplicateAction"
    }

    override fun actionPerformed(e: AnActionEvent) {
        println("duplicate Clicked")
        val client = ElasticsearchHttpClient()
    }

    fun callClient(client: ElasticsearchHttpClient) {

    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = true
    }
}
