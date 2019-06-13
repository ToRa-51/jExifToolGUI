package org.hvdw.jexiftoolgui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.apache.commons.lang3.tuple.Pair;
import org.hvdw.jexiftoolgui.controllers.CommandRunner;
import org.hvdw.jexiftoolgui.controllers.StandardFileIO;
import org.hvdw.jexiftoolgui.controllers.YourCommands;
import org.hvdw.jexiftoolgui.datetime.DateTime;
import org.hvdw.jexiftoolgui.datetime.ModifyDateTime;
import org.hvdw.jexiftoolgui.datetime.ShiftDateTime;
import org.hvdw.jexiftoolgui.editpane.EditExifdata;
import org.hvdw.jexiftoolgui.editpane.EditGPSdata;
import org.hvdw.jexiftoolgui.editpane.EditGeotaggingdata;
import org.hvdw.jexiftoolgui.editpane.EditGpanodata;
import org.hvdw.jexiftoolgui.editpane.EditXmpdata;
import org.hvdw.jexiftoolgui.facades.IPreferencesFacade;
import org.hvdw.jexiftoolgui.metadata.CreateArgsFile;
import org.hvdw.jexiftoolgui.metadata.ExportMetadata;
import org.hvdw.jexiftoolgui.metadata.MetaData;
import org.hvdw.jexiftoolgui.metadata.RemoveMetadata;
import org.hvdw.jexiftoolgui.renaming.RenamePhotos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.hvdw.jexiftoolgui.facades.IPreferencesFacade.PreferenceKey.EXIFTOOL_PATH;


public class mainScreen {
    private static final Logger logger = LoggerFactory.getLogger(mainScreen.class);

