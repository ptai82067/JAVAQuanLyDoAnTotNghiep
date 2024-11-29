package vn.edu.taipp64132083.quanlydoantotnghiep;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.*;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.APIClient;

import java.io.IOException;
import java.util.List;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        try {

            PhanCongDoAnRequest request = new PhanCongDoAnRequest(4,3,1,"2024-09-01 08:00:00","Chia sẻ tài liệu lần 2");

//            System.out.println("Dữ liệu gửi lên API: " + new Gson().toJson(l1));

//            request = APIClient.post("PhanCongDoAn", request);

//            Khoa k1 = new Khoa("Cơ khí");
//            Khoa khoa = APIClient.post("Khoa", k1);
//            Khoa k1 = new Khoa("");
//            Khoa khoa = APIClient.delete("Khoa/5", k1);
            // Tạo một API client cho đối tượng User
            List<HoSoBaoVe> lop = APIClient.get("HoSoBaoVe", HoSoBaoVe.class);
            // In thông tin các người dùng
            for (HoSoBaoVe khoa : lop) {
                System.out.println(khoa.toString());
                System.out.println("-----");
            }
//            Lop khoa = APIClient.show("Lop/1", Lop.class);
//            // In thông tin các người dùng
//
//                System.out.println("Mã Lớp: " + khoa.getMaLop());
//                System.out.println("Tên Lớp: " + khoa.getTenLop());
//                System.out.println("Khoa: " + khoa.getKhoa().getTenKhoa());
//                System.out.println("-----");






        } catch (Exception e) {
            e.printStackTrace();
        }
        launch();

    }
}