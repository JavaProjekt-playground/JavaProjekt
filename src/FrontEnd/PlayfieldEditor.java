package FrontEnd;

import Database.*;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Vector;

public class PlayfieldEditor implements IFormWindow{


    private JLabel AddLabel;
    private JLabel NameLabel;
    private JTextField NameTextField;
    private JTextArea DescriptionTextArea;
    private JTextField PhoneTextField;
    private JLabel DescriptionLabel;
    private JTextField EmailTextField;
    private JLabel EmailLabel;
    private JComboBox RegionComboBox;
    private JTextField AddressTextBox;
    private JLabel RegionLabel;
    private JLabel AddressLabel;
    private JLabel TypeLabel;
    private JComboBox TypeComboBox;
    private JLabel PricePerHourLabel;
    private JTextField PricePerHourTextField;
    public JPanel mainPanel;
    private JButton AddPlaygroundButton;
    private JPanel updatePlayground;

    private Playfield playfield;

    public PlayfieldEditor(Playfield playfield) {
        super();
        addObjects();

        setPlayfield(playfield);

        AddPlaygroundButton.addActionListener(e -> Insert());
    }

    private void Insert(){
        User user = App.getCurrentUser();
        Regions regions = (Regions) RegionComboBox.getModel().getSelectedItem();
        Playfield_type playfield_type = (Playfield_type) TypeComboBox.getModel().getSelectedItem();

        Playfield playfield = new Playfield(
                NameTextField.getText(),
                DescriptionTextArea.getText(),
                AddressTextBox.getText(),
                EmailTextField.getText(),
                PhoneTextField.getText(),
                "",
                user.getID(),
                regions.getID(),
                playfield_type.getID(),
                Integer.valueOf(PricePerHourTextField.getText())
        );
        try {
            App.DB.addPlayfieldTest(playfield);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void addObjects() {
        Vector<Regions> region = null;
        try {
            region = App.DB.getRegions();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for (Regions regions: region) {
            RegionComboBox.addItem(regions);

        }

        Vector<Playfield_type> playfield_type = null;
        try {
            playfield_type = App.DB.getPlayfield_types();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for (Playfield_type playfield_types: playfield_type
        ) {
            TypeComboBox.addItem(playfield_types);
        }
    }

    public Playfield getPlayfield() {
        return playfield;
    }

    public void setPlayfield(Playfield playfield) {
        this.playfield = playfield;
    }
}
