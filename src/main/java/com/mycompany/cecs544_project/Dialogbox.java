/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cecs544_project;

/**
 *
 * @author mniha
 */
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Dialogbox extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField fromField;
    private JTextField toField;
    private JLabel currentDirectory;

    public Dialogbox(String title) {
        this.setTitle(title);
        this.setSize(300, 200);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        fromField.enable(false);

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

    private static void copyFile(String from, String to) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(from);
            os = new FileOutputStream(to);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    private static void renameFile(String from, String to) {
        File fromFile = new File(from);
        File toFile = new File(to);
        fromFile.renameTo(toFile);
    }

    private void onOK() {
        if (getTitle() == "Copying") {
            try {
                copyFile(fromField.getText(), toField.getText());
            } catch(IOException ex) {
                System.out.println("Exception");
            }
        } else if (getTitle() == "Rename") {
            renameFile(fromField.getText(), toField.getText());
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    public String getToField() {
        return toField.getText();
    }

    public void setFromField(String from) {
        fromField.setText(from);
    }

    public void setCurrentDirectory(String current) {currentDirectory.setText(current);}

}
