package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPanelController implements Initializable {

    @FXML
    private BorderPane borderPane;

    private List<Button> menus;  // Khai báo danh sách các nút
    @FXML
    private VBox menuContainer;  // VBox chứa các nút

    @FXML
    private Button page01,page02,page03;  // Nút Page 01
    @FXML
    private Button addPurchase;
    @FXML
    private Button purchaseDetail;
    @FXML
    private AreaChart<?, ?> chartPurchase;
    @FXML
    private AreaChart<?, ?> chartSale;
    @FXML
    private LineChart<?, ?> chartReceipt;

    // Phương thức khởi tạo, bạn có thể thêm khởi tạo cho 'menus' ở đây
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Khởi tạo menus hoặc làm bất cứ thao tác cần thiết nào ở đây
        // Giả sử bạn gán nút cho menus
    }

    // Phương thức thay đổi màu nền các nút khi nhấn
    private void changeButtonBackground(ActionEvent e) {
        Button clickedButton = (Button) e.getSource();

        // Kiểm tra nếu menus không phải là null và có phần tử
        if (menus != null) {
            for (Button otherButton : menus) {
                if (clickedButton == otherButton) {
                    clickedButton.setStyle("-fx-text-fill:#f0f0f0;-fx-background-color:#2b2a26;");
                } else {
                    otherButton.setStyle("-fx-text-fill:#f0f0f0;-fx-background-color:#404040;");
                }
            }
        }
    }

    // Phương thức làm trống BorderPane
    @FXML
    private void clear() {
        borderPane.setCenter(null);
    }

    // Phương thức load FXML vào BorderPane
    @FXML
    private void loadFXML(String fileName) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/vn/edu/taipp64132083/quanlydoantotnghiep/" + fileName + ".fxml"));
            borderPane.setCenter(parent);  // Đặt nội dung vào vị trí center của BorderPane
        } catch (IOException ex) {
            Logger.getLogger(MainPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Phương thức đóng cửa sổ và chuyển sang màn đăng nhập
    @FXML
    private void close() throws IOException {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("User Login");
        stage.show();
    }

    // Phương thức load các trang khác
    @FXML
    private void loadPage01View(ActionEvent e) {
//        loadFXML("Page01View");
        page01.setVisible(false);
        page02.setTranslateY(-30); // Dịch Page 02 lên
        page03.setTranslateY(-30); // Dịch Page 03 lên
        loadFXML("Page01View");
        changeButtonBackground(e);
    }

    @FXML
    private void loadPage02View(ActionEvent e) {
        loadFXML("Page02View");
        changeButtonBackground(e);
    }

    @FXML
    private void loadPage03View(ActionEvent e) {
        loadFXML("Page03View");
        changeButtonBackground(e);
    }

    @FXML
    private void loadPage04View(ActionEvent e) {
        loadFXML("Page04View");
        changeButtonBackground(e);
    }

    @FXML
    private void loadPage05View(ActionEvent e) {
        loadFXML("Page05View");
        changeButtonBackground(e);
    }

    @FXML
    private void loadPage06View(ActionEvent e) {
        loadFXML("Page06View");
        changeButtonBackground(e);
    }

    @FXML
    private void loadPage07View(ActionEvent e) {
        loadFXML("Page07View");
        changeButtonBackground(e);
    }

    @FXML
    private void loadPage08View(ActionEvent e) {
        loadFXML("Page08View");
        changeButtonBackground(e);
    }

    @FXML
    private void loadPage09View(ActionEvent e) {
        loadFXML("Page09View");
        changeButtonBackground(e);
    }

    @FXML
    private void loadPage10View(ActionEvent e) {
        loadFXML("Page10View");
        changeButtonBackground(e);
    }

    @FXML
    private void loadHomeView(ActionEvent e) {
        loadFXML("HomeView");
        changeButtonBackground(e);
    }
}