    private IPreferencesFacade prefs = IPreferencesFacade.defaultInstance;
    //private JFrame rootFrame;
    private JMenuBar menuBar;
    private JMenu myMenu;
    private JMenuItem menuItem;
    private JPanel rootPanel;
    private JTabbedPane tabbedPaneRight;
    private JButton buttonLoadImages;
    private JButton buttonShowImage;
    private JPanel LeftPanel;
    private JPanel LeftbuttonBar;
    private JRadioButton radioButtonViewAll;
    private JPanel ViewRadiobuttonpanel;
    private JPanel ViewDatapanel;
    private JScrollPane ViewDatascrollpanel;
    private JTree FileTree;
    private JScrollPane Leftscrollpane;
    private JTable tableListfiles;
    private JTable ListexiftoolInfotable;
    private JLabel iconLabel;
    private JLabel MyCommandsText;
    private JTextField CommandsParameterstextField;
    private JButton CommandsclearParameterSFieldButton;
    private JButton CommandsclearOutputFieldButton;
    private JButton CommandsgoButton;
    private JButton CommandshelpButton;
    private JTextArea YourCommandsOutputTextArea;
    private JTabbedPane tabbedPaneEditfunctions;
    private JTextField ExifMaketextField;
    private JCheckBox ExifMakecheckBox;
    private JTextField ExifModeltextField;
    private JCheckBox ExifModelcheckBox;
    private JTextField ExifModifyDatetextField;
    private JCheckBox ExifModifyDatecheckBox;
    private JTextField ExifDateTimeOriginaltextField;
    private JCheckBox ExifDateTimeOriginalcheckBox;
    private JTextField ExifCreateDatetextField;
    private JCheckBox ExifCreateDatecheckBox;
    private JTextField ExifArtistCreatortextField;
    private JCheckBox ExifArtistCreatorcheckBox;
    private JTextField ExifCopyrighttextField;
    private JCheckBox ExifCopyrightcheckBox;
    private JTextField ExifUsercommenttextField;
    private JCheckBox ExifUsercommentcheckBox;
    private JTextArea ExifDescriptiontextArea;
    private JCheckBox ExifDescriptioncheckBox;
    private JPanel Camera_Equipment;
    private JPanel DateTime;
    private JPanel CreativeTags;
    private JButton ExifcopyFromButton;
    private JButton ExifsaveToButton;
    private JCheckBox ExifBackupOriginalscheckBox;
    private JButton ExifcopyDefaultsButton;
    private JButton resetFieldsButton;
    private JButton ExifhelpButton;
    private JTextField xmpCreatortextField;
    private JCheckBox xmpCreatorcheckBox;
    private JTextField xmpRightstextField;
    private JCheckBox xmpRightscheckBox;
    private JTextArea xmpDescriptiontextArea;
    private JCheckBox xmpDescriptioncheckBox;
    private JTextField xmpLabeltextField;
    private JCheckBox xmpLabelcheckBox;
    private JTextField xmpSubjecttextField;
    private JCheckBox xmpSubjectcheckBox;
    private JTextField xmpTitletextField;
    private JCheckBox xmpTitlecheckBox;
    private JCheckBox xmpRatingcheckBox;
    private JRadioButton xmp1starradioButton;
    private JRadioButton xmp2starradioButton;
    private JRadioButton xmp3starradioButton;
    private JRadioButton xmp4starradioButton;
    private JRadioButton xmp5starradioButton;
    private JTextField xmpPersontextField;
    private JCheckBox xmpPersoncheckBox;
    private JButton xmpCopyFrombutton;
    private JButton xmpSaveTobutton;
    private JCheckBox xmpBackupOriginalscheckBox;
    private JButton xmpCopyDefaultsbutton;
    private JButton xmpResetFieldsbutton;
    private JButton xmpHelpbutton;
    private JTextField geotaggingImgFoldertextField;
    private JButton geotaggingImgFolderbutton;
    private JTextField geotaggingGPSLogtextField;
    private JButton geotaggingGPSLogbutton;
    private JTextField geotaggingGeosynctextField;
    private JButton geotaggingWriteInfobutton;
    private JButton geotaggingHelpbutton;
    private JLabel GeotaggingLeaveFolderEmptyLabel;
    private JCheckBox geotaggingOverwriteOriginalscheckBox;
    private JPanel ExifEditpanel;
    private JPanel GeotaggingEditpanel;
    private JTextField xmpRegionNametextField;
    private JCheckBox xmpRegionNamecheckBox;
    private JTextField xmpRegionTypetextField;
    private JCheckBox xmpRegionTypecheckBox;
    private JProgressBar progressBar;
    private JLabel OutputLabel;
    private JPanel gpsLocationPanel;
    private JPanel gpsButtonPanel;
    private JPanel gpsCalculationPanel;
    private JButton gpsCopyFrombutton;
    private JButton gpsSaveTobutton;
    private JCheckBox gpsBackupOriginalscheckBox;
    private JButton gpsResetFieldsbutton;
    private JButton gpsMapcoordinatesbutton;
    private JButton gpsHelpbutton;
    private JLabel gpsCalculatorLabelText;
    private JTextField gpsLocationtextField;
    private JCheckBox gpsLocationcheckBox;
    private JCheckBox SaveLatLonAltcheckBox;
    private JTextField gpsLatDecimaltextField;
    private JTextField gpsLatdegtextField;
    private JTextField gpsLatDegreestextField;
    private JTextField gpsLatMinutestextField;
    private JTextField gpsLonDecimaltextField;
    private JTextField gpsLonDegreestextField;
    private JTextField gpsLonMinutestextField;
    private JTextField gpsLonSecondstextField;
    private JCheckBox gpsAboveSealevelcheckBox;
    private JPanel gpsLatLonAltPanel;
    private JRadioButton gpsNorthradioButton;
    private JRadioButton gpsEastradioButton;
    private JRadioButton gpsSouthRadioButton;
    private JRadioButton gpsWestradioButton;
    private JTextField gpsCountrytextField;
    private JCheckBox gpsCountrycheckBox;
    private JTextField gpsStateProvincetextField;
    private JCheckBox gpsStateProvincecheckBox;
    private JTextField gpsCitytextField;
    private JCheckBox gpsCitycheckBox;
    private JButton copyToInputFieldsButton;
    private JTextField CalcLatDecimaltextField;
    private JTextField CalcLatDegtextField;
    private JTextField CalcLatMintextField;
    private JTextField CalcLatSectextField;
    private JTextField gpsCalcLonDecimaltextField;
    private JTextField gpsCalcLondegtextField;
    private JTextField CalcLonMintextField;
    private JTextField CalcLonSectextField;
    private JRadioButton CalcNorthRadioButton;
    private JRadioButton CalcSouthRadioButton;
    private JRadioButton CalcEastradioButton;
    private JRadioButton CalcWestRadioButton;
    private JButton decimalToMinutesSecondsButton;
    private JButton minutesSecondsToDecimalButton;
    private JLabel CopyMetaDataUiText;
    private JRadioButton copyAllMetadataRadiobutton;
    private JRadioButton copyAllMetadataSameGroupsRadiobutton;
    private JRadioButton copySelectiveMetadataradioButton;
    private JCheckBox CopyExifcheckBox;
    private JCheckBox CopyXmpcheckBox;
    private JCheckBox CopyIptccheckBox;
    private JCheckBox CopyIcc_profileDataCheckBox;
    private JCheckBox BackupOriginalscheckBox;
    private JCheckBox CopyGpsCheckBox;
    private JCheckBox CopyJfifcheckBox;
    private JButton UseDataFrombutton;
    private JButton CopyDataCopyTobutton;
    private JButton CopyHelpbutton;
    private JRadioButton UseNonPropFontradioButton;
    private JRadioButton UsePropFontradioButton;
    private JRadioButton radioButtonByTagName;
    private JComboBox comboBoxViewByTagName;
    private JRadioButton radioButtoncommonTags;
    private JComboBox comboBoxViewCommonTags;
    private JRadioButton radioButtonCameraMakes;
    private JComboBox comboBoxViewCameraMake;
    private JTextField geotaggingLocationtextfield;
    private JTextField geotaggingCountrytextfield;
    private JCheckBox geotaggingLocationcheckbox;
    private JCheckBox geotaggingCountrycheckbox;
    private JTextField geotaggingStatetextfield;
    private JCheckBox geotaggingStatecheckbox;
    private JTextField geotaggingCitytextfield;
    private JCheckBox geotaggingCitycheckbox;
    private JLabel GeotaggingLocationLabel;
    private JButton resetGeotaggingbutton;
    private JLabel GeotaggingGeosyncExplainLabel;
    private JTextField gpsAltDecimaltextField;
    private JLabel gPanoTopText;
    private JFormattedTextField gpanoCAIHPtextField;
    private JFormattedTextField gpanoCAIWPtextField;
    private JFormattedTextField gpanoCALPtextField;
    private JFormattedTextField gpanoCATPtextField;
    private JTextField gpanoStitchingSoftwaretextField;
    private JFormattedTextField gpanoFPHPtextField;
    private JFormattedTextField gpanoFPWPtextField;
    private JComboBox gpanoPTcomboBox;
    private JCheckBox checkBox1;
    private JFormattedTextField gpanoPHDtextField;
    private JFormattedTextField gpanoIVHDtextField;
    private JCheckBox gpanoIVHDCheckBox;
    private JFormattedTextField gpanoIVPDtextField;
    private JCheckBox gpanoIVPDCheckBox;
    private JFormattedTextField gpanoIVRDtextField;
    private JCheckBox gpanoIVRDCheckBox;
    private JFormattedTextField gpanoIHFOVDtextField;
    private JCheckBox gpanoIHFOVDtextFieldCheckBox;
    private JButton gpanoResetFieldsbutton;
    private JButton gpanoHelpbutton;
    private JCheckBox gpanoOverwriteOriginalscheckBox;
    private JButton gpanoCopyFrombutton;
    private JButton gpanoCopyTobutton;
    private JLabel gpanoMinVersionText;
    private JCheckBox gpanoStitchingSoftwarecheckBox;
    private JCheckBox gpanoPHDcheckBox;
    private JTextField xmpCredittextField;
    private JCheckBox xmpCreditcheckBox;
    private JLabel xmpGoogleText;
    private JTextField ExiftoolLocationtextField;
    private MenuListener menuListener;
    private JPanel prefPanel;
    private ImageIcon icon;


