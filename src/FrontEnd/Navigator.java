package FrontEnd;

import javax.swing.*;
import java.awt.*;

public class Navigator extends JFrame {

    private LoginForm loginForm;
    private static Navigator _this;

    public Navigator(){
        loginForm = new LoginForm();

        _this = this;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GoTo(loginForm.Prijava);
    }

    public static void GoTo(Container form){
        GoTo(form, "");
    }
    public static void GoTo(Container form, String title){
        _this.setTitle(title);
        _this.setContentPane(form);
        _this.pack();
        _this.setVisible(true);
    }
}
