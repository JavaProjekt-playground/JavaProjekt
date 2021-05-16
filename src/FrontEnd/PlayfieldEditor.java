package FrontEnd;

import Database.*;
import FrontEnd.Models.CellRenderers.PictureCellRenderer;
import FrontEnd.Models.PicturesTableModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Vector;

public class PlayfieldEditor implements IFormWindow {


    private JTextField nameTextField;
    private JTextArea descriptionTextArea;
    private JTextField phoneTextField;
    private JTextField emailTextField;
    private JComboBox<Regions> regionComboBox;
    private JTextField addressTextBox;
    private JComboBox<Playfield_type> typeComboBox;
    private JSpinner pricePerHourSpinner;
    public JPanel mainPanel;
    private JButton editPlayfieldButton;
    private JTable picturesTable;
    private JButton confirmPictureEditButton;
    private JLabel pathLabel;
    private JTextField captionTF;
    private JButton selectPictureButton;
    private JButton goBackButton;
    private JCheckBox thumbnailCB;
    private JLabel titleLabel;
    //fields
    private Vector<Picture> picturesToAdd;
    private Vector<Picture> picturesToRemove;

    private String selectedPath;


    //properties
    private Playfield selectedPlayfield;

    public Playfield getSelectedPlayfield() {
        return selectedPlayfield;
    }

    public void setSelectedPlayfield(Playfield playfield) {
        this.selectedPlayfield = playfield;
        title = playfield == null ? "Dodajanje igrišča" : "Urejanje igrišča";
        titleLabel.setText(playfield == null ? "Novo igrišče" : playfield.Title);
        editPlayfieldButton.setText(playfield == null ? "Dodaj" : "Uredi");
        if (playfield == null) {
            nameTextField.setText("");
            descriptionTextArea.setText("");
            phoneTextField.setText("");
            emailTextField.setText("");
            addressTextBox.setText("");
            pricePerHourSpinner.getModel().setValue(0);

            regionComboBox.setSelectedIndex(0);
            typeComboBox.setSelectedIndex(0);

            setPicturesModel(new Vector<>(), -1);
        } else {
            nameTextField.setText(playfield.Title);
            descriptionTextArea.setText(playfield.Description);
            phoneTextField.setText(playfield.Phone);
            emailTextField.setText(playfield.Email);
            addressTextBox.setText(playfield.Address);
            pricePerHourSpinner.setValue(Double.valueOf(playfield.PricePerHour));
            //set selected region
            for (int i = 0; i < regionComboBox.getItemCount(); i++) {
                if ((regionComboBox.getItemAt(i)).getID() == playfield.RegionID) {
                    regionComboBox.setSelectedIndex(i);
                    break;
                }
            }
            // set selected type
            for (int i = 0; i < typeComboBox.getItemCount(); i++) {
                if ((typeComboBox.getItemAt(i)).getID() == playfield.TypeID) {
                    typeComboBox.setSelectedIndex(i);
                    break;
                }
            }

            Vector<Picture> pics;
            try {
                pics = App.DB.getPictures(playfield.getID());
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                pics = new Vector<>();
            }
            if (pics.size() > 0) setPicturesModel(pics, playfield.ThumbnailID);
            else setPicturesModel(pics, -1);

        }
    }

    private Picture selectedPicture;

    private void setSelectedPicture(Picture pic) {
        selectedPicture = pic;
        selectedPath = null;
        if (pic != null) {
            if (pic.getId() > 0) setPictureImage(pic.getURL());
            else setPictureImage(new File(pic.getFilePath()));
            captionTF.setText(pic.Caption);
            confirmPictureEditButton.setText("Odstrani");
            captionTF.setEnabled(false);
            selectPictureButton.setEnabled(false);
            thumbnailCB.getModel().setPressed(pic == getPicturesModel().getThumbnail());
        } else {
            clearPictureImage();
            captionTF.setText("");
            confirmPictureEditButton.setText("Dodaj");
            captionTF.setEnabled(true);
            selectPictureButton.setEnabled(true);
        }
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    private String title;

    @Override
    public String getTitle() {
        return title;
    }

    public PlayfieldEditor(Playfield playfield) {
        super();
        addObjects();

        picturesToAdd = new Vector<>();
        picturesToRemove = new Vector<>();
        picturesTable.setDefaultRenderer(Picture.class, new PictureCellRenderer());
        picturesTable.setAlignmentX(0.5f);
        picturesTable.setDefaultRenderer(boolean.class, new DefaultTableCellRenderer());

        pricePerHourSpinner.setModel(new SpinnerNumberModel(0, -100000, 100000, 0.01));
        JSpinner.NumberEditor editor = (JSpinner.NumberEditor) pricePerHourSpinner.getEditor();
        DecimalFormat format = editor.getFormat();
        format.setMinimumFractionDigits(2);
        setSelectedPlayfield(playfield);

        editPlayfieldButton.addActionListener(e -> onEditPlayfieldButton_click());
        selectPictureButton.addActionListener(e -> onSelectPictureButton_click());
        confirmPictureEditButton.addActionListener(e -> onConfirmPictureEditButton_click());
        goBackButton.addActionListener(e -> App.goTo(new Dashboard()));
        thumbnailCB.addActionListener(e -> onThumbnailCb_clicked());
        picturesTable.getSelectionModel().addListSelectionListener(e -> onPicturesTable_selectionChanged());
    }

    // action functions
    private void onEditPlayfieldButton_click() {
        if (selectedPlayfield == null) {
            insert();
        } else {
            update();
        }
    }

    private void onSelectPictureButton_click() {
        JFileChooser f = new JFileChooser();
        int returnVal = f.showOpenDialog(mainPanel);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = f.getSelectedFile();
            String path = file.getAbsolutePath();
            String suffix = path.toLowerCase().substring(path.lastIndexOf(".") + 1);
            if (!suffix.equals("jpg") && !suffix.equals("png")) {
                System.out.println("Invalid file: " + path + "\nSuffix: " + suffix);
                return;
            }
            selectedPath = path;
            setPictureImage(file);
        }
    }

