package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

  @FXML
  private StackPane contentPane;
  @FXML
  public void showSinhVienView() {
    loadView("/vn/edu/taipp64132083/quanlydoantotnghiep/SinhVienView.fxml");
  }
  @FXML
  public void showGiangVienView() {
    loadView("/vn/edu/taipp64132083/quanlydoantotnghiep/GiangVienView.fxml");
  }
  @FXML
  public void showDoAnView() {
    loadView("/vn/edu/taipp64132083/quanlydoantotnghiep/DoAnView.fxml");
  }

  @FXML
  public void showPhanCongView(){
    loadView("/vn/edu/taipp64132083/quanlydoantotnghiep/PhanCongView.fxml");
  }

  private void loadView(String resourcePath) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
      Pane pane = loader.load();
      contentPane.getChildren().clear();
      contentPane.getChildren().add(pane);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Không thể tải view: " + resourcePath);
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Service service = new Service();
    try {
      service.getGiangVienList();
      service.getSinhVienList();
      service.getDoAnList();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    loadView("/vn/edu/taipp64132083/quanlydoantotnghiep/SinhVienView.fxml");
  }
}
