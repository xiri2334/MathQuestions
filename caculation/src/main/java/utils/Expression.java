package utils;

import java.util.*;
import java.util.Random;
import static Symbol.Symbol.EQU;

public class Expression {
    /**
     * 获取指定个数和数值范围的运算式字符串和结果
     */
    public static Map<String,String> generate(int n,int round){

        //运算式和结果的集合
        Map<String,String> questionAndResultMap = new HashMap<>();
        //结果集合，用于判断是否重复
        Set<String> result = new HashSet<>();
        for (int i = 0; i < n; i++) {
            //随机获取运算符的个数(1~3个)
            int num = (int)(Math.random()*3)+1;
            //随机获取num个运算符
            Character[] curOperators = OperatorCreate.getOperators(num);
            //随机获取num+1个操作数
            String[] curNumbers = NumberCreate.getNumbers(round,num+1);
            //获取运算式表达式
            String[] questionAndResult = getExpressStr(curOperators, curNumbers);

            if(questionAndResult==null||questionAndResult[1].contains("-")){
                //判断是否为负数
                i--;
            }else if (result.contains(questionAndResult[1])){
                //判断是否重复
                i--;
            }else {
                result.add(questionAndResult[1]);
                questionAndResultMap.put(questionAndResult[0],questionAndResult[1]);
            }
        }
        return questionAndResultMap;
    }

    /**
     * 根据运算符数组和操作数数组生成运算式表达式
     * @param curOperators 运算符数组
     * @param curNumbers 操作数数组
     * @return 运算式字符串以及其结果
     */
    public static String[] getExpressStr(Character[] curOperators, String[] curNumbers){
        //操作数的数量
        int number = curNumbers.length;
        //随机判断是否生成带括号的运算式
        Random random = new Random();
        int isAddBracket = random.nextInt(10);
        Random random = new Random();

        if(isAddBracket==1){
            //生成带括号的表达式
            //当标记为1时代表该操作数已经添加了左括号
            int[] lStamp = new int[number];
            //当标记为1时代表该操作数已经添加了右括号
            int[] rStamp = new int[number];
            //遍历操作数数组，随机添加括号
            for (int index=0;index<number-1;index++) {
                int n = (int)(Math.random()*10) % 2;
                //判断当前操作数是否标记了左括号
                if(n == 0 && rStamp[index] != 1) {
                    //标记左括号
                    lStamp[index] = 1;
                    //操作数之前加上左括号
                    curNumbers[index] = "(" + curNumbers[index];
                    int k = number - 1;
                    //生成右括号的位置
                    int rbracketIndex = random.nextInt(k)%(k-index) + (index+1);
                    //如果当前操作数有左括号，则重新生成右括号位置
                    while (lStamp[rbracketIndex] == 1){
                        rbracketIndex = random.nextInt(k)%(k-index) + (index+1);
                    }
                    rStamp[rbracketIndex] = 1;
                    curNumbers[rbracketIndex] = curNumbers[rbracketIndex] +")";

                }
            }
        }

        //将运算符数组和操作数数组拼成一个运算式字符串
        StringBuilder str = new StringBuilder(curNumbers[0]);
       for (int i = 0; i < curOperators.length; i++) {
            str.append(curOperators[i]).append(curNumbers[i + 1]);
        }
        //最后添加等号
        str.append(EQU);
        //生成的运算式
        String express = str.toString();
        //获取运算式结果
        String value = Calculation.getExpressResult(express);

        if("#".equals(value)){
            //运算过程出现负数
            return null;
        }

        return  new String[]{express,value};

    }
}