    public File[] files;
    private int[] selectedIndices;
    private List<Integer> selectedIndicesList = new ArrayList<Integer>();
    private int SelectedRow;
    public int SelectedCell;
    private int SelectedCopyFromImageIndex;  // Used for the copy metadata from ..

    public String exiftool_path = "";
    private ListSelectionModel listSelectionModel;

    // Initialize all the helper classes
    PreferencesDialog prefsDialog = new PreferencesDialog();
    private MetaData metaData = new MetaData();
    private DateTime dateTime = new DateTime();
    private EditExifdata EEd = new EditExifdata();
    private EditXmpdata EXd = new EditXmpdata();
    private EditGeotaggingdata EGd = new EditGeotaggingdata();
    private EditGPSdata EGPSd = new EditGPSdata();
    private YourCommands YourCmnds = new YourCommands();
    private EditGpanodata EGpanod = new EditGpanodata();

//////////////////////////////////////////////////////////////////////////////////
    // Define the several arrays for the several Edit panes on the right side. An interface or getter/setter methods would be more "correct java", but also
    // creates way more code which doesn't make it clearer either.

    private JTextField[] getExifFields() {
        return new JTextField[]{ExifMaketextField, ExifModeltextField, ExifModifyDatetextField, ExifDateTimeOriginaltextField, ExifCreateDatetextField,
                ExifArtistCreatortextField, ExifCopyrighttextField, ExifUsercommenttextField};
    }

    private JTextArea[] getExifAreas() {
        return new JTextArea[]{ExifDescriptiontextArea};
    }

    private JCheckBox[] getExifBoxes() {
        return new JCheckBox[]{ExifMakecheckBox, ExifModelcheckBox, ExifModifyDatecheckBox, ExifDateTimeOriginalcheckBox, ExifCreateDatecheckBox,
                ExifArtistCreatorcheckBox, ExifCopyrightcheckBox, ExifUsercommentcheckBox, ExifDescriptioncheckBox, ExifBackupOriginalscheckBox};
    }
    private JTextField[] getXmpFields() {
    return new JTextField[] {xmpCreatortextField, xmpCredittextField, xmpRightstextField, xmpLabeltextField, xmpSubjecttextField, xmpTitletextField, xmpPersontextField, xmpRegionNametextField, xmpRegionTypetextField};
    }
    private JTextArea[] getXmpAreas() {
        return new JTextArea[] {xmpDescriptiontextArea};
    }

    private JCheckBox[] getXmpBoxes() {        return new JCheckBox[]{xmpCreatorcheckBox, xmpCreditcheckBox, xmpRightscheckBox, xmpLabelcheckBox, xmpSubjectcheckBox, xmpTitlecheckBox, xmpPersoncheckBox, xmpRegionNamecheckBox, xmpRegionTypecheckBox, xmpDescriptioncheckBox, xmpBackupOriginalscheckBox}; }

    private JTextField[] getGeotaggingFields() { return new JTextField[] {geotaggingImgFoldertextField, geotaggingGPSLogtextField, geotaggingGeosynctextField, geotaggingLocationtextfield, geotaggingCountrytextfield, geotaggingStatetextfield, geotaggingCitytextfield}; }

    private JCheckBox[] getGeotaggingBoxes() {return new JCheckBox[] {geotaggingLocationcheckbox, geotaggingCountrycheckbox, geotaggingStatecheckbox, geotaggingCitycheckbox};}

    private JRadioButton[] getCopyMetaDataRadiobuttons() {return new JRadioButton[] {copyAllMetadataRadiobutton, copyAllMetadataSameGroupsRadiobutton, copySelectiveMetadataradioButton}; }
    private JCheckBox[] getCopyMetaDataCheckBoxes() {return new JCheckBox[] {CopyExifcheckBox, CopyXmpcheckBox, CopyIptccheckBox, CopyIcc_profileDataCheckBox, CopyGpsCheckBox, CopyJfifcheckBox, BackupOriginalscheckBox}; }

    private JTextField[] getGPSFields() {
        return new JTextField[] {gpsLatDecimaltextField, gpsLonDecimaltextField, gpsAltDecimaltextField, gpsLocationtextField, gpsCountrytextField, gpsStateProvincetextField, gpsCitytextField};
    }
    private JCheckBox[] getGpsBoxes() {
        return new JCheckBox[] {SaveLatLonAltcheckBox, gpsAboveSealevelcheckBox, gpsLocationcheckBox, gpsCountrycheckBox, gpsStateProvincecheckBox, gpsCitycheckBox, gpsBackupOriginalscheckBox};
    }

    private JFormattedTextField[] getGpanoFields() {
        return new JFormattedTextField[] {gpanoCAIHPtextField, gpanoCAIWPtextField, gpanoCALPtextField, gpanoCATPtextField, gpanoFPHPtextField, gpanoFPWPtextField, gpanoPHDtextField, gpanoIVHDtextField, gpanoIVPDtextField, gpanoIVRDtextField, gpanoIHFOVDtextField};
    }
    private JCheckBox[] getGpanoCheckBoxes() {
        return new JCheckBox[] {gpanoPHDcheckBox, gpanoStitchingSoftwarecheckBox, gpanoIVHDCheckBox, gpanoIVPDCheckBox, gpanoIVRDCheckBox, gpanoIHFOVDtextFieldCheckBox, gpanoOverwriteOriginalscheckBox};
    }

//////////////////////////////////////////////////////////////////////////////////


