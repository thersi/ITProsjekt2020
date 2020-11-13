package quizapp.ui;

import java.nio.file.Paths;
import java.util.List;
import quizapp.core.User;
import quizapp.json.CryptoUtil;
import quizapp.json.JsonHandler;
import quizapp.json.UsernameHandler;

public class DirectUserAccess implements UserAccess {

  private final static String pathStarter = "../core/src/main/resources/quizapp/json/";
  private final String jsonPath = Paths.get(pathStarter + "JSONHandler.json").toString();
  private JsonHandler jsonHandler = new JsonHandler(this.jsonPath);
  private final String activeUserPath = Paths.get(pathStarter + "activeUser.json").toString();
  private UsernameHandler userHandler = new UsernameHandler(this.activeUserPath);
  private String secretKey = "ssshhhhhhhhhhh!!!!";
  private CryptoUtil cryptoUtil = new CryptoUtil();

  public DirectUserAccess() {

  }

  public DirectUserAccess(Boolean test) {
    if (test) {
      jsonHandler = new JsonHandler(
          "/workspace/gr2022/Quiz-app/core/src/main/resources/quizapp/json/JSONHandlerTest.json");
      userHandler = new UsernameHandler(
          "/workspace/gr2022/Quiz-app/core/src/main/resources/quizapp/json/activeUserTest.json");
    }
  }

  @Override
  public User getUser(String name) {
    User user = jsonHandler.loadUserFromString(name);
    user.setPassword(cryptoUtil.decrypt(user.getPassword(), secretKey));
    return user;
  }

  @Override
  public void putUser(User user) {
    User newUser = new User(user);
    newUser.setPassword(cryptoUtil.encrypt(newUser.getPassword(), secretKey));
    jsonHandler.updateUser(user);
  }

  @Override
  public List<User> getUsers() {
    List<User> users =  jsonHandler.loadFromFile();
    users.stream()
        .forEach(user -> user.setPassword(cryptoUtil.decrypt(user.getPassword(), secretKey)));
    return users;
  }

  @Override
  public User getActiveUser() {
    User user = jsonHandler.loadActiveUser();
    user.setPassword(cryptoUtil.decrypt(user.getPassword(), secretKey));
    return user;
  }

  @Override
  public void putActiveUser(String name) {
<<<<<<< HEAD
    userHandler.saveActiveUser(name, this.jsonPath);
=======
    userHandler.saveActiveUser(name,
        "/workspace/gr2022/Quiz-app/core/src/main/resources/quizapp/json/JSONHandler.json");
>>>>>>> origin/issue-61-tests
  }

  @Override
  public void postUser(User user) {
    User newUser = new User(user);
    newUser.setPassword(cryptoUtil.encrypt(newUser.getPassword(), secretKey));
    jsonHandler.addUser(newUser);
  }

  public void deleteUser(String username) {
    jsonHandler.deleteUser(username);
  }
}
