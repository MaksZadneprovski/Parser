package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostgreConnection {

    private static Connection flatConnection;
    private static Connection statisticsConnection;

    public static Connection getFlatAvitoConnection(){
        if (flatConnection == null){
            try {
                Class.forName("org.postgresql.Driver");
                String url = "jdbc:postgresql://localhost:5432/flat_avito";
                String login = "postgres";
                String password = "3007";
                flatConnection = DriverManager.getConnection(url, login, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flatConnection;
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
