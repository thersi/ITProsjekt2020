package quizapp.ui;

import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import quizapp.core.DirectUserAccess;
import quizapp.core.RemoteUserAccess;
import quizapp.core.User;
import quizapp.core.UserAccess;

public class SignupController extends QuizAppController {

  @FXML
  TextField username;
  @FXML
  TextField password;
  @FXML
  Label errorMessage;
  @FXML
  Button loginButton;
  private UserAccess remoteUserAccess;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
  }

  @FXML
  public void toLogin(ActionEvent event) {
    this.switchSceneWithNode("Login.fxml", loginButton);
  }

  /**
   * Saves user and goes to main menu.
   */
  @FXML
  public void toMainMenu(ActionEvent event) throws Exception {
    try {
      remoteUserAccess = new RemoteUserAccess(new URI("http://localhost:8080/api/user/"));
    } catch (Exception e) {
      remoteUserAccess = new DirectUserAccess();
    }
    final Collection<User> users = remoteUserAccess.getUsers();
    if (users.stream().anyMatch(a -> a.getUsername().equals(username.getText()))) {
      username.clear();
      password.clear();
      errorMessage.setText("Username already taken");
      return;
    } else if (username.getText().equals("") || password.getText().equals("")) {
      username.clear();
      password.clear();
      errorMessage.setText("Username and password must at least contain 1 sign");
      return;
    }
    // Saves user
    final User newUser = new User();
    newUser.setUsername(this.username.getText());
    newUser.setPassword(this.password.getText());
    remoteUserAccess.postUser(newUser);
    // Updates active user
    try {
      remoteUserAccess = new RemoteUserAccess(new URI("http://localhost:8080/api/user/updateActive/"));
    } catch (Exception e) {
      remoteUserAccess = new DirectUserAccess();
    }
    remoteUserAccess.putActiveUser(newUser.getUsername());
    this.switchSceneWithNode("MainPage.fxml", loginButton);
    
  }

}
