package vn.edu.taipp64132083.quanlydoantotnghiep.dal;

import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.SinhVien;

public class AddSinhVienHandler implements EventHandler<SinhVien> {
  @Override
  public void handle(SinhVien sinhVien) {
    System.out.println("Show SinhVien: " + sinhVien.getHoTen());
    APIDataCache.getInstance().addInCache("SinhVien", sinhVien); // Thêm vào cache
  }

}
