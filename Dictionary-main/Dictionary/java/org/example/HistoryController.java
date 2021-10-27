package org.example;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class HistoryController implements Initializable {

  public static ListView<String> HISTORY;
  @FXML
  private ListView<String> historyList;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    HISTORY = historyList;
  }
}
