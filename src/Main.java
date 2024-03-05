import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        final Pattern PATTERN1 = Pattern.compile("((\\S{1,4})([-+*/])(\\S{1,4}))");
        System.out.println("Введите Ваше уравнение");
        String input = in.nextLine();
        Matcher matcher = PATTERN1.matcher(input);
        if (matcher.matches()){
            System.out.println(calc(input));
        } else {
            throw new RuntimeException("Пример некорректно введен, должно быть введено в формате 'firstNumber+secondNumber' без пробелов и с числами от 0 до 10");
        }
    }
    public static String calc(String input) {
        List<String> actions = getActions();

        final Pattern PATTERN = Pattern.compile("([0-9]{1,2})");
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

        FirstNumber firstNumber = new FirstNumber();
        SecondNumber secondNumber = new SecondNumber();
        firstNumber.createNumber(input, indexOfAction, firstNumber);
        secondNumber.createNumber(input, indexOfAction, secondNumber);
        firstNumber.checkRoman();
        secondNumber.checkRoman();

        if ((firstNumber.isNumberRoman()) != (secondNumber.isNumberRoman())){
            throw new RuntimeException("оба аргумента должны быть написаны или римскими, или арабскими цифрами");
        }

        if (firstNumber.isNumberRoman()) {
            firstNumber.romanToArabic();
            secondNumber.romanToArabic();
        }
        matcher = PATTERN.matcher(firstNumber.getNumber());
        matcher1 = PATTERN.matcher(firstNumber.getNumber());
        if ((matcher.matches() && matcher1.matches())&&(firstNumber.convertToInteger()<=10)&&(secondNumber.convertToInteger()<=10)){
            input = checkInput(firstNumber, secondNumber, action);
        } else {
            throw new RuntimeException("Пример некорректно введен, должно быть введено в формате 'firstNumber+secondNumber' без пробелов и с числами от 0 до 10");
        }
        return input;
    }

    public static List<String> getActions(){
        List<String> actions = new ArrayList<>();
        actions.add("+");
        actions.add("-");
        actions.add("*");
        actions.add("/");
        return actions;
    }
    static String checkInput(FirstNumber firstNumber, SecondNumber secondNumber, String action){
        StringBuilder input = new StringBuilder();
        Integer first = Integer.valueOf(firstNumber.getNumber());
        Integer second = Integer.valueOf(secondNumber.getNumber());
        Sum sum = getSum(action, first, second);
        if (firstNumber.isNumberRoman()){
            firstNumber.arabicToRoman();
            secondNumber.arabicToRoman();
            sum.arabicToRoman();
        }
        input.append(firstNumber.getNumber());
        input.append(action);
        input.append(secondNumber.getNumber());
        input.append("=");
        input.append(sum.getNumber());
        return input.toString();
    }

    static Sum getSum(String action, Integer first, Integer second) {
        Sum sum = new Sum();
        if (first > 10 || second > 10){
            throw new RuntimeException("В примере должны применяться числа от 0 до 10");
        }
        float answer = 0;
        switch (action) {
            case "+":
                answer = first + second;
                break;
            case "-":
                answer = first - second;
                break;
            case "*":
                answer = first * second;
                break;
            case "/":
                if (second.equals(0)) {
                    throw new RuntimeException("На ноль делить нельзя");
                }
                answer = (float) first / second;
                break;
        }
        sum.setNumber(String.valueOf(answer));
        return sum;
    }
}