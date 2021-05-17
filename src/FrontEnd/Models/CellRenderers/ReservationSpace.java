package FrontEnd.Models.CellRenderers;

import Database.Reservation;
import Database.Reservation_status;
import Database.User;
import FrontEnd.App;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Vector;

public class ReservationSpace {
    private JPanel mainPanel;
    private JLabel ime;
    private JLabel priimek;
    private JLabel datumOd;
    private JLabel datumDo;
    private JLabel stanje;

    private User user = null;
    private Reservation reservation = null;
    private Reservation_status reservation_status = null;
    private boolean Mode;
    private Vector<Reservation> res;

    public ReservationSpace(Reservation res){
        reservation = res;

        try {
            user = App.DB.getUser(res.getUserID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            reservation_status = App.DB.getreservation_status_Reservation_id(reservation_status.getID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ime.setText(user.Name);
        priimek.setText(user.Surname);
        datumOd.setText(res.FromDate.toString());
        datumDo.setText(res.ToDate.toString());
        stanje.setText(reservation_status.Title);
    }

    public JPanel getContentPane() {
        return mainPanel;
    }
}
