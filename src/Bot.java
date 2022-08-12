import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.ArrayList;

public class Bot {
    private TelegramBot bot;

    private DataBaseUser dbUser;
    private DataBaseWord dbWord;
    private DataBaseLogs dbLogs;
    private DataBaseBotPhrases dbBotPhrases;

    Bot(String botToken) {
        bot = new TelegramBot(botToken);
    }
    public void run() {
        dbUser = new DataBaseUser();
        dbWord = new DataBaseWord();
        dbLogs = new DataBaseLogs();
        dbBotPhrases = new DataBaseBotPhrases();

        //makeGreeting();

        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {

                if (update.message() == null) {
                    //пользователь удалил чат
                    dbLogs.addLogNull(update);
                } else {
                    dbLogs.addLog(update);
                    long chatId = update.message().chat().id();
                    String userText;
                    if (update.message().text() == null) {
                        userText = "Empty message";
                    } else {
                        userText = update.message().text();
                    }
                    String englishWord = dbUser.getEnglishWord(chatId);

                    if (!dbUser.findUser(chatId)) {
                        //Пользователь не найден добавляем нового пользователя
                        dbUser.singUpUser(chatId, update.message().chat().firstName(), update.message().chat().lastName(), update.message().chat().username(), update.message().text());
                        dbUser.setSelectedMenu(chatId, userText);
                        dbUser.setSelectedTest(chatId, " ");
                    }

                    switch (userText) {
                        case "/test_a1" : {
                            bot.execute(new SendMessage(chatId, "Вы выбрали начать тест А1"));
                            dbUser.setSelectedMenu(chatId, userText);
                            dbUser.setSelectedTest(chatId, " ");
                            englishWord = dbWord.getRandomEnglishWord();
                            ReplyKeyboardMarkup rKM = new ReplyKeyboardMarkup(dbWord.getAnswerOptions(englishWord), true, true, "Выберите правильный вариант", true);
                            bot.execute(new SendMessage(chatId, "А попробуй перевести слово: " + englishWord).replyMarkup(rKM));
                            dbUser.setEnglishWord(chatId, englishWord);
                            break;
                        }
                        case "/test_iv" : {
                            bot.execute(new SendMessage(chatId, "Вы выбрали начать тест неправильных глаголов"));
                            bot.execute(new SendMessage(chatId, dbBotPhrases.getPhrase("errors")));
                            bot.execute(new SendMessage(chatId, "Тест еще не готов, попробуй завтра!"));
                            dbUser.setSelectedMenu(chatId, userText);
                            dbUser.setSelectedTest(chatId, " ");
                            break;
                        }
                    }
                    switch (dbUser.getSelectedTest(chatId)) {
                        case "/test_a1" : {
                            if (dbWord.check(englishWord, userText)) {
                                bot.execute(new SendMessage(chatId, dbBotPhrases.getPhrase("congratulation")));
                            } else {
                                bot.execute(new SendMessage(chatId, dbBotPhrases.getPhrase("condolences") + " Правильный перевод: " + dbWord.translateEnglishWord(englishWord)));
                            }
                            englishWord = dbWord.getRandomEnglishWord();
                            ReplyKeyboardMarkup rKM = new ReplyKeyboardMarkup(dbWord.getAnswerOptions(englishWord), true, true, "Выберите правильный вариант", true);
                            bot.execute(new SendMessage(chatId, "А попробуй перевести слово: " + englishWord).replyMarkup(rKM));
                            dbUser.setEnglishWord(chatId, englishWord);
                            break;
                        }
                        case "/test_iv" : {
                            bot.execute(new SendMessage(chatId, "Выбери другой тест."));
                            break;
                        }
                    }
                    dbUser.setSelectedTest(chatId, dbUser.getSelectedMenu(chatId));
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    void makeGreeting() {
        ArrayList<Long> array = dbUser.takeAllUsersId();
        for (Long chatId : array) {
            bot.execute(new SendMessage(chatId, dbBotPhrases.getPhrase("welcome") + " Я снова работаю! И кстати, я научился делать много нового."));
        }
    }
}
