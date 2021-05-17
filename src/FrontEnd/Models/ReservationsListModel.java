package FrontEnd.Models;

import Database.Reservation;

import javax.swing.*;
import java.util.Vector;

public class ReservationsListModel extends DefaultListModel<Reservation> {

    public ReservationsListModel(Vector<Reservation> items){
        for(Reservation i : items) addElement(i);
    }
}
