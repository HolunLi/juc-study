package k12;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Test2 {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        EnumSingle instance1 = EnumSingle.INSTANCE;
        EnumSingle instance2 = EnumSingle.getInstance();
        System.out.println(instance1);
        System.out.println(instance2);

        Constructor<EnumSingle> constructor = EnumSingle.class.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        EnumSingle enumSingle = constructor.newInstance();
        System.out.println(enumSingle);
    }
}

enum EnumSingle {
    INSTANCE;

    public static EnumSingle getInstance() {
        return INSTANCE;
    }
}
