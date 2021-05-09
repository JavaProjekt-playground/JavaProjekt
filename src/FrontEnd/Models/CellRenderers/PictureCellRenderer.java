package FrontEnd.Models.CellRenderers;

import Database.Picture;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PictureCellRenderer extends JLabel implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(value == null) return this;
        try {
            BufferedImage im;

            URL url = ((Picture)value).getURL();
            if(url != null) im = ImageIO.read(url);
            else im = ImageIO.read(new File(((Picture)value).getFilePath()));

            setIcon(new ImageIcon(im.getScaledInstance(-1, 100, BufferedImage.SCALE_DEFAULT)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
