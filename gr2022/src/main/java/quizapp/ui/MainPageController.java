package quizapp.ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;


public class MainPageController {
	
    @FXML
    MenuBar menuBar;

    @FXML
    MenuButton menuButton;
    @FXML
    MenuItem logOutButton;
    @FXML 
    MenuItem profileButton;
    
    @FXML
    Button historyQuizButton;

	

    @FXML
    public void goToHistoryQuiz(ActionEvent event) {
    	this.switchSceneWithButton(event, "HistoryQuiz.fxml");
    }
    

    @FXML
    public void goToProfile(ActionEvent event) {
    	this.switchSceneWithMenuItem("ProfilePage.fxml");
    }

    @FXML
    public void logOut(ActionEvent event) {
    	this.switchSceneWithMenuItem("Login.fxml");
    }
    
    public void switchSceneWithButton(ActionEvent event, String fxmlFile) {
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource(fxmlFile));
			Scene scene = new Scene(parent);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void switchSceneWithMenuItem(String fxmlFile) {
    	try {
        	Stage stage = (Stage) menuBar.getScene().getWindow();
			Parent parent = FXMLLoader.load(getClass().getResource(fxmlFile));
	        Scene scene = new Scene(parent);  
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
   

}
