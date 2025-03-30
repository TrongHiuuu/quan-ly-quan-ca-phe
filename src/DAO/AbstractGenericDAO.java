package DAO;

import lib.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGenericDAO<T, K> implements GenericDAO<T, K> {
    protected final Database db = new Database();

    @Override
    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        String sql = getGetAllQuery(); //Lấy lệnh SQL từ lớp con
        ResultSet rs = db.getAll(sql);
        try {
            while (rs != null && rs.next()) {
                T entity = mapResultSetToDTO(rs);
                loadRelatedData(entity); // Bổ sung dữ liệu liên quan
                list.add(entity);
                return list;
            }
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return null;
    }

    @Override
    public T findById(K id) {
        String sql = getFindByIDQuery(id);
        ResultSet rs = db.getOne(sql);
        try {
            if (rs != null && rs.next()) {
                T entity = mapResultSetToDTO(rs);
                loadRelatedData(entity); // Bổ sung dữ liệu liên quan
                rs.close();
                return entity;
            }
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isExist(K id) {
        String sql = getIsExistQuery(id);
        ResultSet rs = db.getOne(sql);
        try {
            if (rs != null && rs.next()) {
                boolean exists = rs.getInt(1) > 0;
                rs.close();
                return exists;
            }
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean add(T entity){
        String sql = getAddQuery(entity);
        boolean success = db.execute(sql);
        try {
            if(success) {
                addRelatedData(entity);
                //Thực hiện thêm dữ liệu liên quan
            }
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void closeConnection() {
        db.close();
    }

    // *** Các phương thức trừu tượng mà lớp con phải triển khai
    protected abstract String getGetAllQuery();
    protected abstract String getFindByIDQuery(K id);
    protected abstract String getIsExistQuery(K id);
    protected abstract String getAddQuery(T entity);
    // Helper method để ánh xạ ResultSet sang DTO
    protected abstract T mapResultSetToDTO(ResultSet rs) throws SQLException;
    // ***

    //Hook method:
    //  + Là một method mà lớp con có thể chọn override hoặc là không
    //  + Cho phép bổ sung dữ liệu liên quan trong quá trình lấy dữ liệu
    //  + Điều này hữu ích khi ta không muốn ghi đè lại hoàn toàn các phương thức như getAll chẳng hạn
    protected void loadRelatedData(T entity) {
        //Dùng để bố sung thêm các dữ liệu cần lấy
        //thường dùng để lấy danh sách liên quan đến đối tượng như danh sách CT_hoadon
    }
    protected void addRelatedData(T entity) {
        //Dùng để bổ sung các dữ liệu cần thêm (thường dùng để thêm danh sách
        //thường dùng để thêm danh sách liên quan đến đối tượng như danh sách CT_hoadon
    }

}
