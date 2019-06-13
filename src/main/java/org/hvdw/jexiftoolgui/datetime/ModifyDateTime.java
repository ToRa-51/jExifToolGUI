package org.hvdw.jexiftoolgui.datetime;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.hvdw.jexiftoolgui.Utils;
import org.hvdw.jexiftoolgui.ProgramTexts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModifyDateTime extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel ModifyDateTimeLabel;
    private JCheckBox UseRefDateTimecheckBox;
    private JTextField ModifyDatetextField;
    private JCheckBox ModifyDatecheckBox;
    private JTextField DateTimeOriginaltextField;
    private JCheckBox DateTimeOriginalcheckBox;
    private JTextField CreateDatetextField;
    private JCheckBox CreateDatecheckBox;
    private JCheckBox ShiftAboveDateTimecheckBox;
    private JTextField SiftDateTimetextField;
    private JCheckBox shiftForwardcheckBox;
    private JCheckBox UpdateXmpcheckBox;
    private JCheckBox ShiftDateTimecheckBox;

    public int[] selectedFilenamesIndices;
    public File[] files;

    public ModifyDateTime() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void initDialog() {

        ModifyDateTimeLabel.setText(String.format(ProgramTexts.HTML, 370, ProgramTexts.ModifyDateTimeLabel));

        DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        Date date = new Date();
        ModifyDatetextField.setText(dateFormat.format(date));
        DateTimeOriginaltextField.setText(dateFormat.format(date));
        CreateDatetextField.setText(dateFormat.format(date));
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void showDialog(int[] selectedIndices, File[] openedfiles) {
        selectedFilenamesIndices = selectedIndices;
        files = openedfiles;

        pack();
        setLocationByPlatform(true);
        setTitle("Modify Date/Time");
        initDialog();
        setVisible(true);
    }

}
