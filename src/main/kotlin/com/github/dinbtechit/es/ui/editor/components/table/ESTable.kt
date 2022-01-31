

import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.colors.EditorFontType
import com.intellij.ui.JBColor
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.JBUI
import java.awt.Component
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.*
import javax.swing.event.ListSelectionEvent
import javax.swing.table.TableCellRenderer
import javax.swing.table.TableColumn
import javax.swing.table.TableColumnModel
import javax.swing.table.TableModel
import kotlin.math.max
import kotlin.math.min

class ESTable : JBTable {
    val sortModel: CapSortModel = CapSortModel()
    val headerMouseListeners: MutableList<((sortModel: CapSortModel) -> Unit)> = mutableListOf()

    constructor() : super() {
        initComponent()
    }

    constructor(model: CapDefaultTableModel) : super(model) {
        initComponent()
    }

    constructor(model: TableModel, columnModel: TableColumnModel) : super(model, columnModel) {
        initComponent()
    }

    private fun initComponent() {
        val colorsScheme = EditorColorsManager.getInstance().globalScheme
        font = colorsScheme.getFont(EditorFontType.PLAIN)
        autoResizeMode = JBTable.AUTO_RESIZE_OFF
        cellSelectionEnabled = true
        setDefaultRenderer(Any::class.java, CapTableColoredCellRenderer.instance)
        tableHeader.defaultRenderer = CapTableColumnHeaderCellRenderer()
        tableHeader.font = font
        background = EditorColorsManager.getInstance().schemeForCurrentUITheme.defaultBackground
        tableHeader.background = ESTableUI.getResultTableHeaderColor()
        tableHeader.isOpaque = true
        tableHeader.foreground = colorsScheme.defaultForeground
        tableHeader.border = JBUI.Borders.customLine(JBColor.border(), 1, 0, 0, 0)
        tableHeader.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (rowAtPoint(e.point) == 0 || rowCount == 0) {
                    val columnViewIndex: Int = columnAtPoint(e.point)
                    if (columnViewIndex == -1) return
                    val columnModelIndex: Int = convertColumnIndexToModel(columnViewIndex)
                    val columnInfo = getColumnInfo(columnViewIndex)

                    if (columnInfo?.sortField != null) {
                        sortModel.changeSort(columnInfo.sortField)
                        val tableModel: CapDefaultTableModel = model as CapDefaultTableModel
                        var vector = tableModel.dataVector.map {
                            (it as Vector<Any>)
                        }
                        sortModel.getSortFields().reversed().forEach { field ->
                            vector = when (columnInfo.sortType) {
                                CapSortType.DOUBLE -> when (field.order) {
                                    CapSortOrder.ASC -> when (columnModelIndex) {
                                        else -> vector.sortedBy {
                                            it[columnModelIndex].toString().toDouble()
                                        }
                                    }
                                    CapSortOrder.DESC -> when (columnModelIndex) {
                                        else -> vector.sortedByDescending {
                                            it[columnModelIndex].toString().toDouble()
                                        }
                                    }
                                }
                                CapSortType.LONG -> when (field.order) {
                                    CapSortOrder.ASC -> when (columnModelIndex) {
                                        else -> vector.sortedBy {
                                            it[columnModelIndex].toString().toLong()
                                        }
                                    }
                                    CapSortOrder.DESC -> when (columnModelIndex) {
                                        else -> vector.sortedByDescending {
                                            it[columnModelIndex].toString().toLong()
                                        }
                                    }
                                }
                                else -> when (field.order) {
                                    CapSortOrder.ASC -> when (columnModelIndex) {
                                        else -> vector.sortedBy {
                                            it[columnModelIndex].toString()
                                        }
                                    }
                                    CapSortOrder.DESC -> when (columnModelIndex) {
                                        else -> vector.sortedByDescending {
                                            it[columnModelIndex].toString()
                                        }
                                    }
                                }
                            }
                        }
                        tableModel.dataVector.clear()
                        vector.forEach {
                            tableModel.addRow(it)
                        }
                        headerMouseListeners.forEach {
                            it.invoke(sortModel)
                        }
                    }
                }
            }
        })

        gridColor = ESTableUI.getTableGridColor()
        columnModel.addColumnModelListener(object : CapTableColumnModelListener() {
            override fun columnSelectionChanged(e: ListSelectionEvent?) {
                if (e == null) {
                    return
                }
                tableHeader.repaint(tableHeader.getHeaderRect(e.firstIndex))
                tableHeader.repaint(tableHeader.getHeaderRect(e.lastIndex))
            }
        })

        addFocusListener(object : FocusAdapter() {
            override fun focusLost(e: FocusEvent) {
                clearSelection()
            }
        })

        adjustColumnsBySize()
        isOpaque = true
        fillsViewportHeight = true
    }

    fun registerHeaderMouseListener(listener: (sortModel: CapSortModel) -> Unit) {
        headerMouseListeners.add(listener)
    }

    fun getColumnInfo(column: Int): CapTableColumnInfo? {
        return try {
            (model as CapDefaultTableModel).columnInfos[convertColumnIndexToModel(column)]
        } catch (e: Exception) {
            null
        }
    }

    fun getColumnModelIndex(column: Int): Int {
        return convertColumnIndexToModel(column)
    }

    fun getRowModelIndex(row: Int): Int {
        return convertRowIndexToModel(row)
    }

    fun adjustColumnsBySize() {
        for (column in 0 until columnCount) {
            val tableColumn: TableColumn = getColumnModel().getColumn(column)
            val renderer = tableHeader.defaultRenderer as CapTableColumnHeaderCellRenderer
            val header: Component =
                renderer.getTableCellRendererComponent(
                    table = this,
                    value = tableColumn.headerValue,
                    isSelected = false,
                    hasFocus = false,
                    row = 0,
                    column = column
                )
            var preferredWidth = header.preferredSize.width + 16
            val maxWidth = max(500, preferredWidth)
            for (row in 0 until rowCount) {
                val cellRenderer: TableCellRenderer = getCellRenderer(row, column)
                val c: Component = prepareRenderer(cellRenderer, row, column)
                val width: Int = c.preferredSize.width + intercellSpacing.width
                preferredWidth = min(max(preferredWidth, width), maxWidth)

                if (preferredWidth == maxWidth) {
                    break
                }
            }
            tableColumn.preferredWidth = preferredWidth
            updateUI()
        }
    }
}


