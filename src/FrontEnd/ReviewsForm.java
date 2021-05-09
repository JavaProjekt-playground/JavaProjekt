package FrontEnd;

import Database.Playfield;
import Database.Review;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ReviewsForm implements IFormWindow{
    private JComboBox Rating;
    private JTextArea Text;
    private JButton sendReviewButton;
    private JLabel Message;
    public JPanel mainPanel;
    private JButton Back;

    private Review review;
    public String title = "Reviews";

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public ReviewsForm(Playfield playfield) {
        addObjects(review);
        getReview();
        sendReviewButton.addActionListener(e -> Insert(playfield));
    }



    private void Insert(Playfield playfield){
            Review review = new Review(
                Message.getText(),
                Double.valueOf(Rating.getSelectedItem().toString()),
                playfield.getID(),
                App.getCurrentUser().getID(),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())
        );
        try {
            App.DB.addReview(review);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void addObjects(Review review) {
        try {
            Review r = App.DB.getReview(review.getID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //Rating.setSelectedItem(review.Score);
        Text.setText(review.Message);
    }

    public Review getReview() {
        return review;
    }
    public void setReview(Review review) {
        this.review = review;
        title = review == null ? "Add Review" : "Edit Review: " + review.Message;
    }
}
