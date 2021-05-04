package FrontEnd;

import Database.DatabaseManager;
import Database.Playfield;
import Database.Playfield_type;
import Database.Regions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class AddPlayground {

    public AddPlayground() throws SQLException {
        DatabaseManager db = new DatabaseManager();
        Vector<Regions> region = db.getRegions();
        for (Regions regions: region) {
            RegionComboBox.addItem(regions.Name.toString());
        }

        Vector<Playfield_type> playfield_type = db.getPlayfield_types();
        for (Playfield_type playfield_types: playfield_type
             ) {
            TypeComboBox.addItem(playfield_types.Name);
        }
        AddPlaygroundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Playfield playfield = new Playfield(AddNameTextField.getText(), DescriptionTextArea.getText(),
                        AddressTextBox.getText(), EmailTextField.getText(), PhoneTextField.getText(), "", 19, 20, 1, 12);
                try {
                    db.addPlayfieldTest(playfield);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

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
}
