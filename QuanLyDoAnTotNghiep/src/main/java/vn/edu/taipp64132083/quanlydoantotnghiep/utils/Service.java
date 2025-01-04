package vn.edu.taipp64132083.quanlydoantotnghiep.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vn.edu.taipp64132083.quanlydoantotnghiep.dal.APIDataCache;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.*;

import java.util.ArrayList;
import java.util.List;

public class Service {

  public static final String SINHVIEN_TABLE_NAME = "SinhVien";
  public static final String DOAN_TABLE_NAME = "DoAn";
  public static final String GIANGVIEN_TABLE_NAME = "GiangVien";
  public static final String PHANCONG_TABLE_NAME = "PhanCongDoAn";
  public static final String BoMon_TABLE_NAME = "BoMon";

  // Hàm lấy danh sách SinhVien từ cache hoặc API
  public ObservableList<SinhVien> getSinhVienList() throws Exception {
    // Kiểm tra nếu danh sách SinhVien đã có trong cache
    List<SinhVien> list = (List<SinhVien>)APIDataCache.getInstance()
            .getListFromCache(SINHVIEN_TABLE_NAME, SinhVien.class);
    ObservableList<SinhVien> sinhVienList = FXCollections.observableArrayList(list);

    if (sinhVienList == null || sinhVienList.isEmpty()) {
      // Nếu không có trong cache, gọi API để lấy danh sách SinhVien
      System.out.println("Lấy danh sách SinhVien từ API...");
      // Thực hiện request API
      List<SinhVien> list1 = APIClient.get("SinhVien", SinhVien.class);
      sinhVienList = FXCollections.observableArrayList(list1);
      // Lưu vào cache sau khi nhận được dữ liệu
      if (sinhVienList != null) {
        for (SinhVien sinhVien : sinhVienList) {
          // Lưu vào cache
          APIDataCache.getInstance().addInCache(SINHVIEN_TABLE_NAME, sinhVien);
        }
      }
    }
    return sinhVienList;
  }

  // Hàm lấy danh sách DoAn từ cache hoặc API
  public static ObservableList<DoAn> getDoAnList() throws Exception {
    // Kiểm tra nếu danh sách DoAn đã có trong cache
    List<DoAn> list = (List<DoAn>)APIDataCache.getInstance()
            .getListFromCache(DOAN_TABLE_NAME, DoAn.class);
    ObservableList<DoAn> doAnList = FXCollections.observableArrayList(list);
    if (doAnList == null || doAnList.isEmpty()) {
      // Nếu không có trong cache, gọi API để lấy danh sách DoAn
      System.out.println("Lấy danh sách DoAn từ API...");
      List<DoAn> list1 = APIClient.get("DoAn", DoAn.class);
      doAnList = FXCollections.observableArrayList(list1);
      // Lưu vào cache sau khi nhận được dữ liệu
      if (doAnList != null) {
        for (DoAn doAn : doAnList) {
          APIDataCache.getInstance().addInCache(DOAN_TABLE_NAME, doAn);
        }
      }
    }
    return doAnList;
  }
  public static DoAn refeshDoAn() throws Exception {
    List<DoAn> list1 = APIClient.get("DoAn", DoAn.class);
    ObservableList<DoAn> doAnList = FXCollections.observableArrayList(list1);
    // Lưu vào cache sau khi nhận được dữ liệu
    if (doAnList != null) {

        APIDataCache.getInstance().addInCache(DOAN_TABLE_NAME, doAnList.getLast());
      return doAnList.getLast();
    }
    return null;
  }
  public static SinhVien refeshSinhVien() throws Exception {
    List<SinhVien> list1 = APIClient.get("SinhVien", SinhVien.class);
    ObservableList<SinhVien> sinhViens = FXCollections.observableArrayList(list1);
    // Lưu vào cache sau khi nhận được dữ liệu
    if (sinhViens != null) {

      APIDataCache.getInstance().addInCache(SINHVIEN_TABLE_NAME, sinhViens.getLast());
      return sinhViens.getLast();
    }
    return null;
  }
  public static GiangVien refeshGiangVien() throws Exception {
    List<GiangVien> list1 = APIClient.get("GiangVien", GiangVien.class);
    ObservableList<GiangVien> giangViens = FXCollections.observableArrayList(list1);
    // Lưu vào cache sau khi nhận được dữ liệu
    if (giangViens != null) {

      APIDataCache.getInstance().addInCache(GIANGVIEN_TABLE_NAME, giangViens.getLast());
      return giangViens.getLast();
    }
    return null;
  }
  public static ObservableList<GiangVien> getGiangVienList() throws Exception {
    // Kiểm tra nếu danh sách DoAn đã có trong cache
    List<GiangVien> list = (List<GiangVien>)APIDataCache.getInstance()
            .getListFromCache(GIANGVIEN_TABLE_NAME, GiangVien.class);
    ObservableList<GiangVien> giangVienList = FXCollections.observableArrayList(list);
    if (giangVienList == null || giangVienList.isEmpty()) {
      // Nếu không có trong cache, gọi API để lấy danh sách DoAn
      System.out.println("Lấy danh sách GiangVien từ API...");
      List<GiangVien> list1 = APIClient.get("GiangVien", GiangVien.class);
      giangVienList = FXCollections.observableArrayList(list1);
      // Lưu vào cache sau khi nhận được dữ liệu
      if (giangVienList != null) {
        for (GiangVien giangVien : giangVienList) {
          APIDataCache.getInstance().addInCache(GIANGVIEN_TABLE_NAME, giangVien);
        }
      }
    }
    return giangVienList;
  }

