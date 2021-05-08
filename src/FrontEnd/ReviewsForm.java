package FrontEnd;

import Database.*;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ReviewsForm implements IFormWindow{
    private JComboBox Rating;
    private JTextArea textArea1;
    private JButton sendReviewButton;
    private JLabel Message;
    public JPanel mainPanel;

    public String title = "Reviews";

    public ReviewsForm(Playfield playfield) {

    }

    private void Insert(Playfield playfield){
        DatabaseManager db = new DatabaseManager();
        User user = App.getCurrentUser();
        Review review = new Review(
                Message.getText(),
                Double.valueOf(Rating.getSelectedItem().toString()),
                playfield.getID(),
                playfield.UserID,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())
        );
        try {
            db.addReview(review);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
