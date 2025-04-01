package BUS;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;

import java.util.List;

public class TaiKhoanBUS {
    private final TaiKhoanDAO taiKhoanDAO;

    public TaiKhoanBUS() {
        taiKhoanDAO = new TaiKhoanDAO();
    }

    public List<TaiKhoanDTO> getAll() {
        return taiKhoanDAO.getAll();
    }

    public boolean add(TaiKhoanDTO taiKhoan) {
        // Kiểm tra logic nghiệp vụ trước khi thêm
        if (taiKhoan.getTenTK() == null || taiKhoan.getTenTK().isEmpty()) {
            return false; // Tên tài khoản không được rỗng
        }
        if (taiKhoanDAO.isExist("idTK", taiKhoan.getIdTK())) {
            return false; // Tài khoản đã tồn tại
        }
        return taiKhoanDAO.add(taiKhoan);
    }

    public boolean update(TaiKhoanDTO taiKhoan) {
        // Kiểm tra logic nghiệp vụ trước khi cập nhật
        if (!taiKhoanDAO.isExist(taiKhoan.getIdTK())) {
            return false; // Tài khoản không tồn tại
        }
        return taiKhoanDAO.update(taiKhoan);
    }

    public boolean lock(int idTK) {
        if (!taiKhoanDAO.isExist(idTK)) {
            return false; // Tài khoản không tồn tại
        }
        return taiKhoanDAO.lock(idTK);
    }

    public boolean unlock(int idTK) {
        if (!taiKhoanDAO.isExist(idTK)) {
            return false; // Tài khoản không tồn tại
        }
        return taiKhoanDAO.unlock(idTK);
    }

    public void closeConnection() {
        taiKhoanDAO.closeConnection();
    }
}
