package vn.edu.taipp64132083.quanlydoantotnghiep.dal;

import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.DoAn;

public class AddDoAnHandler implements EventHandler<DoAn> {
  @Override
  public void handle(DoAn doAn) {
    System.out.println("Show DoAn: " + doAn.getTenDoAn());
    APIDataCache.getInstance().addInCache("DoAn", doAn); // Thêm vào cache
  }
}
