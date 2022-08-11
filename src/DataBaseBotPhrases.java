import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataBaseBotPhrases extends DataBase {

    public String getPhrase(String str) {
        String select = "SELECT bot_phrases." + str + " FROM translatebot.bot_phrases;";
        int i = 0;
        List<String> array = new ArrayList<>();
        try {
            Statement statement = getDBConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while(resultSet.next()){
                if(resultSet.getString(1) != null) {
                    array.add(resultSet.getString(1));
                    i++;
                }
            }
        } catch (SQLException e) {
            System.out.println("не удалось установить соединение с базой данных getPhrase");
        }
        Random random = new Random();
        return array.get(random.nextInt(i));
    }
}