    //check preferences (a.o. exiftool)
    private boolean checkPreferences() {
        boolean exiftool_exists;
        boolean exiftool_found = false;
        String res;
        List<String> cmdparams = new ArrayList<>();


        exiftool_exists = prefs.keyIsSet(EXIFTOOL_PATH);
        logger.debug("exiftool_exists reports: {}",exiftool_exists);


        if (exiftool_exists) {
            String exiftool_path = prefs.getByKey(EXIFTOOL_PATH, "");
            File tmpFile = new File(exiftool_path);
            boolean exists = tmpFile.exists();
            if (!exists) {
                exiftool_path = null;
                JOptionPane.showMessageDialog(rootPanel, ProgramTexts.ETpreferenceIncorrect, "exiftool preference incorrect", JOptionPane.WARNING_MESSAGE);
            }
            logger.debug("exists is {}", exists);
            logger.debug("preference exiftool returned: {}",exiftool_path);
            if (exiftool_path == null || exiftool_path.isEmpty() || !exists) {
                res = Utils.getExiftoolPath();
            } else {
                res = exiftool_path;
                //String[] cmdparams = {res, "-ver"};
                cmdparams.add(res);
                cmdparams.add("-ver");
                try {
                    OutputLabel.setText("Exiftool available;  Version: " + CommandRunner.runCommand(cmdparams));
                } catch (IOException | InterruptedException ex) {
                    logger.debug("Error executing command");
                }

            }
        } else { // does not exist
            res = Utils.getExiftoolPath();
        }

        if (res != null && !res.isEmpty() && !res.toLowerCase().startsWith("info")) {
            exiftool_found = true;
            // We already checked that the node did not exist and that it is empty or null
            // remove all possible line breaks
            res = res.replace("\n", "").replace("\r", "");
            if (!exiftool_exists) {
                prefs.storeByKey(EXIFTOOL_PATH, res);
            }
        }

        return exiftool_found;
    }
/////////////////////////// End of Startup checks //////////////////////////

