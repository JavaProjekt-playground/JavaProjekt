import FrontEnd.AddPlayground;

import javax.swing.*;
import java.sql.SQLException;

public class JavaProject {

    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("AddPlayground");
        frame.setContentPane(new AddPlayground().addplayground);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
