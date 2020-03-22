package daos;

import org.postgresql.Driver;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DaoClass implements Dao<DtoClass>{
    static Logger logger = Logger.getLogger(DaoClass.class.getName());


    public static final String databaseUrl = "jdbc:postgresql://localhost:5432/karbase";
    private static final String INSERT = "INSERT INTO cars" + "(id, make, model, year, color)" + "values(?,?,?,?,?)";

    public static Connection getConnection(){
        try {
            DriverManager.registerDriver(new Driver());
            logger.log(Level.INFO, "Connected!");
            return DriverManager.getConnection(databaseUrl);
        }catch (SQLException e) {
            throw new RuntimeException("Can't Connect", e);
        }
    }

    public DtoClass getCar(ResultSet rs) throws SQLException {
        DtoClass car = new DtoClass();
        car.setId(rs.getInt("id"));
        car.setMake(rs.getString("make"));
        car.setModel(rs.getString("model"));
        car.setYear(rs.getInt("year"));
        car.setColor(rs.getString("color"));
        return car;
    }



    public DtoClass findById(int id) {
        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM cars WHERE  id = " + id + ";");
            if(rs.next()){
                return getCar(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DtoClass> findAll() {
        Stream.Builder<DtoClass> builder = Stream.builder();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM cars;");
            while (rs.next()){
                builder.accept(getCar(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return builder.build().collect(Collectors.toList());
    }

    public boolean update(DtoClass dto) {
        String sqlQuery = "UPDATE cars SET id=?, make=?, model=?, year=?, color=? WHERE id=" + dto.getId() + ";";
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sqlQuery);
            pstmt.setInt(1,dto.getId());
            pstmt.setString(2,dto.getMake());
            pstmt.setString(3,dto.getModel());
            pstmt.setInt(4,dto.getYear());
            pstmt.setString(5,dto.getColor());
            int i = pstmt.executeUpdate();
            if(i == 1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean create(DtoClass dto) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(INSERT);
            pstmt.setInt(1,dto.getId());
            pstmt.setString(2,dto.getMake());
            pstmt.setString(3,dto.getModel());
            pstmt.setInt(4,dto.getYear());
            pstmt.setString(5,dto.getColor());
            int i = pstmt.executeUpdate();
            if(i == 1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void delete(int id) {
        String sqlQuery = "DELETE FROM cars WHERE id = " + id + ";";
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sqlQuery);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
