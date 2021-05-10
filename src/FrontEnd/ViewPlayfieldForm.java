package FrontEnd;

import Database.Playfield;
import Database.Playfield_type;
import Database.Regions;

import javax.swing.*;
import java.sql.SQLException;

public class ViewPlayfieldForm implements IFormWindow{
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JLabel regionLabel;
    private JLabel addressLabel;
    private JLabel typeLabel;
    private JLabel pricePerHourLabel;
    private JButton reserveButton;
    private JList galleryList;

    public String title = "Details";

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public String getTitle() {
        return "";
    }

    private Playfield selectedPlayfield;
    public void setSelectedPlayfield(Playfield value){
        if(value == null) throw new NullPointerException("Playfield cannot be null.");

        Regions region = null;
        try {
            region = App.DB.getRegion(value.RegionID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Playfield_type pt = null;
        try {
            pt = App.DB.getPlayfield_type(value.TypeID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        nameLabel.setText(value.Title);
        descriptionLabel.setText(value.Description);
        phoneLabel.setText(value.Phone);
        emailLabel.setText(value.Email);
        regionLabel.setText(region.Name);
        typeLabel.setText(pt.Name);
        pricePerHourLabel.setText(String.format("%d €", value.PricePerHour));
    }
}
