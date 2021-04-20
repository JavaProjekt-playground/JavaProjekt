package FrontEnd;

import Database.Review;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReviewsForm {
    private JComboBox Rating;
    private JTextArea textArea1;
    private JButton sendReviewButton;
    private JLabel Message;
    private JPanel ReviewPanel;

    public ReviewsForm() {
        sendReviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Main.form");
                frame.setContentPane(new ReviewsForm().ReviewPanel);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }

        });
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("ReviewsForm");
        frame.setContentPane(new ReviewsForm().ReviewPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
