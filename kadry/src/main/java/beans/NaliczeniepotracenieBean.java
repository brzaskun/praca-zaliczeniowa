/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Naliczeniepotracenie;

/**
 *
 * @author Osito
 */
public class NaliczeniepotracenieBean {
    
    public static Naliczeniepotracenie naliczeniepotracenie;
    
    public static Naliczeniepotracenie create() {
        if (naliczeniepotracenie == null) {
            naliczeniepotracenie = new Naliczeniepotracenie();
            naliczeniepotracenie.setKalendarzmiesiac(KalendarzmiesiacBean.create());
            naliczeniepotracenie.setKwota(2000.0);
            naliczeniepotracenie.setSkladnikpotracenia(SkladnikpotraceniaBean.create());
        }
        return naliczeniepotracenie;
    }
}
