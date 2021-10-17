package com.github.dinbtechit.intellijelasticsearchplugin.ui.dialogs

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NewDialogController {

    private fun getConfiguration(): Flow<String> = flow {
        for (i in 1..10) {
            emit("$i")
        }
    }

    fun subscriber() = GlobalScope.launch {
        getConfiguration().collect { value -> println(value) }
    }

}

