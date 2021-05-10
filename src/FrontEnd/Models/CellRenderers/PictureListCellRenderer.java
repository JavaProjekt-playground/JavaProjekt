package FrontEnd.Models.CellRenderers;

import Database.Picture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PictureListCellRenderer extends JLabel implements ListCellRenderer<Picture> {

    private final int _maxImageWidth;
    private final int _maxImageHeight;

    public PictureListCellRenderer(){
        this(150, 100);
    }
    public PictureListCellRenderer(int imWidth, int imHeight){
        _maxImageHeight = imHeight;
        _maxImageWidth = imWidth;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Picture> list, Picture value, int index, boolean isSelected, boolean cellHasFocus) {
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
        return this;    }
}
