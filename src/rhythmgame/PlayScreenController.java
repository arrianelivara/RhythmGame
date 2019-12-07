
package rhythmgame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
/**
 * FXML Controller class
 *
 * @author arria
 */
public class PlayScreenController implements Initializable {
    
        public static ArrayList<ArrayList<String>> get_keyPressTimeStamps;
	private Game game;
	private Timeline tl;
        
        @FXML
        private HBox rectangles_Box;
        @FXML
        private Text scoreLabel;
        @FXML
        private StackPane gameStackPane;
        @FXML
        private AnchorPane gameAnchorPane;
        @FXML
        private BorderPane playScreenBorderPane;
    @FXML
    private ImageView red;
    @FXML
    private ImageView blue;
    @FXML
    private ImageView yellow;
    @FXML
    private ImageView orange;
    private ImageView missed;
    @FXML
    private ImageView Status;
    
    Image img = new Image("images/Great.png");
    Image img2 = new Image("images/missed.png");
    Image img3 = new Image("images/spam.png");

    /* Inject action events from FXML panes */
        @FXML
	public void handleOnMouseEntered(MouseEvent event) {
		// Display circles on screen
		game.playSong();
		tl.play();
	}
        
        
        
        @FXML
	public void handleKeyPressed(KeyEvent event) {

	  
        }
        
        
        
        @FXML
	public void handleOnKeyReleased(KeyEvent ke) {
            System.out.println(ke.getEventType().toString());
            if(ke.getCode()== KeyCode.D){
                orange.setVisible(false);
                System.out.println("D was released");
                game.removeHaloRadius(0);
                hideStatus();
            }
            if(ke.getCode()== KeyCode.F){
                yellow.setVisible(false);
                System.out.println("F was released");
                game.removeHaloRadius(1);
                hideStatus();
            }
            if(ke.getCode()== KeyCode.J){
                blue.setVisible(false);
                System.out.println("J was released");
                game.removeHaloRadius(2);
                hideStatus();
            }
            if(ke.getCode()== KeyCode.K){
                red.setVisible(false);
                System.out.println("K was released");
                game.removeHaloRadius(3);
                hideStatus();
            }   
            
	}
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
            playScreenBorderPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getEventType().toString());
            if(event.getCode().equals(KeyCode.D)){               
                orange.setVisible(true);
                game.setHaloRadius(0);
                System.out.println("c1: "+tl.getCurrentTime());
                game.updateKeyPressTimeStamp(0, tl.getCurrentTime());
                showStatus();
            }
            if(event.getCode().equals(KeyCode.F)){
                yellow.setVisible(true);
                game.setHaloRadius(1);
                System.out.println("c2: "+tl.getCurrentTime());
                game.updateKeyPressTimeStamp(1, tl.getCurrentTime());
                showStatus();
            }
            if(event.getCode().equals(KeyCode.J)){
                blue.setVisible(true);
                game.setHaloRadius(2);
                System.out.println("c3: "+tl.getCurrentTime());
                game.updateKeyPressTimeStamp(2, tl.getCurrentTime());
                showStatus();
             }
            if(event.getCode().equals(KeyCode.K)){
                red.setVisible(true);
                game.setHaloRadius(3);
                System.out.println("c4: "+tl.getCurrentTime());
                game.updateKeyPressTimeStamp(3, tl.getCurrentTime());
                showStatus();
            }
                    int score = game.scoreGame();                   
                    scoreLabel.setText(String.valueOf(score));
                    
            }
            });
            
        // Begin game
		game = new Game(StartGameController.get_song());
		game.initSongData();
		game.initAudio();
		tl = game.initUI();

		// Add circle animations to scene pane
		for(Rectangle c : game.getRectangles()) {
			playScreenBorderPane.getChildren().addAll(c);
		}
		for(Rectangle h: game.getHalos()){
			gameAnchorPane.getChildren().addAll(h);
		}
                
               
                
    }
    
    public void playGame() {
		tl.play();
                game.playSong();
	}

   public void showStatus(){
           
           int status1 = game.HitORMiss();
            switch (status1) {
                case 0:
                    Status.setImage(img3);
                    Status.setVisible(true);
                    break;
                case 1:
                    Status.setImage(img);
                    Status.setVisible(true);
                    break;
                case 2:
                    Status.setImage(img2);
                    Status.setVisible(true);
                    break;
                default:
                    break;
            }
    }
   public void hideStatus(){

            Status.setVisible(false);
   }
    
    
    
}
