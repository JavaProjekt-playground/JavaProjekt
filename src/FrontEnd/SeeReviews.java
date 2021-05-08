package FrontEnd;

import javax.swing.*;

public class SeeReviews implements IFormWindow{
    private JTable table1;
    public JPanel mainPanel;
    private JLabel Reviews;

    public String title = "Reviews";

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public String getTitle() {
        return "";
    }
}
