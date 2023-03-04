/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import dao.KalendarzmiesiacFacade;
import data.Data;
import entity.Angaz;
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
                            double nowanorma = Z.z(d.getNormagodzinwzorcowa()*d.getEtat1()/d.getEtat2());
                            d.setNormagodzin(nowanorma);
                            if(d.getPrzepracowano()>0 && d.getPrzepracowano()!=nowanorma) {
                                d.setPrzepracowano(Z.z(d.getNormagodzin()));
                            }
                            if (d.getOpiekadziecko()>0 && d.getOpiekadziecko()!=nowanorma) {
                                d.setOpiekadziecko(d.getOpiekadziecko()*d.getEtat1()/d.getEtat2());
                            }
                            if (d.getUrlopPlatny()>0 && d.getUrlopPlatny()!=nowanorma) {
                                d.setUrlopPlatny(d.getUrlopPlatny()*d.getEtat1()/d.getEtat2());
                            }
                            if (d.getUrlopbezplatny()>0 && d.getUrlopbezplatny()!=nowanorma) {
                                d.setUrlopbezplatny(d.getUrlopbezplatny()*d.getEtat1()/d.getEtat2());
                            }
                            if (d.getPoranocna()>0 && d.getPoranocna()!=nowanorma) {
                                d.setPoranocna(d.getPoranocna()*d.getEtat1()/d.getEtat2());
                            }
                            if (d.getOpiekadziecko()>0 && d.getOpiekadziecko()!=nowanorma) {
                                d.setOpiekadziecko(d.getOpiekadziecko()*d.getEtat1()/d.getEtat2());
                            }
                            if (d.getPoranocna()>0 && d.getPoranocna()!=nowanorma) {
                                d.setPoranocna(d.getPoranocna()*d.getEtat1()/d.getEtat2());
                            }
                            if (d.getMacierzynski()>0 && d.getMacierzynski()!=nowanorma) {
                                d.setMacierzynski(d.getMacierzynski()*d.getEtat1()/d.getEtat2());
                            }
                            if (d.getWychowawczy()>0 && d.getWychowawczy()!=nowanorma) {
                                d.setWychowawczy(d.getWychowawczy()*d.getEtat1()/d.getEtat2());
                            }
                        }
                    }
                }
                kalendarzmiesiacFacade.edit(k);
            } else if (czyjestprzed==false) {
                    break;
            }
        }
    }
    
    public static EtatPrac pobierzetat(Angaz angaz, String data) {
       EtatPrac zwrot = null;
        List<EtatPrac> etatList1 = angaz.getEtatList();
        if (etatList1!=null) {
            if (etatList1.size()==1) {
                zwrot = etatList1.get(0);
            } else {
                for (EtatPrac p : etatList1) {
                    String datagranicznaod = p.getDataod();
                    String datagranicznado = p.getDataod()==null||p.getDatado().equals("")? null : p.getDatado();
                    if (datagranicznado==null) {
                        if (Data.czyjestpo(datagranicznaod, data)) {
                            zwrot = p;
                        }
                    } else {
                        if (DataBean.czysiemiescidzien(data, datagranicznaod, datagranicznado)) {
                            zwrot = p;
                        }
                    }
                }
            }
        }
        return zwrot;
    }
}
