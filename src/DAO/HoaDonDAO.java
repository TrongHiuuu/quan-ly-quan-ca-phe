package DAO;

import DTO.CTHoaDonDTO;
import DTO.HoaDonDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

//Dùng Integer thay vì int là bởi Integer là kiểu đối tượng, còn int là kiểu nguyên thủy
//Các tham số kiểu (T, K, ...) trong generic chỉ chấp nhận các lớp hoặc interface
public class HoaDonDAO extends BaseDAO<HoaDonDTO> {

    @Override
    protected String getTableName() {
        return "hoadon";
    }

    @Override
    protected List<String> getTableColumns() {
        return List.of("idHD", "ngaytao", "idTK");
    }


    @Override
    protected HoaDonDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new HoaDonDTO(
                rs.getInt("idHD"),
                rs.getDate("ngaytao").toLocalDate(),
                rs.getInt("idTK")
        );
    }

    public List<HoaDonDTO> searchDate(Date start, Date end) {
        List<HoaDonDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM hoadon WHERE 1=1 ";
        if (start != null) {
            sql += "AND (ngaytao >= " + start +
                    ") ";
        }
        if (end != null) {
            sql += "AND (ngaytao <= " + end +
                    ") ";
        }
        ResultSet rs = db.getAll(sql);
        try {
            while (rs != null && rs.next()) {
                HoaDonDTO hd = mapResultSetToDTO(rs);
                list.add(hd);
            }
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonDTO> searchIdNV(int idTK) {
        List<HoaDonDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM hoadon WHERE idTK = " + idTK;
        ResultSet rs = db.getAll(sql);
        try {
            while (rs != null && rs.next()) {
                HoaDonDTO hd = mapResultSetToDTO(rs);
                list.add(hd);
            }
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonDTO> searchTongtien(int giaBD, int giaKT) {
        List<HoaDonDTO> list = new ArrayList<>();
        String sql = "SELECT h.* FROM hoadon h " +
                "INNER JOIN CT_hoadon ct ON h.idHD = ct.idHD " +
                "GROUP BY h.idHD, h.ngaytao, h.idTK " +
                "HAVING SUM(ct.soluong * ct.gialucdat) >= " + giaBD + " " +
                "AND SUM(ct.soluong * ct.gialucdat) <= " + giaKT;
        ResultSet rs = db.getAll(sql);
        try {
            while (rs != null && rs.next()) {
                HoaDonDTO hd = mapResultSetToDTO(rs);
                list.add(hd);
            }
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
