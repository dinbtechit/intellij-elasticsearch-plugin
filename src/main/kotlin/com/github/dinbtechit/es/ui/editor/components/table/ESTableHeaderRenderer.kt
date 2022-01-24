@file:Suppress("JAVA_MODULE_DOES_NOT_EXPORT_PACKAGE")

import com.intellij.icons.AllIcons
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.colors.EditorFontType
import com.intellij.ui.CellRendererPanel
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.JBUI
import icons.ElasticsearchIcons
import sun.swing.table.DefaultTableCellHeaderRenderer
import java.awt.*
import javax.swing.Icon
import javax.swing.JLabel
import javax.swing.JTable


class CapTableColumnHeaderCellRenderer : DefaultTableCellHeaderRenderer {

    constructor() : super() {
        val colorsScheme = EditorColorsManager.getInstance().globalScheme
        font = colorsScheme.getFont(EditorFontType.PLAIN)
    }

    override fun getTableCellRendererComponent(
        table: JTable,
        value: Any?,
        isSelected: Boolean,
        hasFocus: Boolean,
        row: Int,
        column: Int
    ): Component {

        val colorsScheme = EditorColorsManager.getInstance().globalScheme
        val font: Font = colorsScheme.getFont(EditorFontType.PLAIN)
        val label = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column) as JLabel
        label.apply {
            setFont(font)
        }

        val columnInfo = (table as ESTable).getColumnInfo(column)
        val panel = createColumnHeaderCellPanel(table, label, table.sortModel, columnInfo)
        var border = panel.border
        val indent = JBUI.Borders.empty(0, 8, 0, 0)
        border = JBUI.Borders.merge(border, indent, true)
        border = JBUI.Borders.merge(border, JBUI.Borders.customLine(ESTableUI.getTableGridColor(), 1, 0, 1, 1), true)
        panel.border = border
        if (table.isColumnSelected(column)) {
            panel.background = ESTableUI.getSelectedLineColor()
        }
        return panel
    }

    private fun createColumnHeaderCellPanel(
        table: JTable,
        nameLabel: JLabel,
        sortModel: CapSortModel,
        columnInfo: CapTableColumnInfo?
    ): CapTableColumnHeaderCellRendererPanel {
        val sortField = sortModel.getSortFields().find { it.field == columnInfo?.sortField?.fieldName }

        val sortIcon = when {
            columnInfo?.sortField?.fieldName == null -> {
                null
            }
            sortField == null -> {
                AllIcons.General.ArrowSplitCenterV
            }
            sortField.order == CapSortOrder.ASC -> {
                AllIcons.General.ArrowUp
            }
            else -> {
                AllIcons.General.ArrowDown
            }
        }
        val sortText = if (sortField != null) {
            (sortModel.getSortFields().indexOf(sortField) + 1).toString()
        } else {
            null
        }
        return CapTableColumnHeaderCellRendererPanel(
            table = table,
            text = nameLabel.text,
            tooltip = columnInfo?.tooltip,
            sortIcon = sortIcon,
            sortText = sortText
        )
    }
}

class CapTableColumnHeaderCellRendererPanel : CellRendererPanel {

    constructor(
        table: JTable,
        text: String,
        tooltip: String?,
        sortIcon: Icon?,
        sortText: String?
    ) : super() {
        val myNameLabel: JLabel = JBLabel(ElasticsearchIcons.DataTable.Column)
        val mySortLabel: JLabel = JBLabel()
        myNameLabel.border = IdeBorderFactory.createEmptyBorder(NAME_LABEL_ROW_INSETS)
        mySortLabel.border = IdeBorderFactory.createEmptyBorder(SORT_LABEL_ROW_INSETS)
        mySortLabel.verticalAlignment = 0
        val colorsScheme = EditorColorsManager.getInstance().globalScheme
        val font: Font = colorsScheme.getFont(EditorFontType.PLAIN)
        //val font: Font = table.tableHeader.font
        val foreground: Color = table.foreground

        myNameLabel.font = font
        myNameLabel.foreground = foreground
        myNameLabel.text = text

        mySortLabel.font = font
        mySortLabel.foreground = foreground
        mySortLabel.icon = sortIcon
        mySortLabel.text = sortText

        tooltip?.let {
            toolTipText = it
        }

        this.layout = BorderLayout()
        this.add(myNameLabel, BorderLayout.CENTER)
        this.add(mySortLabel, BorderLayout.EAST)
    }

    companion object {
        private val NAME_LABEL_ROW_INSETS: Insets = JBUI.insets(0, 3, 0, 0)
        private val SORT_LABEL_ROW_INSETS: Insets = JBUI.insets(0, 2, 0, 3)
    }
}


