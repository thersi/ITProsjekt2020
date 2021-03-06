package quizapp.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import quizapp.core.User;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class JSONHandlerTest {

  private static JsonHandler handler;
  private User user1;
  private User user2;
  private User user3;

  // Sets up usernames, Users and quizzes
  @BeforeEach
  public void setUp() {
    List<User> usernames = new ArrayList<>();
    handler = new JsonHandler("src/main/resources/quizapp/json/JSONHandlerTest.json");
    user1 = new User("Hallvard", "Trætteberg");
    user2 = new User("George", "Stoica");
    usernames.add(user1);
    usernames.add(user2);
    user1.addQuiz("testquiz123", 0.69);
    handler.writeToFile(usernames);
    user3 = new User("test", "person");
  }

  @Test
  public void loadFromFileTest() {
    Collection<User> loadedUsers = handler.loadFromFile();
    assertEquals(2, loadedUsers.size());
    assertTrue(loadedUsers.contains(user1));
    assertTrue(loadedUsers.contains(user2));
    assertFalse(loadedUsers.contains(user3));
  }


  @Test
  public void activeUserTest() {
    UsernameHandler usernameHandler = new UsernameHandler("src/main/resources/quizapp/json/activeUser.json");
    usernameHandler.saveActiveUser("Hallvard", "src/main/resources/quizapp/json/JSONHandlerTest.json");
    assertEquals("Hallvard", handler.loadActiveUser().getUsername());
  }

  @Test
  public void loadUserByNameTest() {
    assertEquals(user1, handler.loadUserFromString("Hallvard"));
  }

  @Test
  public void updateUserTest() {
    assertFalse(user1.getDarkMode());
    user1.setDarkMode(true);
    handler.updateUser(user1);
    assertTrue(user1.getDarkMode());
  }

  @Test
  public void deleteUser() {
    handler.deleteUser(user1.getUsername());
    Collection<User> loadedUsers = handler.loadFromFile();
    System.out.println(loadedUsers);
    assertEquals(1, loadedUsers.size());
    assertFalse(loadedUsers.contains(user1));
    assertTrue(loadedUsers.contains(user2));
  }

  @Test
  public void addUser() {
    handler.addUser(user3);
    Collection<User> loadedUsers = handler.loadFromFile();
    assertEquals(3, loadedUsers.size());
    assertTrue(loadedUsers.contains(user1));
    assertTrue(loadedUsers.contains(user2));
    assertTrue(loadedUsers.contains(user3));
  }

  @AfterAll
  public static void after() {
    //empties testdoc
    JsonHandler jsonHandler = new JsonHandler("src/main/resources/quizapp/json/JSONHandlerTest.json");
    jsonHandler.writeToFile(new ArrayList<User>());
  }

}
