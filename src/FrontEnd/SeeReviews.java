package FrontEnd;

import Database.Playfield;

import javax.swing.*;

public class SeeReviews implements IFormWindow{
    private JTable reviews;
    public JPanel seeReview;
    private JLabel Reviews;
    private JButton AddButton;
    private JButton EditButton;
    private JButton DeleteButton;
    private JButton YourReviewsButton;

    public String title = "Reviews";
    private Playfield playfield;
    public SeeReviews() {
        AddButton.addActionListener(e -> App.goTo(new ReviewsForm(playfield)));
        EditButton.addActionListener(e -> App.goTo(new ReviewsForm(playfield)));
        DeleteButton.addActionListener(e -> Delete());
    }

    @Override
    public JPanel getMainPanel() {
        return seeReview;
    }

    @Override
    public String getTitle() {
        return "";
    }

    private void Delete(){

    }

    /*private Review getSelectedReview(){
        int i = reviews.getSelectedRow();
        Review r = i > -1 ? ((ReviewsTableModel)reviews.getModel()).getReview(i) : null;
        return r;
    }*/
}
