import com.pengrad.telegrambot.model.Update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseLogs extends DataBase {

    public void addLog(Update update) {
        String insert = "INSERT INTO `translatebot`.`logs` (`update_id`, `message_id`, `chatId`, `firstName`, `lastName`, `username`, `languageCode`, `text`) " +
                "VALUES ('" + update.updateId() + "', '" + update.message().messageId() + "', '" + update.message().from().id() + "', '" +
                update.message().from().firstName() + "', '" + update.message().from().lastName() + "', '" + update.message().from().username() +
                "', '" + update.message().from().languageCode() + "', '" + update.message().text() + "');";
        try {
            PreparedStatement prSt = getDBConnection().prepareStatement(insert);
            prSt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("не получилось добавить в базу addLog");
        }
    }
    public void addLogNull(Update update) {
        String insert = "INSERT INTO `translatebot`.`logs` (`update_id`, `message_id`, `chatId`, `firstName`, `lastName`, `username`, `languageCode`, `text`) " +
                "VALUES ('" + update.updateId() + "', '0', '0', 'null', 'null', 'null', 'null', 'null');";
        try {
            PreparedStatement prSt = getDBConnection().prepareStatement(insert);
            prSt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("не получилось добавить в базу addLog");
        }
    }
}
