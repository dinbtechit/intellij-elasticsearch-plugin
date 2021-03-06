
import javax.swing.event.ChangeEvent
import javax.swing.event.ListSelectionEvent
import javax.swing.event.TableColumnModelEvent
import javax.swing.event.TableColumnModelListener

class CapTableColumnInfo(
    val columnName: String,
    val sortType: CapSortType = CapSortType.STRING,
    val tooltip: String? = null
) {
    val sortField: CapSortField = CapSortField(columnName, null)
}

open class CapTableColumnModelListener : TableColumnModelListener {

    override fun columnAdded(e: TableColumnModelEvent?) {
    }

    override fun columnRemoved(e: TableColumnModelEvent?) {
    }

    override fun columnMarginChanged(e: ChangeEvent?) {
    }

    override fun columnSelectionChanged(e: ListSelectionEvent?) {
    }

    override fun columnMoved(e: TableColumnModelEvent?) {
    }
}