import java.util.ArrayList;

public class DiceGame {
    private Dice dice1, dice2, dice3;
    private Player player1, player2;

    public DiceGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        dice1 = new Dice();
        dice2 = new Dice();
        dice3 = new Dice();
    }

    public void play() {
        player1.roll(dice1, dice2, dice3);
        player2.roll(dice1, dice2, dice3);

        System.out.println("승자: " + getWinnerName());
    }

    private String getWinnerName() {
        ArrayList<Integer> arrayList1 = player1.getValueArrayList();
        ArrayList<Integer> arrayList2 = player2.getValueArrayList();

        player1.setScore(calScore(arrayList1));
        player2.setScore(calScore(arrayList2));

        System.out.println(player1.toString());
        System.out.println(player2.toString());

        if (player1.getScore() > player2.getScore()) {
            return player1.getName();
        } else if (player1.getScore() < player2.getScore()) {
            return player2.getName();
        } else {
            return "무승부";
        }
    }

    /*
     * 점수를 계산해 반환하는 메소드
     * 트리플인 경우 100 + faceValue
     * 더블인 경우 50 + faceValue(중복된 수)
     * 중복이 없는 경우 세 수의 합
     * */
    private int calScore(ArrayList<Integer> arrayList) {
        if (isTriple(arrayList)) {
            return 100 + arrayList.get(0);
        } else if (isDouble(arrayList)) {
            if (arrayList.get(0) == arrayList.get(1)) {
                return 50 + arrayList.get(0);
            } else if (arrayList.get(1) == arrayList.get(2)) {
                return 50 + arrayList.get(1);
            } else {
                return 50 + arrayList.get(2);
            }
        } else {
            return arrayList.get(0) + arrayList.get(1) + arrayList.get(2);
        }
    }

    private boolean isTriple(ArrayList<Integer> arrayList) {
        if (arrayList.get(0) == arrayList.get(1) &&
                arrayList.get(1) == arrayList.get(2)) {
            return true;
        }

        return false;
    }

    private boolean isDouble(ArrayList<Integer> arrayList) {
        if (arrayList.get(0) == arrayList.get(1) ||
                arrayList.get(1) == arrayList.get(2) ||
                arrayList.get(2) == arrayList.get(0)) {
            return true;
        }

        return false;
    }
}
