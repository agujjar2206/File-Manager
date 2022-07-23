package com.mycompany.cecs544_project;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.io.IOException;
import java.io.File;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class FolderPanel extends JPanel {

    JTree folderTree = new JTree();
    DefaultTreeModel tModel;
    JScrollPane srPane = new JScrollPane();
    FilePanel filePl;
    File mainDir;

    public FolderPanel(File mainDirectory) {
        //filePl = filePanel;
        mainDir = mainDirectory;

        //folderTree.addTreeSelectionListener(new FolderTreeSelection());
        // folderTree.addTreeWillExapandListener(new FolderTreeExpansion());
        srPane.setViewportView(folderTree);
        this.setLayout(new BorderLayout());
        this.add(srPane, BorderLayout.CENTER);
        displayContentTree(mainDirectory);
        srPane.setSize(350, 3950);
        folderTree.setSize(srPane.getSize());
        folderTree.addTreeSelectionListener(new FolderTreeSelection());
        //DefaultTreeCellRenderer cellRenderer = (DefaultTreeCellRenderer)folderTree.getCellRenderer();
        //cellRenderer.setLeafIcon(cellRenderer.getClosedIcon());
    }

    public void displayContentTree(File driveFile) {
        // System.out.println(driveFile.toString());
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(driveFile.toString());
        tModel = new DefaultTreeModel(node);

        File[] folders = driveFile.listFiles();
        DefaultMutableTreeNode nodefiles;

        for (int i = 0; i < folders.length; i++) {
            File folderFiles = folders[i];
            if (folderFiles.isDirectory()) {
                FileInfo fileInfo = new FileInfo(folderFiles.getName(), folders[i]);
                nodefiles = new DefaultMutableTreeNode(fileInfo);
                node.add(nodefiles);
                displayExpansionFolder(folderFiles, nodefiles);
            }
        }
        folderTree.setModel(tModel);
    }
    public JTree getDirTree() {
        return folderTree;
    }

    public void displayExpansionFolder(File folderFiles, DefaultMutableTreeNode rootNode) {
        File[] nodeChildFiles = folderFiles.listFiles();
        DefaultMutableTreeNode childnodes = null;

        if (nodeChildFiles != null) {
            for (File subchildFiles : nodeChildFiles) {
                FileInfo fInfo = new FileInfo(subchildFiles.toString());
                childnodes = new DefaultMutableTreeNode(fInfo);
                if (rootNode.getChildCount() != nodeChildFiles.length) {
                    rootNode.add(childnodes);

                }
            }
        }
    }

    public void setFilePanel(FilePanel filePanel) {
        filePl = filePanel;
    }

    class FolderTreeSelection implements TreeSelectionListener {
        
        @Override
        public void valueChanged(TreeSelectionEvent tse) {
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) folderTree.getLastSelectedPathComponent();

            try {
                Desktop dTop = Desktop.getDesktop();
                FileInfo fileInfo = (FileInfo) treeNode.getUserObject();
                displayExpansionFolder(fileInfo.getFile(), treeNode);
                boolean checkfolder = (fileInfo.getFile().isDirectory());
                if (checkfolder == false) {
                    filePl.setFile(fileInfo.getFile());
                    dTop.open(fileInfo.getFile());
                } else {
                    filePl.fileList(fileInfo.getFile());
                }
            } catch (ClassCastException ce) {
                System.out.println(ce.toString());
            } catch (IOException ex) {
                Logger.getLogger(FolderPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
