package edu.utdallas.csdesign.spring17.nutriscope.data.source.firebase;

/**
 * Created by john on 3/18/17.
 */

public class User {

    private String uid;
    private String email;
    private String nickname;
    private String age;
    private String sex;
    private String height;
    private String weight;
    private String activityLevel;
    private String calorieGoal;
    private String hft;
    private String hin;

    public User() {

    }

    public User(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    ;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    ;

    public String getAge() {
        return age;
    }

    ;

    public void setAge(String age) {
        this.age = age;
    }

    ;

    public String getSex() {
        return sex;
    }

    ;

    public void setSex(String sex) {
        this.sex = sex;
    }

    ;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public String getCalorieGoal() {
        return calorieGoal;
    }

    public void setCalorieGoal(String calorieGoal) {
        this.calorieGoal = calorieGoal;
    }

    public String getHft(){return hft;};

    public void setHft(String hft){this.hft = hft;};

    public String getHin(){return hin;};

    public void setHin(String hin){this.hin = hin;};

}
