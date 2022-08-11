public class Test {
    public static void main(String[] args) {
        DataBaseBotPhrases dbBotPhrases = new DataBaseBotPhrases();
        while (true) {
            System.out.println(dbBotPhrases.getPhrase("welcome"));
            System.out.println(dbBotPhrases.getPhrase("goodbye"));
            System.out.println(dbBotPhrases.getPhrase("congratulation"));
            System.out.println(dbBotPhrases.getPhrase("condolences"));
            System.out.println(dbBotPhrases.getPhrase("errors"));
        }
    }
}
