package quizapp.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import quizapp.json.CryptoUtil;
import quizapp.core.User;
import java.io.*;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.util.List;

public class JsonHandler {
  private String path;
  private Writer file;
  private String secretKey = "ssshhhhhhhhhhh!!!!";

  public JsonHandler(String path) {
    this.path = path;
  }

  /**
   * Function writes a hashmap as a JSON object to a JSON file.
   */
  public void writeToFile(List<User> userList) {
    List<User> users = new ArrayList<>(userList);
    CryptoUtil crypto = new CryptoUtil();
    users.stream().forEach(user -> {
      try {
        user.setPassword(crypto.encrypt(user.getPassword(), secretKey));
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    });
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String javaObjectString = gson.toJson(users); // converts to json
    try {

      FileOutputStream fileStream = new FileOutputStream(path);
      file = new OutputStreamWriter(fileStream, "UTF-8");
      file.write(javaObjectString);


    } catch (IOException e) {
      e.printStackTrace();
    } finally {

      try {
        file.flush();
        file.close();
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println(e);
      }
    }
  }

  /**
  * Function reads a JSON file and returns a list of users.
  */
  public List<User> loadFromFile() {
    try {
      CryptoUtil cryptoUtil = new CryptoUtil();
      InputStream inputStream = new FileInputStream(path);
      Reader fileReader = new InputStreamReader(inputStream, "UTF-8");
      List<User> users = new Gson().fromJson(fileReader, new TypeToken<List<User>>() {
      }.getType());
      users 
          .stream()
          .forEach(user -> {
            try {
              user.setPassword(cryptoUtil.decrypt(user.getPassword(), secretKey));
            } catch (Exception e) {
              e.printStackTrace();
            } 
          });
      return users;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }

  /**
   * Loads user that currently is active.
   */
  public User loadActiveUser() {
    final String pathStarter = "../core/src/main/resources/quizapp/json/";
    final String ActiveUserPath = Paths.get(pathStarter + "activeUser.json").toString();
    UsernameHandler usernameHandler = new UsernameHandler(ActiveUserPath);
    return this.loadFromFile().stream()
        .filter(user -> user.getUsername().equals(usernameHandler.loadActiveUser()))
        .findFirst().get();
  }
  
  public User loadUserFromString(String name) {
    return this.loadFromFile().stream()
        .filter(user -> user.getUsername().equals(name))
        .findFirst().get();
  }


  /**
   * Adds User to database.
   */
  public void updateUser(User user) {
    List<User> users = loadFromFile();
    User user2 = users
        .stream()
        .filter(u -> u.getUsername().equals(user.getUsername()))
        .findAny()
        .get();
    users.remove(user2);
    users.add(user);
    writeToFile(users);

  }
  public void addUser(User user) {
    User newUser = new User(user);
    List<User> users = loadFromFile();
    users.add(newUser);
    writeToFile(users);
  }

}
