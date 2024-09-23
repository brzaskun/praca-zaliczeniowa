/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import comparator.KalendarzmiesiacRMNormalcomparator;
import dao.KalendarzmiesiacFacade;
import data.Data;
import entity.Angaz;
import entity.Dzien;
import entity.EtatPrac;
import entity.Kalendarzmiesiac;
import java.util.Collections;
import java.util.List;
import z.Z;

/**
 *
 * @author Osito
 */
public class EtatBean {
   
    
    public static void edytujkalendarz(EtatPrac selected, KalendarzmiesiacFacade kalendarzmiesiacFacade) {
        List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByAngaz(selected.getAngaz());
        Collections.sort(kalendarze, new KalendarzmiesiacRMNormalcomparator());
        for (Kalendarzmiesiac k : kalendarze) {
            boolean czyjestpo = Data.czyjestpo(selected.getDataod(), k.getRok(), k.getMc());
            boolean czyjestprzed = Data.czyjestprzed(selected.getDatado(), k.getRok(), k.getMc());
            if (czyjestpo&&czyjestprzed) {
                List<Dzien> dzienList = k.getDzienList();
                for (Dzien d : dzienList) {
                    boolean czysiemiesci = DataBean.czysiemiescidzien(d.getDatastring(), selected.getDataod(), selected.getDatado());
                    String datado = selected.getDatado()!=null&&selected.getDatado().length()==10?selected.getDatado():k.getOstatniDzien();
                    boolean pozaumowapoczatekmiesiaca = d.getKod()!=null&&d.getKod().equals("D")&&DataBean.czysiemiescidzien(d.getDatastring(), Data.pierwszyDzien(selected.getDataod()), datado);
                    boolean pozaumowakoniecmiesiaca = d.getKod()!=null&&d.getKod().equals("D")&&DataBean.czysiemiescidzien(d.getDatastring(), selected.getDataod(), datado);
                    if (czysiemiesci||pozaumowapoczatekmiesiaca||pozaumowakoniecmiesiaca) {
                        if (selected!=null) {
                            d.setEtat1(selected.getEtat1());
                            d.setEtat2(selected.getEtat2());
                            double nowanorma = Z.z(d.getNormagodzinwzorcowa()*selected.getEtat1()/selected.getEtat2());
                            d.setNormagodzin(nowanorma);
                            if(d.getPrzepracowano()>0 && d.getPrzepracowano()!=nowanorma) {
                                d.setPrzepracowano(nowanorma);
                            }
                            if (d.getWynagrodzeniezachorobe()>0 && d.getWynagrodzeniezachorobe()!=nowanorma) {
                                d.setWynagrodzeniezachorobe(nowanorma);
                            }
                            if (d.getZasilek()>0 && d.getZasilek()!=nowanorma) {
                                d.setZasilek(nowanorma);
                            }
                            if (d.getOpiekadziecko()>0 && d.getOpiekadziecko()!=nowanorma) {
                                d.setOpiekadziecko(nowanorma);
                            }
                            if (d.getUrlopPlatny()>0 && d.getUrlopPlatny()!=nowanorma) {
                                d.setUrlopPlatny(nowanorma);
                            }
                            if (d.getUrlopbezplatny()>0 && d.getUrlopbezplatny()!=nowanorma) {
                                d.setUrlopbezplatny(nowanorma);
                            }
                            if (d.getPoranocna()>0 && d.getPoranocna()!=nowanorma) {
                                d.setPoranocna(nowanorma);
                            }
                            if (d.getOpiekadziecko()>0 && d.getOpiekadziecko()!=nowanorma) {
                                d.setOpiekadziecko(nowanorma);
                            }
                            if (d.getPoranocna()>0 && d.getPoranocna()!=nowanorma) {
                                d.setPoranocna(nowanorma);
                            }
                            if (d.getMacierzynski()>0 && d.getMacierzynski()!=nowanorma) {
                                d.setMacierzynski(nowanorma);
                            }
                            if (d.getWychowawczy()>0 && d.getWychowawczy()!=nowanorma) {
                                d.setWychowawczy(nowanorma);
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
                        if (Data.czyjestpoTerminData(datagranicznaod, data)) {
                            zwrot = p;
              
                        }
                    } else {
                        if (DataBean.czysiemiescidzien(data, datagranicznaod, datagranicznado)) {
                            zwrot = p;
                        }
                    }
                }
            }
            //musi byc jakis etat bo nie pokazuje nic dla zwolnionych i zamknietych na podgladzie wykorzystanego urlopu
                    //01.07.2023
                    //i musi byc w tym miejscu a nie u gory
            if (zwrot==null&&etatList1!=null&&etatList1.size()==1) {
                zwrot = etatList1.get(0);
            }
        }
        return zwrot;
    }
}
