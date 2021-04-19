package FrontEnd;

import Database.DatabaseManager;
import Database.Playfield_type;
import Database.Regions;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Vector;


public class AddPlayground {

    public AddPlayground() throws SQLException {
        DatabaseManager db = new DatabaseManager();
        Vector<Regions> region = db.getRegions();
        for (Regions regions: region) {
            System.out.println(regions.Name);
            RegionComboBox.addItem(regions.Name.toString());
        }

        Vector<Playfield_type> playfield_type = db.getPlayfield_types();
        for (Playfield_type playfield_types: playfield_type
             ) {
            System.out.println(playfield_types.Name);
            TypeComboBox.addItem(playfield_types.Name);
        }
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
    private JButton button1;
}
