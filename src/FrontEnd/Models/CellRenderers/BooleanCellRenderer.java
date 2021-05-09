package FrontEnd.Models.CellRenderers;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class BooleanCellRenderer extends JRadioButton implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(value.getClass() != boolean.class) return null;

//        this.
        return null;
    }
}
