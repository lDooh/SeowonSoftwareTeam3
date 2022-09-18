public class Dice {
    private int faceValue;

    public void roll() {
        faceValue = (int)(Math.random() * 6) + 1;
    }

    public int getFaceValue() {
        return faceValue;
    }
}
