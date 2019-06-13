package org.hvdw.jexiftoolgui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.hvdw.jexiftoolgui.facades.IPreferencesFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static org.hvdw.jexiftoolgui.facades.IPreferencesFacade.PreferenceKey.*;

public class PreferencesDialog extends JDialog {
    JPanel contentPanel;
    JButton buttonSave;
    JButton buttonCancel;
    JTextField ExiftoolLocationtextField;
    JButton ExiftoolLocationbutton;
    JTextField ImgStartFoldertextField;
    JButton ImgStartFolderButton;
    JTextField ArtisttextField;
    JTextField CopyrightstextField;
    JCheckBox UseLastOpenedFoldercheckBox;
    JCheckBox CheckVersioncheckBox;

    // Initialize all the helper classes
    //AppPreferences AppPrefs = new AppPreferences();
    private IPreferencesFacade prefs = IPreferencesFacade.defaultInstance;
    private static final Logger logger = LoggerFactory.getLogger(PreferencesDialog.class);

    PreferencesDialog() {
        setContentPane(contentPanel);
        setModal(true);
        getRootPane().setDefaultButton(buttonSave);

        buttonSave.addActionListener(e -> onSave());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPanel.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ExiftoolLocationbutton.addActionListener(actionEvent -> {

            String ETpath = "";
            ETpath = Utils.whereIsExiftool(contentPanel);
            getExiftoolPath(contentPanel, ExiftoolLocationtextField, ETpath, "preferences");
        });
        ImgStartFolderButton.addActionListener(actionEvent -> getDefaultImagePath(contentPanel, ImgStartFoldertextField));
    }

    private void onSave() {
        // add your code here
        savePrefs();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    // As the exiftoolLocator is already created in the Utils as startup check we simply leave it there
    // although you can discuss that it can also be a preference if we deviate from the standard one
    // of course we do need the return value which we will get from the listener
    public void getExiftoolPath(JPanel myComponent, JTextField myExiftoolTextfield, String ePath, String fromWhere) {
        if ("cancelled".equals(ePath)) {
            if ("startup".equals(fromWhere)) {
                JOptionPane.showMessageDialog(myComponent, ProgramTexts.cancelledETlocatefromStartup, "Cancelled locate ExifTool", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(myComponent, ProgramTexts.cancelledETlocatefromPrefs, "Cancelled locate ExifTool", JOptionPane.WARNING_MESSAGE);
            }
        } else if ("no exiftool binary".equals(ePath)) {
            if ("startup".equals(fromWhere)) {
                JOptionPane.showMessageDialog(myComponent, ProgramTexts.wrongETbinaryfromStartup, "Wrong executable", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(myComponent, ProgramTexts.wrongETbinaryfromPrefs, "Wrong executable", JOptionPane.WARNING_MESSAGE);
            }
        } else { // Yes. It looks like we have a correct exiftool selected
            // remove all possible line breaks
            ePath = ePath.replace("\n", "").replace("\r", "");
            //prefs.put("exiftool", ePath);
            myExiftoolTextfield.setText(ePath);
        }
    }

    // Locate the default image path, if the user wants it
    public void getDefaultImagePath(JPanel myComponent, JTextField defImgFolder) {
        String SelectedFolder;

        final JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Locate preferred default image folder ...");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int status = chooser.showOpenDialog(myComponent);
        if (status == JFileChooser.APPROVE_OPTION) {
            SelectedFolder = chooser.getSelectedFile().getAbsolutePath();
            defImgFolder.setText(SelectedFolder);
        }
    }

    private void savePrefs() {
        logger.debug("Saving the preferences");
        logger.debug("artist {}", ArtisttextField.getText());
        logger.debug("copyrights {}", CopyrightstextField.getText());
        logger.debug("exiftool {}", ExiftoolLocationtextField.getText());
        logger.debug("defaultstartfolder {}", ImgStartFoldertextField.getText());
        logger.debug("uselastopenedfolder {}", UseLastOpenedFoldercheckBox.isSelected());
        logger.debug("Check for new version on startup {}", CheckVersioncheckBox.isSelected());

        if (!ArtisttextField.getText().isEmpty()) {
            logger.trace("{}: {}", ARTIST.key, ArtisttextField.getText());
            prefs.storeByKey(ARTIST, ArtisttextField.getText());
        }
        if (!CopyrightstextField.getText().isEmpty()) {
            logger.trace("{}: {}", COPYRIGHTS.key, CopyrightstextField.getText());
            prefs.storeByKey(COPYRIGHTS, CopyrightstextField.getText());
        }
        if (!ExiftoolLocationtextField.getText().isEmpty()) {
            logger.trace("{}: {}", EXIFTOOL_PATH.key, ExiftoolLocationtextField.getText());
            prefs.storeByKey(EXIFTOOL_PATH, ExiftoolLocationtextField.getText());
        }
        if (!ImgStartFoldertextField.getText().isEmpty()) {
            logger.trace("{}: {}", DEFAULT_START_FOLDER.key, ImgStartFoldertextField.getText());
            prefs.storeByKey(DEFAULT_START_FOLDER, ImgStartFoldertextField.getText());
        }

        logger.trace("{}: {}", USE_LAST_OPENED_FOLDER.key, UseLastOpenedFoldercheckBox.isSelected());
        prefs.storeByKey(USE_LAST_OPENED_FOLDER, UseLastOpenedFoldercheckBox.isSelected());


        logger.trace("{}: {}", VERSION_CHECK.key, CheckVersioncheckBox.isSelected());
        prefs.storeByKey(VERSION_CHECK, CheckVersioncheckBox.isSelected());

        JOptionPane.showMessageDialog(contentPanel, "Settings saved", "Settings saved", JOptionPane.INFORMATION_MESSAGE);
    }


    private void retrievePreferences() {
        // get current preferences
        ExiftoolLocationtextField.setText(prefs.getByKey(EXIFTOOL_PATH, ""));
        ImgStartFoldertextField.setText(prefs.getByKey(DEFAULT_START_FOLDER, ""));
        ArtisttextField.setText(prefs.getByKey(ARTIST, ""));
        CopyrightstextField.setText(prefs.getByKey(COPYRIGHTS, ""));
        UseLastOpenedFoldercheckBox.setSelected(prefs.getByKey(USE_LAST_OPENED_FOLDER, false));
        CheckVersioncheckBox.setSelected(prefs.getByKey(VERSION_CHECK, false));
    }

    // The  main" function of this class
    void showDialog() {
        setSize(700, 400);
        double x = getParent().getBounds().getCenterX();
        double y = getParent().getBounds().getCenterY();
        //setLocation((int) x - getWidth() / 2, (int) y - getHeight() / 2);
        setLocationRelativeTo(null);
        retrievePreferences();
        setVisible(true);

    }

}