    private void loadImages() {
        OutputLabel.setText("Loading images ....");
        files = StandardFileIO.getFileNames(mainScreen.this.rootPanel);
        if (files != null) {
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Utils.displayFiles(mainScreen.this.tableListfiles, mainScreen.this.ListexiftoolInfotable, mainScreen.this.iconLabel);
                    MyVariables.setSelectedRow(0);
                    String[] params = whichRBselected();
                    Utils.getImageInfoFromSelectedFile(params, files, mainScreen.this.ListexiftoolInfotable);
                    mainScreen.this.buttonShowImage.setEnabled(true);
                    //OutputLabel.setText(" Images loaded ...");
                    OutputLabel.setText("");
                    // progressbar enabled immedately after this void run starts in the InvokeLater, so I disable it here at the end of this void run
                    Utils.progressStatus(progressBar, false);
                }
            });
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    progressBar.setVisible(true);
                }
            });
        }
    }


    private static List<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        List<Component> compList = new ArrayList<Component>();
        for (Component comp : comps) {
            compList.add(comp);
            //logger.debug(comp.toString());
            if (comp instanceof Container) {
                compList.addAll(getAllComponents((Container) comp));
            }
        }
        return compList;
    }

    private void setCopyMetaDatacheckboxes(boolean state) {
        CopyExifcheckBox.setEnabled(state);
        CopyXmpcheckBox.setEnabled(state);
        CopyIptccheckBox.setEnabled(state);
        CopyIcc_profileDataCheckBox.setEnabled(state);
        CopyGpsCheckBox.setEnabled(state);
        CopyJfifcheckBox.setEnabled(state);
    }

    private void fillViewTagNamesComboboxes() {
        // Fill all combo boxes in the View panel
        String TagNames = StandardFileIO.readTextFileAsStringFromResource("texts/ExifToolTagNames.txt");
        String[] Tags = TagNames.split("\\r?\\n"); // split on new lines
        comboBoxViewByTagName.setModel(new DefaultComboBoxModel(Tags));

        TagNames = StandardFileIO.readTextFileAsStringFromResource("texts/CommonTags.txt");
        Tags = TagNames.split("\\r?\\n"); // split on new lines
        comboBoxViewCommonTags.setModel(new DefaultComboBoxModel(Tags));

        TagNames = StandardFileIO.readTextFileAsStringFromResource("texts/CameraTagNames.txt");
        Tags = TagNames.split("\\r?\\n"); // split on new lines
        comboBoxViewCameraMake.setModel(new DefaultComboBoxModel(Tags));

    }

    // region IntelliJ GUI Code Generated

    // endregion

    // region Action Listeners and radio button groups
    class MenuActionListener implements ActionListener {

        // menuListener
        public void actionPerformed(ActionEvent ev) {
            String[] dummy = null;
            logger.debug("Selected: {}", ev.getActionCommand());

            switch (ev.getActionCommand()) {
                case "Load Images":
                    // identical to button "Load Images"
                    loadImages();
                    break;
                case "Preferences":
                    PreferencesDialog prefdialog = new PreferencesDialog();
                    prefdialog.showDialog();
                    break;
                case "Exit":
                    System.exit(0);
                    break;
                case "Rename photos":
                    RenamePhotos renPhotos = new RenamePhotos();
                    renPhotos.setTitle("Rename Photos");
                    renPhotos.showDialog();
                    break;
                case "Copy all metadata to xmp format":
                    if (selectedIndicesList.size() > 0) {
                        OutputLabel.setText("Copying all relevant data to its xmp variants, please be patient ...");
                        metaData.copyToXmp();
                        OutputLabel.setText("");
                    } else {
                        JOptionPane.showMessageDialog(rootPanel, ProgramTexts.NoImgSelected, "No images selected", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case "Repair JPG(s) with corrupted metadata":
                    if (selectedIndicesList.size() > 0) {
                        OutputLabel.setText("Repairing jpg data, please be patient ...");
                        metaData.repairJPGMetadata( progressBar);
                        OutputLabel.setText("");
                    } else {
                        JOptionPane.showMessageDialog(rootPanel, ProgramTexts.NoImgSelected, "No images selected", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case "Export metadata":
                    if (selectedIndicesList.size() > 0) {
                        ExportMetadata expMetadata = new ExportMetadata();
                        expMetadata.showDialog(selectedIndices, files, progressBar);
                    } else {
                        JOptionPane.showMessageDialog(rootPanel, ProgramTexts.NoImgSelected, "No images selected", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case "Remove metadata":
                    if (selectedIndicesList.size() > 0) {
                        RemoveMetadata rmMetadata = new RemoveMetadata();
                        rmMetadata.showDialog(progressBar);
                        OutputLabel.setText("");
                    } else {
                        JOptionPane.showMessageDialog(rootPanel, ProgramTexts.NoImgSelected, "No images selected", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case "Shift Date/time":
                    if (selectedIndicesList.size() > 0) {
                        ShiftDateTime SDT = new ShiftDateTime();
                        SDT.showDialog(progressBar);
                        OutputLabel.setText("");
                    } else {
                        JOptionPane.showMessageDialog(rootPanel, ProgramTexts.NoImgSelected, "No images selected", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case "Modify Date/time":
                    if (selectedIndicesList.size() > 0) {
                        ModifyDateTime MDT = new ModifyDateTime();
                        MDT.showDialog(selectedIndices, files);
                        OutputLabel.setText("");
                    } else {
                        JOptionPane.showMessageDialog(rootPanel, ProgramTexts.NoImgSelected, "No images selected", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case "Set file date to DateTimeOriginal":
                    if (selectedIndicesList.size() > 0) {
                        dateTime.setFileDateTimeToDateTimeOriginal(progressBar);
                    } else {
                        JOptionPane.showMessageDialog(rootPanel, ProgramTexts.NoImgSelected, "No images selected", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case "Create args file(s)":
                    if (selectedIndicesList.size() > 0) {
                        CreateArgsFile CAF = new CreateArgsFile();
                        CAF.showDialog(selectedIndices, files, progressBar);
                    } else {
                        JOptionPane.showMessageDialog(rootPanel, ProgramTexts.NoImgSelected, "No images selected", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case "About jExifToolGUI":
                    JOptionPane.showMessageDialog(mainScreen.this.rootPanel, String.format(ProgramTexts.HTML, 450, ProgramTexts.aboutText), "About jExifToolGUI for ExifTool by Phil Harvey", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "About ExifTool":
                    JOptionPane.showMessageDialog(mainScreen.this.rootPanel, String.format(ProgramTexts.HTML, 450, ProgramTexts.aboutExifToolText), "About ExifTool by Phil Harvey", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "jExifToolGUI homepage":
                    Utils.openBrowser(ProgramTexts.ProjectWebSite);
                    break;
                case "ExifTool homepage":
                    Utils.openBrowser("https://www.sno.phy.queensu.ca/~phil/exiftool/");
                    break;
                case "License":
                    Utils.showLicense(mainScreen.this.rootPanel);
                    break;
                case "Check for new version":
                    Utils.checkForNewVersion("menu");
                    break;
                default:
                    break;
            }

        }
    }


    private void programButtonListeners() {
        // Main screen left panel
        buttonLoadImages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //File opener: Load the images; identical to Menu option Load Images.
                loadImages();
            }
        });
        buttonShowImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Utils.extdisplaySelectedImageInDefaultViewer();
            }
        });

        // Your Commands pane buttons
        CommandsclearParameterSFieldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CommandsParameterstextField.setText("");
            }
        });
        CommandsclearOutputFieldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                YourCommandsOutputTextArea.setText("");
            }
        });
        CommandsgoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (selectedIndicesList.size() > 0) {
                    if (CommandsParameterstextField.getText().length() > 0) {
                        OutputLabel.setText("Now executing your commands ...");
                        YourCmnds.executeCommands(CommandsParameterstextField.getText(), YourCommandsOutputTextArea, UseNonPropFontradioButton, progressBar);
                        OutputLabel.setText("The output should be displayed above ...");
                    } else {
                        JOptionPane.showMessageDialog(rootPanel, "No command parameters given", "No parameters", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPanel, ProgramTexts.NoImgSelected, "No images selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        CommandshelpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(rootPanel, String.format(ProgramTexts.HTML, 700, HelpTexts.YourCommandsHelp), "Help for the Your Commands panel", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Edit Exif pane buttons
        ExifcopyFromButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EEd.copyExifFromSelected(getExifFields(), ExifDescriptiontextArea);
            }
        });
        ExifsaveToButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (selectedIndicesList.size() > 0) {
                    EEd.writeExifTags(getExifFields(), ExifDescriptiontextArea, getExifBoxes(), progressBar);
                } else {
                    JOptionPane.showMessageDialog(rootPanel, ProgramTexts.NoImgSelected, "No images selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        ExifcopyDefaultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        resetFieldsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EEd.resetFields(getExifFields(), ExifDescriptiontextArea);
            }
        });
        ExifhelpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(rootPanel, String.format(ProgramTexts.HTML, 700, HelpTexts.ExifAndXmpHelp), "Help for the Exif edit panel", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        // Edit xmp buttons
        xmpCopyFrombutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EXd.copyXmpFromSelected(getXmpFields(), xmpDescriptiontextArea);
            }
        });
        xmpSaveTobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (selectedIndicesList.size() > 0) {
                    EXd.writeXmpTags(getXmpFields(), xmpDescriptiontextArea, getXmpBoxes(), progressBar);
                } else {
                    JOptionPane.showMessageDialog(rootPanel, ProgramTexts.NoImgSelected, "No images selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        xmpCopyDefaultsbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        xmpResetFieldsbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EXd.resetFields(getXmpFields(), xmpDescriptiontextArea);
            }
        });
        xmpHelpbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(rootPanel, String.format(ProgramTexts.HTML, 700, HelpTexts.ExifAndXmpHelp), "Help for the XMP edit panel", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        // Edit geotagging buttons
        geotaggingImgFolderbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String ImgPath = EGd.getImagePath(rootPanel);
                if (!"".equals(ImgPath)) {
                    geotaggingImgFoldertextField.setText(ImgPath);
                }
            }
        });
        geotaggingGPSLogbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String TrackFile = EGd.gpsLogFile(rootPanel);
                if (!"".equals(TrackFile)) {
                    geotaggingGPSLogtextField.setText(TrackFile);
                }
            }
        });
        geotaggingWriteInfobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (("".equals(geotaggingImgFoldertextField.getText())) && (selectedIndicesList.size() == 0)) {
                    JOptionPane.showMessageDialog(rootPanel, "No images selected and no image folder path selected", "No images, no path", JOptionPane.WARNING_MESSAGE);
                } else {
                    if ("".equals(geotaggingGPSLogtextField.getText())) {
                        JOptionPane.showMessageDialog(rootPanel, "No gps track log selected", "No gps log", JOptionPane.WARNING_MESSAGE);
                    } else {
                        EGd.writeInfo(getGeotaggingFields(), getGeotaggingBoxes(), geotaggingOverwriteOriginalscheckBox.isSelected(), progressBar);
                    }
                }
                OutputLabel.setText("");
            }
        });
        resetGeotaggingbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EGd.ResetFields(getGeotaggingFields(), getGeotaggingBoxes());
            }
        });
        geotaggingHelpbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(rootPanel, String.format(ProgramTexts.HTML, 700, HelpTexts.GeotaggingHelp), "Help for the Geotagging edit panel", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Edit gps buttons
        gpsCopyFrombutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EGPSd.copyGPSFromSelected(getGPSFields(), getGpsBoxes());
            }
        });
        gpsSaveTobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (selectedIndicesList.size() > 0) {
                    EGPSd.writeGPSTags(getGPSFields(), getGpsBoxes(), progressBar);
                } else {
                    JOptionPane.showMessageDialog(rootPanel, ProgramTexts.NoImgSelected, "No images selected", JOptionPane.WARNING_MESSAGE);
                }
            }

        });
        gpsResetFieldsbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EGPSd.resetFields(getGPSFields());
            }
        });
        gpsMapcoordinatesbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Utils.openBrowser("https://www.mapcoordinates.net/en");
            }
        });
        gpsHelpbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(rootPanel, String.format(ProgramTexts.HTML, 600, HelpTexts.GPSHelp), "Help for the GPS-Location edit panel", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        decimalToMinutesSecondsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        minutesSecondsToDecimalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        copyToInputFieldsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        // Copy metadata buttons
        copyAllMetadataRadiobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setCopyMetaDatacheckboxes(false);
            }
        });
        copyAllMetadataSameGroupsRadiobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setCopyMetaDatacheckboxes(false);
            }
        });
        copySelectiveMetadataradioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setCopyMetaDatacheckboxes(true);
            }
        });
        UseDataFrombutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SelectedCopyFromImageIndex = SelectedRow;
            }
        });
        CopyDataCopyTobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //metaData.copyMetaData(getCopyMetaDataRadiobuttons(), getCopyMetaDataCheckBoxes(), SelectedCopyFromImageIndex, selectedIndices, files, progressBar);
                metaData.copyMetaData(getCopyMetaDataRadiobuttons(), getCopyMetaDataCheckBoxes(), SelectedCopyFromImageIndex, progressBar);
            }
        });
        CopyHelpbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(rootPanel, String.format(ProgramTexts.HTML, 450, HelpTexts.CopyMetaDataHelp), "Help for the Copy metadata panel", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // The buttons from the Gpano edit tab
        gpanoCopyFrombutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EGpanod.copyGpanoFromSelected(getGpanoFields(), gpanoStitchingSoftwaretextField, gpanoPTcomboBox, getGpanoCheckBoxes());
                JOptionPane.showMessageDialog(rootPanel, ProgramTexts.GpanoSetSaveCheckboxes, "Set Save checkboxes", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gpanoCopyTobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (selectedIndicesList.size() > 0) {
                    // Now we need to check on completeness of mandatory fields
                    boolean allFieldsFilled = EGpanod.checkFieldsOnNotBeingEmpty(getGpanoFields(), gpanoPTcomboBox);
                    if (allFieldsFilled) {
                        EGpanod.writeGpanoTags(getGpanoFields(), getGpanoCheckBoxes(), gpanoStitchingSoftwaretextField, gpanoPTcomboBox, progressBar);
                    } else {
                        JOptionPane.showMessageDialog(rootPanel, ProgramTexts.NotAllMandatoryFields, "Not all manadatory fields complete", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPanel, ProgramTexts.NoImgSelected, "No images selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        gpanoResetFieldsbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EGpanod.resetFields(getGpanoFields(), gpanoStitchingSoftwaretextField, getGpanoCheckBoxes());
            }
        });
        gpanoHelpbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(rootPanel, String.format(ProgramTexts.HTML, 450, HelpTexts.GpanoHelp), "Help for the Edit Gpano metadata panel", JOptionPane.INFORMATION_MESSAGE);
            }
        });

    }

    private void ViewRadiobuttonListener() {

        //Add listeners
        radioButtonViewAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //logger.debug("button selected: {}", radioButtonViewAll.getText());
                Utils.getImageInfoFromSelectedFile(MyConstants.ALL_PARAMS, files, mainScreen.this.ListexiftoolInfotable);
            }
        });
        radioButtoncommonTags.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String[] params = Utils.getWhichCommonTagSelected(comboBoxViewCommonTags);
                //Utils.selectImageInfoByTagName(comboBoxViewCommonTags, SelectedRow, files, mainScreen.this.ListexiftoolInfotable);
                Utils.getImageInfoFromSelectedFile(params, files, mainScreen.this.ListexiftoolInfotable);
            }
        });
        comboBoxViewCommonTags.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (radioButtoncommonTags.isSelected()) {
                    String[] params = Utils.getWhichCommonTagSelected(comboBoxViewCommonTags);
                    //Utils.selectImageInfoByTagName(comboBoxViewCommonTags, SelectedRow, files, mainScreen.this.ListexiftoolInfotable);
                    Utils.getImageInfoFromSelectedFile(params, files, mainScreen.this.ListexiftoolInfotable);
                }
            }
        });

        radioButtonByTagName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utils.selectImageInfoByTagName(comboBoxViewByTagName, files, mainScreen.this.ListexiftoolInfotable);
            }
        });
        comboBoxViewByTagName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (radioButtonByTagName.isSelected()) {
                    Utils.selectImageInfoByTagName(comboBoxViewByTagName, files, mainScreen.this.ListexiftoolInfotable);
                }
            }
        });
        radioButtonCameraMakes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Utils.selectImageInfoByTagName(comboBoxViewCameraMake, files, mainScreen.this.ListexiftoolInfotable);
            }
        });
        comboBoxViewCameraMake.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (radioButtonCameraMakes.isSelected()) {
                    Utils.selectImageInfoByTagName(comboBoxViewCameraMake, files, mainScreen.this.ListexiftoolInfotable);
                }
            }
        });
    }



    private String[] whichRBselected() {
        // just to make sure anything is returned we defaultly return all
        String[] params = MyConstants.ALL_PARAMS;
        // Very simple if list
        if (mainScreen.this.radioButtonViewAll.isSelected()) {
            params = MyConstants.ALL_PARAMS;
        } else if (radioButtoncommonTags.isSelected()) {
            params = Utils.getWhichCommonTagSelected(comboBoxViewCommonTags);
        } else if (radioButtonByTagName.isSelected()) {
            params = Utils.getWhichTagSelected(comboBoxViewByTagName);
        } else if (radioButtonCameraMakes.isSelected()) {
            params = Utils.getWhichTagSelected(comboBoxViewCameraMake);
        }
        return params;
    }
    

    // This is the general table listener that also enables multi row selection
    class SharedListSelectionHandler implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            // Perfectly working row selection method of first program
            List<Integer> tmpselectedIndices = new ArrayList<>();
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            if (lsm.isSelectionEmpty()) {
                logger.debug("no index selected");
            } else {
                // Find out which indexes are selected.
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i)) {
                        tmpselectedIndices.add(i);
                        SelectedRow = i;
                        MyVariables.setSelectedRow(i);
                    }
                }
                String[] params = whichRBselected();
                Utils.getImageInfoFromSelectedFile(params, files, ListexiftoolInfotable);

                selectedIndices = tmpselectedIndices.stream().mapToInt(Integer::intValue).toArray();
                logger.debug("Selected indices: {}", tmpselectedIndices);
                selectedIndicesList = tmpselectedIndices;
                MyVariables.setSelectedFilenamesIndices(selectedIndices);
            }

        }
    }

    void fileNamesTableMouseListener() {
        // Use the mouse listener for the single cell double-click selection for the left table to be able to
        // display the image in the default viewer

        tableListfiles.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    Utils.extdisplaySelectedImageInDefaultViewer();
                    logger.debug("double-click registered");
                }
            }
        });
    }
    // endregion

    private void createmyMenuBar(JFrame frame) {
        menuBar = new JMenuBar();

        // File menu
        myMenu = new JMenu("File");
        myMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(myMenu);
        menuItem = new JMenuItem("Load Images");
        myMenu.setMnemonic(KeyEvent.VK_L);
        menuItem.addActionListener(new MenuActionListener());
        myMenu.add(menuItem);
        menuItem = new JMenuItem("Preferences");
        myMenu.setMnemonic(KeyEvent.VK_P);
        menuItem.addActionListener(new MenuActionListener());
        myMenu.add(menuItem);
        menuItem = new JMenuItem("Exit");
        menuItem.setMnemonic(KeyEvent.VK_X);
        menuItem.addActionListener(new MenuActionListener());
        myMenu.add(menuItem);

        // Extra menu
        myMenu = new JMenu("Extra");
        myMenu.setMnemonic(KeyEvent.VK_E);
        menuBar.add(myMenu);
        menuItem = new JMenuItem("Rename photos");
        //myMenu.setMnemonic(KeyEvent.VK_R);
        menuItem.addActionListener(new MenuActionListener());
        myMenu.add(menuItem);
        myMenu.addSeparator();
        menuItem = new JMenuItem("Export metadata");
        menuItem.addActionListener(new MenuActionListener());
        myMenu.add(menuItem);
        menuItem = new JMenuItem("Copy all metadata to xmp format");
        menuItem.addActionListener(new MenuActionListener());
        myMenu.add(menuItem);
        menuItem = new JMenuItem("Remove metadata");
        menuItem.addActionListener(new MenuActionListener());
        myMenu.add(menuItem);
        myMenu.addSeparator();
        menuItem = new JMenuItem("Shift Date/time");
        menuItem.addActionListener(new MenuActionListener());
        myMenu.add(menuItem);
        menuItem = new JMenuItem("Modify Date/time");
        menuItem.addActionListener(new MenuActionListener());
        myMenu.add(menuItem);
        menuItem = new JMenuItem("Set file date to DateTimeOriginal");
        menuItem.addActionListener(new MenuActionListener());
        myMenu.add(menuItem);
        myMenu.addSeparator();
        menuItem = new JMenuItem("Repair JPG(s) with corrupted metadata");
        menuItem.addActionListener(new MenuActionListener());
        myMenu.add(menuItem);
        menuItem = new JMenuItem("Create args file(s)");
        menuItem.addActionListener(new MenuActionListener());
        myMenu.add(menuItem);

        // Help menu
        myMenu = new JMenu("Help");
        myMenu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(myMenu);
        menuItem = new JMenuItem("jExifToolGUI homepage");
        menuItem.addActionListener(new MenuActionListener());
        myMenu.add(menuItem);
        menuItem = new JMenuItem("ExifTool homepage");
        menuItem.addActionListener(new MenuActionListener());
        myMenu.add(menuItem);
        menuItem = new JMenuItem("Manual");
        myMenu.add(menuItem);
        myMenu.addSeparator();
        menuItem = new JMenuItem("Donate");
        myMenu.add(menuItem);
        myMenu.addSeparator();
        menuItem = new JMenuItem("License");
        menuItem.addActionListener(new MenuActionListener());
        myMenu.add(menuItem);
        menuItem = new JMenuItem("Check for new version");
        menuItem.addActionListener(new MenuActionListener());
        myMenu.add(menuItem);
        menuItem = new JMenuItem("About ExifTool");
        menuItem.addActionListener(new MenuActionListener());
        myMenu.add(menuItem);
        menuItem = new JMenuItem("About jExifToolGUI");
        menuItem.addActionListener(new MenuActionListener());
        myMenu.add(menuItem);

        // Finally add menubar to the frame
        frame.setJMenuBar(menuBar);
    }

    // Sets the necessary screen texts. This might later be transformed in some i18n option.
    private void setProgramScreenTexts() {
        String version = "";
        MyCommandsText.setText(ProgramTexts.MyCommandsText);
        xmpGoogleText.setText(ProgramTexts.XmpGoogleText);
        GeotaggingLeaveFolderEmptyLabel.setText(ProgramTexts.GeotaggingLeaveFolderEmpty);
        GeotaggingLocationLabel.setText(String.format(ProgramTexts.HTML, 600, ProgramTexts.GeotaggingLocationLabel));
        GeotaggingGeosyncExplainLabel.setText(String.format(ProgramTexts.HTML, 600, ProgramTexts.GeotaggingGeosyncExplainLabel));
        gpsCalculatorLabelText.setText(String.format(ProgramTexts.HTML, 110, ProgramTexts.gpsCalculatorLabelText));
        gPanoTopText.setText(String.format(ProgramTexts.HTML, 600, ProgramTexts.GPanoTopText));
        // Special dynamic version string
        String exiftool = prefs.getByKey(EXIFTOOL_PATH, "");
        List<String> cmdparams = new ArrayList<>();
        cmdparams.add(exiftool);
        cmdparams.add("-ver");

        try {
            version = CommandRunner.runCommand(cmdparams);
        } catch (IOException | InterruptedException ex) {
            logger.debug("Error executing command");
            version = "I cannot determine the exiftool version";
        }
        gpanoMinVersionText.setText(String.format(ProgramTexts.HTML, 600, ProgramTexts.GpanoMinVersionText + version));
    }

    private void setFormattedFieldFormats(JFormattedTextField[] theFields) {
        Locale currentLocale = Locale.getDefault();
        NumberFormat formatter = NumberFormat.getNumberInstance(currentLocale );
        formatter.setMaximumFractionDigits(4);
        for (JFormattedTextField field : theFields) {
            field.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(formatter)));
        }
    }

    private mainScreen(JFrame frame) {
        boolean preferences = false;

        Utils.progressStatus(progressBar, false);

        createmyMenuBar(frame);
        ViewRadiobuttonListener();
        fillViewTagNamesComboboxes();

        // Now check the preferences
        preferences = checkPreferences();
        if (!preferences) {
            Utils.checkExifTool(mainScreen.this.rootPanel);
        }

        // Use the mouselistener for the double-click to display the image
        fileNamesTableMouseListener();
        //Use the table listener for theselection of multiple cells
        listSelectionModel = tableListfiles.getSelectionModel();
        tableListfiles.setRowSelectionAllowed(true);
        tableListfiles.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
        //cellSelectionModel.addListSelectionListener(new SharedListSelectionListener());

        // Make left "tableListfiles" and right "ListexiftoolInfotable" tables read-only (un-editable)
        // This also fixex the double-click bug on the image where it retrieves the object name of the images on double-click
        tableListfiles.setDefaultEditor(Object.class, null);
        ListexiftoolInfotable.setDefaultEditor(Object.class, null);

        // icon for my dialogs
        InputStream stream = StandardFileIO.getResourceAsStream("icons/jexiftoolgui-64.png");
        try {
            icon = new ImageIcon(ImageIO.read(stream));
        } catch (IOException ex) {
            logger.debug("Error executing command");
        }


        // Try to set the defaults for artist and copyrights in the edit exif/xmp panes if prefs available
        Pair<String, String> prefsArtistCopyRights = Utils.checkPrefsArtistCopyRights();
        ExifArtistCreatortextField.setText(prefsArtistCopyRights.getLeft());
        xmpCreatortextField.setText(prefsArtistCopyRights.getLeft());
        ExifCopyrighttextField.setText(prefsArtistCopyRights.getRight());
        xmpRightstextField.setText(prefsArtistCopyRights.getRight());

        //Combobox on Gpano edit tab
        for (String item : MyConstants.GPANO_PROJECTIONS) {
            gpanoPTcomboBox.addItem(item);
        }

        programButtonListeners();
        // Some texts
        setProgramScreenTexts();
        // Set formatting for the JFormatted textFields
        setFormattedFieldFormats(getGpanoFields());

        Utils.checkForNewVersion("startup");
    }

    static void createAndShowGUI() {
        JFrame frame = new JFrame("jExifToolGUI V" + ProgramTexts.Version + "   for ExifTool by Phil Harvey");
        frame.setContentPane(new mainScreen(frame).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*frame.setIconImage(
                new ImageIcon(getClass().getClassLoader().getResource("resources/jexiftoolgui.ico"))
        );*/
        try {
            // Significantly improves the look of the output in
            // terms of the folder/file icons and file names returned by FileSystemView!
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception weTried) {
           logger.error("Could nod start GUI.", weTried);
        }

        frame.pack();
        //frame.setLocationRelativeTo(null);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}
