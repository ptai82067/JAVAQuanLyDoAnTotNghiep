package vn.edu.taipp64132083.quanlydoantotnghiep.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vn.edu.taipp64132083.quanlydoantotnghiep.dal.APIDataCache;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.DoAn;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.SinhVien;

import java.util.ArrayList;
import java.util.List;

public class Service {

  private static final String SINHVIEN_TABLE_NAME = "SinhVien";
  private static final String DOAN_TABLE_NAME = "DoAn";

  // Hàm lấy danh sách SinhVien từ cache hoặc API
  public ObservableList<SinhVien> getSinhVienList() throws Exception {
    // Kiểm tra nếu danh sách SinhVien đã có trong cache
    List<SinhVien> list = (List<SinhVien>)APIDataCache.getInstance().getListFromCache(SINHVIEN_TABLE_NAME, SinhVien.class);
    ObservableList<SinhVien> sinhVienList = FXCollections.observableArrayList(list);

    if (sinhVienList == null || sinhVienList.isEmpty()) {
      // Nếu không có trong cache, gọi API để lấy danh sách SinhVien
      System.out.println("Lấy danh sách SinhVien từ API...");
      List<SinhVien> list1 = APIClient.get("SinhVien", SinhVien.class); // Thực hiện request API
      sinhVienList = FXCollections.observableArrayList(list1);
      // Lưu vào cache sau khi nhận được dữ liệu
      if (sinhVienList != null) {
        for (SinhVien sinhVien : sinhVienList) {
          APIDataCache.getInstance().addInCache(SINHVIEN_TABLE_NAME, sinhVien); // Lưu vào cache
        }
      }
    }
    return sinhVienList;
  }

  // Hàm lấy danh sách DoAn từ cache hoặc API
  public ObservableList<DoAn> getDoAnList() throws Exception {
    // Kiểm tra nếu danh sách DoAn đã có trong cache
    List<DoAn> list = (List<DoAn>)APIDataCache.getInstance().getListFromCache(DOAN_TABLE_NAME, DoAn.class);
    ObservableList<DoAn> doAnList = FXCollections.observableArrayList(list);
    if (doAnList == null || doAnList.isEmpty()) {
      // Nếu không có trong cache, gọi API để lấy danh sách DoAn
      System.out.println("Lấy danh sách DoAn từ API...");
      List<DoAn> list1 = APIClient.get("DoAn", DoAn.class); // Thực hiện request API
      doAnList = FXCollections.observableArrayList(list1);
      // Lưu vào cache sau khi nhận được dữ liệu
      if (doAnList != null) {
        for (DoAn doAn : doAnList) {
          APIDataCache.getInstance().addInCache(DOAN_TABLE_NAME, doAn); // Lưu vào cache
        }
      }
    }
    return doAnList;
  }
}
