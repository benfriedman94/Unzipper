/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdfd63unzipper;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ui.*;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;
import net.lingala.zip4j.model.FileHeader;

/**
 *
 * @author Ben
 */
public class FXMLDocumentController extends UIScene {
    
    private final FileChooser fileChooser = new FileChooser();
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private String source;
    private String destination;
    private Unzip unzip;
    
    @FXML
    private TextField filePath;
      
    @FXML
    private TextField destinationDir;
    
    @FXML
    private Label status;
    
    @FXML
    private ProgressBar progressBar;
            
    @FXML
    private TextArea extractedPathsTextArea;
    
    @FXML
    private void selectFile(ActionEvent event) {
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("zip files", "*.zip"));
        File selectedFile = fileChooser.showOpenDialog(this.uiStage.getStage());
        source = selectedFile.getAbsolutePath();
        filePath.setText(source);
    }
    
    @FXML 
    private void selectDestination(ActionEvent event) {
        File directory = directoryChooser.showDialog(this.uiStage.getStage());
        destination = directory.getAbsolutePath();
        destinationDir.setText(destination);
    }
    
    @FXML
    private void startUnzip(ActionEvent event) {
        unzip = new Unzip(source, destination);
        unzip.setOnNotification((percentage, status) -> {           
            if(status == Status.FINISHED) {
                for(FileHeader fileHeader: unzip.getResultFileList()) {
                    extractedPathsTextArea.appendText(fileHeader.getFileName() + "\n");
                }
                extractedPathsTextArea.appendText("Done!");
            }        
            this.status.setText("Status: " + unzip.getStatus());
            this.progressBar.setProgress((double)percentage/100);        
        });
        unzip.start();
    }
    
    @FXML 
    private void stopUnzip(ActionEvent event) {
        unzip.stopThread();
        status.setText("Status: " + unzip.getStatus());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