  public static ObservableList<PhanCongDoAn> getPhanCongList() throws Exception {
    // Kiểm tra nếu danh sách DoAn đã có trong cache
    List<PhanCongDoAn> list = (List<PhanCongDoAn>)APIDataCache.getInstance()
            .getListFromCache(PHANCONG_TABLE_NAME, PhanCongDoAn.class);
    ObservableList<PhanCongDoAn> phanCongDoAns = FXCollections.observableArrayList(list);
    if (phanCongDoAns == null || phanCongDoAns.isEmpty()) {
      // Nếu không có trong cache, gọi API để lấy danh sách DoAn
      System.out.println("Lấy danh sách PhanCong từ API...");
      List<PhanCongDoAn> list1 = APIClient.get(PHANCONG_TABLE_NAME, PhanCongDoAn.class);
      phanCongDoAns = FXCollections.observableArrayList(list1);
      // Lưu vào cache sau khi nhận được dữ liệu
      if (phanCongDoAns != null) {
        for (PhanCongDoAn phanCongDoAn : phanCongDoAns) {
          APIDataCache.getInstance().addInCache(PHANCONG_TABLE_NAME, phanCongDoAn);
        }
      }
    }
    return phanCongDoAns;
  }

  public static ObservableList<BoMon> getBoMon() throws Exception {
    // Kiểm tra nếu danh sách DoAn đã có trong cache
    List<BoMon> list = (List<BoMon>)APIDataCache.getInstance()
            .getListFromCache(BoMon_TABLE_NAME, BoMon.class);
    ObservableList<BoMon> boMons = FXCollections.observableArrayList(list);
    if (boMons == null || boMons.isEmpty()) {
      // Nếu không có trong cache, gọi API để lấy danh sách DoAn
      System.out.println("Lấy danh sách Bộ Môn từ API...");
      List<BoMon> list1 = APIClient.get(BoMon_TABLE_NAME, BoMon.class);
      boMons = FXCollections.observableArrayList(list1);
      // Lưu vào cache sau khi nhận được dữ liệu
      if (boMons != null) {
        for (BoMon boMon : boMons) {
          APIDataCache.getInstance().addInCache(BoMon_TABLE_NAME, boMon);
        }
      }
    }
    return boMons;
  }
}
