package com.mycompany.cecs544_project;

/**
 *
 * @author harsh
 */

import java.io.*;
import java.text.*;
public class FileInfo {
    boolean fileDetails;
    String fileName;
    File file;
    
    public FileInfo(String fileName){
        file = new File(fileName);
        fileDetails = false;
    }
    
    public FileInfo(String Name, File file){
        fileName = Name;
        this.file = file;
        fileDetails = false;
    }
    
    public File getFile(){
        return file;
    }
    
    public boolean isDirectory(){
        return file.isDirectory();
    }
     public void setDetail(boolean d) {
        fileDetails = d;
    }
    
    public String toString(){
        if(file.getName().equals("")){
            return file.getPath();
        }
        
        if(fileDetails == true){
            DecimalFormat deciformat = new DecimalFormat("#,###");
            SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
            String s = String.format("%-18s%s%25s", file.getName(), dateformat.format(file.lastModified()), deciformat.format(file.length()));
            return s;       
        }
        else{
            return file.getName();
        }
    }
}
