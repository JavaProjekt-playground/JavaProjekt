package FrontEnd;

import Database.*;
import FrontEnd.TableModels.UserInformation;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Vector;

public class AddPlayground {


    private JLabel AddLabel;
    private JLabel NameLabel;
    private JTextField AddNameTextField;
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
    public JPanel addplayground;
    private JButton AddPlaygroundButton;

    public AddPlayground() {
        super();
        addObjects();
        AddPlaygroundButton.addActionListener(e -> Insert());
    }

    private void Insert(){
        DatabaseManager db = new DatabaseManager();
        User user = UserInformation.getUserInformation();
        Regions regions = (Regions) RegionComboBox.getModel().getSelectedItem();
        Playfield_type playfield_type = (Playfield_type) TypeComboBox.getModel().getSelectedItem();

        Playfield playfield = new Playfield(
                AddNameTextField.getText(),
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
            db.addPlayfieldTest(playfield);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void addObjects() {
        DatabaseManager db = new DatabaseManager();
        Vector<Regions> region = null;
        try {
            region = db.getRegions();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for (Regions regions: region) {
            RegionComboBox.addItem(regions);

        }

        Vector<Playfield_type> playfield_type = null;
        try {
            playfield_type = db.getPlayfield_types();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for (Playfield_type playfield_types: playfield_type
        ) {
            TypeComboBox.addItem(playfield_types);
        }
    }
}

