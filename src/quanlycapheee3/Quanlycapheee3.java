/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package quanlycapheee3;

import BUS.HoaDonBUS;
import BUS.TaiKhoanBUS;
import DAO.HoaDonDAO;
import DTO.CTHoaDonDTO;
import DTO.HoaDonDTO;
import DTO.TaiKhoanDTO;
import DAO.TaiKhoanDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
public class Quanlycapheee3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//       TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
//        System.out.println("Danh sách tất cả tài khoản:");
//        List<TaiKhoanDTO> danhSachTaiKhoan = taiKhoanBUS.getAll();
//        if (danhSachTaiKhoan.isEmpty()) {
//            System.out.println("Không có tài khoản nào trong cơ sở dữ liệu.");
//        } else {
//            for (TaiKhoanDTO tk : danhSachTaiKhoan) {
//                System.out.println(tk);
//            }
//        }
        HoaDonBUS hoaDonBUS = new HoaDonBUS();
        HoaDonDTO hd = new HoaDonDTO(0, LocalDate.now(), 1);
        List<CTHoaDonDTO> chiTiet = new ArrayList<>();
        chiTiet.add(new CTHoaDonDTO(1, 0, 2, 25000)); // 2 sản phẩm, giá 50k
        chiTiet.add(new CTHoaDonDTO(2, 0, 1, 20000)); // 1 sản phẩm, giá 75k
        hd.setChiTietHoaDon(chiTiet);
        System.out.println("Lỗi ở sau mã này");

        HoaDonDAO dao = new HoaDonDAO();
        
        int lastId = dao.getLastIdHD();
        System.out.println("ID hóa đơn lớn nhất: " + lastId);
        System.out.println("Kết quả: " + hd);
        //Nhớ đóng kết nối
        //TaiKhoanDAO.closeConnection();
    }

}
