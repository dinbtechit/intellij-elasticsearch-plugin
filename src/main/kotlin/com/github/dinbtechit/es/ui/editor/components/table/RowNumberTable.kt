package com.github.dinbtechit.es.ui.editor.components.table

import ESTableUI
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.colors.EditorFontType
import com.intellij.ui.table.JBTable
import java.awt.Component
import java.awt.Font
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import javax.swing.*
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener
import javax.swing.event.TableModelListener
import javax.swing.table.DefaultTableCellRenderer
import javax.swing.table.TableCellRenderer
import javax.swing.table.TableColumn


/*
*	Use a JTable as a renderer for row numbers of a given main table.
*  This table must be added to the row header of the scrollpane that
*  contains the main table.
*/
class RowNumberTable(private val main: JBTable) : JTable(), ChangeListener, PropertyChangeListener,
    TableModelListener {

    private val colorsScheme = EditorColorsManager.getInstance().globalScheme

    init {
        font = colorsScheme.getFont(EditorFontType.PLAIN)
        main.addPropertyChangeListener(this)
        main.model.addTableModelListener(this)
        isFocusable = false
        setAutoCreateColumnsFromModel(false)
        setSelectionModel(main.selectionModel)
        setRowHeight(main.rowHeight)
        val column = TableColumn()
        column.headerValue = " "
        addColumn(column)
        column.cellRenderer = RowNumberRenderer()
        getColumnModel().getColumn(0).preferredWidth = 28
        tableHeader.background = ESTableUI.getResultTableHeaderColor()
        tableHeader.resizingAllowed = true
        tableHeader.reorderingAllowed = false
        gridColor = ESTableUI.getTableGridColor()
        background = ESTableUI.getTableBackground()
        preferredScrollableViewportSize = preferredSize
    }

    override fun addNotify() {
        super.addNotify()
        val c: Component = parent

        //  Keep scrolling of the row table in sync with the main table.
        if (c is JViewport) {
            c.addChangeListener(this)
        }
    }

    override fun prepareRenderer(renderer: TableCellRenderer, row: Int, column: Int): Component {
        return super.prepareRenderer(renderer, row, column).apply {
            val graduallyGrowColWidth = false // In the future this can be made as Table Policy
            preferredSize.width = updateColumnWidth(
                column, this.preferredSize.width, this@RowNumberTable
            )

            if (graduallyGrowColWidth) preferredScrollableViewportSize = preferredSize.apply { width += 20 }
            else getColumnModel().getColumn(0).preferredWidth = preferredSize.width
        }
    }

    private fun updateColumnWidth(column: Int, width: Int, table: JTable): Int {
        val tableColumn = table.columnModel.getColumn(column)
        val headerWidth: Int = ColumnHeaderRenderer().getTableCellRendererComponent(
            table,
            tableColumn.headerValue,
            false,
            false,
            -1,
            column
        ).preferredSize.width + 4
        val newWidth = Math.max(width, headerWidth) + 2 * table.intercellSpacing.width
        tableColumn.preferredWidth =
            Math.min(Math.max(newWidth, tableColumn.preferredWidth), 250)
        return newWidth
    }

    class ColumnHeaderRenderer : DefaultTableHeaderCellRenderer() {

        override fun getTableCellRendererComponent(
            table: JTable,
            value: Any,
            selected: Boolean,
            focused: Boolean,
            row: Int,
            column: Int
        ): Component {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column)
            val selectedColumn = table.selectedColumn
            if (selectedColumn == column) {
                setFont(getFont().deriveFont(Font.BOLD))
            }
            return this
        }
    }

    open class DefaultTableHeaderCellRenderer : DefaultTableCellRenderer() {
        init {
            horizontalAlignment = CENTER
            horizontalTextPosition = LEFT
            verticalAlignment = BOTTOM
            isOpaque = false
        }

        override fun getTableCellRendererComponent(
            table: JTable, value: Any,
            isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int
        ): Component {
            super.getTableCellRendererComponent(
                table, value,
                isSelected, hasFocus, row, column
            )
            val tableHeader = table.tableHeader
            if (tableHeader != null) {
                foreground = tableHeader.foreground
            }
            border = UIManager.getBorder("TableHeader.cellBorder")
            return this
        }
    }

    /*
	 *  Delegate method to main table
	 */
    override fun getRowCount(): Int = main.rowCount


    override fun getRowHeight(row: Int): Int {
        val rowHeight = main.getRowHeight(row)
        if (rowHeight != super.getRowHeight(row)) {
            super.setRowHeight(row, rowHeight)
        }
        return rowHeight
    }

    /*
	 *  No model is being used for this table so just use the row number
	 *  as the value of the cell.
	 */
    override fun getValueAt(row: Int, column: Int): Any {
        val rowNumber = (row + 1).toString()
        return rowNumber
    }

    /*
	 *  Don't edit data in the main TableModel by mistake
	 */
    override fun isCellEditable(row: Int, column: Int): Boolean = false

    /*
	 *  Do nothing since the table ignores the model
	 */
    override fun setValueAt(value: Any, row: Int, column: Int) {}

    //
    //  Implement the ChangeListener
    //
    override fun stateChanged(e: ChangeEvent) {
        //  Keep the scrolling of the row table in sync with main table
        val viewport = e.source as JViewport
        val scrollPane = viewport.parent as JScrollPane
        scrollPane.verticalScrollBar.value = viewport.viewPosition.y
    }

    //
    //  Implement the PropertyChangeListener
    //
    override fun propertyChange(e: PropertyChangeEvent) {
        //  Keep the row table in sync with the main table
        if ("selectionModel" == e.propertyName) setSelectionModel(main.selectionModel)
        if ("rowHeight" == e.propertyName) repaint()
        if ("model" == e.propertyName) {
            main.model.addTableModelListener(this)
            revalidate()
        }
    }


    /*
	 *  Attempt to mimic the table header renderer
	 */
    inner class RowNumberRenderer : DefaultTableCellRenderer() {
        init {
            horizontalAlignment = CENTER
            background = ESTableUI.getResultTableHeaderColor()
            isOpaque = true
            font = colorsScheme.getFont(EditorFontType.PLAIN)
        }

        override fun getTableCellRendererComponent(
            table: JTable, value: Any, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int
        ): Component {

            font = colorsScheme.getFont(EditorFontType.PLAIN)

            if (table != null) {
                val header = table.tableHeader
                if (header != null) {
                    foreground = header.foreground
                    background = ESTableUI.getResultTableHeaderColor()

                }
            }

            if (isSelected) {
                background = ESTableUI.getResultTableHeaderColor().darker()
            }

            text = value.toString() ?: ""
            border = UIManager.getBorder("TableHeader.cellBorder")
            return this
        }
    }
}