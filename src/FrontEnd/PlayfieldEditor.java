package FrontEnd;

import Database.Playfield;
import Database.Playfield_type;
import Database.Regions;

import javax.swing.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    private String title;
    @Override
    public String getTitle() {
        return title;
    }

    public PlayfieldEditor(Playfield playfield) {
        super();
        addObjects();

        setPlayfield(playfield);
        AddPlaygroundButton.addActionListener(e -> Insert());
    }

    private void Insert(){
        Regions regions = (Regions) RegionComboBox.getModel().getSelectedItem();
        Playfield_type playfield_type = (Playfield_type) TypeComboBox.getModel().getSelectedItem();
        Playfield playfield = new Playfield(
                NameTextField.getText(),
                DescriptionTextArea.getText(),
                AddressTextBox.getText(),
                EmailTextField.getText(),
                PhoneTextField.getText(),
                " ",
                App.getCurrentUser().getID(),
                regions.getID(),
                playfield_type.getID(),
                setPoint()
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
        title = playfield == null ? "Add playfield" : "Edit playfield: " + playfield.Title;
    }

    public Double setPoint(){
        double d = Double.valueOf(PricePerHourTextField.getText());
        DecimalFormat df = new DecimalFormat("###.##");
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();

        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        return d;
    }
}

