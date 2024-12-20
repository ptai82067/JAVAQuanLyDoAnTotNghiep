package vn.edu.taipp64132083.quanlydoantotnghiep.dal;

import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.SinhVien;

public class UpdateSinhVienHandler implements EventHandler<SinhVien> {
  @Override
  public void handle(SinhVien sinhVien) {
    System.out.println("Update SinhVien: " + sinhVien.getHoTen());
    APIDataCache.getInstance().updateInCache("SinhVien", sinhVien); // Cập nhật trong cache
  }
}