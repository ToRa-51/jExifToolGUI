package org.hvdw.jexiftoolgui.datetime;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.hvdw.jexiftoolgui.MyVariables;
import org.hvdw.jexiftoolgui.ProgramTexts;
import org.hvdw.jexiftoolgui.Utils;
import org.hvdw.jexiftoolgui.controllers.CommandRunner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class ShiftDateTime extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JCheckBox shiftForwardCheckBox;
    private JTextField ShiftDateTimetextField;
    private JLabel ShiftDateTimeLabel;
    private JCheckBox ShiftDateTimeOriginalcheckBox;
    private JCheckBox ShiftCreateDatecheckBox;
    private JCheckBox ShiftModifyDatecheckBox;
    private JCheckBox BackupOfOriginalscheckBox;
    private JCheckBox UpdatexmpcheckBox;

    private int[] selectedFilenamesIndices;
    public File[] files;
    private JProgressBar progBar;

    public ShiftDateTime() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void initDialog() {

        ShiftDateTimeLabel.setText(String.format(ProgramTexts.HTML, 320, ProgramTexts.ShiftDateTimeLabel));
    }

    private void writeInfo() {
        String shiftOption;
        List<String> cmdparams = new LinkedList<>();

        String exiftool = Utils.platformExiftool();
        cmdparams.add(exiftool);
        if (!BackupOfOriginalscheckBox.isSelected()) {
            cmdparams.add("-overwrite_original");
        }
        if (shiftForwardCheckBox.isSelected()) {
            shiftOption = "+=";
        } else {
            shiftOption = "-=";
        }

        if (ShiftDateTimeOriginalcheckBox.isSelected()) {
            cmdparams.add("-exif:DateTimeOriginal" + shiftOption + ShiftDateTimetextField.getText().trim());
            if (UpdatexmpcheckBox.isSelected()) {
                cmdparams.add("-xmp:DateTimeOriginal" + shiftOption + ShiftDateTimetextField.getText().trim());
            }
        }
        if (ShiftModifyDatecheckBox.isSelected()) {
            cmdparams.add("-exif:ModifyDate" + shiftOption + ShiftDateTimetextField.getText().trim());
            if (UpdatexmpcheckBox.isSelected()) {
                cmdparams.add("-xmp:ModifyDate" + shiftOption + ShiftDateTimetextField.getText().trim());
            }
        }
        if (ShiftCreateDatecheckBox.isSelected()) {
            cmdparams.add("-exif:CreateDate" + shiftOption + ShiftDateTimetextField.getText().trim());
            if (UpdatexmpcheckBox.isSelected()) {
                cmdparams.add("-xmp:DateTimeDigitized" + shiftOption + ShiftDateTimetextField.getText().trim());
            }
        }

        for (int index : selectedFilenamesIndices) {
            if (Utils.isOsFromMicrosoft()) {
                cmdparams.add(files[index].getPath().replace("\\", "/"));
            } else {
                cmdparams.add(files[index].getPath());
            }
        }

        CommandRunner.runCommandWithProgressBar(cmdparams, progBar);
    }

    private void onOK() {
        writeInfo();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void showDialog(JProgressBar progressBar) {
        selectedFilenamesIndices = MyVariables.getSelectedFilenamesIndices();
        files = MyVariables.getSelectedFiles();
        progBar = progressBar;

        pack();
        setLocationByPlatform(true);
        setTitle("Shift Date/Time");
        initDialog();
        setVisible(true);
    }

}
