package quizapp.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Question {

  private String question;
  private int correctAlternative;
  private List<String> alternatives = new ArrayList<>();

  /**
   * This class is used for representing a question, with alternatives and the
   * correct answer.
   */
  public Question(String question, String alternative1, String alternative2, String alternative3, 
      String alternative4, int correctAlternative) {
    this.question = question;
    alternatives = Arrays.asList(alternative1, alternative2, alternative3, alternative4);
    if (correctAlternative <= 3 && correctAlternative >= 0) {
      this.correctAlternative = correctAlternative;
    } else {
      throw new IllegalArgumentException(
          "The correct alternative must be a number between 0 and 3.");
    }
  }

  /**
   * Returns quiestion as string.
   */
  public String getQuestion() {
    return question;
  }

  /**
   * Returns the alternative.
   */
  public String getAlternative(int num) {
    if (!(num >= 0 && num <= 3)) {
      throw new IllegalArgumentException("num must be between 0 and 3");
    }
    return alternatives.get(num);
  }

  /**
   * returns Correct Alternative.
   */
  public int getCorrect_alternative() {
    return correctAlternative;
  }

}