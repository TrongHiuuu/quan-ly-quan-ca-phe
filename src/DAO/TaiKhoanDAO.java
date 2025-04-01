package DAO;

import DTO.TaiKhoanDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAO extends BaseDAO<TaiKhoanDTO> {

    @Override
    protected String getTableName() {
        return "taikhoan";
    }

        @Override
        protected List<String> getTableColumns() {
            return List.of("idTK", "tenTK", "matkhau", "hoten", "email", "dienthoai", "trangthai", "idNQ");
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
        String sql = "SELECT * FROM "+getTableName()+" WHERE 1=1 ";

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
        StringBuilder sql = new StringBuilder("UPDATE " + getTableName() + " " +
                "SET ");
        for (int i = 0; i < getTableColumns().size(); i++) {
            sql.append(getTableColumns().get(i)).append(" = ?");
            if (i < getTableColumns().size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(" WHERE idTK = ?");
        List<Object> params = List.of(
                taiKhoan.getTenTK(),
                taiKhoan.getMatkhau(),
                taiKhoan.getHoten(),
                taiKhoan.getEmail(),
                taiKhoan.getDienthoai(),
                taiKhoan.getTrangthai(),
                taiKhoan.getIdNQ(),
                taiKhoan.getIdTK() // Giá trị cho WHERE
        );
        return db.execute(sql.toString(), params);
    }

    // Khóa tài khoản (trangthai = false)
    public boolean lock(int idTK) {
        String sql = "UPDATE taikhoan SET trangthai = ? WHERE idTK = ?";
        List<Object> params = new ArrayList<>();
        params.add(false);
        params.add(idTK);
        return db.execute(sql, params);
    }

    // Mở khóa tài khoản (trangthai = true)
    public boolean unlock(int idTK) {
        String sql = "UPDATE taikhoan SET trangthai = ? WHERE idTK = ?";
        List<Object> params = new ArrayList<>();
        params.add(true);
        params.add(idTK);
        return db.execute(sql, params);
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
