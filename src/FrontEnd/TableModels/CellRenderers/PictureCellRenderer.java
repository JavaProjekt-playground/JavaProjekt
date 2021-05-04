package FrontEnd.TableModels.CellRenderers;

import Database.Picture;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class PictureCellRenderer extends JLabel implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(value == null) return this;
        try {
            BufferedImage im = ImageIO.read(((Picture)value).getURL());
            setIcon(new ImageIcon(im));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }
}
