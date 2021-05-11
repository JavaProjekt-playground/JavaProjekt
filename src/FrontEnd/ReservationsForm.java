package FrontEnd;

import Database.Playfield;
import Database.Reservation;

import javax.swing.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ReservationsForm implements IFormWindow {
    private JLabel NameT;
    private JLabel SurnameT;
    private JLabel EmailT;
    private JSpinner DateFromT;
    private JSpinner DateToT;
    private JLabel AddL;
    private JTable AddTable;
    private JButton AddButton;
    private JButton BackButton;
    private JPanel mainPanel;
    private JLabel Title;

    private boolean mode;
    private String title;
    private Playfield play = null;

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
        play = playfield;
        mode = Mode(reservation);
        addObject(playfield, reservation);
        DateFromT.setModel(new SpinnerDateModel());
        DateToT.setModel(new SpinnerDateModel());
        BackButton.addActionListener(e -> Back());
        AddButton.addActionListener(e -> Insert(play, reservation));
    }

    public boolean Mode(Reservation reservation) {
        if (reservation != null) {
            Title.setText("Spreminjanje podatkov naročila");
            return true;
        } else {
            Title.setText("Naročanje");
            return false;
        }
    }

    private void addObject(Playfield playfield, Reservation reservation){
        NameT.setText(App.getCurrentUser().Name);
        SurnameT.setText(App.getCurrentUser().Surname);
        EmailT.setText(App.getCurrentUser().Email);
        if(mode){
            //SET VALUE TO DATE SPINNER
            //DateFromT.setText(String.valueOf(reservation.FromDate));
            //DateToT.setText(String.valueOf(reservation.ToDate));
        }
    }

    private void Insert(Playfield playfield, Reservation reservation){
        if(mode){}
        else {
                Reservation res = new Reservation(
                    UtilsH.convertStringToTimestamp(new SimpleDateFormat("yyyy-MM-dd").format(DateFromT.getValue())),
                    UtilsH.convertStringToTimestamp(new SimpleDateFormat("yyyy-MM-dd").format(DateToT.getValue())),
                    new Timestamp(System.currentTimeMillis()),
                    false,
                    1,
                    App.getCurrentUser().getID(),
                    playfield.getID()
                );
                res.toString();
        }
    }

    private void Back(){
        App.canGoBack();
        App.goBack();
    }


}


