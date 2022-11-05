/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Angaz;
import java.util.ArrayList;

/**
 *
 * @author Osito
 */
public class AngazBean {
    
    public static Angaz angaz;
    
    public static Angaz create() {
        if (angaz==null) {
            angaz = new Angaz();
            angaz.setFirma(FirmaBean.create());
            angaz.setPracownik(PracownikBean.create());
            angaz.setSkladnikwynagrodzeniaList(new ArrayList<>());
            angaz.getSkladnikwynagrodzeniaList().add(SkladnikwynagrodzeniaBean.createWynagrodzenie());
            //umowa.getSkladnikwynagrodzeniaList().add(SkladnikwynagrodzeniaBean.createPremiaUznaniowa());
            angaz.setSkladnikpotraceniaList(new ArrayList<>());
            angaz.getSkladnikpotraceniaList().add(SkladnikpotraceniaBean.create());
        }
        return angaz;
    }
}
