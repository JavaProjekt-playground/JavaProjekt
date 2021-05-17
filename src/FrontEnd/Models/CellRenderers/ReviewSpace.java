package FrontEnd.Models.CellRenderers;

import Database.Review;
import Database.User;
import FrontEnd.App;
import FrontEnd.ViewPlayfieldForm;

import javax.swing.*;
import java.sql.SQLException;

public class ReviewSpace{
    private JPanel contentPane;
    private JLabel userLabel;
    private JLabel scoreLabel;
    private JLabel messageLabel;

    public ReviewSpace(Review r) {
        User user = null;

        try {
            user = App.DB.getUser(r.getUserID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String mode = r.getCreatedAt().equals(r.getUpdatedAt()) ? "objavljeno" : "urejeno";
        String userName = user != null ? user.Name + " " + user.Surname : "Izbrisani uporabnik";
        String dateString = r.getUpdatedAt().toString();
        String msg = String.format("%s, %s %s", userName, mode, dateString);

        userLabel.setText(msg);
        scoreLabel.setText(Integer.valueOf(r.score).toString());
        messageLabel.setText(r.message);
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
