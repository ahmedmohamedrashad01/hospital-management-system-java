
package fsl_tool;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class FSL_Tool extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        try {
            Parent p = FXMLLoader.load(this.getClass().getResource("Login.fxml"));
            Scene scene = new Scene(p);
            primaryStage.setTitle("FSL_Tool App");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(ex.getMessage());
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
