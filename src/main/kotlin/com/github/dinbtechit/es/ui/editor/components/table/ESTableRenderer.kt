import com.github.dinbtechit.es.ui.editor.components.table.StyleAttributesProvider
import com.intellij.openapi.editor.impl.FontFallbackIterator
import com.intellij.ui.ColoredTableCellRenderer
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.components.JBLabel
import java.awt.*
import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.table.DefaultTableCellRenderer


class CapTableColoredCellRenderer() : ColoredTableCellRenderer() {

    override fun customizeCellRenderer(
        table: JTable,
        value: Any?,
        selected: Boolean,
        hasFocus: Boolean,
        row: Int,
        column: Int
    ) {
        border = EMPTY_BORDER
        background = when {
            table.isCellSelected(row, column) -> {
                ESTableUI.getSelectedCellColor()
            }
            table.isRowSelected(row) -> {
                ESTableUI.getSelectedLineColor()
            }
            else -> {
                ESTableUI.getTableBackground()
            }
        }

        if (value == null) {
            appendInternal("(null)", StyleAttributesProvider.getNullAttributes())
            return
        }
        if (value is Collection<Any?>) {
            if (value.size > 1) {
                appendInternal("[", StyleAttributesProvider.getBracesAttributes())
                value.asSequence().forEachIndexed { index, it ->
                    writeValue(it)
                    if (index != value.size - 1) {
                        appendInternal(", ", StyleAttributesProvider.getKeywordAttributes())
                    }
                }
                appendInternal("]", StyleAttributesProvider.getBracesAttributes())
            } else {
                writeValue(value.firstOrNull())
            }
        } else {
            writeValue(value)
        }
    }

    private fun writeValue(value: Any?) {
        when (value) {
            null -> {
                appendInternal("null", StyleAttributesProvider.getKeywordAttributes())
            }
            is Number -> {
                appendInternal(value.toString(), StyleAttributesProvider.getNumberAttributes())
            }
            is Boolean -> {
                appendInternal(value.toString(), StyleAttributesProvider.getKeywordAttributes())
            }
            else -> {
                appendInternal(value.toString(), StyleAttributesProvider.getIdentifierAttributes())
            }
        }
    }

    private fun appendInternal(fragment: String, attributes: SimpleTextAttributes) {
        foreground = attributes.fgColor
        setTextAlign(SwingConstants.LEFT)
        append(fragment, attributes)
    }

    companion object {
        val instance = CapTableColoredCellRenderer()
        private val EMPTY_BORDER = EmptyBorder(1, 1, 1, 1)
        private val SIZE = 32
        private val HALF = SIZE / 2
    }


}


internal class ESTableCellHtmlRenderer() : DefaultTableCellRenderer() {

    override fun getTableCellRendererComponent(
        table: JTable?,
        value: Any?,
        isSelected: Boolean,
        hasFocus: Boolean,
        row: Int,
        column: Int
    ): Component {
        val comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column)
        try {
            val valStr = if (value?.toString() is String) value.toString() else ""
            val fontPre = FontFallbackIterator().apply {
                start(valStr, 0, valStr.length)
            }
            text = "<html><span style='font-family: ${fontPre.font.family}'>${value}</span></html>"
        } catch (e: Exception) {
            text = ""
        }
        verticalAlignment = JBLabel.LEADING
        return comp
    }
}