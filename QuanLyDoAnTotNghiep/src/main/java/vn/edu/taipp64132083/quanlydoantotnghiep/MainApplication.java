package vn.edu.taipp64132083.quanlydoantotnghiep;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.Khoa;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.Lop;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.LopRequest;
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

            Khoa k1 = APIClient.show("Khoa/1", Khoa.class);
//
            LopRequest l1 = new LopRequest("64CNTT-CLC1",k1.getMaKhoa());
//            System.out.println("Dữ liệu gửi lên API: " + new Gson().toJson(l1));

            l1 = APIClient.post("Lop", l1);

//            Khoa k1 = new Khoa("Cơ khí");
//            Khoa khoa = APIClient.post("Khoa", k1);
//            Khoa k1 = new Khoa("");
//            Khoa khoa = APIClient.delete("Khoa/5", k1);
            // Tạo một API client cho đối tượng User
            List<Lop> lop = APIClient.get("Lop", Lop.class);
            // In thông tin các người dùng
            for (Lop khoa : lop) {
                System.out.println("Mã Lớp: " + khoa.getMaLop());
                System.out.println("Tên Lớp: " + khoa.getTenLop());
                System.out.println("Khoa: " + khoa.getKhoa().getTenKhoa());
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