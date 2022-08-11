import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.ArrayList;

public class EnglishWords {
    public static void main(String[] args) {
        TelegramBot bot = new TelegramBot("5498793443:AAHIih9n6I0XtPLf8PV4UqpIU8DzQhQDPd8");
        DataBaseUser dbUser = new DataBaseUser();
        DataBaseWord dbWord = new DataBaseWord();
        DataBaseLogs dbLogs = new DataBaseLogs();

        ArrayList<Long> array = dbUser.takeAllUsersId();
        for (Long chatId : array) {
            bot.execute(new SendMessage(chatId, "Я снова работаю! И кстати, я научился делать много нового."));
        }

        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                dbLogs.addLog(update);
                long chatId = update.message().chat().id();
                String userText = update.message().text();
                String englishWord = dbUser.getEnglishWord(chatId);
                dbUser.setLastText(chatId, update.message().text());

                if (dbUser.findUser(chatId)) {
                    //Пользователь найден
                    if (dbWord.check(englishWord, userText)) {
                        bot.execute(new SendMessage(chatId, "Правильно!"));
                    } else {
                        bot.execute(new SendMessage(chatId, "Ошибка! Правильный перевод: " + dbWord.translateEnglishWord(englishWord)));
                    }
                } else {
                    //Добавление пользователя в базу
                    dbUser.singUpUser(chatId, update.message().chat().firstName(), update.message().chat().lastName(), update.message().chat().username(), update.message().text());
                }
                englishWord = dbWord.getRandomEnglishWord();
                ReplyKeyboardMarkup rKM = new ReplyKeyboardMarkup(dbWord.getAnswerOptions(englishWord), true, true, "Выберите правильный вариант", true);
                bot.execute(new SendMessage(chatId, "А попробуй перевести слово: " + englishWord).replyMarkup(rKM));
                dbUser.setEnglishWord(chatId, englishWord);
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }
}
