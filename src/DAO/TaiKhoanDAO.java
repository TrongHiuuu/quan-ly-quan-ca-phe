package DAO;

import DTO.HoaDonDTO;
import DTO.TaiKhoanDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAO extends AbstractGenericDAO<TaiKhoanDTO, Integer> {

    @Override
    protected String getGetAllQuery() {
        return "SELECT * FROM taikhoan";
    }

    @Override
    protected String getFindByIDQuery(Integer idTK) {
        return "SELECT * FROM taikhoan WHERE idTK = " + idTK;
    }

    @Override
    protected String getIsExistQuery(Integer idTK) {
        return "SELECT COUNT(*) FROM taikhoan WHERE idTK = " + idTK;
    }

    @Override
    protected String getAddQuery(TaiKhoanDTO taiKhoan) {
        return "INSERT " +
                "INTO taikhoan (tenTK, matkhau, hoten, email, dienthoai, trangthai, idNQ) " +
                "VALUES (" +
                " '" + taiKhoan.getTenTK() + "', " +
                " '" +taiKhoan.getMatkhau() + "', " +
                " '" +taiKhoan.getHoten() + "', " +
                " '" +taiKhoan.getEmail() + "', " +
                " '" +taiKhoan.getDienthoai() + "', " +
                (taiKhoan.getTrangthai() ? 1 : 0) + ", " +
                taiKhoan.getIdNQ() +
                ")";
    }

    @Override
    protected TaiKhoanDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new TaiKhoanDTO(
                rs.getInt("idTK"),
                rs.getString("tenTK"),
                rs.getString("matkhau"),
                rs.getString("hoten"),
                rs.getString("email"),
                rs.getString("dienthoai"),
                rs.getBoolean("trangthai"),
                rs.getString("idNQ")
        );
    }

    // Tìm kiếm tài khoản theo từ khóa
    public List<TaiKhoanDTO> search(String kyw) {
        List<TaiKhoanDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM taikhoan WHERE 1";
        /*
         * Lý do không đơn thuần dùng != "" ở đây là vì trong java:
         *   + Phép != là toán tử kiểm tra tham chiếu, nó kiểm tra xem 2 biến
         *       có cùng địa chỉ bộ nhớ hay không, chứ không kiểm tra nội dung chuỗi
         *   + Phương thức equals() hoặc isEmpty() là phương thức kiểm tra nội dung
         *       nó kiểm tra nội dung 2 chuỗi, kể cả 2 đối tượng là 2 đối tượng riêng biệt trong bộ nhớ
         * ***Trong Java, các chuỗi literal (được khai báo trong chương trình) giống nhau được lưu trong String Pool
         *       và tái sử dụng cùng một đối tượng trong bộ nhớ. String kyw là string
         *       được nhập từ input người dùng, điều này có thể dẫn đến kyw và chuỗi "" được dùng
         *       trong toán tử != trả về true do khác String Pool, từ đó khiến sql luôn thêm điều kiện phía sau
         * ***
         */
        if (!kyw.isEmpty())
            sql += "AND (tenTK LIKE '%" + kyw + "%' " +
                    "OR hoten LIKE '%" + kyw + "%'" +
                    "OR email LIKE '%" + kyw + "%'" +
                    "OR dienthoai LIKE '%" + kyw + "%'" +
                    ") ";
        ResultSet rs = db.getAll(sql);
        try {
            while (rs != null && rs.next()) {
                TaiKhoanDTO tk = mapResultSetToDTO(rs);
                list.add(tk);
            }
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Cập nhật tài khoản
    public boolean update(TaiKhoanDTO taiKhoan) {
        String sql = "UPDATE taikhoan " +
                "SET " +
                "tenTK = '" + taiKhoan.getTenTK() + "', " +
                "matkhau = '" + taiKhoan.getMatkhau() + "', " +
                "hoten = '" + taiKhoan.getHoten() + "', " +
                "email = '" + taiKhoan.getEmail() + "', " +
                "dienthoai = '" + taiKhoan.getDienthoai() + "', " +
                "trangthai = " + (taiKhoan.getTrangthai() ? 1 : 0) + ", " +
                "idNQ = " + taiKhoan.getIdNQ() + " " +
                "WHERE idTK = " + taiKhoan.getIdTK();
        return db.execute(sql);
    }

    // Khóa tài khoản (trangthai = false)
    public boolean lock(int idTK) {
        String sql = "UPDATE taikhoan SET trangthai = 0 WHERE idTK = " + idTK;
        return db.execute(sql);
    }

    // Mở khóa tài khoản (trangthai = true)
    public boolean unlock(int idTK) {
        String sql = "UPDATE taikhoan SET trangthai = 1 WHERE idTK = " + idTK;
        return db.execute(sql);
    }

    public TaiKhoanDTO login(String tenTK, String matKhau) {
        String sql = "SELECT * FROM taikhoan WHERE tenTK = '" + tenTK + "' AND matkhau = '" + matKhau + "'";
        ResultSet rs = db.getOne(sql);
        try {
            if (rs != null && rs.next()) {
                return mapResultSetToDTO(rs);
            }
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Trả về null nếu không tìm thấy
    }
}
