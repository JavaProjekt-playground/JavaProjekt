package FrontEnd;

import Database.User;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SettingsEditor implements IFormWindow {
    private JPanel mainPanel;
    private JTextField nameTF;
    private JButton editButton;
    private JSpinner bDateSpinner;
    private JTextField phoneTF;
    private JTextField emailTF;
    private JTextField surnameTF;
    private JPasswordField checkPasswordPF;
    private JPasswordField oldPasswordPF;
    private JPasswordField newPasswordPF;
    private JButton cancelEditButton;

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    private String title = "Nastavitve";

    @Override
    public String getTitle() {
        return title;
    }

    private User currentUser;
    private void setCurrentUser(User value){
        if(value == null) throw new NullPointerException("Value must not be null.");

        currentUser = value;

        title = String.format("Nastavitve: %s %s", value.Name, value.Surname);

        nameTF.setText(value.Name);
        surnameTF.setText(value.Surname);
        emailTF.setText(value.Email);
        phoneTF.setText(value.Phone);
        bDateSpinner.setValue(value.BDate);

        oldPasswordPF.setText("");
        newPasswordPF.setText("");
        checkPasswordPF.setText("");

        setIsUserEditMode(false);
    }

    private boolean isUserEditMode = false;
    private void setIsUserEditMode(boolean value){
        if(value){
            editButton.setText("Potrdi");
        }
        else {
            editButton.setText("Uredi");
        }

        nameTF.setEditable(value);
        surnameTF.setEditable(value);
        phoneTF.setEditable(value);
        emailTF.setEditable(value);
        bDateSpinner.setEnabled(value);

        checkPasswordPF.setEditable(value);
        newPasswordPF.setEditable(value);
        oldPasswordPF.setEditable(value);

        isUserEditMode = value;
    }

    public SettingsEditor() {

        bDateSpinner.setModel(new SpinnerDateModel());

        try{
            setCurrentUser(App.getCurrentUser());
        }catch(NullPointerException npex){
            App.logout();
        }

        editButton.addActionListener(e -> editUser());
        cancelEditButton.addActionListener(e -> setCurrentUser(currentUser));
    }

    private void editUser(){
        if(!isUserEditMode) {
            setIsUserEditMode(true);
            return;
        }

        String name = nameTF.getText();
        String surname = surnameTF.getText();
        String phone = phoneTF.getText();
        String email = emailTF.getText();
        Timestamp bDate = new Timestamp(((java.util.Date)bDateSpinner.getValue()).getTime());

        if(name.isEmpty() || surname.isEmpty() || email.isEmpty()){
            JOptionPane.showMessageDialog(App.getWindow(), "Ime, priimek in email morajo biti vpisani.");
            return;
        }

        String oldPass = String.valueOf(oldPasswordPF.getPassword());

        if(oldPass.isEmpty()){
            JOptionPane.showMessageDialog(App.getWindow(), "Vpisati morate trenutno geslo.");
            return;
        }

        String newPass = String.valueOf(newPasswordPF.getPassword());
        String passChk = String.valueOf(checkPasswordPF.getPassword());

        boolean editPass = !newPass.isEmpty();
        if(editPass && !newPass.equals(passChk)){
            JOptionPane.showMessageDialog(App.getWindow(), "Novi gesli se ne ujemata");
            return;
        }

        User editedUser = new User(name, surname, email, phone, bDate);
        editedUser.setID(currentUser.getID());

        boolean res = false;

        try {
            res = App.DB.updateUser(editedUser, oldPass, newPass);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(res){
            JOptionPane.showMessageDialog(App.getWindow(), "Uporabnik uspešno posodobljen.");
            App.EditUser(editedUser);
            setCurrentUser(App.getCurrentUser());
            return;
        }

        JOptionPane.showMessageDialog(App.getWindow(), "Napaka pri urejanju uporabnika. Preverite geslo.");
    }
}
