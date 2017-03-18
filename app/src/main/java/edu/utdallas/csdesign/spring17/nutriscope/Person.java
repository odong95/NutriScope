package edu.utdallas.csdesign.spring17.nutriscope;



/**
 * Created by Lately on 3/8/2017.
 */

public class Person {

    String name;
    int age;

    public Person() {

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
        return "{Person(" + "name='" + name + "'"
                + ", age=" + age + ")}";
    }
}
