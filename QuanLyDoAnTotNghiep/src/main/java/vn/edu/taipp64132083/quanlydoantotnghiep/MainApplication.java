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

            NoiDungThucHienRequest request = new NoiDungThucHienRequest(
                    "Lập trình ứng dụng Java", // Mô tả
                    "http://example.com/mau-nguon", // Link mẫu nguồn
                    1, // Tình trạng (1: Đang thực hiện)
                    "2024-10-5", // Thời gian thực hiện
                    "file_tao_ra.java", // File tạo ra
                    "Ghi chú về công việc", // Ghi chú
                    3 // SinhVienID
            );
//            System.out.println("Dữ liệu gửi lên API: " + new Gson().toJson(l1));

            request = APIClient.post("NoiDungThucHien", request);

//            Khoa k1 = new Khoa("Cơ khí");
//            Khoa khoa = APIClient.post("Khoa", k1);
//            Khoa k1 = new Khoa("");
//            Khoa khoa = APIClient.delete("Khoa/5", k1);
            // Tạo một API client cho đối tượng User
            List<NoiDungThucHien> lop = APIClient.get("NoiDungThucHien", NoiDungThucHien.class);
            // In thông tin các người dùng
            for (NoiDungThucHien khoa : lop) {
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