package FrontEnd;

import Database.*;
import FrontEnd.Models.CellRenderers.PictureListCellRenderer;
import FrontEnd.Models.CellRenderers.ReviewCellRenderer;
import FrontEnd.Models.GalleryListModel;
import FrontEnd.Models.ReviewListModel;

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
    private JTabbedPane galleryReviewTabbedPane;
    private JList<Picture> galleryList;
    private JList<Review> reviewList;
    private JSlider reviewScoreSlider;
    private JTextArea reviewMessageTA;
    private JButton postReviewButton;
    private JLabel playfieldScoreLabel;
    private JButton goBackButton;
    private JButton deleteReviewButton;

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

        selectedPlayfield = value;

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
        regionLabel.setText(region != null ? region.Name : "null");
        typeLabel.setText(pt != null ? pt.Name : "null");
        addressLabel.setText(value.Address);
        pricePerHourLabel.setText(String.format("%f €", value.PricePerHour));
        playfieldScoreLabel.setText(Double.toString(value.getAvgScore()));

        Vector<Picture> pictures = new Vector<>();

        try {
            pictures = App.DB.getPictures(value.getID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        galleryList.setModel(new GalleryListModel(pictures));

        Vector<Review> reviews = new Vector<>();
        try {
            reviews = App.DB.getReviews(value.getID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (int i = 0; i < reviews.size(); i++) {
            if(reviews.get(i).getUserID() == App.getCurrentUser().getID()){
                setSelectedReview(reviews.get(i));
                reviews.removeElementAt(i);
                break;
            }
        }

        reviewList.setModel(new ReviewListModel(reviews));
    }

    private Review selectedReview;
    private void setSelectedReview(Review value){
        selectedReview = value;
        if(value == null){
            reviewMessageTA.setText("");
            reviewScoreSlider.getModel().setValue(3);
            postReviewButton.setText("Objavi");
        }
        else{
            reviewMessageTA.setText(value.message);
            reviewScoreSlider.getModel().setValue(value.score);
            postReviewButton.setText("Uredi");
        }

        deleteReviewButton.setVisible(value != null);
    }

// constructor
    public ViewPlayfieldForm(Playfield field){
        setup();
        setListeners();
        galleryList.setCellRenderer(new PictureListCellRenderer());
        setSelectedReview(null);
        setSelectedPlayfield(field);

    }

    private void setup(){
        reviewList.setCellRenderer(new ReviewCellRenderer());
    }

    private void setListeners(){
        reserveButton.addActionListener(e -> App.goTo(new ReservationsForm(selectedPlayfield)));
        goBackButton.addActionListener(e -> App.goBack());
        deleteReviewButton.addActionListener(e -> deleteReview());
        postReviewButton.addActionListener(e -> onPostReviewButton_click());
    }

    private void onPostReviewButton_click(){
        if(selectedReview == null) postReview();
        else updateReview();
    }

    private void deleteReview(){
        if(selectedReview == null) return;

        boolean res = false;
        try {
            res = App.DB.deleteReviews(selectedReview);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(res){
            JOptionPane.showMessageDialog(mainPanel, "Ocena uspešno izbrisana");
            setSelectedReview(null);
            updatePlayfieldScore();
            return;
        }

        JOptionPane.showMessageDialog(mainPanel, "Napaka pri brisanju ocene");
    }

    private void updateReview(){
        Review r = getReview();
        if(r == null) return;

        boolean res = false;
        try {
            res = App.DB.updateReviews(r);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(res){
            JOptionPane.showMessageDialog(mainPanel, "Ocena uspešno posodobljena.");
            setSelectedReview(r);
            updatePlayfieldScore();
            return;
        }

        JOptionPane.showMessageDialog(mainPanel, "Napaka pri posodabljanju ocene");
    }

    private void postReview(){
        Review res = getReview();
        if(res == null) return;

        boolean success = false;
        try {
            success = App.DB.addReview(res);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(success){
            setSelectedReview(res);
            JOptionPane.showMessageDialog(mainPanel, "Ocena uspešno poslana.");
            updatePlayfieldScore();

            return;
        }

        JOptionPane.showMessageDialog(mainPanel, "Napaka pri pošiljanju ocene.");
    }

    private void updatePlayfieldScore(){
        double d;
        try {
            d = App.DB.getPlayfieldAvgScore(selectedPlayfield.getID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            d = -1;
        }

        if(d > 0) {
            selectedPlayfield.setAvgScore(d);
            playfieldScoreLabel.setText(Double.toString(d));
        }
    }

    private Review getReview(){
        if(reviewMessageTA.getText().isEmpty()){
            JOptionPane.showMessageDialog(mainPanel, "Vpišite sporočilo.");
            return null;
        }

        Review res = new Review(reviewMessageTA.getText(), reviewScoreSlider.getValue(), selectedPlayfield.getID(), App.getCurrentUser().getID());

        if(selectedReview != null) res.setId(selectedReview.getId());

        return res;
    }
}
