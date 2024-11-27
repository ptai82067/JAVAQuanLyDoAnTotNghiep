package vn.edu.taipp64132083.quanlydoantotnghiep.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.IOException;

public class SceneSwitcher {
  // Hàm static để chuyển scene
  public static void switchScene(ActionEvent event, String fxmlFile) {
    try {
      // Load tệp FXML
      FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlFile));
      Parent root = loader.load();

      // Lấy stage từ sự kiện
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

      // Tạo scene mới và thiết lập cho stage
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Cannot load the FXML file: " + fxmlFile, e);
    }
  }
}
