package com.mycompany.cecs544_project;

import java.io.File;
import javax.swing.*;

public class FileContent extends JInternalFrame {
    JSplitPane splitPane;
    FolderPanel folderPanel;
    boolean isframeClosed;
    FilePanel filePanel;
    File drive;
    
    public FileContent(File drive)
    {
        
        folderPanel = new FolderPanel(drive);
        filePanel = new FilePanel(drive);
        
        folderPanel.setFilePanel(filePanel);
        
        this.setTitle(drive.toString());
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, folderPanel, filePanel);
        splitPane.setResizeWeight(0.5);
        this.setTitle("C:");
        
        this.getContentPane().add(splitPane);
        this.setResizable(true);
        this.setClosable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setBounds(0,30,575,450);
      
        this.setVisible(true);
        
    }
}
