import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import parser.ParserAvito;
import telegram.BotTG;
import telegram.Users;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.*;

public class Main  {
    private static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {
        BotTG.botTG.botConnect();
        Handler handler = new FileHandler("C:\\Users\\user\\Desktop\\logMain.txt");
        log.addHandler(handler);
        handler.setFormatter(new SimpleFormatter());

        try {
            BotTG.botTG.sendMessage("Started parsing");
            ParserAvito.parse();
            BotTG.botTG.sendMessage("Finished parsing");
        } catch (Exception e) {
            BotTG.botTG.sendMessage("Error parsing");
            log.log(Level.WARNING,"Error",e);
        }
    }
}
