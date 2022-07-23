package com.mycompany.cecs544_project;

import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class FilePanel extends JPanel{
    File currFile;
    File currDir;
    DefaultListModel listModel = new DefaultListModel();
    JList list = new JList();
    Desktop deskTop = Desktop.getDesktop();
    JScrollPane scrollPane = new JScrollPane();
    JPopupMenu popUp = new JPopupMenu();
    public JList getList() {
        return list;
    }
    public File getCurDir() {
        return currDir;
    }
    public File getcurFile() {
        return currFile;
    }
    
    FilePanel(File f){
        currFile = f;
       list.setModel(listModel);
       
      
       // createModel(f);
        scrollPane.setViewportView(list);
        this.setLayout(new BorderLayout());
        scrollPane.setSize(390, 3950);
        list.setSize(scrollPane.getSize());
        
        MouseListener mouseListener = new mouseActionListener();
        list.addMouseListener(mouseListener);
        this.add(scrollPane);
        this.setDropTarget(new MyDropTarget());
        list.setDragEnabled(true);
    }
    
  /*  public void createModel(File f){
        File fileData;
        File[] subfiles = f.listFiles();
        if(subfiles != null) {
            for(int i=0; i < subfiles.length; i++){
                fileData = subfiles[i];
                FileInfo fileInfo = new FileInfo(fileData.toString(), fileData);
                listModel.addElement(fileInfo);
            }
        }
        list.setDragEnabled(true);
        list.setModel(listModel);
    }*/
     public void setDetail(boolean d) {
        for (int i = 0; i < listModel.getSize(); i++) {
            FileInfo fileinfo = (FileInfo)listModel.getElementAt(i);
            fileinfo.setDetail(d);
        }
        scrollPane.repaint();
    }
    
    public void setFile(File f){
        currFile = f;
    }
    
    public void fileList(File f){
        File[] subFiles;
        subFiles = f.listFiles();
        
        if(subFiles != null){
            listModel.clear();
            list.removeAll();
            
            for(File subFile : subFiles){
                String fileName = subFile.toString();
                FileInfo fileInfo = new FileInfo(fileName, subFile);
                listModel.addElement(fileInfo);
            }
            list.setModel(listModel);
        }
        else{
            listModel.clear();
        }
    }
    public void check(MouseEvent e){
             if(e.isPopupTrigger()){
                 list.setSelectedIndex(list.locationToIndex(e.getPoint()));
                 popUp.show(list, e.getX(), e.getY());
                 currFile=new File((String)list.getSelectedValue());
             }
         }
   
   
            
        class MyDropTarget extends DropTarget {
        public void drop(DropTargetDropEvent evt){
            try {
                //types of events accepted
                evt.acceptDrop(DnDConstants.ACTION_COPY);
                //storage to hold the drop data for processing
                List result = new ArrayList();
                //what is being dropped? First, Strings are processed
                if(evt.getTransferable().isDataFlavorSupported(DataFlavor.stringFlavor)){     
                    String temp = (String)evt.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    //String events are concatenated if more than one list item 
                    //selected in the source. The strings are separated by
                    //newline characters. Use split to break the string into
                    //individual file names and store in String[]
                    String[] next = temp.split("\\n");
                    //add the strings to the listmodel
                    for(int i=0; i<next.length;i++)
                        listModel.addElement(next[i]); 
                }
                else{ //then if not String, Files are assumed
                    result =(java.util.List)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    //process the input
                    for(Object o : result){
                        System.out.println(o.toString());
                        listModel.addElement(o.toString());
                    }
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
         }
        
         public class mouseActionListener extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e){
            check(e);
        }
        
        @Override
        public void mouseReleased(MouseEvent e){
            check(e);
        }
         
        @Override
        public void mouseClicked(MouseEvent e){
            System.out.println("mouse test 1");
            if(e.getClickCount() == 2){
                if(list.getSelectedValue() != null){
                    FileInfo fInfo = (FileInfo)list.getSelectedValue();
                    File file = fInfo.getFile();
                    Desktop dTop = Desktop.getDesktop();
                    
                    try{
                        if(!file.isDirectory()){
                            currFile = file;
                            dTop.open(file);
                        }
                    }
                    catch(IOException io){
                        System.out.println(io.toString());
                    }
                }
            }
        }
        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popUp.show(e.getComponent(),
                           e.getX(), e.getY());
            }
        }
         }
}

        
  
        
         
        
        
         
    

