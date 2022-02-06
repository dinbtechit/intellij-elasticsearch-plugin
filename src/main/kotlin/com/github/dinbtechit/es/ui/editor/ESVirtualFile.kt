package com.github.dinbtechit.es.ui.editor

import CapDefaultTableModel
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.dinbtechit.es.configuration.ConnectionInfo
import com.github.dinbtechit.es.models.elasticsearch.IndicesCollection
import com.github.dinbtechit.es.models.elasticsearch.index.Mapping
import com.github.dinbtechit.es.models.elasticsearch.index.MappingReq
import com.github.dinbtechit.es.models.elasticsearch.index.Search
import com.github.dinbtechit.es.services.ElasticsearchHttpClient
import com.github.wnameless.json.flattener.FlattenMode
import com.github.wnameless.json.flattener.JsonFlattener
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileSystem
import com.jetbrains.rd.util.first
import kotlinx.coroutines.flow.*
import java.io.InputStream
import java.io.OutputStream
import java.lang.Runnable
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap

open class ESVirtualFile(
    private val fileName: String = "Default", val connection: ConnectionInfo?
) : VirtualFile(), DumbAware {

    val mapper = jacksonObjectMapper()

    override fun getName(): String = fileName
    override fun getFileSystem(): VirtualFileSystem = ESVirtualFileSystem()
    override fun getPath(): String = name
    override fun isWritable(): Boolean = true
    override fun isDirectory(): Boolean = false
    override fun isValid(): Boolean = true
    override fun getParent(): VirtualFile? = null
    override fun getChildren(): Array<VirtualFile> = emptyArray()
    override fun contentsToByteArray(): ByteArray = ByteArray(0)
    override fun getTimeStamp(): Long = System.currentTimeMillis()
    override fun getLength(): Long = 0

    override fun getOutputStream(requestor: Any?, newModificationStamp: Long, newTimeStamp: Long): OutputStream {
        TODO("Not yet implemented")
    }

    override fun getInputStream(): InputStream {
        TODO("Not yet implemented")
    }

    override fun refresh(asynchronous: Boolean, recursive: Boolean, postRunnable: Runnable?) {
        TODO("Not yet implemented")
    }

    private suspend fun getFieldMappings(): Map<String, Any> {
        val mappingRequest = ElasticsearchHttpClient<MappingReq>()
        val mappingResultJson = mappingRequest.sendRequest(connection!!, MappingReq(name))
        val tempResult: Map<String, Map<String, Any?>> = mapper.readValue(mappingResultJson)
        return mapper.convertValue(tempResult.first().value["mappings"]!!)
    }

    private suspend fun getFlattenedFieldNames(
        hits: List<IndicesCollection.Hits.Hits>,
        sorted: Boolean = true
    ): List<Mapping> {
        val fields = mutableListOf<Mapping>()
        fun mappingsToObj(properties: Map<String, Any?>, parent: String? = null) {
            for ((k, v) in properties) {
                if (v is Map<*, *>
                    && v.containsKey("properties")
                    && v["properties"] is Map<*, *>
                    && v["type"] != "object"
                    && isParentAnArray(hits, k).not()
                ) {
                    mappingsToObj(
                        mapper.convertValue(v["properties"] as Map<String, Any?>),
                        if (parent.isNullOrBlank()) k else "$parent.$k"
                    )
                } else {
                    val type = if (v is Map<*, *> && v["type"] != null) v["type"] as String else null
                    if (parent.isNullOrBlank()) {
                        fields.add(Mapping(k, type))
                    } else {
                        fields.add(Mapping("$parent.$k", type))
                    }
                }
            }
        }

        val properties = getFieldMappings()["properties"]
        if (properties is Map<*, *>) mappingsToObj(properties as Map<String, Any?>)
        if (sorted) fields.sortedBy { name }
        return fields
    }

    private suspend fun searchIndex(): IndicesCollection {
        val searchRequest = ElasticsearchHttpClient<Search>()
        val searchResultJson = searchRequest.sendRequest(connection!!, Search(name))
        return mapper.readValue(searchResultJson)
    }

    private fun IndicesCollection.Hits.Hits.getFlattenedSource(): Map<String, Any?> {
        val json = jacksonObjectMapper().writeValueAsString(source)
        return JsonFlattener(json).withFlattenMode(FlattenMode.KEEP_PRIMITIVE_ARRAYS).flattenAsMap()
    }

    suspend fun getContentAsTable(): CapDefaultTableModel {
        val columns = Vector<Any>()
        val rows = Vector<Vector<Any?>>()
        println("getContentAsTable... Start")
        val hits = searchIndex().hits.hits
        columns.add("_id")
        val columnNames = getFlattenedFieldNames(hits)
        columns.addAll(columnNames.stream().map { it.name }.collect(Collectors.toList()))
        for (eachHit in hits) {
            getRow(eachHit, columnNames).collect {
                rows.add(it)
            }
        }
        println("getContentAsTable... Finished ${columns.size}cX${rows.size}r")
        return CapDefaultTableModel(rows, columns)
    }

    private fun getRow(
        eachHit: IndicesCollection.Hits.Hits,
        columnNames: List<Mapping>,
    ): Flow<Vector<Any?>> {
        return flow {
            val cell = Vector<Any?>()
            cell.add(eachHit.id)
            val flattenSource = eachHit.getFlattenedSource()
            for (col in columnNames) {
                cell.add(
                    if (col.type != "object") {
                        if (flattenSource[col.name] == null) eachHit.source[col.name] else flattenSource[col.name]
                    }
                    else eachHit.source[col.name]
                )
            }
            emit(cell)
        }
    }

    private fun isParentAnArray(hits: List<IndicesCollection.Hits.Hits>, colName: String?): Boolean {
        if (hits.isEmpty().not()) {
            val rootDocument = hits.stream().map{ it.source[colName]}.filter { it != null }.findFirst().orElse(null)
            if (rootDocument != null && rootDocument is List<*>) return true
        }
        return false
    }
}
