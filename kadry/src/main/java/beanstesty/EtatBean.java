/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import dao.KalendarzmiesiacFacade;
import data.Data;
import entity.Dzien;
import entity.EtatPrac;
import entity.Kalendarzmiesiac;
import java.util.List;
import z.Z;

/**
 *
 * @author Osito
 */
public class EtatBean {
   
    
    public static void edytujkalendarz(EtatPrac selected, KalendarzmiesiacFacade kalendarzmiesiacFacade) {
        List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByAngaz(selected.getAngaz());
        for (Kalendarzmiesiac k : kalendarze) {
            boolean czyjestpo = Data.czyjestpo(selected.getDataod(), k.getRok(), k.getMc());
            boolean czyjestprzed = Data.czyjestprzed(selected.getDatado(), k.getRok(), k.getMc());
            if (czyjestpo&&czyjestprzed) {
                List<Dzien> dzienList = k.getDzienList();
                for (Dzien d : dzienList) {
                    boolean czysiemiesci = DataBean.czysiemiescidzien(d.getDatastring(), selected.getDataod(), selected.getDatado());
                    if (czysiemiesci) {
                        if (selected!=null) {
                            d.setEtat1(selected.getEtat1());
                            d.setEtat2(selected.getEtat2());
                            d.setNormagodzin(Z.z(d.getNormagodzinwzorcowa()*d.getEtat1()/d.getEtat2()));
                            d.setPrzepracowano(Z.z(d.getNormagodzin()));
                        }
                    }
                }
                kalendarzmiesiacFacade.edit(k);
            } else if (czyjestprzed==false) {
                    break;
            }
        }
    }
}
