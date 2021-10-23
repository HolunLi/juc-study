package k10;

import java.util.function.Predicate;

/**
 * Predicate接口（断定型接口）
 * 有一个输入参数，返回值只能是boolean类型
 */
public class Test2 {
    public static void main(String[] args) {
        /*
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.isEmpty(); //判断字符串是否为空
            }
        };
        */

        //简写
        Predicate<String> predicate = str -> str.isEmpty();

        System.out.println(predicate.test("haha"));
    }
}
