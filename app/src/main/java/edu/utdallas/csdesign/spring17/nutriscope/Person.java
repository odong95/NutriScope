package edu.utdallas.csdesign.spring17.nutriscope;

import io.realm.RealmObject;

/**
 * Created by Lately on 3/8/2017.
 */

public class Person extends RealmObject {

    String name;
    int age;

    public Person()
    {

    }
    public String getName()
    {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{Person(" + "name='"+ name + "'"
                + ", age=" + age + ")}";
    }
}
