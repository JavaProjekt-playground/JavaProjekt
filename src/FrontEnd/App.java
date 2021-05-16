package FrontEnd;

import Database.DatabaseManager;
import Database.User;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class App extends JFrame{
    private JPanel contentPanel;
    private JPanel topBar;
    private JButton goBackButton;
    private JButton goForwardButton;
    private JButton profileButton;
    private JButton logoutButton;
    private JLabel userLabel;
    private JPanel basePanel;

    private static App _this;
//    private static JFrame frame;
    public static DatabaseManager DB;

    private static Vector<IFormWindow> _history;
    private static int _historyIndex;
    private static final int _historyLimit = 5;

    private static User currentUser;

    private static void setCurrentUser(User value) {
        currentUser = value;
        _this.topBar.setVisible(value != null);

        if (value == null) {
            _historyIndex = -1;
            _history.setSize(0);
            setContent(new LoginForm());
        } else {
            _this.userLabel.setText(value.Name + "" + value.Surname);
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }


    public App() {

//        frame = new JFrame();
        _this = this;
        _history = new Vector<>();
        _historyIndex = -1;

        DB = new DatabaseManager();

        goForwardButton.addActionListener(e -> goForward());
        goBackButton.addActionListener(e -> goBack());
        logoutButton.addActionListener(e -> logout());
        profileButton.addActionListener(e -> goTo(new SettingsEditor()));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(basePanel);

        pack();
        setVisible(true);

        setCurrentUser(null);
    }

    private static void setContent(IFormWindow form) {
        _this.goBackButton.setEnabled(canGoBack());
        _this.goForwardButton.setEnabled(canGoForward());
        _this.profileButton.setVisible(form.getClass() != SettingsEditor.class);

        _this.setTitle(form.getTitle());

        _this.contentPanel.removeAll();
        _this.contentPanel.add(form.getMainPanel());

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

        setCurrentUser(user);
        goTo(new Dashboard());
    }

    public static void logout() {

        int a = JOptionPane.showConfirmDialog(_this.basePanel, "Ste prepričani, da se želite odjaviti?", "Odjava", JOptionPane.OK_CANCEL_OPTION);


        if(a == JOptionPane.OK_OPTION) setCurrentUser(null);
    }


    public static void exit() {
        _this.dispatchEvent(new WindowEvent(_this, WindowEvent.WINDOW_CLOSING));
    }

    public static void EditUser(User user) {
        if (user == null) throw new NullPointerException("user cannot be null");

        setCurrentUser(user);
    }

}
