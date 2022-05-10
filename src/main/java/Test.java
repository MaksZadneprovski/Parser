import db.LiksDAO;
import db.StatisticsFlatAvitoDAO;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        StatisticsFlatAvitoDAO statisticsFlatAvitoDAO = new StatisticsFlatAvitoDAO();
        System.out.println(LiksDAO.getLinks());
        statisticsFlatAvitoDAO.printAllRows();
    }
}
