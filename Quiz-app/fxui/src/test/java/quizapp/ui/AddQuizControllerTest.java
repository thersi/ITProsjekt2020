package quizapp.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import java.awt.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import quizapp.core.DirectQuizAccess;
import quizapp.core.DirectUserAccess;
import quizapp.core.Quiz;
import quizapp.core.UserAccess;


@TestMethodOrder(OrderAnnotation.class)
public class AddQuizControllerTest extends FxuiTest {

  private Stage stage;
  private DirectQuizAccess directQuizAccess = new DirectQuizAccess();
  private UserAccess directUserAccess = new DirectUserAccess();

  @Override
  public void start(final Stage stage) throws Exception {
    directQuizAccess = new DirectQuizAccess();
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("AddQuiz.fxml"));
    final Parent root = loader.load();
    stage.setScene(new Scene(root));
    stage.show();
    this.stage = stage;
  }

  @Test
  public void MakeAQuizTest() throws AWTException {
    // Deletes quiz if it already exists
    directQuizAccess.deleteQuiz("Color-quiz");
    fillInQuiz("Color quiz");
    Quiz quiz = directQuizAccess.getQuiz("Color-quiz");
    assertNotNull(quiz);
    assertEquals("Color quiz", quiz.getName());
    assertEquals(1, quiz.getQuestion(0).getCorrectAlternative());
    assertEquals("Green", quiz.getQuestion(0).getAlternative(1));
    assertEquals("What color do you get if you mix red and yellow?", quiz.getQuestion(1).getQuestion());
    // Checks that the scene has changed to main menu
    assertNull(stage.getScene().lookup("#mainMenu"));
    assertNotNull(stage.getScene().lookup("#menuButton"));
    directQuizAccess.deleteQuiz("Color-quiz");
  }

  @Test
  public void submitUncompleteQuizTest() throws AWTException {
    ScrollPane scroll = (ScrollPane) stage.getScene().lookup("#scroll");
    findTextField("#title").setText("Test quiz");
    findTextField("#q0").setText("Is this a test?");
    findTextField("#q0an0").setText("Yes");
    findTextField("#q0an1").setText("No");
    findTextField("#q0an2").setText("Purple");
    findTextField("#q0an3").setText("Red");
    clickOnButton("#q0a0");
    scroll.setVvalue(1.0);
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
    }
    clickOnButton("#submit");
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
    }
    String text = ((Label) stage.getScene().lookup("#scoreLabel")).getText();
    assertEquals("Invalid Quiz. All fields must be filled.", text);
  }

  @Test
  public void makeQuizWithInvalidName() throws AWTException {
    fillInQuiz("History quiz");
    String text = ((Label) stage.getScene().lookup("#scoreLabel")).getText();
    assertEquals("This quiz name is already taken.", text);
  }



  @Test    
  public void goToMainMenuTest() {
    // Checks that we on add quiz page
    assertNotNull(stage.getScene().lookup("#mainMenu"));
    assertNull(stage.getScene().lookup("#menuButton"));
    // Changes Scene to Main Menu
    clickOnButton("#mainMenu");
    // Checks that we are on the Main page scene
    assertNull(stage.getScene().lookup("#mainMenu"));
    assertNotNull(stage.getScene().lookup("#menuButton"));
    directQuizAccess.deleteQuiz("Color-quiz");

  }

  @Test   
  public void checkCorrectUserDisplayed() {
    // Checks active user and makes sure it matches username displayed on menu
    // button
    String activeUser = ((MenuButton) stage.getScene().lookup("#userMenu")).getText();
    assertEquals(directUserAccess.getActiveUser().getUsername(), activeUser);
  }

  private TextField findTextField(String node) {
    return (TextField) stage.getScene().lookup(node);
  }

  private void fillInQuiz(String title) throws AWTException {
    ScrollPane scroll = (ScrollPane) stage.getScene().lookup("#scroll");
    // Fills in everything needed to make a quiz
    scroll.setVvalue(0.0);
    findTextField("#title").setText(title);
    findTextField("#q0").setText("What color do you get if you mix blue and yellow?");
    findTextField("#q0an0").setText("Blue");
    findTextField("#q0an1").setText("Green");
    findTextField("#q0an2").setText("Purple");
    findTextField("#q0an3").setText("Red");
    clickOnButton("#q0a1");
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
    }
    scroll.setVvalue(0.5);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
    }
    findTextField("#q1").setText("What color do you get if you mix red and yellow?");
    findTextField("#q1an0").setText("Green");
    findTextField("#q1an1").setText("Yellow");
    findTextField("#q1an2").setText("Blue");
    findTextField("#q1an3").setText("Orange");
    clickOnButton("#q1a3");
    // Scrolls to bottom of screen
    // Slows down the code to give the robot time to scroll
    scroll.setVvalue(1);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
    }
    findTextField("#q2").setText("What color do you get if you mix blue and red?");
    findTextField("#q2an0").setText("Black");
    findTextField("#q2an1").setText("White");
    findTextField("#q2an2").setText("Purple");
    findTextField("#q2an3").setText("Green");
    clickOnButton("#q2a2");
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
    }
    clickOnButton("#submit");
    directQuizAccess.deleteQuiz("Color-Quiz");
  }
}