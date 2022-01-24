package com.github.dinbtechit.es.ui.editor.components

import CapDefaultTableModel
import ESTable
import ESTableUI
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.dinbtechit.es.models.elasticsearch.IndicesCollection
import com.github.dinbtechit.es.models.elasticsearch.index.MappingReq
import com.github.dinbtechit.es.models.elasticsearch.index.Search
import com.github.dinbtechit.es.services.ElasticsearchHttpClient
import com.github.dinbtechit.es.ui.editor.ESVirtualFile
import com.github.dinbtechit.es.ui.editor.components.table.RowNumberTable
import com.github.wnameless.json.flattener.FlattenMode
import com.github.wnameless.json.flattener.JsonFlattener
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.components.JBScrollPane
import com.intellij.util.ui.JBUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.awt.BorderLayout
import java.awt.Color
import javax.swing.JPanel
import javax.swing.ScrollPaneConstants

class EditorContent(
    val file: VirtualFile
) : JPanel() {

    // Component
    private val splitter = OnePixelSplitter(true, 0.01f)
    private val editorQueryPanel = EditorQueryPanel()
    private val editorBodyPanel = JPanel()
    private val tableModel = EditorTableModel()
    val table = ESTable(tableModel)

    init {
        layout = BorderLayout(0, 0)
        background = Color.decode("#2B2B2B")
        splitter.apply {
            setResizeEnabled(false)
            firstComponent = editorQueryPanel
            secondComponent = createEditorBody()
        }
        add(splitter, BorderLayout.CENTER)
    }

    private fun createEditorBody(): JPanel {
        return JPanel().apply {
            background = Color.decode("#2B2B2B")
            layout = BorderLayout(0, 0)
            val scrollPanel = JBScrollPane(
                table,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
            ).apply {
                background = EditorColorsManager.getInstance().schemeForCurrentUITheme.defaultBackground
                setRowHeaderView(RowNumberTable(table))
                getCorner(JBScrollPane.UPPER_LEFT_CORNER).apply {
                    background = ESTableUI.getResultTableHeaderColor()
                    border = JBUI.Borders.merge(
                        border,
                        JBUI.Borders.customLine(Color.BLUE, 1, 0, 1, 1),
                        true
                    )

                }
            }
            // Add Components
            add(scrollPanel, BorderLayout.CENTER)
        }
    }

    inner class EditorTableModel : CapDefaultTableModel() {
        init {
            CoroutineScope(Dispatchers.Default).launch {
                if (file is ESVirtualFile) {
                    val mapper = jacksonObjectMapper()

                    val mappingRequest = ElasticsearchHttpClient<MappingReq>()
                    val mappingResultJson = mappingRequest.sendRequest(file.connection!!, MappingReq(file.name))
                    val mappings = mutableListOf<String>()
                    val tempResult: Map<String, Map<String, Any?>> = mapper.readValue(mappingResultJson)
                    val firstMappings = tempResult[file.name]?.get("mappings")
                    val properties: Map<String, Any?> = mapper.convertValue(firstMappings!!)

                    fun mappingsToObj(properties: Map<String, Any?>, parent: String? = null) {
                        for ((k, v) in properties) {
                            if (v is Map<*, *> && v.containsKey("properties") && v["properties"] is Map<*, *>) {
                                mappingsToObj(mapper.convertValue(v["properties"] as Map<String, Any?>),
                                    if (parent.isNullOrBlank()) k else "$parent.$k")
                            } else {
                                if (parent.isNullOrBlank()) mappings.add(k) else mappings.add("$parent.$k")
                            }
                        }
                    }
                    if (properties["properties"] is Map<*, *>) {
                        mappingsToObj(properties["properties"] as Map<String, Any?>)
                    }


                    val searchRequest = ElasticsearchHttpClient<Search>()
                    val searchResultJson = searchRequest.sendRequest(file.connection, Search(file.name))
                    val index: IndicesCollection = mapper.readValue(searchResultJson)

                    mappings.sort()

                    addColumn("_id")
                    for (col in mappings) {
                        addColumn(col)
                    }

                    for (eachHit in index.hits.hits) {
                        val row = arrayListOf<Any?>(eachHit.id)
                        for (col in mappings) {
                            row.add(eachHit.getFlattenedSource()[col])
                        }
                        addRow(row.toArray())
                    }

                    table.adjustColumnsBySize()
                    table.repaint()
                    table.revalidate()
                }
            }
        }

        fun IndicesCollection.Hits.Hits.getFlattenedSource(): Map<String, Any?> {
            val json = jacksonObjectMapper().writeValueAsString(source)
            return JsonFlattener(json).withFlattenMode(FlattenMode.KEEP_PRIMITIVE_ARRAYS).flattenAsMap()
        }

    }

}