package org.example;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Stack;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

public class PrimaryController implements Initializable {

  public static String WORD = null;
  public static String DEFINITION = null;
  public static Database database;
  public static TextField KEY_WORD;

  static {
    try {
      database = new Database();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  ActionEvent event;
  List<String> historySearched = new Stack<>();
  ArrayList<String> wordslist = database.wordList();
  private Set<String> words = new HashSet<>(wordslist);
  private Pane searchView;
  private Pane editView;
  private Pane historyView;
  @FXML
  private TextField enterWord;
  @FXML
  private AnchorPane mainView;

  private AutoCompletionBinding<String> autoComletionBinding;

  public PrimaryController() throws IOException, SQLException {
    searchView = FXMLLoader.load(getClass().getResource("searchScene.fxml"));
    editView = FXMLLoader.load(getClass().getResource("editScene.fxml"));
    historyView = FXMLLoader.load(getClass().getResource("historyScene.fxml"));
  }

  @FXML
  public void searchButton(ActionEvent event) throws IOException {
    mainView.getChildren().clear();
    String word = enterWord.getText();
    String definition = database.searchWord(word);
    WORD = word;
    DEFINITION = definition;
    if (historySearched.contains(word)) {
      historySearched.remove(word);
    }
    historySearched.add(0, word);
    if (definition == null) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Search error");
      alert.setHeaderText("Cannot search this word in dictionary");
      alert.setContentText("Are you want to add this word to the dictionary?");
      ButtonType buttonYes = new ButtonType("Yes", ButtonData.YES);
      ButtonType buttonNo = new ButtonType("No", ButtonData.NO);
      alert.getButtonTypes().setAll(buttonYes, buttonNo);
      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == buttonYes) {
        editButton(event);
      } else {
        enterWord.clear();
      }
    } else {
      SearchController.SEARCH_DEFINITION.setText(definition);
      mainView.getChildren().add(searchView);
    }
  }

  @FXML
  public void editButton(ActionEvent event) {
    mainView.getChildren().clear();
    String word = enterWord.getText();
    String definition = database.searchWord(word);
    WORD = word;
    DEFINITION = definition;
    EditController.EDIT_DEFINITION.setText(definition);
    mainView.getChildren().add(editView);
  }

  @FXML
  public void historyButton(ActionEvent event) {
    mainView.getChildren().clear();
    mainView.getChildren().add(historyView);
    HistoryController.HISTORY.getItems().clear();
    HistoryController.HISTORY.getItems().addAll(historySearched);
    HistoryController.HISTORY.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {
          @Override
          public void changed(ObservableValue<? extends String> observableValue, String s,
              String t1) {
            String currentWord = HistoryController.HISTORY.getSelectionModel().getSelectedItem();
            enterWord.setText(currentWord);
          }
        });
  }

  @FXML
  public void favoriteButton(ActionEvent event) {

  }

  @FXML
  public void click(MouseEvent event) {
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    KEY_WORD = enterWord;
    words.add("ab");
    autoComletionBinding = TextFields.bindAutoCompletion(enterWord, words);
    enterWord.setOnKeyPressed(keyEvent -> {
      switch (keyEvent.getCode()) {
        case ENTER:
          learnWord(enterWord.getText().trim());
          break;
        default:
          break;
      }
    });

  }

  private void learnWord(String text) {
    wordslist.add(text);
    if (autoComletionBinding != null) {
      autoComletionBinding.dispose();
    }
    autoComletionBinding = TextFields.bindAutoCompletion(enterWord, words);
  }
}
