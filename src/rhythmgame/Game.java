
package rhythmgame;

/**
 *
 * @author arria
 */

import javafx.animation.*;
import javafx.scene.effect.Lighting;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.*;
import javafx.scene.effect.Reflection;
import javafx.scene.shape.Rectangle;


public class Game {
    // Song info
    private String song;
    private String song_file;
    private static PlayScreenController play;

    // Beat lists
    private static  ArrayList<ArrayList<String>> keyPressTimeStamps = new ArrayList<>();
    private static  ArrayList<ArrayList<String>> trackDict = new ArrayList<>(4);
    private String beatFile;
    
    // Window settings
    final int winLength = 574;
    private int[] colPosition = {355, 433, 510, 587};
    
    // Rectangle settings
    private Rectangle r1;
    private Rectangle r2;
    private Rectangle r3;
    private Rectangle r4;
    private ObservableList<Rectangle> rectangles = FXCollections.observableArrayList();
    private ObservableList<Rectangle> halos = FXCollections.observableArrayList();
    
    final double opacity = 0.18;
    final int width = 15;
    
    private Paint[] color = {Color.rgb(255,119,0), Color.rgb(241,255,31), Color.rgb(31,253,255), Color.rgb(255, 31, 31)};
    private Paint[] haloColor = {Color.rgb(255,119,0, opacity), Color.rgb(241,255,31, opacity), Color.rgb(31,253,255, opacity), Color.rgb(255, 31, 31, opacity)};

    // Animation settings
    private int delayTime = 0;

    // Media settings
    protected MediaPlayer mediaPlayer;

    // Constructor
	public Game(String song_title) {
		song = song_title;
		song_file = StartGameController.get_song_file();
	}

	/* Create arrayList of song beats based on chosen song and
	 * initialize empty user's keypress list.
	 * @return
	 */
	protected void initSongData() {
		// Initialize empty user_keypress file
        for (int i = 0; i < 4; i++) {
            ArrayList<String> keyPressElement = new ArrayList<>();
            keyPressTimeStamps.add(keyPressElement);
        }

        // Load song beat array
        trackDict = createTrackList();
	}

	/* Create rectangle and halo animation and begin animation timeline.
	 * @return timeline
	 */
	protected Timeline initUI() {
            createHaloRectangles();
            ArrayList<ArrayList<String>> dict = new ArrayList<>();
            Timeline tl = new Timeline(new KeyFrame(Duration.ZERO));
            ObservableList<KeyFrame> keyframes = FXCollections.observableArrayList();

        // Create beat data arrayList based on filename
        dict = createTrackList();

        // Iterate though dictionary for beat time and column numbers
        for (int i = 0; i < 4; i++) {
            for(String ts: dict.get(i)){
                int timeStamp = Integer.parseInt(ts);
                Rectangle r = createRectangle(i);
                rectangles.add(r);

                // Create keyFrame for each musical note (rectangles) at top of screen
                keyframes.add(new KeyFrame(new Duration(timeStamp-900+delayTime),
                        new KeyValue(r.translateYProperty(), 0, Interpolator.EASE_IN)));

                // Create keyFrame for rectangles at bottom of screen
                keyframes.add(new KeyFrame(new Duration(delayTime + timeStamp),
                        new KeyValue(r.translateYProperty(), winLength-width*2, Interpolator.EASE_IN)));

                // Create keyFrame for falling rectangles animation
                keyframes.add(new KeyFrame(new Duration(timeStamp+150), new KeyValue(r.translateYProperty(),winLength,Interpolator.LINEAR)));
            }
        }

        // Add all keyFrames to timeline
        for (KeyFrame kf : keyframes) {
            tl.getKeyFrames().add(kf);
        }

        return tl;
    }
        /* Getters */
	protected ObservableList<Rectangle> getRectangles() {
	    return rectangles;
	}

	protected ObservableList<Rectangle> getHalos() {
	    return halos;
	}

	/* Creates an arrayList of track beats for each of the four different keys by
	 * loading and parsing through a song text file.
	 *
	 * @return ArrayList<ArrayList<String>> track list
	 */
	private ArrayList<ArrayList<String>> createTrackList() {
		set_beat_file();

        // Initialize empty 2D arrayList containing original song beats
        for (int i = 0; i < 4; i++) {
            ArrayList<String> dictElement = new ArrayList<>();
            trackDict.add(dictElement);
        }

        String line;
        String[] beatPair;
        String timeSt;
        String[] col;

        // Parse through beat file and split timeStamps with respective key presses into arrayList
        // Avoid file not found exception
        try {
            Scanner sc = new Scanner(new FileReader(beatFile));
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                line = line.replaceAll("\\s+", "");
                beatPair = line.split("\\|");
                timeSt = beatPair[0];
                col = beatPair[1].split(",");

                for(String nCol : col){
                    trackDict.get(Integer.parseInt(nCol)-1).add(timeSt);
                }
            }
            sc.close();
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }

