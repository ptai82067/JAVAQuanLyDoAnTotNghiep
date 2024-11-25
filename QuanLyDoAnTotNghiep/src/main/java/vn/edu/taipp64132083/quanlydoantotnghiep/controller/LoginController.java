package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class LoginController {
    @FXML
    private StackPane contentPane;
    @FXML
    private Button home;
    @FXML
    private Button page01;
    @FXML
    private Button page02;

    @FXML
    public void initialize() {
//        // Load nội dung trang Home mặc định
//        loadPage("login-view.fxml");
//
//        // Gắn sự kiện cho các nút
//        home.setOnAction(event -> loadPage("Home.fxml"));
//        page01.setOnAction(event -> loadPage("Page01.fxml"));
//        page02.setOnAction(event -> loadPage("Page02.fxml"));
    }

    private void loadPage(String fxmlFile) {
        try {
            // Load FXML của trang mới
            Node newPage = FXMLLoader.load(getClass().getResource(fxmlFile));
            // Xóa nội dung cũ và thêm trang mới vào contentPane
            contentPane.getChildren().clear();
            contentPane.getChildren().add(newPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
