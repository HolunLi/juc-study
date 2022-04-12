package k11;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * 题目：一分钟内完成此题，只能用一行代码去实现
 * 现在有5个用户，去筛选
 * 1. ID 必须是偶数
 * 2. 年龄必须大于23岁
 * 3. 用户名转为大写字母
 * 4. 按用户名，倒序排序
 * 5. 只输出一个用户
 */
public class Test {
    public static void main(String[] args) {
        User user1 = new User(1, "a", 25);
        User user2 = new User(14, "b", 89);
        User user3 = new User(3, "c", 2);
        User user4 = new User(8, "d", 27);
        User user5 = new User(5, "f", 28);
        User user6 = new User(4, "g", 29);
        User user7 = new User(7, "h", 29);
        List<User> users = Arrays.asList(user1, user2, user3, user4, user5, user6, user7);
        for (User  user : users) {
            System.out.println(user);
        }

        System.out.println("经筛选后，输出的用户");
     /*   users.stream()
                .filter(u->u.getId() % 2 == 0)
                .filter(u->u.getAge() > 23)
                .map(u->{
                    u.setName(u.getName().toUpperCase());
                    return u;
                })
                .sorted((u1, u2)->{return u2.compareTo(u1);})
                .limit(1)
                .forEach(System.out ::println);*/
        users.stream()
                .filter(u -> u.getId() % 2 == 0 && u.getAge() > 23)
                .peek(u -> u.setName(u.getName().toUpperCase()))
                .sorted(Comparator.comparing(User::getName, Comparator.reverseOrder())) //Comparator.reverseOrder()等价于(u1, u2)-> u2.compareTo(u1)}
                .limit(1)
                .forEach(System.out::println);
    }
}

class User implements Comparable<User> {
    private int id;
    private String name;
    private int age;

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(User o) {
        return this.getName().compareTo(o.getName());
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}