package FrontEnd;

import Database.Picture;
import Database.Playfield;
import FrontEnd.Models.CellRenderers.PictureCellRenderer;
import FrontEnd.Models.PlayfieldTableModel;

import javax.swing.*;
import java.sql.SQLException;

public class Dashboard implements IFormWindow{
    public JPanel mainPanel;
    private JButton OrderButton;
    private JButton SearchButton;
    private JButton SettingsButton;
    private JTable PlayfieldsTable;
    private JTextField SearchTextField;
    private JButton AddButton;
    private JButton UpdateButton;

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String title;

    public Dashboard() {
        super();

        PlayfieldTableModel model;
        try {
            model = new PlayfieldTableModel(App.DB.getPlayfields(10));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            model = null;
        }

        PlayfieldsTable.setModel(model);
        PlayfieldsTable.setDefaultRenderer(Picture.class, new PictureCellRenderer());

        title = String.format("Dashboard - %s %s", App.getCurrentUser().Name, App.getCurrentUser().Surname);

        UpdateButton.addActionListener(e -> onUpdateButton_click());
        AddButton.addActionListener(e -> onAddButton_click());
    }

    private void onUpdateButton_click(){
        Playfield pf = getSelectedPlayfield();

        if(pf == null) return;

        if(pf.UserID != App.getCurrentUser().getID()){
            JOptionPane.showMessageDialog(mainPanel, "User does not own this playfield.");
            return;
        }

        goToPlayfieldEditor(pf);
    }

    private void onAddButton_click(){
        goToPlayfieldEditor(null);
    }

    private Playfield getSelectedPlayfield(){
        int i = PlayfieldsTable.getSelectedRow();
        Playfield pf = i > -1 ? ((PlayfieldTableModel)PlayfieldsTable.getModel()).getPlayfield(i) : null;
        return pf;
    }

    private void goToPlayfieldEditor(Playfield field){
        App.goTo(new PlayfieldEditor(field));
    }
}