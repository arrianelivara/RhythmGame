/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rhythmgame;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author arria
 */
public class StartGameController implements Initializable {

    @FXML
    private Button btnPlayScreen;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ComboBox<String> selectSongDropdown;
    
        private static String song;
	private static String song_file;
	private static HashMap<String, String> song_list = new HashMap<String, String>();
	private static HashMap<String, String> song_file_list = new HashMap<String, String>();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        populateDropdownMenu();
        createSongMap();
        
        // Default to easiest song if user does not pick song
		set_song("Buttercup");
		set_song_file(get_song());
        
                // Dropdown menu listener
		selectSongDropdown.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String old_song, String new_song) {
                set_song(new_song);
                set_song_file(new_song);
            }
		});	
	
    }    
  
    /**
	 * Creates a hashmap of song files as values
	 * @return
	 */
	protected void createSongMap() {
		song_list.put(selectSongDropdown.getItems().get(0), "seven_nation_army.mp3");
		song_list.put(selectSongDropdown.getItems().get(1), "its_time.mp3");
		song_list.put(selectSongDropdown.getItems().get(2), "holy_ghost.mp3");
		song_list.put(selectSongDropdown.getItems().get(3), "lange_her.mp3");
                song_list.put(selectSongDropdown.getItems().get(4), "buttercup.wav");
                
		//printSongMap();
	}
    /** Getter
	 * @return String song
     */
	protected static String get_song() {
		return song;
	}

	/** Getter
	 * @return String song file
     */
	protected static String get_song_file() {
		return song_file;
	}

	/** Setter
	 * @param new_song
     */
	protected void set_song(String new_song) {
		song = new_song;
	}

	/** Setter
	 * @param new_song
     */
	protected void set_song_file(String new_song) {
		song_file = song_list.get(get_song());
	}
    private void populateDropdownMenu() {
		// Populate drop down menu
		selectSongDropdown.setPromptText("Seven Nation Army - The White Stripes (Easy)");
		selectSongDropdown.getItems().addAll(
				"Seven Nation Army - The White Stripes (Easy)",
				"It's Time - Imagine Dragons (Medium)",
				"Holy Ghost - Børns (Hard)",
				"Lange Her - Cro ft. Teesy (Legendary)",
                                "Buttercup");
	}
    
 /**
	 * Prints hashmap of song files as values
	 * @return
	 */
	private void printSongMap() {
		Set set = song_list.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()) {
			HashMap.Entry mentry = (HashMap.Entry)iterator.next();
			System.out.print("Song Key is: "+ mentry.getKey() + " & Value is: ");
			System.out.println(mentry.getValue());
		}

		Set set2 = song_file_list.entrySet();
		Iterator iterator2 = set2.iterator();
		while(iterator2.hasNext()) {
			HashMap.Entry mentry2 = (HashMap.Entry)iterator2.next();
			System.out.print("Song file key is: "+ mentry2.getKey() + " & Value is: ");
			System.out.println(mentry2.getValue());
		}
	}
    
   
        

    @FXML
    private void playScreen(MouseEvent event) {       
        try {
            BorderPane pane = FXMLLoader.load(getClass().getResource("PlayScreen.fxml"));
            rootPane.getChildren().setAll(pane);
            
            
           
            
        } catch (IOException ex) {
            Logger.getLogger(StartGameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadSecond(ActionEvent event) {
    }
    
    public void loadTutorial(Button btnPlayScreen) {
		FXMLLoader tut_first_loader = new FXMLLoader(RhythmGame.class.getResource("PlayScreen.fxml"));
		RhythmGame.switchScreen(tut_first_loader,btnPlayScreen);
	}

}

