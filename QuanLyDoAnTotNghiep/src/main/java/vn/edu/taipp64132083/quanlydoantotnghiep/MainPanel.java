package vn.edu.taipp64132083.quanlydoantotnghiep;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainPanel extends Application {
    public MainPanel() {
    }

    public void start(Stage stage) throws Exception {
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("MainPanelView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("User Login");
        stage.getIcons().add(new Image(getClass().getResource("/vn/edu/taipp64132083/quanlydoantotnghiep/asset/icon.png").toExternalForm()));

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}




















//package vn.edu.taipp64132083.quanlydoantotnghiep;
//
//import com.google.gson.Gson;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.*;
//import vn.edu.taipp64132083.quanlydoantotnghiep.utils.APIClient;
//
//import java.io.IOException;
//import java.util.List;
//
//public class MainApplication extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("LoginView.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
//
//    }
//
//
//    public static void main(String[] args) {
//        try {
//
//            BinhLuanRequest request = new BinhLuanRequest(1,"hihi haha","2024-1-1");
//
////            System.out.println("Dữ liệu gửi lên API: " + new Gson().toJson(l1));
//
////            request = APIClient.post("BinhLuan", request);
//
////            Khoa k1 = new Khoa("Cơ khí");
////            Khoa khoa = APIClient.post("Khoa", k1);
////            Khoa k1 = new Khoa("");
////            Khoa khoa = APIClient.delete("Khoa/5", k1);
//            // Tạo một API client cho đối tượng User
//            List<RolePermission> lop = APIClient.get("RolePermissions", RolePermission.class);
//            // In thông tin các người dùng
//            for (RolePermission khoa : lop) {
//                System.out.println(khoa.toString());
//                System.out.println("-----");
//            }
////            Lop khoa = APIClient.show("Lop/1", Lop.class);
////            // In thông tin các người dùng
////
////                System.out.println("Mã Lớp: " + khoa.getMaLop());
////                System.out.println("Tên Lớp: " + khoa.getTenLop());
////                System.out.println("Khoa: " + khoa.getKhoa().getTenKhoa());
////                System.out.println("-----");
//
//
//
//
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        launch();
//
//    }
//}