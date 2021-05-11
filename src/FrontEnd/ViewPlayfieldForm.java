package FrontEnd;

import Database.Picture;
import Database.Playfield;
import Database.Playfield_type;
import Database.Regions;
import FrontEnd.Models.CellRenderers.PictureListCellRenderer;
import FrontEnd.Models.GalleryListModel;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Vector;

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
        return title;
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
        addressLabel.setText(value.Address);
        pricePerHourLabel.setText(String.format("%f €", value.PricePerHour));

        Vector<Picture> pictures = new Vector<>();

        try {
            pictures = App.DB.getPictures(value.getID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        galleryList.setModel(new GalleryListModel(pictures));
    }

// constructor
    public ViewPlayfieldForm(Playfield field){

        setListeners();
        galleryList.setCellRenderer(new PictureListCellRenderer());

        setSelectedPlayfield(field);

    }

    private void setListeners(){
        reserveButton.addActionListener(e -> App.goTo(new ReservationsForm(selectedPlayfield)));
    }


}
