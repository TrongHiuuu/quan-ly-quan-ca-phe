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
public class HoaDonDAO extends AbstractGenericDAO<HoaDonDTO, Integer> {

    @Override
    protected String getGetAllQuery() {
        return "SELECT * FROM hoadon";
    }

    @Override
    protected String getFindByIDQuery(Integer idDH) {
        return "SELECT * FROM hoadon WHERE idHD = " + idDH;
    }

    @Override
    protected String getIsExistQuery(Integer idDH) {
        return "SELECT COUNT(*) FROM hoadon WHERE idHD = " + idDH;
    }

    @Override
    protected String getAddQuery(HoaDonDTO hd) {
        return "INSERT " +
                "INTO hoadon (ngaytao, idTK) " +
                "VALUES (" +
                " '" + hd.getNgaytao() + "', " +
                hd.getIdTK() +
                ")";
    }

    @Override
    protected HoaDonDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new HoaDonDTO(
                rs.getInt("idHD"),
                rs.getDate("ngaytao").toLocalDate(),
                rs.getInt("idTK")
        );
    }

    @Override
    protected void loadRelatedData(HoaDonDTO hd) {
        hd.setChiTietHoaDon(getCTHoaDonByID(hd.getIdHD()));
    }

    @Override
    protected void addRelatedData(HoaDonDTO hd) {
        // Thêm chi tiết hóa đơn vào bảng CT_hoadon
        List<CTHoaDonDTO> chiTietHoaDon = hd.getCTHoaDon();
        if (chiTietHoaDon != null && !chiTietHoaDon.isEmpty()) {
            for (CTHoaDonDTO ct : chiTietHoaDon) {
                String sql = "INSERT INTO CT_hoadon (idSP, idHD, soluong, gialucdat) " +
                        "VALUES (" +
                        ct.getIdSP() + ", " +
                        getLastIdHD() + ", " +
                        ct.getSoluong() + ", " +
                        ct.getGialucdat() +
                        ")";
                boolean success = db.execute(sql);
                if (!success) {
                    System.err.println("Thêm chi tiết hóa đơn thất bại cho idHD: " + ct.getIdHD());
                }
            }
        }
    }

    public int getLastIdHD(){
        String sql = "SELECT MAX(idHD) as lastIdHD FROM hoadon";
        ResultSet rs = db.getOne(sql);
        try {
            if (rs != null && rs.next()) {
                int lastId = rs.getInt("lastIdHD"); // Lấy giá trị từ cột alias "lastIdHD"
                rs.close(); // Đóng ResultSet sau khi sử dụng
                return lastId; // Trả về idHD lớn nhất
            }
            if (rs != null) {
                rs.close();
            }
            return 0; // Trả về 0 nếu bảng rỗng hoặc không có dữ liệu
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Trả về -1 nếu có lỗi
        }
    }

    public List<HoaDonDTO> searchDate(Date start, Date end) {
        List<HoaDonDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM hoadon WHERE 1=1 ";
       if(start != null) {
            sql += "AND (ngaytao >= "+ start +
                    ") ";
        }
       if(end != null){
            sql += "AND (ngaytao <= "+ end +
                    ") ";
        }
        ResultSet rs = db.getAll(sql);
        try {
            while (rs != null && rs.next()) {
                HoaDonDTO hd = mapResultSetToDTO(rs);
                hd.setChiTietHoaDon(getCTHoaDonByID(hd.getIdHD()));
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
                hd.setChiTietHoaDon(getCTHoaDonByID(hd.getIdHD()));
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
                "AND SUM(ct.soluong * ct.gialucdat) <= "+ giaKT;
        ResultSet rs = db.getAll(sql);
        try {
            while (rs != null && rs.next()) {
                HoaDonDTO hd = mapResultSetToDTO(rs);
                hd.setChiTietHoaDon(getCTHoaDonByID(hd.getIdHD()));
                list.add(hd);
            }
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<CTHoaDonDTO> getCTHoaDonByID(int idHD) {
        List<CTHoaDonDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM CT_hoadon WHERE idHD = " + idHD;
        ResultSet rs = db.getAll(sql);
        try {
            while (rs != null && rs.next()) {
                list.add(new CTHoaDonDTO(
                        rs.getInt("idSP"),
                        rs.getInt("idHD"),
                        rs.getInt("soluong"),
                        rs.getInt("gialucdat")
                ));
            }
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