    private void onConfirmPictureEditButton_click() {
        if (selectedPicture == null) {
            addPicture();
            return;
        }
        removePicture(selectedPicture);
    }

    private void onThumbnailCb_clicked() {
        System.out.println("Action");

        if (selectedPicture == null) return;
        getPicturesModel().switchThumbnail(selectedPicture);
    }

    private void onPicturesTable_selectionChanged() {
        int i = picturesTable.getSelectedRow();
        if (i < 0 || i >= getPicturesModel().getRowCount()) {
            setSelectedPicture(null);
            return;
        }
        Picture p = getPicturesModel().getPictureAt(i);
        setSelectedPicture(p);


    }

    //
    private void insert() {
        Playfield pf = getPlayfield();

        if (pf == null) return;

        boolean res = false;
        try {
            res = App.DB.addPlayfield(pf, picturesToAdd, getPicturesModel().getThumbnail());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (res) {
            JOptionPane.showMessageDialog(mainPanel, "Vstavljanje uspešno.");
            setSelectedPlayfield(pf);
            return;
        }

        JOptionPane.showMessageDialog(mainPanel, "Napaka pri vstavljanju.");
    }

    private void update() {
        Playfield pf = getPlayfield();
        if (pf == null) return;
        pf.Thumbnail = getPicturesModel().getThumbnail();

        boolean res = false;
        try {
            res = App.DB.updatePlayfield(pf, picturesToAdd, picturesToRemove);
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        if (res) {
            JOptionPane.showMessageDialog(mainPanel, "Spremembe uspešno shranjene.");
            setSelectedPlayfield(pf);
            return;
        }

        JOptionPane.showMessageDialog(mainPanel, "Napaka.");
    }

    private void addPicture() {
        if (selectedPath == null || selectedPath.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Izberite sliko.");
            return;
        }
        String caption = captionTF.getText();
        if (caption.equals("")) {
            JOptionPane.showMessageDialog(mainPanel, "Napišite sporočilo.");
            return;
        }
//        System.out.println(selectedPath);
        Picture p = new Picture(caption, selectedPath);

        if (selectedPlayfield != null) p.setPlayfieldID(selectedPlayfield.getID());
        picturesToAdd.add(p);
        getPicturesModel().addPicture(p, thumbnailCB.getModel().isSelected());
    }

    private void removePicture(Picture p) {
        if (selectedPicture == getPicturesModel().getThumbnail()) getPicturesModel().switchThumbnail(null);
        if (selectedPlayfield == null) {
            picturesToAdd.remove(p);
        } else {
            picturesToRemove.add(p);
        }
        getPicturesModel().removePicture(p);
        setSelectedPicture(null);
    }

    // utils
    private void setPicturesModel(Vector<Picture> pics, int thumbID) {
        picturesTable.setModel(new PicturesTableModel(pics, thumbID));
    }

    private PicturesTableModel getPicturesModel() {
        return (PicturesTableModel) picturesTable.getModel();
    }

    private void setPictureImage(URL path) {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon im = null;
        if (bi != null)
            im = new ImageIcon(bi.getScaledInstance(100, 100, BufferedImage.SCALE_DEFAULT));
        pathLabel.setIcon(im);
    }

    private void setPictureImage(File file) {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon im = null;
        if (bi != null)
            im = new ImageIcon(bi.getScaledInstance(100, 100, BufferedImage.SCALE_DEFAULT));
        pathLabel.setIcon(im);
    }

    private void clearPictureImage() {
        pathLabel.setIcon(null);
    }

    private void addObjects() {
        Vector<Regions> region = new Vector<>();
        try {
            region = App.DB.getRegions();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for (Regions regions : region) {
            regionComboBox.addItem(regions);

        }

        Vector<Playfield_type> playfield_type = new Vector<>();
        try {
            playfield_type = App.DB.getPlayfield_types();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for (Playfield_type playfield_types : playfield_type
        ) {
            typeComboBox.addItem(playfield_types);
        }
    }

    private Playfield getPlayfield() {
        //getting values
        int userID = App.getCurrentUser().getID();
        int regionID = ((Regions) regionComboBox.getModel().getSelectedItem()).getID();
        int typeID = ((Playfield_type) typeComboBox.getModel().getSelectedItem()).getID();
        String title = nameTextField.getText();
        String description = descriptionTextArea.getText();
        String address = addressTextBox.getText();
        String email = emailTextField.getText();
        String phone = phoneTextField.getText();
        double price = (double) pricePerHourSpinner.getValue();

        // validation
        if (title.isEmpty() ||
                description.isEmpty() ||
                address.isEmpty() ||
                (phone.isEmpty() && email.isEmpty())) {
            JOptionPane.showMessageDialog(mainPanel, "Izpolnite vsa potrebna polja.");
            return null;
        }

        Playfield res = new Playfield(
                title,
                description,
                address,
                email,
                phone,
                "",
                userID,
                regionID,
                typeID,
                price
        );
        if (selectedPlayfield != null) {
            res.setID(selectedPlayfield.getID());
            res.ThumbnailID = selectedPlayfield.ThumbnailID;
        }
        return res;
    }

}