package FrontEnd;

import Database.Reservation;
import FrontEnd.Models.CellRenderers.ReservationCellRenderer;
import FrontEnd.Models.ReservationsListModel;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Vector;

public class SeeReservations implements IFormWindow{
    private JLabel TitleL;
    private JButton EditButton;
    private JButton SeeReservationsButton;
    private JButton DeleteButton;
    private JList ReservationsL;
    private JPanel mainPanel;
    private JComboBox StatusCB;


    @Override
    public JPanel getMainPanel() { return mainPanel; }

    @Override
    public String getTitle() { return title; }

    public String title;
    private boolean Mode;
    private Vector<Reservation> r;


    public SeeReservations(boolean mode){
        Mode = mode;
        ModeAdd();
        setup();
        AddIntoList();
        SeeReservationsButton.addActionListener(e -> SetMode());
    }


    private void AddIntoList(){
        if(Mode){
            try {
                r = App.DB.getReservationsUser(App.getCurrentUser().getID());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            try {
                r = App.DB.getYourReservations(App.getCurrentUser().getID());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        //System.out.println(r);
        ReservationsL.setModel(new ReservationsListModel(r));
    }
    private void ModeAdd(){
        if(Mode){
            TitleL.setText("Naročila za tvoja igrišča");
            DeleteButton.setText("Spremeni status naročila");
            StatusCB.setVisible(true);
            SeeReservationsButton.setText("Tvoja naročila");
            EditButton.setVisible(false);
        }
        else {
            TitleL.setText("Tvoja naročila");
            DeleteButton.setText("Izbriši naročilo");
            StatusCB.setVisible(false);
            SeeReservationsButton.setText("Naročila za tvoje igrišče");
            EditButton.setVisible(true);
        }
    }

    private void SetMode(){
        if(Mode)
            Mode = false;
        else
            Mode = true;
        ModeAdd();
        setup();
        AddIntoList();
    }

    private void setup() {
        ReservationsL.setCellRenderer(new ReservationCellRenderer());
    }

}
