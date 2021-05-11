package FrontEnd;

import Database.Playfield;
import Database.Reservation;

import javax.swing.*;
import java.sql.SQLException;

public class ReservationsForm implements IFormWindow {
    private JLabel NameT;
    private JTextField SurnameT;
    private JTextField EmailT;
    private JSpinner DateFromT;
    private JTextField DateToT;
    private JLabel AddL;
    private JTable AddTable;
    private JButton AddButton;
    private JButton BackButton;
    private JPanel mainPanel;

    private boolean mode;
    private String title;

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public String getTitle() {
        return title;
    }


    public ReservationsForm(Playfield playfield){
        this(playfield, null);
    }
    public ReservationsForm(Playfield playfield, Reservation reservation){
        mode = Mode(playfield, reservation);
        addObject(playfield, reservation);
        DateFromT.setModel(new SpinnerDateModel());
        BackButton.addActionListener(e -> Back());
    }

    public boolean Mode(Playfield playfield, Reservation reservation) {
        if (reservation != null) {
            return true;
        } else {
            return false;
        }
    }

    private void addObject(Playfield playfield, Reservation reservation){
        NameT.setText(App.getCurrentUser().Name);
        SurnameT.setText(App.getCurrentUser().Surname);
        EmailT.setText(App.getCurrentUser().Email);
        if(mode){
            //DateFromT.setText(String.valueOf(reservation.FromDate));
            DateToT.setText(String.valueOf(reservation.ToDate));
        }
    }



    private void Back(){
        App.canGoBack();
        App.goBack();
    }

    public static void main(String[] args){
        try {
            App.goTo(new ReservationsForm(App.DB.getPlayfield(31)));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}


