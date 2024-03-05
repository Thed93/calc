import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

abstract class Number{
    String number;
    boolean isRoman = false;
    public Number() {
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public boolean isRoman() {
        return isRoman;
    }

    public void setRoman(boolean roman) {
        isRoman = roman;
    }

    void romanToArabic() {
        String romanNumeral = number.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((!romanNumeral.isEmpty()) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
            setNumber(String.valueOf(result));
        }

        if (!romanNumeral.isEmpty()) {
            throw new IllegalArgumentException(number + " не может быть конвртировано в арабский");
        }

    }
    void arabicToRoman() {
        float num = Float.parseFloat(number);
        if (num < 1){
            throw new RuntimeException("Арабское число меньше 1 не может быть конвертировано в римское");
        }
        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while ((num > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= num) {
                sb.append(currentSymbol.name());
                num -= currentSymbol.getValue();
            } else {
                i++;
            }
        }
        setNumber(sb.toString());
    }

    List<String> getRomanNumbers(){
        List<String> roman = new ArrayList<>();
        roman.add("I");
        roman.add("II");
        roman.add("III");
        roman.add("IV");
        roman.add("V");
        roman.add("VI");
        roman.add("VII");
        roman.add("VIII");
        roman.add("IX");
        roman.add("X");
        return roman;
    }

    void checkRoman(Number number){
        List<String> roman = number.getRomanNumbers();
        for (String s: roman ) {
            if (number.getNumber().equals(s)){
                number.setRoman(true);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Number number1 = (Number) o;
        return isRoman == number1.isRoman && Objects.equals(number, number1.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, isRoman);
    }
    @Override
    public String toString() {
        return number;
    }
}
