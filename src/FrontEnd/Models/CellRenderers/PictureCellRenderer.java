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

    private final int _maxImageHeight;
    private final int _maxImageWidth;

    public PictureCellRenderer(){
        this(100, 100);
    }

    public PictureCellRenderer(int maxCellWidth, int maxCellHeight){
        _maxImageHeight = maxCellHeight;
        _maxImageWidth = maxCellWidth;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(value == null) return this;
        try {
            Picture val = (Picture)value;
            BufferedImage im;
            URL url = val.getURL();
            if(url != null) im = ImageIO.read(url);
            else im = ImageIO.read(new File(val.getFilePath()));

            int h = im.getHeight(), w = im.getWidth();

            if(h > _maxImageHeight ) {
                double r = (double)_maxImageHeight / (double)h;
                h = (int)Math.floor(r*(double)h);
                w = (int)Math.floor(r*(double)w);
            }
            if(h > _maxImageWidth ) {
                double r = (double)_maxImageHeight / (double)w;
                h = (int)Math.floor(r*(double)h);
                w = (int)Math.floor(r*(double)w);
            }

            setToolTipText(val.Caption);
            setIcon(new ImageIcon(im.getScaledInstance(w, h, BufferedImage.SCALE_DEFAULT)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
