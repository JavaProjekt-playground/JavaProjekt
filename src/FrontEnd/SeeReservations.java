package FrontEnd;

import javax.swing.*;

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


    public SeeReservations(boolean mode){
        Mode = mode;
        ModeAdd();
        SeeReservationsButton.addActionListener(e -> SetMode());
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
    }

}
