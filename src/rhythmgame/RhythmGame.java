/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rhythmgame;

import java.io.IOException;
import java.util.HashMap;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author arria
 */
public class RhythmGame extends Application {

        private static Stage primaryStage;
	public static HashMap<String, Scene> screens = new HashMap<String, Scene>();
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
//        Parent root = FXMLLoader.load(getClass().getResource("StartGame.fxml"));      
//        Scene scene = new Scene(root);
//        stage.setTitle("Rhythm Game");
//        stage.setScene(scene);
//        stage.show();

                try {
			// Load startup screen
			FXMLLoader loader = new FXMLLoader(RhythmGame.class.getResource("StartGame.fxml"));
			AnchorPane screen = (AnchorPane) loader.load();
                        
			Scene scene = new Scene(screen);
                        
			primaryStage.setScene(scene);
			primaryStage.setTitle("Rhythm Game");
			primaryStage.show();
                        scene.getRoot().requestFocus();

		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    public static Stage getPrimaryStage() {
		return primaryStage;
	}
    /**
     * @param args the command line arguments
     */
    public static void switchScreen(FXMLLoader loader, Button button) {
		// Grab stage
		Window window = button.getScene().getWindow();
		Stage stage = (Stage) window;

		try {
			// Replace with new Pane scene on current stage
			Pane screen = (Pane) loader.load();
			Scene scene = new Scene(screen);
                        scene.setOnKeyPressed((EventHandler<? super KeyEvent>) screen);
			stage.setScene(scene);
			stage.setTitle("Rhythm Game");
			stage.show();
                        

		} catch (IOException e) {
			e.printStackTrace();
		}
    }      
    public static void main(String[] args) {
        launch(args);
    }
    
    
    

	
    
}