        return trackDict;
    }

	/* Set beat file by replacing .mp3 extension with .txt to get beat file of chosen song
	 * @return
	 */
	private void set_beat_file() {
		String[] addr = song_file.split("\\.");
		beatFile = addr[0]+".txt";
	}

	
	private Rectangle createRectangle(int col) {
            Rectangle r = new Rectangle(colPosition[col], 0, 77, 23);
            r.setEffect(new Lighting());
            r.setFill(color[col]);
            return r;
        }

	/* Create halo effect when user presses key corresponding to a circle
	 * @return
	 */
	private void createHaloRectangles(){
            r1 = new Rectangle(colPosition[0],winLength-45,77,25);
            r1.setFill(haloColor[0]);
            r2 = new Rectangle(colPosition[1],winLength-45,77,25);
            r2.setFill(haloColor[1]);
            r3 = new Rectangle(colPosition[2],winLength-45,77,25);
            r3.setFill(haloColor[2]);
            r4 = new Rectangle(colPosition[3],winLength-45,77,25);
            r4.setFill(haloColor[3]);
            
            halos.add(r1);
            halos.add(r2);
            halos.add(r3);
            halos.add(r4);
    }

	
	protected void setHaloRadius(int index) {
	switch(index) {
                case(0):
                        r1.setHeight(25);
                        r1.setEffect(new Reflection(0.0,0.75,0.79,0.02));          
                break;
		case(1):
                        r2.setHeight(25);
			r2.setEffect(new Reflection(0.0,0.75,0.79,0.02));              
                break;
		case(2):
                        r3.setHeight(25);
			r3.setEffect(new Reflection(0.0,0.75,0.79,0.02));                       
                break;
		case(3):
                        r4.setHeight(25);
			r4.setEffect(new Reflection(0.0,0.75,0.79,0.02));                   
                break;
		}
	}
        
        protected void removeHaloRadius(int index) {
	switch(index) {
                case(0):
                        r1.setHeight(25);
                        r1.setEffect(null);          
                break;
		case(1):
                        r2.setHeight(25);
			r2.setEffect(null);              
                break;
		case(2):
                        r3.setHeight(25);
			r3.setEffect(null);                       
                break;
		case(3):
                        r4.setHeight(25);
			r4.setEffect(null);                   
                break;
		}
	}

	/* Load song file and initiate new Media object with chosen song queued
	 * @return
	 */
	protected void initAudio() {
		Media sound = new Media(new File(song_file).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
	}

	/* Play/resume audio
	 * @return
	 */
	protected void playSong() {
		mediaPlayer.play();
	}

	/* Pause audio
	 * @return
	 */
	protected void pauseSong() {
		mediaPlayer.pause();
	}


	/* Return current score as user plays game by comparing user's key presses with original key beats.
	 * Add 10 points for each key pressed that is within 400 ms of actual correct beat
	 * @returns score
	 */
	protected static int scoreGame() {
        ArrayList<String> user_beats, orig_beats;
        int score = 0;
        int smallest_lst_size = 0;
        int orig_curr_beat, user_curr_beat;

        // Grab timeStamp list for each of the four keys
        for (int i = 0; i < 4; i++) {
            user_beats = keyPressTimeStamps.get(i);
            orig_beats = trackDict.get(i);
            // If user pressed the key LESS than actual key beats
            if (orig_beats.size() >= user_beats.size()) {
                smallest_lst_size = user_beats.size();
            } else {
                smallest_lst_size = orig_beats.size();
            } 

            for (int j = 0; j <= smallest_lst_size - 1; j++) {
                orig_curr_beat = Math.round(Float.parseFloat(orig_beats.get(j)));
                user_curr_beat = Math.round(Float.parseFloat(user_beats.get(j).replace(" ms", "")));
                // Check to make sure user keypress within 400ms of actual correct beat
                if (Math.abs(user_curr_beat - orig_curr_beat) <= 1000) {
                    score += 10;
                    }     
                }
            }
            
        return score; 

    }
        
        protected static int HitORMiss(){
            ArrayList<String> user_beats, orig_beats;
            int status = 0;
            int smallest_lst_size = 0;
            int orig_curr_beat, user_curr_beat;
            
            // Grab timeStamp list for each of the four keys
        for (int i = 0; i < 4; i++) {
            user_beats = keyPressTimeStamps.get(i);
            orig_beats = trackDict.get(i);
            // If user pressed the key LESS than actual key beats
            if (orig_beats.size() >= user_beats.size()) {
                smallest_lst_size = user_beats.size();
            } else {
                smallest_lst_size = orig_beats.size();
            } 
            
            for (int j = 0; j <= smallest_lst_size - 1; j++) {
                orig_curr_beat = Math.round(Float.parseFloat(orig_beats.get(j)));
                user_curr_beat = Math.round(Float.parseFloat(user_beats.get(j).replace(" ms", "")));
                // Check to make sure user keypress within 400ms of actual correct beat
            
                if (Math.abs(user_curr_beat - orig_curr_beat) <= 1000) {                    
                    status = 1;
                    }
                else if (Math.abs(user_curr_beat - orig_curr_beat) >= 1001){
                    status = 2;
                }  
            }   
                      
        } return status;
        }
        

        
	/* Add new user key press to user's current keyPressTimeStamp list
	 * @return
	 */
	public void updateKeyPressTimeStamp(int i, Duration currentTime) {
		keyPressTimeStamps.get(i).add(currentTime.toString());
	}
}

