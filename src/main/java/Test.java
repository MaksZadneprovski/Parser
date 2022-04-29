import db.StatisticsDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws SQLException {
        StatisticsDAO statisticsDAO = new StatisticsDAO();
        statisticsDAO.printAllRows();
    }
}
