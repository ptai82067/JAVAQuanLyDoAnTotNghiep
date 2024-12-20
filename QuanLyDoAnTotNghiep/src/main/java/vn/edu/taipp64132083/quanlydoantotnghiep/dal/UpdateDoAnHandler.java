package vn.edu.taipp64132083.quanlydoantotnghiep.dal;

import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.DoAn;

public class UpdateDoAnHandler implements EventHandler<DoAn> {
  @Override
  public void handle(DoAn doAn) {
    System.out.println("Update DoAn: " + doAn.getTenDoAn());
    APIDataCache.getInstance().updateInCache("DoAn", doAn); // Cập nhật trong cache
  }
}