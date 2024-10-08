package utils;

import Symbol.Symbol;

public class OperatorCreate {
    private final static Character[] operatorTypes = new Character[]{Symbol.ADD, Symbol.SUB, Symbol.MUL, Symbol.DIV};

    /**
     * 随机获取num个运算符的数组
     */
    public static Character[] getOperators(int num) {

        Character[] operators = new Character[num];

        for (int i = 0; i < num; i++) {
            //随机获取运算符的类型(0~3 代表4个运算符的类型)
            int operatorTypeIndex = (int)(Math.random()*4);
            Character operatorType = operatorTypes[operatorTypeIndex];
            operators[i] = operatorType;
        }

        return operators;
    }



}