public class SecondNumber extends Number implements CreatingNumber {
    public SecondNumber() {
    }
    @Override
    public void createNumber(String prim, int indexOfAction, Number number) {
        StringBuilder b = new StringBuilder();
        for (int i = indexOfAction+1; i < prim.length() ; i++) {
            char ch = prim.charAt(i);
            b.append(ch);
        }
        number.setNumber(b.toString());
        number.checkRoman(number);
    }
}
