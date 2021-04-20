import FrontEnd.LoginForm;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Main {

    private static JFrame frame;
    public static void SetContent(Container pane){
        frame.setContentPane(pane);
    }

    public static void main(String[] args) throws SQLException {
        frame = new JFrame("AddPlayground");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        SetContent(new LoginForm().Prijava);
        frame.pack();
        frame.setVisible(true);
    }
}
