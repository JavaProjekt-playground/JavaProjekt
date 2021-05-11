package FrontEnd.Models.CellRenderers;

import Database.Review;
import FrontEnd.ViewPlayfieldForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ReviewCellRenderer implements ListCellRenderer<Review> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Review> list, Review value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value == null) return null;

        Review r = value;

        return new ReviewSpace(r).getContentPane();
    }
}
