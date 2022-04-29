package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostgreConnection {

    private static Connection DbConnection;

    public static Connection getFlatAvitoConnection(){
        if (DbConnection == null){
            try {
                Class.forName("org.postgresql.Driver");
                String url = "jdbc:postgresql://46.173.219.119:5432/flatdb";
                String login = "root";
                String password = "root";
                DbConnection = DriverManager.getConnection(url, login, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return DbConnection;
    }

    public static PreparedStatement getPreparedStatement(String sql){
        PreparedStatement preparedStatement = null;
        try{
           preparedStatement = getFlatAvitoConnection().prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    public static void closePrepareStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
