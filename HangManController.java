
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


//this class control implementation
public class HangManController {

    @FXML
    private Canvas canv;
    
    @FXML
    private Text guessedList;

    @FXML
    private ComboBox<Character> letterC;

    @FXML
    private Text match ;
    
    private Game g;
    
    
    private GraphicsContext gc;
    
    @FXML
    private Text title;


    @FXML
    void geTextInput(ActionEvent event) { 
    	//gc = canv.getGraphicsContext2D();
    	char c = letterC.getValue();
    	letterC.setValue(null);
    	setComboBox(c);
    	g.roundManage(match ,guessedList ,c , gc);
    	
    	
    }
    
    @FXML
  //start a new game
    void pressedNewGame(ActionEvent event) {
    	gc.clearRect(0, 0, canv.getWidth(), canv.getHeight());
    	guessedList.setFill(Color.BLACK);
    	
    	initialize();
    }
    
    @FXML
 // initializeing the game , by constructing game object
    public void initialize() {
    	title.setText("HangMan Game");
    	title.setFill(Color.TURQUOISE);
    	gc = canv.getGraphicsContext2D();
    	gc.setFill(Color.LIGHTBLUE);
    	gc.fillRect(0, 0, canv.getWidth(), canv.getHeight());
    	g = new Game();
    	letterC.getItems().clear();
    	initComboBox();
    	guessedList.setText("chosen letters list :");
    	init();
    	
    }
    
 // initializing the options in the comboBox
    private void initComboBox() {
    	for(int i = (int)('a');  i <= (int)('z') ; i++) {
    		letterC.getItems().add((char)(i));
    	}
    }
    
    
    private void setComboBox(char c) {
    	
    	letterC.getItems().removeAll(c);
    	
    }
    
	// initialize match according to it's length
	private void init() {
		String st = "";
    	for(int i = 0; i <g.getAWordToGuess().length() ; i++ ) {
    		st += "_ ";
    	}
    	match.setText(st);
	}
    
	


}
