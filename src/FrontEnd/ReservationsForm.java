package FrontEnd;

import Database.Playfield;
import Database.Reservation;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
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
    private int check;

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public String getTitle() {
        return title;
    }


    public ReservationsForm(Playfield playfield) {
        this(playfield, null);
    }

    public ReservationsForm(Playfield playfield, Reservation reservation) {
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

    private void addObject(Playfield playfield, Reservation reservation) {
        NameT.setText(App.getCurrentUser().Name);
        SurnameT.setText(App.getCurrentUser().Surname);
        EmailT.setText(App.getCurrentUser().Email);
        if (mode) {
            //SET VALUE TO DATE SPINNER
            //DateFromT.setText(String.valueOf(reservation.FromDate));
            //DateToT.setText(String.valueOf(reservation.ToDate));
        }
    }

    private void Insert(Playfield playfield, Reservation reservation) {
        if (mode) {
        } else {
            Reservation res = new Reservation(
                    UtilsH.convertStringToTimestampWithTime(new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(DateFromT.getValue())),
                    UtilsH.convertStringToTimestampWithTime(new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(DateToT.getValue())),
                    new Timestamp(System.currentTimeMillis()),
                    false,
                    1,
                    App.getCurrentUser().getID(),
                    playfield.getID()
            );
            try {
                check = App.DB.CheckDateReservation(res.FromDate, res.ToDate);
                //System.out.println(check);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (check == 0) {
                try {
                    App.DB.addReservation(res);
                    JOptionPane.showMessageDialog(mainPanel, "Uspešno ste rezervirali to igrišče, počakajte na odziv od lastnika");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (check == 1) {
                JOptionPane.showMessageDialog(mainPanel, "Datum od ne more biti večji od datuma do, " +
                        "ali pa je datum za ta termin že rezerviran");
            } else
                JOptionPane.showMessageDialog(mainPanel, "Ta datum je že rezerviran");
        }
    }

    private void Back() {
        App.canGoBack();
        App.goBack();
    }


}


