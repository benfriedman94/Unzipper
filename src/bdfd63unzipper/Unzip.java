/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdfd63unzipper;

import java.util.List;
import javafx.application.Platform;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.progress.ProgressMonitor;

/**
 *
 * @author Ben
 */

enum Status {
    NOTSTARTED ("Not Running"), 
    RUNNING ("Running"), 
    FINISHED ("Finished"), 
    STOPPED ("Stopped");
    
    private final String name;
    
    private Status(String n){
        name = n;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}

public class Unzip extends Thread {
    
    private Status status;
    private final String source;
    private final String destination;
    private int percentage;
    private Notification notification;
    private ZipFile zipFile;
    private List<FileHeader> resultFileList;
      
    public Unzip(String source, String destination) {
        this.source = source;
        this.destination = destination;
        this.status = Status.NOTSTARTED;
    }
   
    @Override
    public void run() {
        status = Status.RUNNING;
        
        try {
            zipFile = new ZipFile(source);            
            ProgressMonitor progressMonitor = zipFile.getProgressMonitor();
                        
            while(progressMonitor.getState() == ProgressMonitor.STATE_BUSY) {
                try {
                    Thread.sleep(700);
                } catch(InterruptedException ex) {
                    return;
                }                
            }          
            
            if (progressMonitor.getResult() == ProgressMonitor.RESULT_ERROR) {
                if (progressMonitor.getException() != null) {
                        progressMonitor.getException().printStackTrace();
                } else {
                        System.err.println("An error occurred without any exception");
                }
            } else if (progressMonitor.getResult() == ProgressMonitor.RESULT_SUCCESS) {
                status = Status.FINISHED;
                percentage = 100;
                updateProgress();
            }  
            
            zipFile.extractAll(destination);
            this.resultFileList = zipFile.getFileHeaders();
            updateProgress();              
        } catch (ZipException e) {
            e.printStackTrace();
        } 
    }
    
    public void stopThread() {
        status = Status.STOPPED;
        zipFile.getProgressMonitor().cancelAllTasks();
        notification.handle(percentage, status);       
    }
    
    public void setOnNotification(Notification notification) {
        this.notification = notification;
    }
    
    private void updateProgress() {
        if (notification != null) {
            Platform.runLater(() -> {
                notification.handle(percentage, status);
            });
        }
    }
    
    public String getStatus() {
       return status.toString();
    }
    
    public List<FileHeader> getResultFileList() {
        return this.resultFileList;
    }
}
