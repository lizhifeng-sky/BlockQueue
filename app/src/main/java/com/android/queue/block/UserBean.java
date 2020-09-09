package com.android.queue.block;

/**
 * @author lizhifeng
 * @date 2020/9/9 15:29
 */
public class UserBean {
    private String name;
    private String age;

    public UserBean(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
