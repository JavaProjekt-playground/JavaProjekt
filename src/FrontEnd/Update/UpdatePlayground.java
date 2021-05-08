package FrontEnd.Update;

import Database.*;
import FrontEnd.TableModels.UserInformation;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Vector;

public class UpdatePlayground {


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
    public JPanel updatePlayground;
    private JButton AddPlaygroundButton;

    public UpdatePlayground(Playfield playfield) {
        super();
        addObjects(playfield);
        AddPlaygroundButton.addActionListener(e -> Update());
    }

    private void Update(){
        DatabaseManager db = new DatabaseManager();
        User user = UserInformation.getUserInformation();
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
            db.addPlayfieldTest(playfield);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void addObjects(Playfield playfield) {
        DatabaseManager db = new DatabaseManager();
        NameTextField.setText(playfield.Title);
        DescriptionTextArea.setText(playfield.Description);
        AddressTextBox.setText(playfield.Address);
        EmailTextField.setText(playfield.Email);
        PhoneTextField.setText(playfield.Phone);
        PricePerHourTextField.setText(String.valueOf(playfield.PricePerHour));
        Vector<Regions> region = null;
        try {
            region = db.getRegions();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for (Regions regions: region) {
            RegionComboBox.addItem(regions);

        }
        try {
            Regions getRegion = db.getRegions(playfield.RegionID);
            RegionComboBox.setSelectedItem(getRegion);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

        try {
            Playfield_type getPlayfieldType = db.getPlayfield_type(playfield.TypeID);
            TypeComboBox.setSelectedItem(getPlayfieldType);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}

