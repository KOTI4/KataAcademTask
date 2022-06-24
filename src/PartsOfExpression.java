import java.util.Objects;

public class PartsOfExpression {
    public String firstNumber;
    public String operation;
    public String secondNumber;

    PartsOfExpression(String firstNumber, String operation, String secondNumber){
        this.firstNumber = firstNumber;
        this.operation = operation;
        this.secondNumber = secondNumber;
    }

    public static PartsOfExpression getParts(String expression) throws Exception {
        int i = 0;
        String firstNumber = "";

        try {
            while (!isOperation(expression.charAt(i))){
                firstNumber += expression.charAt(i);
                i++;
            }
        }
        catch (StringIndexOutOfBoundsException e) {
            throw new Exception(ErrorMessages.NOT_FIND_ANY_PROPER_OPERATOR);
        }

        String operation = "" + expression.charAt(i);
        i++;

        String secondNumber = expression.substring(i);

        firstNumber = firstNumber.replaceAll(" ", "");
        secondNumber = secondNumber.replaceAll(" ", "");

        CheckNumberContainsOperator(secondNumber);
        CheckNumberIsNotNegativeAndNotNull(firstNumber);
        CheckNumberIsNotNegativeAndNotNull(secondNumber);
        CheckNumberIsNotZero(firstNumber);
        CheckNumberIsNotZero(secondNumber);

        return new PartsOfExpression(firstNumber, operation, secondNumber);
    }

    private static void CheckNumberContainsOperator(String number) throws Exception {
        for (int i = 0; i < number.length(); i++){
            if (isOperation(number.charAt(i))) throw new Exception(ErrorMessages.NOT_CORRECT_FORMAT);
        }
    }

    private static void CheckNumberIsNotNegativeAndNotNull(String number) throws Exception {
        if (number.contains("-") || Objects.equals(number, "")) throw new Exception(ErrorMessages.NOT_NEGATIVE_NOT_NULL_NUMBER);
    }

    private static void CheckNumberIsNotZero(String number) throws Exception {
        if (Objects.equals(number, "0")) throw new Exception(ErrorMessages.NOT_ZERO_NUMBER);
    }

    static Boolean isOperation(char symbol){
        return symbol == '/' || symbol == '*' || symbol == '+' || symbol == '-';
    }
}
