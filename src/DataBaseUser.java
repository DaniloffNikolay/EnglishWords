import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataBaseUser extends DataBase {

    public void singUpUser(long chatId, String firstName, String lastName, String userName, String lastText) {
        try {
            String insert = "INSERT INTO `translatebot`.`users` (`chatId`, `firstName`, `lastName`, `userName`, `lastText`) " +
                    "VALUES('" + chatId + "', '" + firstName + "', '" + lastName + "', '" + userName + "', '" + lastText + "');";
            PreparedStatement prSt = getDBConnection().prepareStatement(insert);
            prSt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("не получилось добавить в базу");
        }
    }
    public void setLastText(long chatId, String lastText) {
        try {
            String update = "UPDATE `translatebot`.`users` SET `lastText` = '" + lastText + "' WHERE (`chatId` = '" + chatId + "');";
            PreparedStatement prStUpdate = getDBConnection().prepareStatement(update);
            prStUpdate.executeUpdate();
        } catch (SQLException e) {
            System.out.println("не получилось обновить базу данных");
        }
    }
    public String getLastText(long chatId) {
        String select = "SELECT users.lastText from translatebot.users WHERE chatId = " + chatId + ";";
        try {
            Statement statement = getDBConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while(resultSet.next()){
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            System.out.println("не удалось установить соединение с базой данных");
        }
        return null;
    }
    public boolean findUser(long chatId) {
        String select = "SELECT users.chatId from translatebot.users WHERE chatId = " + chatId + ";";
        try {
            Statement statement = getDBConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            System.out.println("не удалось установить соединение с базой данных");
        }
        return false;
    }
    public void setEnglishWord(long chatId, String englishWord) {
        try {
            String update = "UPDATE `translatebot`.`users` SET `englishWord` = '" + englishWord + "' WHERE (`chatId` = '" + chatId + "');";
            PreparedStatement prStUpdate = getDBConnection().prepareStatement(update);
            prStUpdate.executeUpdate();
        } catch (SQLException e) {
            System.out.println("не получилось обновить базу данных");
        }
    }
    public String getEnglishWord(long chatId) {
        String select = "SELECT users.englishWord from translatebot.users WHERE chatId = " + chatId + ";";
        try {
            Statement statement = getDBConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while(resultSet.next()){
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            System.out.println("не удалось установить соединение с базой данных");
        }
        return null;
    }
    public ArrayList<Long> takeAllUsersId() {
        ArrayList<Long> array = new ArrayList<>();
        String select = "SELECT users.chatId from translatebot.users;";
        try {
            Statement statement = getDBConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while(resultSet.next()){
                array.add(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            System.out.println("не удалось установить соединение с базой данных takeAllUsersId");
        }
        return array;
    }
}
