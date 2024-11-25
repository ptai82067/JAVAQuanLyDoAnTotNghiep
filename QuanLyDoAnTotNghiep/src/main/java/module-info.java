module vn.edu.taipp64132083.quanlydoantotnghiep {
  requires javafx.fxml;
  requires com.jfoenix;
  requires javafx.graphics;
  requires javafx.controls;


  opens vn.edu.taipp64132083.quanlydoantotnghiep to javafx.fxml;
    exports vn.edu.taipp64132083.quanlydoantotnghiep;
  exports vn.edu.taipp64132083.quanlydoantotnghiep.controller;
  opens vn.edu.taipp64132083.quanlydoantotnghiep.controller to javafx.fxml;
}