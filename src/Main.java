import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Введите операцию");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        String mathematicalExpression = reader.readLine();

        String calculationResult = calc(mathematicalExpression);
        System.out.println("Ответ: " + calculationResult);
    }

    public static String calc(String input) throws Exception {
        PartsOfExpression partsOfExpression = PartsOfExpression.getParts(input);
        return calculateExpressionWithParts(partsOfExpression);
    }

    private static String calculateExpressionWithParts(PartsOfExpression partsOfExpression) throws Exception {

        CheckIfNumbersAreInteger(partsOfExpression.firstNumber, partsOfExpression.secondNumber);

        OperandsType type = GetTypeOfOperands(partsOfExpression);

        String firstNumberStr = partsOfExpression.firstNumber;
        String secondNumberStr = partsOfExpression.secondNumber;

        if(type == OperandsType.Roman){
            firstNumberStr = translateRomanToArabicAndCheckIfMoreTen(firstNumberStr);
            secondNumberStr = translateRomanToArabicAndCheckIfMoreTen(secondNumberStr);
        }
        else CheckIfArabicOperandsAreMoreTen(firstNumberStr, secondNumberStr);

        Integer firstNumber = Integer.parseInt(firstNumberStr);
        Integer secondNumber = Integer.parseInt(secondNumberStr);

        String answer = "";

        switch (partsOfExpression.operation){
            case "+": answer = firstNumber + secondNumber + "";
                break;
            case "-": answer = firstNumber - secondNumber + "";
                break;
            case "/": answer = firstNumber / secondNumber + "";
                break;
            case "*": answer = firstNumber * secondNumber + "";
                break;
        }

        if (type == OperandsType.Roman)
        {
            if(Integer.parseInt(answer) == 0)
                throw new Exception(ErrorMessages.ROMAN_NUMBERS_NOT_HAVE_ZERO);
            if(Integer.parseInt(answer) < 0)
                throw new Exception(ErrorMessages.ROMAN_NUMBERS_NOT_HAVE_NEGATIVE);
            return translateHundredArabicToRoman(answer);
        }
        else return answer;
    }

    private static void CheckIfNumbersAreInteger(String firstNumber, String secondNumber) throws Exception {
        if (firstNumber.contains(".") || firstNumber.contains(",") ||
                secondNumber.contains(".") || secondNumber.contains(","))
            throw new Exception(ErrorMessages.NUMBERS_ARE_NOT_INTEGER);
    }

    private static void CheckIfArabicOperandsAreMoreTen(String firstNumber, String secondNumber) throws Exception {
        if (Integer.parseInt(firstNumber) > 10 || Integer.parseInt(secondNumber) > 10)
            throw new Exception(ErrorMessages.ARABIC_NUMBER_MORE_TEN);
    }

    private static String translateHundredArabicToRoman(String str) {
        int number = Integer.parseInt(str);
        int hundredCount = number / 100;
        number = number % 100;
        int fiftyCount = number / 50;
        number = number % 50;
        int tenCount = number / 10;
        number = number % 10;

        String numberStr = "";
        for (int i = 0; i < hundredCount; i++) numberStr += "C";

        if (fiftyCount > 0) {
            if (tenCount == 4) numberStr += "XC";
            else numberStr += "L";
        }
        else {
            if (tenCount == 4) numberStr += "XL";
        }

        if (tenCount != 4)
            for (int i = 0; i < tenCount; i++) numberStr += "X";

        if (number != 0)
        {
            DictionaryRomanArabic num = DictionaryRomanArabic.values()[number - 1];
            numberStr += num.toString();
        }

        return numberStr;
    }

    private static String translateRomanToArabicAndCheckIfMoreTen(String str) throws Exception {
        try {
            return (DictionaryRomanArabic.valueOf(str).ordinal() + 1) + "";
        }
        catch (IllegalArgumentException e){
            throw new Exception(ErrorMessages.ROMAN_NUMBER_MORE_TEN);
        }
    }

    private static OperandsType GetTypeOfOperands(PartsOfExpression partsOfExpression) throws Exception {
        if (areAllSymbolsArabic(partsOfExpression.firstNumber) &&
                areAllSymbolsArabic(partsOfExpression.secondNumber)) return OperandsType.Arabic;
        else if (areAllSymbolsRoman(partsOfExpression.firstNumber) &&
                areAllSymbolsRoman(partsOfExpression.secondNumber)) return OperandsType.Roman;
        else if (areAllSymbolsArabic(partsOfExpression.firstNumber) && areAllSymbolsRoman(partsOfExpression.secondNumber)
                || areAllSymbolsRoman(partsOfExpression.firstNumber) && areAllSymbolsArabic(partsOfExpression.secondNumber))
                         throw new Exception(ErrorMessages.ROMAN_ARABIC_TOGETHER);
        else throw new Exception(ErrorMessages.NOT_A_NUMBER);
    }

    private static boolean areAllSymbolsRoman(String str) {
        boolean areRoman = true;
        for (int i = 0; i < str.length(); i++)
            if (!(str.charAt(i) == 'I' || str.charAt(i) == 'V' || str.charAt(i) == 'X'))
                areRoman = false;
        return areRoman;
    }
    private static boolean areAllSymbolsArabic(String str) {
        boolean areArabic = true;
        for (int i = 0; i < str.length(); i++)
            if (!Character.isDigit(str.charAt(i))) areArabic = false;
        return areArabic;
    }
}
