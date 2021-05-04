package FrontEnd;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Navigator extends JFrame {

    private LoginForm loginForm;
    private static Navigator _this;

    private static Vector<Container> _history;
    private static Vector<String> _titleHistory;
    private static int _historyIndex;
    private static final int _historyLimit = 5;

    public Navigator(){
        loginForm = new LoginForm();

        _this = this;
        _history = new Vector<>();
        _titleHistory = new Vector<>();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GoTo(loginForm.Prijava);
    }

    public static void GoTo(Container form){
        goTo(form, "");
    }
    public static void goTo(Container form, String title){

        _historyIndex++;
        if(_historyIndex >= _historyLimit){
            _history.remove(0);
            _titleHistory.remove(0);
            _historyIndex--;
        }

        if(_historyIndex < _history.size()){
            _history.setSize(_historyIndex+1);
            _history.set(_historyIndex, form);
            _titleHistory.set(_historyIndex, title);
        }

        _this.setTitle(title);
        _this.setContentPane(form);
        _this.pack();
        _this.setVisible(true);
    }

    public static boolean canGoBack(){
        return _historyIndex - 1 >= 0;
    }

    public static void goBack(){
        if(!canGoBack()) return;

        _historyIndex--;

        goTo(_history.get(_historyIndex), _titleHistory.get(_historyIndex));
    }

    public static boolean canGoForward(){
        return _historyIndex < _history.size() - 1;
    }

    public static void goForward(){
        if(!canGoForward()) return;

        _historyIndex++;

        goTo(_history.get(_historyIndex), _titleHistory.get(_historyIndex));
    }
}
