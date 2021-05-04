package FrontEnd;

import javax.swing.*;

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
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Playfield playfield = new Playfield(AddNameTextField.getText(), DescriptionTextArea.getText(),
                        AddressTextBox.getText(), EmailTextField.getText(), PhoneTextField.getText(), "", 17, 12, 1, 12);
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
