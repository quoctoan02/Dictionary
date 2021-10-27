module org.example {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires java.sql;
  requires org.controlsfx.controls;
  requires javafx.media;
  requires freetts;

  opens org.example to javafx.fxml;
  exports org.example;
}
