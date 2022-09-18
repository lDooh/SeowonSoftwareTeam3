public class DiceMain {
    public static void main(String[] args) {
        DiceGame dicegame = new DiceGame(new Player("Lee"), new Player("Kim"));

        dicegame.play();
    }
}
