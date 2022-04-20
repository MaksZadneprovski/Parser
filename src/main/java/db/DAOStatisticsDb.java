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
    public void insertStatisticsFlatAvito(StatisticsFlatAvito statisticsFlatAvito){
        insertRow(
                statisticsFlatAvito.getDollar(),
                statisticsFlatAvito.getAveragePriceMeter(),
                statisticsFlatAvito.getMedianPriceMeter(),
                statisticsFlatAvito.getAveragePriceMeter1(),
                statisticsFlatAvito.getMedianPriceMeter1(),
                statisticsFlatAvito.getAveragePriceMeter2(),
                statisticsFlatAvito.getMedianPriceMeter2(),
                statisticsFlatAvito.getAveragePriceMeter3(),
                statisticsFlatAvito.getMedianPriceMeter3(),
                statisticsFlatAvito.getCity(),
                new Date(statisticsFlatAvito.getDate())
        );
    }
    private void insertRow(int d, long pm, long mp, long pm1, long mp1, long pm2, long mp2, long pm3, long mp3, String c, Date date ){
        try {
            PreparedStatement preparedStatement = PostgreConnection.getFlatAvitoConnection().prepareStatement("INSERT INTO statistics (dollar, averagepricemeter, medianpricemeter, averagepricemeter1, medianpricemeter1, averagepricemeter2, medianpricemeter2, averagepricemeter3, medianpricemeter3, city, date) values(?,?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setLong(1,d);
            preparedStatement.setLong(2,pm);
            preparedStatement.setLong(3,mp);
            preparedStatement.setLong(4,pm1);
            preparedStatement.setLong(5,mp1);
            preparedStatement.setLong(6,pm2);
            preparedStatement.setLong(7,mp2);
            preparedStatement.setLong(8,pm3);
            preparedStatement.setLong(9,mp3);
            preparedStatement.setString(10,c);
            preparedStatement.setDate(11,date);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
