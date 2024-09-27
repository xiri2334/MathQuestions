package utils;

import java.util.Map;
import java.util.Scanner;


public class Main {
   public static void main(String[] args) {

        System.out.println("---------------欢迎使用四则运算题目生成程序---------------");
        System.out.println("---------------使用-n 参数控制题目生成数目 -r 参数控制题目中数值范围---------------");
        System.out.println("---------------使用-e Exercises.txt -a Answers.txt 检查答案的正确率---------------");
        System.out.println("---------------退出请输入  Exit    ---------------");
        while(true){
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            if("Exit".equals(command) || "exit".equals(command)){
                break;
            }
            //先校验输入避免出现不可靠输入导致程序退出
            String[] s = InputCheck.checkParams(command);
            if (s !=null){
                if ("-n".equals(s[0])) {
                    if (Integer.parseInt(s[1]) <= 0) {
                        System.out.println("-n参数输入错误，请重新输入");
                        break;
                    }
                    else {
                        if (Integer.parseInt(s[3]) <= 0) {
                            System.out.println("-r参数输入错误，请重新输入");
                        } else {
                            //获取运算式数组
                            Map<String, String> questionAndResultMap = Expression.generate(Integer.parseInt(s[1]), Integer.parseInt(s[3]));
                            File.WriteFile(questionAndResultMap);
                        }
                    }
                }else {
                    String exerciseFileUrl = s[1];
                    String answerFileUrl = s[3];
                    //验证答案
                    File.MatchAnswerFile(exerciseFileUrl, answerFileUrl);
                }
            }else {
                System.out.println("参数输入有误，请重新输入");
            }

        }
    }
}

