package FrontEnd;

import javax.swing.*;

public class Podrobno implements IFormWindow{
    private JPanel mainPanel;
    private JTextField NameTextField;
    private JLabel NameLabel;
    private JLabel DescriptionLabel;
    private JLabel EmailLabel;
    private JTextArea DescriptionTextArea;
    private JTextField PhoneTextField;
    private JTextField EmailTextField;
    private JLabel RegionLabel;
    private JLabel AddressLabel;
    private JLabel TypeLabel;
    private JLabel PricePerHourLabel;
    private JComboBox RegionComboBox;
    private JTextField AddressTextBox;
    private JComboBox TypeComboBox;
    private JTextField PricePerHourTextField;
    private JLabel PodrbnoLabel;
    private JLabel PhoneLabel;
    private JLabel PictureLabel;
    private JButton AddButton;

    public String title = "Details";

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public String getTitle() {
        return "";
    }
}
