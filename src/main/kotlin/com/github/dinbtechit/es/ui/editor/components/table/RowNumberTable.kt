package com.github.dinbtechit.es.ui.editor.components.table

import ESTableUI
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.colors.EditorFontType
import com.intellij.ui.table.JBTable
import java.awt.Component
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import javax.swing.JScrollPane
import javax.swing.JTable
import javax.swing.JViewport
import javax.swing.UIManager
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener
import javax.swing.event.TableModelListener
import javax.swing.table.DefaultTableCellRenderer
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
        val column = TableColumn()
        column.headerValue = " "
        addColumn(column)
        column.cellRenderer = RowNumberRenderer()
        getColumnModel().getColumn(0).preferredWidth = 25
        tableHeader.background = ESTableUI.getResultTableHeaderColor()
        tableHeader.resizingAllowed = true
        autoResizeMode = AUTO_RESIZE_ALL_COLUMNS
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
        preferredScrollableViewportSize = preferredSize.apply {
            width = 25 + (rowNumber.length * 5)
        }
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
        if ("selectionModel" == e.propertyName) {
            setSelectionModel(main.selectionModel)
        }
        if ("rowHeight" == e.propertyName) {
            repaint()
        }
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