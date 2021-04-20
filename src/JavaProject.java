import FrontEnd.AddPlayground;
import FrontEnd.LoginForm;
import FrontEnd.Main;

import javax.swing.*;
import java.sql.SQLException;

public class JavaProject {

    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("AddPlayground");
        frame.setContentPane(new LoginForm().Prijava);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
