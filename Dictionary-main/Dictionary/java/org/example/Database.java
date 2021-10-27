package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {

  Connection conn = null;
  private PreparedStatement pst;
  private Statement st;
  private String command;
  private ResultSet rs;

  public Database() throws SQLException {
    conn = DBConnection.getConnection();
    System.out.println("Success");
  }


  public String searchWord(String word) {
    try {
      command = "SELECT * FROM dictionary WHERE Word =?";
      pst = conn.prepareStatement(command);
      pst.setString(1, word);
      rs = pst.executeQuery();
      if (rs.next()) {
        return rs.getString(2);
      }
    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
    return null;
  }

  public void editWord(String word, String definition) {
    try {
      command = "UPDATE dictionary SET Definition=? WHERE Word =?";
      pst = conn.prepareStatement(command);
      pst.setString(1, definition);
      pst.setString(2, word);
      pst.executeUpdate();
      System.out.println("edit success");
    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
  }

  public void deleteWord(String word) {
    try {
      command = "DELETE FROM dictionary WHERE Word =?";
      pst = conn.prepareStatement(command);
      pst.setString(1, word);
      pst.executeUpdate();
      System.out.println("Delete success");
    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
  }

  public ArrayList<String> wordList() {
    ArrayList<String> wordsList = new ArrayList<String>();
    try {
      command = "SELECT * FROM dictionary";
      st = conn.createStatement();
      rs = st.executeQuery(command);
      while (rs.next()) {
        wordsList.add(rs.getString("Word"));
      }
    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
    return wordsList;
  }

  public void addWord(String word, String definition) {
    command = "INSERT INTO dictionary (Word, Definition) VALUES ('" +
        word + "', '" +
        definition +
        "');";
    try {
      pst = conn.prepareStatement(command);
      pst.executeUpdate();
    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
    System.out.println("successfully to add word!");
  }


}
