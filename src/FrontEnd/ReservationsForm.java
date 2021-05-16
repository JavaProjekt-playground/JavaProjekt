package FrontEnd;

import Database.Playfield;
import Database.Reservation;
import FrontEnd.Models.ReservationsTableModel;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class ReservationsForm implements IFormWindow {
    private JLabel NameT;
    private JLabel SurnameT;
    private JLabel EmailT;
    private JSpinner DateFromT;
    private JSpinner DateToT;
    private JLabel AddL;
    private JButton AddButton;
    private JButton BackButton;
    private JPanel mainPanel;
    private JLabel Title;
    private JList ReservationsL;
    private JButton Add;
    private JTable ReservationT;
    private JButton DeleteB;

    private boolean mode;
    private String title;
    private Playfield play = null;
    private int check;
    private Reservation res = null;

    private ReservationsTableModel model;


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

        DeleteB.addActionListener(e -> DeleteT());
    }


    public ReservationsForm(Playfield playfield, Reservation reservation){
        play = playfield;
        mode = Mode(reservation);
        addObject(reservation);
        DateFromT.setModel(new SpinnerDateModel());
        DateToT.setModel(new SpinnerDateModel());
        BackButton.addActionListener(e -> Back());
        AddButton.addActionListener(e -> Insert());
        Add.addActionListener(e -> insertList(playfield, reservation));
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

    private void addObject(Reservation reservation){
        NameT.setText(App.getCurrentUser().Name);
        SurnameT.setText(App.getCurrentUser().Surname);
        EmailT.setText(App.getCurrentUser().Email);
        if(mode){
            DateToT.setValue(reservation.ToDate);
            DateFromT.setValue(reservation.FromDate);
        }
    }

    private void Insert(){
        if(mode){
            try {
                App.DB.updateReservation(res);
                JOptionPane.showMessageDialog(mainPanel, "Uspešno ste spremenili spremembe naročila");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            try {
                App.DB.addReservation(res);
                JOptionPane.showMessageDialog(mainPanel, "Uspešno ste rezervirali to igrišče, sedaj morate le še počakati na potrditev lastnika");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private void Back(){
        App.canGoBack();
        App.goBack();
    }


    private void insertList(Playfield playfield, Reservation reservation) {
        if(mode){
            res = new Reservation(
                    UtilsH.convertStringToTimestampWithTime(new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(DateFromT.getValue())),
                    UtilsH.convertStringToTimestampWithTime(new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(DateToT.getValue())),
                    new Timestamp(System.currentTimeMillis()),
                    false,
                    1,
                    App.getCurrentUser().getID(),
                    playfield.getID()
            );
            try {
                check = App.DB.CheckDateReservation(res.FromDate, res.ToDate, res.PlayfieldID);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            if (check == 1) {
                JOptionPane.showMessageDialog(mainPanel, "Datum od ne more biti večji od datuma do, " +
                        "ali pa je datum za ta termin že rezerviran");
            } else if (check > 1) {
                JOptionPane.showMessageDialog(mainPanel, "Ta datum je že rezerviran");
            } else {
                Vector<Reservation> reservations = new Vector<>();
                reservations.add(res);
                System.out.println(reservations);
                /*ReservationsTableModel model;
                model = new ReservationsTableModel(reservations);
                ReservationT.setModel(model);
                ReservationT.setVisible(true);*/
            }
        }
        else {
            res = new Reservation(
                    UtilsH.convertStringToTimestampWithTime(new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(DateFromT.getValue())),
                    UtilsH.convertStringToTimestampWithTime(new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(DateToT.getValue())),
                    new Timestamp(System.currentTimeMillis()),
                    false,
                    1,
                    App.getCurrentUser().getID(),
                    playfield.getID()
            );
            try {
                check = App.DB.CheckDateReservation(res.FromDate, res.ToDate, res.PlayfieldID);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            if (check == 1) {
                JOptionPane.showMessageDialog(mainPanel, "Datum od ne more biti večji od datuma do, " +
                        "ali pa je datum za ta termin že rezerviran");
            } else if (check > 1) {
                JOptionPane.showMessageDialog(mainPanel, "Ta datum je že rezerviran");
            } else {
                Vector<Reservation> reservations = new Vector<>();
                reservations.add(res);
                System.out.println(reservations);
                //ReservationsTableModel model;
                model = new ReservationsTableModel(reservations);
                ReservationT.setModel(model);
                ReservationT.setVisible(true);
            }
        }
    }

    public void DeleteT(){

        if(ReservationT.getSelectedRow() != -1) {
            // remove selected row from the model
            //model.removeRow(ReservationT.getSelectedRow());
            //ReservationT.remove(ReservationT.getSelectedRow());

            //getReservationModel().removeRow(ReservationT.getSelectedRow());
            int rowCount= model.getRowCount();

            System.out.println(rowCount);

            for(int i=0;i<rowCount;i++ ){
                model.removeRow(i);
                //System.out.println(i);
            }
            JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
            res = null;
        }
    }

    private ReservationsTableModel getReservationModel(){
        return (ReservationsTableModel)ReservationT.getModel();
    }
}


