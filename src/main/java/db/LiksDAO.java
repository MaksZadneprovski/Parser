package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class LiksDAO {
    public static Map<String, String> getLinks(){
        Map<String, String> links = new HashMap<>();
        try {
            Statement  statement = PostgreConnection.getFlatAvitoConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM links;");
            while (rs.next()) {
                String key = rs.getString(1);
                String value = rs.getString(2);
                links.put(key,value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return links;
    }
}
