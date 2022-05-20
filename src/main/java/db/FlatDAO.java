package db;

import model.FlatAvito;
import model.StatisticsFlatAvito;

import java.sql.*;

public class FlatDAO {

    public  void printAllRows() throws SQLException {
            try {
                Statement statement = PostgreConnection.getFlatAvitoConnection().createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM flat;");
                while (rs.next()) {
                    String str = rs.getString(1) +
                            ", " + rs.getString(2)+
                            ", "+rs.getString(3) +
                            ", "+rs.getString(4) +
                            ", "+rs.getString(5) +
                            ", "+rs.getString(6) +
                            ", "+rs.getString(6) +
                            ", "+rs.getString(7) +
                            ", "+rs.getString(8) +
                            ", "+rs.getString(9) +
                            ", "+rs.getString(10) +
                            ", "+rs.getString(11) +
                            ", " + rs.getString(12);
                    System.out.println(str);
                }
                rs.close();
                statement.close();
            } finally {
                PostgreConnection.getFlatAvitoConnection().close();
            }
    }

    public void insert(FlatAvito  fA){
        PreparedStatement ps = null;
        try {
            ps =  PostgreConnection.getPreparedStatement("INSERT INTO flat (price, priceDollar, dollar, pricePerMeter, numberOfRooms, floors, square, city, address, href, date) values(?,?,?,?,?,?,?,?,?,?,?)");
            ps.setLong(1,fA.getPrice());
            ps.setInt(2,fA.getPriceDollar());
            ps.setInt(3,fA.getDollar());
            ps.setInt(4,fA.getPricePerMeter());
            ps.setInt(5,fA.getNumberOfRooms());
            ps.setInt(6,fA.getFloors());
            ps.setDouble(7,fA.getSquare());
            ps.setString(8,fA.getCity());
            ps.setString(9,fA.getAddress());
            ps.setString(10,fA.getHref());
            ps.setDate(11,new Date(fA.getDate()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            PostgreConnection.closePrepareStatement(ps);
        }
    }
    public void deleteAllData(){
        try {
            Statement st = PostgreConnection.getFlatAvitoConnection().createStatement();
            st.executeUpdate("TRUNCATE TABLE flat;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
