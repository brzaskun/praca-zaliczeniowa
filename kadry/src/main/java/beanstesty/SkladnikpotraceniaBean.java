/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Skladnikpotracenia;


/**
 *
 * @author Osito
 */
public class SkladnikpotraceniaBean {
    
    public static Skladnikpotracenia skladnikpotracenia;
    
    public static Skladnikpotracenia create() {
        if (skladnikpotracenia == null) {
            skladnikpotracenia = new Skladnikpotracenia();
            skladnikpotracenia.setNazwa("wynagrodzenie zasadnicze");
            skladnikpotracenia.setUmowa(UmowaBean.create());
        }
        return skladnikpotracenia;
    }
}
