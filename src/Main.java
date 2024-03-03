import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите Ваше уравнение");
        String primFirst = in.nextLine();
        System.out.println(calc(primFirst));

    }
    public static String calc(String input) {
        List<String> roman = getRomanNumbers();
        List<String> actions = getActions();
        boolean isRoman = false;
        final Pattern PATTERN = Pattern.compile("([0-9]{1,2})([-+*/]{1})([0-9]{1,2})");
        final Pattern PATTERN1 = Pattern.compile("([0-9]{1,2})");
        Matcher matcher, matcher1;
        int indexOfAction = 0;
        String action = null;
        boolean actionIsHere = false;


        for (String c : actions) {
            if (input.contains(c)) {
                indexOfAction = input.indexOf(c);
                action = c;
                actionIsHere = true;
            }
        }
        if (!actionIsHere){
            throw new RuntimeException("в примере нету алгебраического действия");
        }

        for (String s: roman) {
            if (input.contains(s)){
                isRoman = true;
            }
        }

        String firstNumber = firstNumber(input, indexOfAction);
        String secondNumber = secondNumber(input, indexOfAction);

        if (isRoman){
            matcher = PATTERN1.matcher(firstNumber);
            matcher1 = PATTERN1.matcher(secondNumber);
            if (matcher.matches() || matcher1.matches()){
                throw new RuntimeException("оба аргумента должны быть написаны или римскими, или арабскими цифрами");
            }
            StringBuilder prinSecond = new StringBuilder();
            prinSecond.append(romanToArabic(firstNumber));
            prinSecond.append(action);
            prinSecond.append(romanToArabic(secondNumber));
            matcher = PATTERN.matcher(prinSecond);
            if (matcher.matches()){
                input = checkInput(firstNumber, secondNumber, action, isRoman);
            } else {
                throw new RuntimeException("Пример некорректно введен, должно быть введено в формате 'firstNumber+secondNumber' без пробелов и с числами от 0 до 10");
            }
        } else {
            matcher = PATTERN.matcher(input);
            if (matcher.matches()) {
                input = checkInput(firstNumber, secondNumber, action, isRoman);
            } else {
                throw new RuntimeException("Пример некорректно введен, должно быть введено в формате 'firstNumber+secondNumber' без пробелов и с числами от 0 до 10");
            }
        }

        return input;
    }

    public static List<String> getRomanNumbers(){
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
    public static List<String> getActions(){
        List<String> actions = new ArrayList<>();
        actions.add("+");
        actions.add("-");
        actions.add("*");
        actions.add("/");
        return actions;
    }
    public static int romanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " не может быть конвртировано в арабский");
        }

        return result;
    }
    public static String arabicToRoman(float number) {
        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }
    public static String firstNumber(String prim, int indexOfAction){
        StringBuilder a = new StringBuilder();
        for (int i = 0; i < indexOfAction; i++) {
            char ch = prim.charAt(i);
            a.append(ch);
        }
        System.out.println("Первое число = "+ a);
        return a.toString();
    }
    public static String secondNumber(String prim, int indexOfAction){
        StringBuilder b = new StringBuilder();
        for (int i = indexOfAction+1; i < prim.length() ; i++) {
            char ch = prim.charAt(i);
            b.append(ch);
        }
        System.out.println("Второе число = " + b);
        return b.toString();
    }
    static String checkInput(String firstNumber, String secondNumber, String action, boolean isRoman){

        StringBuilder input = new StringBuilder();
        if (isRoman){
            firstNumber = String.valueOf(romanToArabic(firstNumber));
            secondNumber = String.valueOf(romanToArabic(secondNumber));
        }
        Integer first = Integer.valueOf(firstNumber);
        Integer second = Integer.valueOf(secondNumber);
        if (first > 10 || second > 10){
            throw new RuntimeException("В примере должны применяться числа от 0 до 10");
        }
        float sum = 0;
        switch (action) {
            case "+":
                sum = first + second;
                break;
            case "-":
                sum = first - second;
                break;
            case "*":
                sum = first * second;
                break;
            case "/":
                if (second.equals(0)) {
                    throw new RuntimeException("На ноль делить нельзя");
                }
                sum = first / second;
                break;
        }
        if (isRoman){
            input.append(arabicToRoman(first));
            input.append(action);
            input.append(arabicToRoman(second));
            input.append("=");
            input.append(arabicToRoman(sum));
        } else {
            input.append(first);
            input.append(action);
            input.append(second);
            input.append("=");
            input.append(sum);
        }
        return input.toString();
    }
}