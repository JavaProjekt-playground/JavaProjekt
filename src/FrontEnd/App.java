package FrontEnd;

import Database.DatabaseManager;
import Database.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class App extends JFrame {

    private LoginForm loginForm;
    private static App _this;
    public static DatabaseManager DB;

    private static Vector<IFormWindow> _history;
    private static Vector<String> _titleHistory;
    private static int _historyIndex;
    private static final int _historyLimit = 5;

    private static User currentUser;


    public App() {
        loginForm = new LoginForm();

        _this = this;
        _history = new Vector<>();
        _titleHistory = new Vector<>();
        _historyIndex = -1;

        DB = new DatabaseManager();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        goTo(loginForm);
    }

    private static void setContent(IFormWindow form) {
        _this.setTitle(form.getTitle());
        _this.setContentPane(form.getMainPanel());
        _this.pack();
        _this.setVisible(true);
    }

    public static void goTo(IFormWindow form) {

        _historyIndex++;
        if (_historyIndex >= _historyLimit) {
            _history.remove(0);
            _historyIndex--;
        }

        _history.setSize(_historyIndex + 1);
        _history.set(_historyIndex, form);
        setContent(form);
    }

    public static boolean canGoBack() {
        return _historyIndex - 1 >= 0;
    }

    public static void goBack() {
        if (!canGoBack()) return;

        _historyIndex--;

        setContent(_history.get(_historyIndex));
    }

    public static boolean canGoForward() {
        return _historyIndex < _history.size() - 1;
    }

    public static void goForward() {
        if (!canGoForward()) return;

        _historyIndex++;

        setContent(_history.get(_historyIndex));
    }


    public static void login(User user) {
        if (user == null) throw new NullPointerException("user cannot be null");

        currentUser = user;
        goTo(new Dashboard());
    }

    public static void logout() {
        currentUser = null;
        _historyIndex = 0;
        goTo(new LoginForm());
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void exit(){
        _this.dispatchEvent(new WindowEvent(_this, WindowEvent.WINDOW_CLOSING));
    }
}
