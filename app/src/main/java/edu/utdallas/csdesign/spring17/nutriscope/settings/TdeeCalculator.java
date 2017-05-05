package edu.utdallas.csdesign.spring17.nutriscope.settings;


import android.net.ParseException;
import android.text.TextUtils;

public class TdeeCalculator
{

    private String gender = "";
    private double weight = 0,height = 0,age = 0;
    private double activityLvl = 0;
    public TdeeCalculator()
    {

    }

    public String getTEE()
    {
        if(gender.equals("Male"))
        {
            return Integer.toString((int)((10 * weight + 6.25 * height - 5 * age + 5)*activityLvl));
        }
        else
        {
            return Integer.toString((int)((10 * weight + 6.25 * height - 5 * age - 161)*activityLvl));
        }

    }

    public void setGender(String gender){
            this.gender = gender;
    }

    public void setHeight(String ft, String in)
    {
        if(!TextUtils.isEmpty(ft) && !TextUtils.isEmpty(in)) {
            double c = Integer.parseInt(ft) * 30.48;
            this.height = c + (Integer.parseInt(in) * 2.54);
        }

    }

    public void setWeight(String weight)
    {
            this.weight= (Integer.parseInt(weight) * 0.453592);
    }

    public void setAge(String age)
    {
        this.age = Integer.parseInt(age);
    }

    public void setActivityLevel(String lvl)
    {
        this.activityLvl = Double.parseDouble(lvl);
    }

    public boolean isFilled()
    {
        if(!TextUtils.isEmpty(gender) && age != 0 && height != 0 && weight != 0 && activityLvl != 0)
        {
            return true;
        }
        return false;
    }

    public static double getTdee(String ft, String in, String age, String w,
                                 String activityLevel, String sex) {


        try {
            double height = Integer.parseInt(ft) * 30.48 + (Integer.parseInt(in) * 2.54);
            double weight = Double.parseDouble(w) * 0.453592;
            double dAge = Double.parseDouble(age);
            double dActivityLevel = Double.parseDouble(activityLevel);

        if (height == 0.0 || weight == 0.0 || dAge == 0.0 || dActivityLevel == 0.0 || (!sex.equals("Male") && !sex.equals("Female"))) {
            throw new IllegalArgumentException("argument 0 or not m/f");
        }


        if(sex.equals("Male"))
        {
            return 10 * weight + 6.25 * height - 5 * dAge + 5 * dActivityLevel;
        }
        else
        {
            return 10 * weight + 6.25 * height - 5 * dAge - 161 * dActivityLevel;
        }

        } catch(NumberFormatException ex) {
            throw new IllegalArgumentException("user not right data");
        } catch(NullPointerException ex) {
            throw new IllegalArgumentException("NPE");
        }



    }

}
