package db;

import model.FlatAvito;

import java.sql.*;

public class DAOFlatDb {

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

    public static void main(String[] args) {
        try {
            new DAOFlatDb().printAllRows();
//            new FlatDb().insertRow(1122,3333333,2342,545,656,4324,33.2,"city","yo","city", new Date(System.currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insertFlatAvito(FlatAvito flatAvito){
        insertRow(
                flatAvito.getPrice(),
                flatAvito.getPriceDollar(),
                flatAvito.getDollar(),
                flatAvito.getPricePerMeter(),
                flatAvito.getNumberOfRooms(),
                flatAvito.getFloors(),
                flatAvito.getSquare(),
                flatAvito.getCity(),
                flatAvito.getAddress(),
                flatAvito.getHref(),
                new Date(flatAvito.getDate())
        );
    }
    private void insertRow(long p, int pd, int d, int ppm, int nor, int f, double s, String c, String a, String h, Date date ){
        try {
            PreparedStatement preparedStatement = PostgreConnection.getFlatAvitoConnection().prepareStatement("INSERT INTO flat (price, priceDollar, dollar, pricePerMeter, numberOfRooms, floors, square, city, address, href, date) values(?,?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setLong(1,p);
            preparedStatement.setInt(2,pd);
            preparedStatement.setInt(3,d);
            preparedStatement.setInt(4,ppm);
            preparedStatement.setInt(5,nor);
            preparedStatement.setInt(6,f);
            preparedStatement.setDouble(7,s);
            preparedStatement.setString(8,c);
            preparedStatement.setString(9,a);
            preparedStatement.setString(10,h);
            preparedStatement.setDate(11,date);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
