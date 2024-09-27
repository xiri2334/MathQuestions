package utils;

import Symbol.Symbol;

import java.io.File;

public class InputCheck {
    /*
     * @Description: 参数检查 只有两种合法输入
     * @param command
     * @return: 返回定好顺序的命令数组
     */
   public static String[] checkParams(String command) {
        String[] s = command.split(" ");

        if (s.length == 4) {
            String first = s[0];
            String third = s[2];
            
            if (("-n".equals(first) && "-r".equals(third)) || ("-e".equals(first) && "-a".equals(third))) return s;
            else return null;
        }
        return  null;
    }

    /*
     * @Description: 对绝对路径和相对路径的支持
     * @param path
     * @return: 正确路径
     */
    public static String improvePath(String path) {
        //替换分隔符
        if (path.contains("/")){
            path = path.replace("/", File.separator);
        }
        String p;
        //绝对路径
        if (path.indexOf(":") > 0) {
            p = path;
        } else {//相对路径
            p = Symbol.PRINT_FILE_URL + File.separator + path;
        }
        return p;
    }
}
