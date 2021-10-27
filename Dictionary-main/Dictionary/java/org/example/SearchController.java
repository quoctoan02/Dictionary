package org.example;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class SearchController implements Initializable {

  public static TextArea SEARCH_DEFINITION;
  @FXML
  private TextArea definitionText;

  @FXML
  public void speakWord(ActionEvent event) throws Exception {
    Sound sound = new Sound();
    sound.Speak(PrimaryController.WORD);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    SEARCH_DEFINITION = definitionText;
  }
}
