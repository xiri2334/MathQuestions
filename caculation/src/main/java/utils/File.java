package utils;

import Symbol.Symbol;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class File {
    /**
     * 根据运算式子生成练习文件和答案文件
     *
     */
    public static void printExerciseFileAndAnswerFile(Map<String, String> questionAndResultMap) {
        java.io.File dir = new java.io.File(Symbol.PRINT_FILE_URL);
        //解决FileNotFound
        if (!dir.exists()) {
            dir.mkdir();
        }
        java.io.File exerciseFile = new java.io.File(Symbol.PRINT_FILE_URL, "Exercises.txt");
        java.io.File answerFile = new java.io.File(Symbol.PRINT_FILE_URL, "Answers.txt");
        try {
            OutputStream exerciseFileOutputStream = new FileOutputStream(exerciseFile);
            OutputStream answerFileOutputStream = new FileOutputStream(answerFile);
            StringBuilder exerciseBuffer = new StringBuilder();
            StringBuilder answerFileBuffer = new StringBuilder();
            System.out.println("正在写出到文件...");
            int count =1;
            for(Map.Entry<String, String> entry:questionAndResultMap.entrySet()){

                exerciseBuffer.append(count).append("、");
                exerciseBuffer.append(entry.getKey()).append("\r\n");
                answerFileBuffer.append(count).append("、");
                answerFileBuffer.append(entry.getValue()).append("\r\n");
                count++;
            }
            exerciseFileOutputStream.write(exerciseBuffer.toString().getBytes());
            answerFileOutputStream.write(answerFileBuffer.toString().getBytes());
            exerciseFileOutputStream.close();
            answerFileOutputStream.close();
            System.out.println("操作成功！！！");
        }
        catch (IOException e) {
            System.out.println("文件操作异常，请重试");
        }


    }

    /**
     * 验证答案的正确率
     */
    public static void validateAnswerFile(String exerciseFileUrl, String answerFileUrl) {
        //SymbolConstant.PRINT_FILE_URL, exerciseFileUrl);
        java.io.File exerciseFile = new java.io.File(InputCheck.improvePath(exerciseFileUrl));
        java.io.File answerFile = new java.io.File(InputCheck.improvePath(answerFileUrl));
        java.io.File gradeFile = new java.io.File(Symbol.PRINT_FILE_URL, "Grade.txt");
        if (exerciseFile.isFile() && answerFile.isFile()) {
            BufferedReader exerciseReader = null;
            BufferedReader answerReader = null;
            OutputStream gradeFileOutputStream = null;
            List<Integer> Correct = new ArrayList<>();
            List<Integer> Wrong = new ArrayList<>();
            try {
                exerciseReader = new BufferedReader(new InputStreamReader(new FileInputStream(exerciseFile)));
                answerReader = new BufferedReader(new InputStreamReader(new FileInputStream(answerFile)));
                String exerciseStr;
                String answerStr;
                //记录行数
                int line = 0;

                int tag=0;
                System.out.println("开始验证...");
                while ((exerciseStr = exerciseReader.readLine()) != null && (answerStr = answerReader.readLine()) != null) {
                    //获取运算式的正确答案
                    StringBuffer answersBuffer =new StringBuffer(answerStr);
                    //消除答案文件中的序号

                    int count=1;
                    tag++;
                    int flag=tag;
                    while(flag/10!=0){
                        flag=flag/10;
                        count++;
                    }
                    for(int  i=0;i<=count;i++)
                    {
                        answersBuffer.deleteCharAt(0);
                    }
                    answerStr =answersBuffer.toString();

                    String realAnswer = Calculation.getExpressResult(exerciseStr);
                    if (realAnswer.equals(answerStr)) {
                        line++;
                        Correct.add(line);
                    } else {
                        line++;
                        Wrong.add(line);
                    }
                }
                String result = "Correct:" + Correct.size() + Correct + "\r\n" + "Wrong:" + Wrong.size() + Wrong;
                //保存成绩文件
                gradeFileOutputStream = new FileOutputStream(gradeFile);
                gradeFileOutputStream.write(result.getBytes());
                //打印结果
                System.out.print(result);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (exerciseReader != null) {
                    try {
                        exerciseReader.close();
                    } catch (IOException ignored) {
                    }
                }
                if (answerReader != null) {
                    try {
                        answerReader.close();
                    } catch (IOException ignored) {
                    }
                }
                if (gradeFileOutputStream != null) {
                    try {
                        gradeFileOutputStream.close();
                    } catch (IOException ignored) {
                    }
                }
            }

        } else {
            System.out.println("文件不存在！！！");
        }
    }
}