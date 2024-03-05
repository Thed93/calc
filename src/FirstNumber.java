public class FirstNumber extends Number implements CreatingNumber {
    public FirstNumber() {
    }
    @Override
    public void createNumber(String prim, int indexOfAction, Number number) {
        StringBuilder a = new StringBuilder();
        for (int i = 0; i < indexOfAction; i++) {
            char ch = prim.charAt(i);
            a.append(ch);
        }
        number.setNumber(a.toString());
    }

}

