package telegram;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Users {
    public static Path path = Path.of("D:\\Java\\Projects\\Parser\\Data\\ListUsers.txt");
    public static List<String> listUsers;
    static {
        try {
            listUsers = new ArrayList<>(Files.readAllLines(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
