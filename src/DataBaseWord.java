import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class DataBaseWord extends DataBase {

    public void addWords(String englishWord, String russianWord) {
        try {
            String insert = "INSERT INTO `translatebot`.`words` (`englishWords`, `russianWord`) VALUES ('" + englishWord + "', '" + russianWord + "');";
            PreparedStatement prSt = getDBConnection().prepareStatement(insert);
            prSt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("не получилось добавить в базу слова");
        }
    }
    public String translateEnglishWord(String englishWord) {
        String select = "SELECT words.russianWord from translatebot.words WHERE englishWords = '" + englishWord + "';";
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
    public String translateRussianWord(String russianWord) {
        String select = "SELECT words.englishWords from translatebot.words WHERE russianWord = " + russianWord + ";";
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
    public String getRandomEnglishWord() {
        Random random = new Random();
        int count = 0;

        String select = "SELECT words.id from translatebot.words;";
        try {
            Statement statement = getDBConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while(resultSet.next()){
                count++;
            }
            select = "SELECT words.englishWords from translatebot.words WHERE id = " + random.nextInt(count) + ";";
            resultSet = statement.executeQuery(select);
            while(resultSet.next()){
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            System.out.println("не удалось установить соединение с базой данных");
        }
        return null;
    }
    public boolean check(String englishWord, String russianWord) {
        String select = "SELECT words.russianWord from translatebot.words WHERE englishWords = '" + englishWord + "';";
        String translateWord = null;
        try {
            Statement statement = getDBConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while(resultSet.next()){
                translateWord = resultSet.getString(1).toLowerCase();
            }
        } catch (SQLException e) {
            System.out.println("не удалось установить соединение с базой данных");
        }
        if (translateWord != null && translateWord.equals(russianWord.toLowerCase())) {
            return true;
        }
        return false;
    }
    public String[][] getAnswerOptions(String englishTrueAnswer) {
        String russianTrueAnswer = translateEnglishWord(englishTrueAnswer);
        String[][] str = new String[2][2];

        str[0][0] = translateEnglishWord(getRandomEnglishWord());
        str[0][1] = translateEnglishWord(getRandomEnglishWord());
        str[1][0] = translateEnglishWord(getRandomEnglishWord());
        str[1][1] = translateEnglishWord(getRandomEnglishWord());

        Random random = new Random();
        str[random.nextInt(2)][random.nextInt(2)] = russianTrueAnswer;

        return str;
    }
}
