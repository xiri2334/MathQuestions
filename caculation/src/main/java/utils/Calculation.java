package utils;

import Fraction.Fraction;

import java.util.*;

public class Calculation {
    public  static  String getExpressResult( String express){
        //运算符栈，用于存放运算符包括
        Stack<Character> operators = new Stack<>();
        //操作数栈,用于存放操作数
        Stack<Fraction> fractions = new Stack<>();
        //将表达式字符串转成字符数组
        char[] chars = express.toCharArray();
        //遍历获取处理
        for (int i=0;i<chars.length;i++) {
            //获取当前的字符
            char c = chars[i];

            if(c=='('){
                //如果是左括号，入栈
                operators.push(c);
            }else if(c==')'){
                //当前字符为右括号
                //当运算符栈顶的元素不为‘(’,则继续
                while(operators.peek()!='('){
                    //拿取操作栈中的两个分数
                    Fraction fraction1 = fractions.pop();
                    Fraction  fraction2 = fractions.pop();
                    //获取计算后的值
                    Fraction result = calculate(operators.pop(), fraction1.getNumerator(), fraction1.getDenominator(),
                            fraction2.getNumerator(), fraction2.getDenominator());
                    if(result.getNumerator()<0){
                        //保证运算过程不出现负数
                        return  "#";
                    }
                    //将结果压入栈中
                    fractions.push(result);
                }
                //将左括号出栈
                operators.pop();
            }else if(c=='+'||c=='-'||c=='*'||c=='÷'){
                //是运算符
                //当运算符栈不为空，且当前运算符优先级小于栈顶运算符优先级
                while(!operators.empty()&&!priority(c, operators.peek())){
                    //拿取操作栈中的两个分数
                    Fraction fraction1 = fractions.pop();
                    Fraction  fraction2 = fractions.pop();
                    //获取计算后的值
                    Fraction result = calculate(operators.pop(), fraction1.getNumerator(), fraction1.getDenominator(),
                            fraction2.getNumerator(), fraction2.getDenominator());
                    if(result.getNumerator()<0){
                        return  "#";
                    }
                    //将结果压入栈中
                    fractions.push(result);
                }
                //将运算符入栈
                operators.push(c);
            }else{//是操作数
                if(c>='0'&&c<='9'){
                    StringBuilder buf = new StringBuilder();
                    //这一步主要是取出一个完整的数值 比如 2/5、9、9/12
                    while(i< chars.length&&(chars[i]=='/'||((chars[i]>='0')&&chars[i]<='9'))){
                        buf.append(chars[i]);
                        i++;
                    }
                    i--;
                    //到此 buf里面是一个操作数
                    String val = buf.toString();
                    //标记‘/’的位置
                    int flag = val.length();
                    for(int k=0;k<val.length();k++){
                        if(val.charAt(k)=='/'){
                            //当获取的数值存在/则标记/的位置，便于接下来划分分子和分母生成分数对象
                            flag = k;
                        }
                    }
                    StringBuilder numeratorBuf = new StringBuilder();
                    StringBuilder denominatorBuf = new StringBuilder();
                    for(int j=0;j<flag;j++){
                        numeratorBuf.append(val.charAt(j));
                    }
                    //判断是否为分数
                    if(flag!=val.length()){
                        for(int q=flag+1;q<val.length();q++){
                            denominatorBuf.append(val.charAt(q));
                        }
                    }else{
                        //如果不是分数则分母计为1
                        denominatorBuf.append('1');
                    }
                    fractions.push(new Fraction(Integer.parseInt(numeratorBuf.toString()), Integer.parseInt(denominatorBuf.toString())));
                }
            }
        }

        while(!operators.empty()){
            Fraction fraction1 = fractions.pop();
            Fraction  fraction2 = fractions.pop();

            //获取计算后的值
            Fraction result = calculate(operators.pop(), fraction1.getNumerator(), fraction1.getDenominator(),
                    fraction2.getNumerator(), fraction2.getDenominator());
            if(result.getNumerator()<0){
                return "#";
            }
            //将结果压入栈中
            fractions.push(result);
        }

        //计算结果
        Fraction result = fractions.pop();
        //获取最终的结果(将分数进行约分)
        return getFinalResult(result);

    }

    private static String getFinalResult(Fraction result) {
        int denominator = result.getDenominator();
        int numerator = result.getNumerator();
        int gcd = gcd(numerator,denominator);

        if(denominator==0){
            return "0";
        }

        if(denominator/gcd == 1){//分母为1
            numerator = numerator/gcd;
            return String.valueOf(numerator);
        }
        else{
            //如果分子大于分母则化成真分数的形式
            if(result.getNumerator() > denominator){
                result = getRealFraction(result);
                numerator = result.getNumerator()/gcd;
                denominator = result.getDenominator()/gcd;
                return result.getInter() + "'" + numerator + "/" + denominator;
            }else{
                numerator = numerator/gcd;
                denominator = denominator/gcd;
                return numerator+"/"+denominator;
            }
        }
    }

    /**
     * 求最大公约数
     * @param numerator 分子
     * @param denominator 分母
     * @return 最大公约数
     */
    private static int gcd(int numerator,int denominator){
        int x = numerator;
        int y = denominator;
        while (y != 0) {
            int z = x % y;
            x = y;
            y = z;
        }
        return x;
    }

    /**
     * 将带分数化成假分数
     */
    private static Fraction getRealFraction(Fraction result){
        int numerator = result.getNumerator();
        int denominator = result.getDenominator();
        //计算分子部分
        int newNumerator = numerator % denominator;
        //计算整数部分
        int inter = numerator/denominator;
        Fraction fraction = new Fraction(newNumerator, denominator);
        fraction.setInter(inter);
        return fraction;
    }

    /**
     * 判断两个运算符的优先级
     * 当opt1的优先级大于opt2时返回true
     */
    private static boolean priority(char opt1,char opt2){
        if((opt1=='+'||opt1=='-')&&(opt2=='*'||opt2=='÷')){
            return false;
        }else if((opt1=='+'||opt1=='-')&&(opt2=='+'||opt2=='-')){
            return false;
        }else {
            return (opt1 != '*' && opt1 != '÷') || (opt2 != '*' && opt2 != '÷');
        }
    }

    /**
     * 对两个分数进行相应的运算，获取结果
     * @param opt 运算符
     * @param num1 分子1
     * @param den1 分母1
     * @param num2 分子2
     * @param den2 分母2
     * @return 结果
     */
    private static Fraction calculate(Character opt, int num1, int den1, int num2, int den2){
        //结果数组,存放结果的分子与分母
        int[] result = new int[2];

        switch (opt){
            case'+':
                result[0] = num1*den2 + num2*den1; result[1]= den1*den2;
                break;
            case '-':
                result[0] = num2*den1 - num1*den2; result[1]= den1*den2;
                break;
            case '*':
                result[0] = num1*num2; result[1] = den1*den2;
                break;
            case '÷':
                result[0] = num2*den1; result[1] = num1*den2;
                break;
        }
        return new Fraction(result[0],result[1]);
    }

}

