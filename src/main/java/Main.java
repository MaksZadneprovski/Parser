import parser.ParserAvito;

import java.sql.SQLException;

public class Main  {

    public static void main(String[] args) {
        try {
            ParserAvito.parse();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
