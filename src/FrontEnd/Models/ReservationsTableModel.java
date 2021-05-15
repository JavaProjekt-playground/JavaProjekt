package FrontEnd.Models;

import Database.Reservation;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class ReservationsTableModel extends DefaultTableModel {
    private String[] columnNames = new String[]{
            "Playfield", "From", "To"
    };
    private Object[][] data = {
            {new Object(), new Object(), new Object()}
    };
    private Reservation[] reservations;

    public ReservationsTableModel(Vector<Reservation> r) {
        super();

        reservations = new Reservation[r.size()];
        reservations = r.toArray(reservations);
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return reservations != null ? reservations.length : 0;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        switch (column) {
            case 0:
                return reservations[row].OrderDate;
            case 1:
                return reservations[row].FromDate;
            case 2:
                return reservations[row].ToDate;
            default:
                return null;
        }
    }
    public Reservation getReservation(int rowIndex) {
        return reservations[rowIndex];
    }
}
