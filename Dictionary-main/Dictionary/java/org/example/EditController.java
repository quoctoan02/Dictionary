package org.example;

import static org.example.PrimaryController.DEFINITION;
import static org.example.PrimaryController.KEY_WORD;
import static org.example.PrimaryController.WORD;
import static org.example.PrimaryController.database;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class EditController implements Initializable {

  public static TextArea EDIT_DEFINITION;
  private Stage stage;
  private Scene scene;
  private Parent root;
  @FXML
  private TextArea editDefinitionText;

  @FXML
  public void save(ActionEvent event) {
    String definition = EDIT_DEFINITION.getText();
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    if (DEFINITION != null) {
      database.editWord(WORD, definition);
      alert.setTitle("Edit word");
      alert.setHeaderText(null);
      alert.setContentText("Save word successfully!");
      alert.showAndWait();
      DEFINITION = definition;
    } else {
      database.addWord(WORD, definition);
      alert.setTitle("Add word");
      alert.setHeaderText(null);
      alert.setContentText("Add word successfully!");
      alert.showAndWait();
      DEFINITION = definition;
    }
  }

  @FXML
  public void cancel(ActionEvent event) throws IOException {
    KEY_WORD.clear();
    root = FXMLLoader.load(getClass().getResource("primary.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setTitle("Eng-Vie Dictionary");
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void delete(ActionEvent event) throws IOException {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Delete Word");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to delete this word?");
    ButtonType buttonYes = new ButtonType("Yes", ButtonData.YES);
    ButtonType buttonNo = new ButtonType("No", ButtonData.NO);
    alert.getButtonTypes().setAll(buttonYes, buttonNo);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == buttonYes) {
      Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION);
      PrimaryController.database.deleteWord(WORD);
      deleteAlert.setTitle("Delete Word");
      deleteAlert.setHeaderText(null);
      deleteAlert.setContentText("Delete word successfully!");
      deleteAlert.showAndWait();
      cancel(event);
    }
  }

  @FXML
  public void speak(ActionEvent event) throws Exception {
    Sound sound = new Sound();
    sound.Speak(PrimaryController.WORD);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    EDIT_DEFINITION = editDefinitionText;
  }
}
