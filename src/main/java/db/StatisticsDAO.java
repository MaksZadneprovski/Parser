package db;

import model.StatisticsFlatAvito;

import java.sql.*;

public class StatisticsDAO {

    public  void printAllRows() throws SQLException {
        try {
            Statement statement = PostgreConnection.getFlatAvitoConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM statistics;");
            while (rs.next()) {
                String str = rs.getLong(1) +
                        ", " + rs.getLong(2)+
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

    public static void main(String[] args) {
        try {
            StatisticsDAO statisticsDAO = new StatisticsDAO();
//            statisticsDAO.deleteAllData();
            statisticsDAO.printAllRows();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void insert(StatisticsFlatAvito sFA){
        PreparedStatement ps = null;
        try {
            ps =  PostgreConnection.getPreparedStatement("INSERT INTO statistics (dollar, averagepricemeter, medianpricemeter, averageprice1, medianprice1, averageprice2, medianprice2, averageprice3, medianprice3, city, date) values(?,?,?,?,?,?,?,?,?,?,?)");
            ps.setLong(1,sFA.getDollar());
            ps.setLong(2,sFA.getAveragePriceMeter());
            ps.setLong(3,sFA.getMedianPriceMeter());
            ps.setLong(4,sFA.getAveragePrice1());
            ps.setLong(5,sFA.getMedianPrice1());
            ps.setLong(6,sFA.getAveragePrice2());
            ps.setLong(7,sFA.getMedianPrice2());
            ps.setLong(8,sFA.getAveragePrice3());
            ps.setLong(9,sFA.getMedianPrice3());
            ps.setString(10, sFA.getCity());
            ps.setDate(11,new Date(sFA.getDate()));
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
}
