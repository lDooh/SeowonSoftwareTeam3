import java.util.ArrayList;

public class Player {
    private String name;
    private int value1, value2, value3;
    private int score;

    public Player(String name) {
        this.name = name;
    }

    public void roll(Dice dice1, Dice dice2, Dice dice3) {
        dice1.roll();
        dice2.roll();
        dice3.roll();

        value1 = dice1.getFaceValue();
        value2 = dice2.getFaceValue();
        value3 = dice3.getFaceValue();
    }

    public ArrayList<Integer> getValueArrayList() {
        ArrayList<Integer> valueArrayList = new ArrayList<>();

        valueArrayList.add(value1);
        valueArrayList.add(value2);
        valueArrayList.add(value3);

        return valueArrayList;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + "의 주사위: " + value1 + ", " + value2 + ", " + value3 + " => 점수: " + score;
    }
}
