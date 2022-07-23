package com.mycompany.cecs544_project;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileWindow extends JFrame {
    JPanel mainPanel, viewPanel;
    JMenuBar menuBar;
    JComboBox drives;
    JToolBar toolBar,statusBar;
    JButton simple, details;
    JDesktopPane dPane;
    List<FileContent> fileContents;
    //static JDesktopPane deskTop;
    JLabel drive,totalSpace,freeSpace,usedSpace;
    File currentDrive;
    
    public FileWindow(){
        mainPanel = new JPanel();
        viewPanel = new JPanel();
        
        menuBar = new JMenuBar();
        toolBar = new JToolBar();
        
        simple = new JButton("Simple");
        details = new JButton("Details");
        
        details.addActionListener(new DetailsActionListener());
        simple.addActionListener(new SimpleActionListener());
        dPane = new JDesktopPane();
        fileContents = new ArrayList<>();
        
        mainPanel.setLayout(new BorderLayout());
        viewPanel.setLayout(new BorderLayout());  
    }
    
    void run(){
        createWindow();
        
        createMenu();
        viewPanel.add(menuBar, BorderLayout.NORTH);
        
        createToolbar();
        viewPanel.add(toolBar, BorderLayout.SOUTH);
        
        FileContent fileFolder = new FileContent(new File("C:\\"));
        fileContents.add(fileFolder);
        dPane.add(fileFolder);
        
        mainPanel.add(viewPanel, BorderLayout.NORTH);
        mainPanel.add(dPane, BorderLayout.CENTER);
                
        this.add(mainPanel);
        this.setSize(800,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
   
    void createWindow(){
        this.setTitle("CECS 544 File Manager");
        viewPanel.setLayout(new BorderLayout());
    }
    public void revalidateAndPaint() {
	this.revalidate();
	this.repaint();
	}
    
    void createMenu(){
        JMenu file, tree, window, help;
        JMenuItem rename, copy, delete, run, exit;
        JMenuItem expandBranch, collapseBranch;
        JMenuItem New ,cascade;
        JMenuItem Help,about;
        
        file = new JMenu("File");
        
        rename = new JMenuItem("Rename");
        copy = new JMenuItem("Copy");
        delete = new JMenuItem("Delete");
        run = new JMenuItem("Run");
        exit = new JMenuItem("Exit");
        
        file.add(rename);
        file.add(copy);
        file.add(delete);
        file.add(run);
        file.add(exit);
        menuBar.add(file);
        
        tree = new JMenu("Tree");
        
        expandBranch = new JMenuItem("Expand Branch");
        collapseBranch = new JMenuItem("Collapse Branch");
        
                 
        
        tree.add(expandBranch);
        tree.add(collapseBranch);
        menuBar.add(tree);
        expandBranch.addActionListener(new ExpandCollapseListener());
        collapseBranch.addActionListener(new ExpandCollapseListener());
        
        
        window = new JMenu("Window");
        New=new JMenuItem("New");
        cascade=new JMenuItem("Cascade");
      
        
        window.add(New);
        window.add(cascade);
        menuBar.add(window);
        New.addActionListener(new NewActionListener());
        cascade.addActionListener(new CascadeActionListener());
        
         
         help=new JMenu("Help");
         Help=new JMenuItem("Help");
         about=new JMenuItem("About");
         
         help.add(Help);
         help.add(about);
         menuBar.add(help);
         

   }
    
    void createToolbar(){
        File[] folders;
        folders = File.listRoots();
        
        drives = new JComboBox(folders);
        drives.setMaximumSize(new Dimension(100,50));
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        toolBar.add(drives);
        toolBar.add(simple);
        toolBar.add(details);
        
        toolBar.setMargin(new Insets(4,4,4,4));
        toolBar.setFloatable(false);
        toolBar.setVisible(true);
    }
    
    private void buildStatusbar(String currentDrive) {
        File file = new File(currentDrive);
        drive = new JLabel("Current Drive: " + currentDrive);
        totalSpace = new JLabel("   Total Space: " + String.valueOf(file.getTotalSpace()/(1024*1024*1024)) + "GB");
        usedSpace = new JLabel("    Used Space: " + String.valueOf((file.getTotalSpace() - file.getFreeSpace())/1073741824) + "GB");
        freeSpace = new JLabel("    Free Space: " + String.valueOf(file.getFreeSpace()/(1024*1024*1024)) + "GB"); 
        statusBar.add(drive);
        statusBar.add(freeSpace);
        statusBar.add(usedSpace);
        statusBar.add(totalSpace);
        statusBar.setFloatable(false);
    }
    
     public class NewActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("new test");
           File f = (File) drives.getSelectedItem();
	   FileContent filecontentFrame = new FileContent(f);
           dPane.add(filecontentFrame);
	  // filecontentFrame.moveToFront();
           revalidateAndPaint();
           
			
                      

        }    
    }
     
     public class CascadeActionListener implements ActionListener{
         @Override
         public void actionPerformed(ActionEvent ae)
         {
             int offset = 0;
                for (int i = 0; i < fileContents.size(); i++){
                    if (!fileContents.get(i).isframeClosed){
                        fileContents.get(i).setBounds(offset, offset, 500, 500);
                        offset += 35;
                    }
                }
         }
     }
      private class ExpandCollapseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.print("Hell");
            FileContent selectedFrame = (FileContent) dPane.getSelectedFrame();
            if (selectedFrame == null)
                return;
            JTree tree = selectedFrame.folderPanel.getDirTree();
            int row = tree.getMinSelectionRow();
            if (e.getActionCommand().equals("Expand Branch") && tree.isCollapsed(row))
                tree.expandRow(row);
            if (e.getActionCommand().equals("Collapse Branch") && tree.isExpanded(row))
                tree.collapseRow(row);
        }
    }
      private class DetailsActionListener implements ActionListener{
          @Override
          public void actionPerformed(ActionEvent ae){
                FileContent activecontent = (FileContent)dPane.getSelectedFrame();
                if (activecontent == null) {
                    return;
                }
                FilePanel fp = activecontent.filePanel;
                fp.setDetail(true);
            }
      }
           private class SimpleActionListener implements ActionListener{
          @Override
          public void actionPerformed(ActionEvent ae){
                FileContent activecontent = (FileContent)dPane.getSelectedFrame();
                if (activecontent == null) {
                    return;
                }
                FilePanel fp = activecontent.filePanel;
                fp.setDetail(false);
            }
        }
     }
    

