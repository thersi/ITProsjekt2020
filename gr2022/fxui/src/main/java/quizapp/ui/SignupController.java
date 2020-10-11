package quizapp.ui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import quizapp.core.User;
import quizapp.json.JsonHandler;
import quizapp.json.UsernameHandler;

public class SignupController implements Initializable {

  @FXML
  TextField username;
  @FXML
  TextField password;
  @FXML
  Label errorMessage;

  private final JsonHandler handler = new JsonHandler(
      "/workspace/gr2022/gr2022/core/src/main/resources/quizapp/json/JSONHandler.json");
  private final UsernameHandler usernameHandler = new UsernameHandler(
      "/workspace/gr2022/gr2022/core/src/main/resources/quizapp/json/activeUser.json");

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub
  }

  /**
   * Saves user and goes to main menu.
   */
  @FXML
  public void toMainMenu(ActionEvent event) throws Exception {
    final List<User> user = handler.loadFromFile();
    if (user.stream().anyMatch(a -> a.getUsername().equals(username.getText()))) {
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
    // New user is saved
    final User newUser = new User();
    newUser.setUsername(this.username.getText());
    newUser.setPassword(this.password.getText());
    user.add(newUser);
    handler.writeToFile(user);

    usernameHandler.saveActiveUser(username.getText(),
        "/workspace/gr2022/gr2022/core/src/main/resources/quizapp/json/JSONHandler.json");

    // Gets the stage information and sets the scene
    Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
    Scene tableViewScene = new Scene(tableViewParent);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(tableViewScene);
    window.show();
  }
}