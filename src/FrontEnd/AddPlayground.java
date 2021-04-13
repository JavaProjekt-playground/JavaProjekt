package FrontEnd;

import Database.DatabaseManager;
import Database.Regions;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Vector;


public class AddPlayground {


    public void createWindow() throws SQLException {
        JFrame frame = new JFrame("AddPlayground");
        frame.setContentPane(new AddPlayground().addplayground);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        DatabaseManager db = new DatabaseManager();

        Vector<Regions> region = db.getRegions();
        for (Regions regions: region) {
            System.out.println(regions.Name);
            RegionComboBox.addItem(regions.Name);
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
}
