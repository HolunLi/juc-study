package k10;

import java.util.function.Function;

/**
 * 函数式接口（接口中只有一个抽象方法的接口），函数式接口可以使用lambda表达式简化
 * 函数式接口分为四大类：Function接口（函数型接口）、Predicate接口（断定型接口）、Supplier接口（供给型接口）和 Consumer接口（消费型接口）
 */
public class Test {
    public static void main(String[] args) {
        /**
         * Function 函数型接口, 有一个输入参数，有一个输出参数
         */

        /*
        Function<String, Object> function = new Function<String, Object>() { //String作为方法的参数类型（输入参数），Object作为方法返回值类型（输出类型）
            @Override
            public Object apply(String s) {
                return s;
            }
        };
        */

        //简化
        Function<String, Object> function = str -> str;

        System.out.println(function.apply("haha"));
    }
}
