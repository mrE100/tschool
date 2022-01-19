package com.tsystems.javaschool.tasks.calculator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        // TODO: Implement the logic here
        String result = null;
        String reversePolish;
        try {
            reversePolish = ReversePolishNotation(statement);
            result = calc(reversePolish);
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    private static String calc(String reversePolish) throws Exception{
        double doubleA = 0, doubleB = 0;
        String stringTmp;
        Deque<Double> stack = new ArrayDeque<Double>();
        StringTokenizer stringToken = new StringTokenizer(reversePolish);
        while (stringToken.hasMoreTokens()) {
            try {
                stringTmp = stringToken.nextToken().trim();
                if (1 == stringTmp.length() && isOperand(stringTmp.charAt(0))) {
                    if (stack.size() < 2) {
                        throw new Exception("неверное количество данных для операции");
                    }
                    doubleB = stack.pop();
                    doubleA = stack.pop();
                    switch (stringTmp.charAt(0)) {
                        case '+':
                            doubleA += doubleB;
                            break;
                        case '-':
                            doubleA -= doubleB;
                            break;
                        case '*':
                            doubleA *= doubleB;
                            break;
                        case '/':
                            if (doubleB == 0) {
                                throw new Exception("деление на ноль");
                            } else {
                                doubleA /= doubleB;
                            }
                            break;
                        default:
                            throw new Exception("недопустимая операция " + stringTmp);
                    }
                    stack.push(doubleA);
                } else {
                    doubleA = Double.parseDouble(stringTmp);
                    stack.push(doubleA);
                }
            } catch (Exception e) {
                throw new Exception("недопустимый символ " + reversePolish);
            }
        }
        if (stack.size() > 1) {
            throw new Exception("операций больше,чем чисел");
        }
        Double result = Math.round(stack.pop() * 10000.0) / 10000.0;
        if (result % 1 == 0) {
            return String.valueOf(result.intValue());
        } else {
            return String.valueOf(result);
        }

    }

    private static String ReversePolishNotation(String statement) throws Exception{
        StringBuilder stringStack = new StringBuilder(""), stringResult = new StringBuilder("");
        char chIn, chTmp;
        for (int i = 0; i < statement.length(); i++) {
            chIn = statement.charAt(i);
            if (isOperand(chIn)) {
                while (stringStack.length() > 0) {
                    chTmp = stringStack.substring(stringStack.length() - 1).charAt(0);
                    if (isOperand(chTmp) && (operandPriority(chIn) <= operandPriority(chTmp))) {
                        stringResult.append(" ").append(chTmp).append(" ");
                        stringStack.setLength(stringStack.length() - 1);
                    } else {
                        stringResult.append(" ");
                        break;
                    }
                }
                stringResult.append(" ");
                stringStack.append(chIn);
            } else if ('(' == chIn) {
                stringStack.append(chIn);
            } else if (')' == chIn) {
                chTmp = stringStack.substring(stringStack.length() - 1).charAt(0);
                while ('(' != chTmp) {
                    if (stringStack.length() < 1) {
                        throw new Exception("ошибка в количестве скобок");
                    }
                    stringResult.append(" ").append(chTmp);
                    stringStack.setLength(stringStack.length() - 1);
                    chTmp = stringStack.substring(stringStack.length() - 1).charAt(0);
                }
                stringStack.setLength(stringStack.length() - 1);
            } else if (',' == chIn || '^' == chIn || '%' == chIn) {
                throw new Exception("недопустимый символ");
            } else {
                stringResult.append(chIn);
            }
        }
        while (stringStack.length() > 0) {
            stringResult.append(" ").append(stringStack.substring(stringStack.length()-1));
            stringStack.setLength(stringStack.length()-1);
        }
        return  stringResult.toString();
    }

    private static byte operandPriority(char operand) {
        switch (operand) {
            case '*':
            case '/':
                return 2;
        }
        return 1;
    }

    private static boolean isOperand(char ch) {
        switch (ch) {
            case '-':
            case '+':
            case '*':
            case '/':
                return true;
        }
        return false;
    }

}
