package FrontEnd.Models.CellRenderers;

import Database.Reservation;

import javax.swing.*;
import java.awt.*;

public class ReservationCellRenderer implements ListCellRenderer<Reservation> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Reservation> list, Reservation value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value == null) return null;

        Reservation r = value;

        return new ReservationSpace(r).getContentPane();
    }
}
