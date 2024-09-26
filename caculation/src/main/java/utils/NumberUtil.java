package utils;

import java.util.Random;


public class NumberUtil {
    public static String [] getNumbers(int round,int num){
        Random random=new Random();
        String [] numbers =new String[num];
        for(int i=0;i<num;i++)
        {//判断是整数还是小数
            int tag=(int)(Math.random()*10)%2;
            if(tag==1)
            {
                int n=(random.nextInt(round))+1;
                numbers[i]=n+" ";

            }
            else {
                int numerator = (random.nextInt(round))+1;
                int denominator = (random.nextInt(round))+1;
                //判断是否为真分数，且不能生成带0的分数
                while(numerator>=denominator||numerator==0||denominator==0){
                    numerator = (random.nextInt(round))+1;
                    denominator = (random.nextInt(round))+1;
                }
                //拼装成分数形式
                numbers[i] = numerator+"/"+denominator;
            }
        }
        return numbers;
    }
}