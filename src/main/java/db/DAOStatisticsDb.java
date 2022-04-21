package db;

import model.StatisticsFlatAvito;

import java.sql.*;

public class DAOStatisticsDb {

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
            new DAOStatisticsDb().printAllRows();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void insert(StatisticsFlatAvito sFA){
        PreparedStatement ps =  PostgreConnection.getPreparedStatement("INSERT INTO statistics (dollar, averagepricemeter, medianpricemeter, averagepricemeter1, medianpricemeter1, averagepricemeter2, medianpricemeter2, averagepricemeter3, medianpricemeter3, city, date) values(?,?,?,?,?,?,?,?,?,?,?)");
        try {
            ps.setLong(1,sFA.getDollar());
            ps.setLong(2,sFA.getAveragePriceMeter());
            ps.setLong(3,sFA.getMedianPriceMeter());
            ps.setLong(4,sFA.getAveragePriceMeter1());
            ps.setLong(5,sFA.getMedianPriceMeter1());
            ps.setLong(6,sFA.getAveragePriceMeter2());
            ps.setLong(7,sFA.getMedianPriceMeter2());
            ps.setLong(8,sFA.getAveragePriceMeter3());
            ps.setLong(9,sFA.getMedianPriceMeter3());
            ps.setString(10, sFA.getCity());
            ps.setDate(11,new Date(sFA.getDate()));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            PostgreConnection.closePrepareStatement(ps);
        }
    }
}
