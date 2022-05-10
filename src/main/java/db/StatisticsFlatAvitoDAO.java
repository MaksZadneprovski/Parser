package db;

import model.StatisticsFlatAvito;

import java.sql.*;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatisticsFlatAvitoDAO {

    public  void printAllRows() throws SQLException {

            Statement statement = PostgreConnection.getFlatAvitoConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM statistics;");
            while (rs.next()) {
                String str = rs.getLong(1) +
                        ", " + rs.getLong(2)+
                        ", "+rs.getString(3) +
                        ", "+rs.getString(4) +
                        ", "+rs.getString(5) +
                        ", "+rs.getString(6) +
                        ", "+rs.getString(7) +
                        ", "+rs.getString(8) +
                        ", "+rs.getString(9) +
                        ", "+rs.getString(10) +
                        ", "+rs.getString(11) +
                        ", "+rs.getString(12) +
                        ", "+rs.getString(13) +
                        ", " + rs.getString(14);
                System.out.println(str);
            }
            rs.close();
            statement.close();

    }

    public static void main(String[] args) {
        try {
            StatisticsFlatAvitoDAO statisticsFlatAvitoDAO = new StatisticsFlatAvitoDAO();
            statisticsFlatAvitoDAO.deleteAllData();
            statisticsFlatAvitoDAO.printAllRows();
            Statement statement = PostgreConnection.getFlatAvitoConnection().createStatement();
//            statement.execute("INSERT INTO statistics (dollar, averagepricemeter) values(50,44)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public  void insert(StatisticsFlatAvito sFA){
        PreparedStatement ps = null;
        try {
            ps =  PostgreConnection.getPreparedStatement("INSERT INTO statistics (dollar, averagepricemeter, medianpricemeter,averageprice, medianprice, averageprice1, medianprice1, averageprice2, medianprice2, averageprice3, medianprice3, city, date, countflats) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setLong(1,sFA.getDollar());
            ps.setLong(2,sFA.getAveragePriceMeter());
            ps.setLong(3,sFA.getMedianPriceMeter());
            ps.setLong(4,sFA.getAveragePrice());
            ps.setLong(5,sFA.getMedianPrice());
            ps.setLong(6,sFA.getAveragePrice1());
            ps.setLong(7,sFA.getMedianPrice1());
            ps.setLong(8,sFA.getAveragePrice2());
            ps.setLong(9,sFA.getMedianPrice2());
            ps.setLong(10,sFA.getAveragePrice3());
            ps.setLong(11,sFA.getMedianPrice3());
            ps.setString(12, sFA.getCity());
            ps.setObject(13, new Date(sFA.getDate()).toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime());
            ps.setLong(14,sFA.getCountFlats());
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
            st.executeUpdate("TRUNCATE TABLE statistics;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<StatisticsFlatAvito> getAllDataList() {
        List<StatisticsFlatAvito> sFAList = new ArrayList<>();
        try {
            Statement st = PostgreConnection.getFlatAvitoConnection().createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * from statistics;");
            while (resultSet.next()){
                sFAList.add(new StatisticsFlatAvito(
                        resultSet.getInt(2),
                        resultSet.getLong(3),
                        resultSet.getLong(4),
                        resultSet.getLong(5),
                        resultSet.getLong(6),
                        resultSet.getLong(7),
                        resultSet.getLong(8),
                        resultSet.getLong(9),
                        resultSet.getLong(10),
                        resultSet.getLong(11),
                        resultSet.getLong(12),
                        resultSet.getString(13),
                        resultSet.getDate(14).getTime(),
                        resultSet.getLong(15)
                ));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return sFAList;
    }

}
