package cn.sspku.data.utils;

import java.util.Objects;

/**
 * 判断为空工具类
 * @author
 */
public class sspkuNullUtils {
    public static boolean isNull(String str){
        if(str == null || Objects.equals("",str) || Objects.equals("null",str) || Objects.equals("undefined",str)) {
            return true;
        }
        return false;
    }
}
