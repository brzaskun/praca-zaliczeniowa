/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

/**
 *
 * @author Osito
 */
public class Student {

    private String name;
    private double maths;
    private String science;
    private String english;

    public Student(){}

    public Student(String name, double maths, String science, String english) {
        this.name = name;
        this.maths = maths;
        this.science = science;
        this.english = english;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaths() {
        return maths;
    }

    public void setMaths(double maths) {
        this.maths = maths;
    }

   

    public String getScience() {
        return science;
    }

    public void setScience(String science) {
        this.science = science;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

   //getters and setter..

    @Override
    public String toString() {
        return name+ ": Maths "+maths+ " Science "+science+" English "+english;
    }
}
