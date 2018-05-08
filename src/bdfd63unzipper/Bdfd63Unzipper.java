/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdfd63unzipper;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.*;

/**
 *
 * @author Ben
 */
public class Bdfd63Unzipper extends Application {
    
    private UIStage mainUIStage;
    private UIScene mainUIScene;
    
    @Override
    public void start(Stage stage) throws Exception {
        mainUIStage = new UIStage(stage);
        
        try {
            mainUIScene = mainUIStage.loadScene("MainUI", getClass().getResource("FXMLDocument.fxml"));
        } catch (Exception ex) {
            System.out.println("error loading scene occurred");
        }
        mainUIStage.displayScene(mainUIScene);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
