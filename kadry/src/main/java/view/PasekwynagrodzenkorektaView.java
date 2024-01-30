/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.Pasekpomocnik;
import beanstesty.PasekwynagrodzenBean;
import comparator.Pasekwynagrodzencomparator;
import comparator.Pracownikcomparator;
import dao.PasekwynagrodzenFacade;
import dao.PodatkiFacade;
import embeddable.PitKorektaNiemcy;
import entity.FirmaKadry;
import entity.Pasekwynagrodzen;
import entity.Podatki;
import entity.Pracownik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import z.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PasekwynagrodzenkorektaView  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    @Inject
    private PodatkiFacade podatkiFacade;
    private TreeMap<Pracownik, List<Pasekwynagrodzen>> pracownicyzpaskami;
    private List<Pasekwynagrodzen> paskiwybranego;
    @Inject
    private WpisView wpisView;
    private boolean dialogOtwarty;
    private Pracownik selectedpracownik;
    private Pasekwynagrodzen selectedpasek;
    private PitKorektaNiemcy pitKorektaNiemcy;
    private PitKorektaNiemcy pitKorektaNiemcyFirma;
    private List<PitKorektaNiemcy> listpitKorektaNiemcy;
    
    public void open() {
        dialogOtwarty = true;
    }
    public void close() {
        dialogOtwarty = false;
    }
    
    public void reloadDialog() {
        boolean zwrot = false;
        if (dialogOtwarty) {
            init();
        }
    }
   
    public void init() {
        String rok = wpisView.getRokWpisu();
        FirmaKadry firma = wpisView.getFirma();
        List<Pasekwynagrodzen> listapaski = pasekwynagrodzenFacade.findByRokWyplFirma(rok, firma);
        Predicate<Pasekwynagrodzen> isQualified = item->item.getAngaz().getPrzekroczenierok()==null;
        listapaski.removeIf(isQualified);
        isQualified = item->item.getAngaz().getPrzekroczenierok().equals("");
        listapaski.removeIf(isQualified);
        Collections.sort(listapaski, new Pasekwynagrodzencomparator());
        Map<Pracownik, List<Pasekwynagrodzen>> pracownicyzpask = listapaski.stream().collect(Collectors.groupingBy(Pasekwynagrodzen::getPracownik));
        pracownicyzpaskami = new TreeMap<>(new Pracownikcomparator());
        pracownicyzpaskami.putAll(pracownicyzpask);
        
        System.out.println("");
    }
    
    public void pobierzfirme() {
        listpitKorektaNiemcy = new ArrayList<>();
        pitKorektaNiemcyFirma = new PitKorektaNiemcy();
        if (pracownicyzpaskami.isEmpty()==false) {
            for (Pracownik pracownik : pracownicyzpaskami.keySet()) {
                selectedpracownik = pracownik;
                pobierzdane();
                listpitKorektaNiemcy.add(pitKorektaNiemcy);
                pitKorektaNiemcyFirma.sumuj(pitKorektaNiemcy);
            }
        }
    }
    
    public void pobierzdane() {
        if (selectedpracownik!=null) {
            paskiwybranego = pracownicyzpaskami.get(selectedpracownik);
            List<Podatki> stawkipodatkowe = podatkiFacade.findByRokUmowa(wpisView.getRokWpisu(), "P");
            pitKorektaNiemcy = new PitKorektaNiemcy();
            Pasekwynagrodzen paseksuma = new Pasekwynagrodzen("2023","13");
            for (Iterator<Pasekwynagrodzen> it = paskiwybranego.iterator(); it.hasNext();) {
                Pasekwynagrodzen p = it.next();
                if (p.getRodzajWynagrodzenia()!=1006) {
                    it.remove();
                }
            }
            for (Pasekwynagrodzen pasek : paskiwybranego) {
                //omijamy zasilki
                if (pasek.getKalendarzmiesiac()!=null&&pasek.getRodzajWynagrodzenia()!=1006) {
                    //adżornamiento do sytuacji od listopad 2023
                    Pasekpomocnik sumujprzychodyzlisty = PasekwynagrodzenBean.sumujprzychodyzlisty(pasek);
                    pasek.naniespomocnika(sumujprzychodyzlisty, pasek.isPrzekroczenieoddelegowanie());
                    PasekwynagrodzenBean.razemspolecznepracownikkorektalp(pasek);
                    try {
                        if (pasek.getPodatekdochodowyzagranicawaluta()>0.0) {
                           pasek.setPrzekroczenieoddelegowanie(true);
                           pasekwynagrodzenFacade.edit(pasek);
                        }
                        
                    } catch (Exception e) {
                        String nazwisko = pasek.getKalendarzmiesiac()!=null&&pasek.getAngaz()!=null?pasek.getNazwiskoImie():"brak nazwiska";
                        System.out.println("Blad save korekta niemcy pasek"+nazwisko);
                    }
                    if (pasek.isPrzekroczenieoddelegowanie()) {
                        double nowespoleczne = Z.z(pasek.getSpoleczneudzialpolska());
                        //pitKorektaNiemcy.setAngaz(pasek.getAngaz());
                        //pitKorektaNiemcy.dodajstare(pasek);
                        if (pasek.isPrzekroczenieoddelegowanie()) {
                            //musi byc tak bo inaczej zasilki traktyuje jak zlecenie
                            if (pasek.isZlecenie()==false) {
                                double nowapodstawaumowaoprace = Z.z(pasek.getPrzychodypodatekpolska()-nowespoleczne-pasek.getKosztyuzyskania());
                                pasek.setPrzekroczeniekorektapodstawypolska(nowapodstawaumowaoprace);
                                obliczpodatekwstepnyDBStandard(pasek, nowapodstawaumowaoprace, stawkipodatkowe, 0.0);
                            } else if (pasek.isDo26lat()==false) {
                                double nowapodstawaumowazlecenia = Z.z(pasek.getPrzychodypodatekpolska()-nowespoleczne);
                                double przekroczeniekoszt = Z.z(nowapodstawaumowazlecenia*0.2);
                                pasek.setPrzekroczeniekosztyuzyskania(przekroczeniekoszt);
                                nowapodstawaumowazlecenia = Z.z(nowapodstawaumowazlecenia-pasek.getPrzekroczeniekosztyuzyskania());
                                pasek.setPrzekroczeniekorektapodstawypolska(nowapodstawaumowazlecenia);
                                obliczpodatekwstepnyZlecenieDB(pasek, stawkipodatkowe, pasek.isNierezydent());
                            }
                            pasek.setPrzekroczeniepodstawaniemiecka(pasek.getOddelegowaniewaluta());
                            //pitKorektaNiemcy.dodajnowe(pasek);

                        } else {
                            pasek.setPrzekroczeniekorektapodstawypolska(0.0);
                            pasek.setPrzekroczenienowypodatek(0.0);
                            pasek.setPrzekroczeniepodstawaniemiecka(0.0);
                            pasek.setPrzekroczeniepodatekniemiecki(0.0);
                            pasekwynagrodzenFacade.edit(pasek);
                        }
                    } else if (pasek.isPrzekroczenieoddelegowanie()&&(pasek.getPodstawaopodatkowaniazagranicawaluta()>1289.0 &&pasek.getPodatekdochodowyzagranicawaluta()==0.0)) {
                        pasek.setPrzekroczeniepodstawaniemiecka(pasek.getPodstawaopodatkowaniazagranicawaluta());
                    } else if (pasek.getPodatekdochodowyzagranicawaluta()>0.0||(pasek.getPodstawaopodatkowaniazagranicawaluta()<1289.0 &&pasek.getPodatekdochodowyzagranicawaluta()==0.0)) {
                            pasek.setPrzekroczeniekorektapodstawypolska(0.0);
                            pasek.setPrzekroczenienowypodatek(0.0);
                            pasek.setPrzekroczeniepodstawaniemiecka(0.0);
                            pasek.setPrzekroczeniepodatekniemiecki(0.0);
                            pasekwynagrodzenFacade.edit(pasek);
                    }
                    if (pasek.getPrzychodypodatekpolska()==0.0) {

                    }
                    paseksuma.dodajPasek(pasek);
                    pasekwynagrodzenFacade.edit(pasek);
                }
            }
            //pitKorektaNiemcy.roznica();
            paskiwybranego.add(paseksuma);
        }
    }

    private static void obliczpodatekwstepnyDBStandard(Pasekwynagrodzen pasek, double podstawaopodatkowania, List<Podatki> stawkipodatkowe, double sumapoprzednich) {
        //double kwotawolna = pasek.getKwotawolna()>0.0? stawkipodatkowe.get(0).getWolnamc():0.0;
        double kwotawolna = stawkipodatkowe.get(0).getWolnamc();
        double podatek = Z.z0(Z.z0(podstawaopodatkowania) * stawkipodatkowe.get(0).getStawka());
        double drugiprog = stawkipodatkowe.get(0).getKwotawolnado();
        if (sumapoprzednich >= drugiprog) {
            podatek = Z.z(Z.z0(podstawaopodatkowania) * stawkipodatkowe.get(1).getStawka());
            pasek.setDrugiprog(true);
        } else if (sumapoprzednich < drugiprog) {
            double razemzbiezacym = sumapoprzednich + podstawaopodatkowania;
            if (razemzbiezacym > drugiprog) {
                double podatekdol = Z.z(Z.z0(drugiprog - sumapoprzednich) * stawkipodatkowe.get(0).getStawka());
                double podatekgora = Z.z(Z.z0(razemzbiezacym - drugiprog) * stawkipodatkowe.get(1).getStawka());
                podatek = podatekdol + podatekgora;
                pasek.setDrugiprog(true);
            } else {
                podatek = Z.z0(Z.z0(podstawaopodatkowania) * stawkipodatkowe.get(0).getStawka());
            }
        }
        if (podatek<=kwotawolna) {
            podatek = 0.0;
        } else {
            podatek = podatek-kwotawolna;
        }
        if (pasek.isDo26lat()) {
            podatek = 0.0;
        } 
        pasek.setPrzekroczenienowypodatek(podatek);
    }
    
     private static void obliczpodatekwstepnyZlecenieDB(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean nierezydent) {
        double kwotawolna = pasek.getKwotawolna()>0.0? stawkipodatkowe.get(0).getWolnamc():0.0;
        double podatek = Z.z0(Z.z0(pasek.getPrzekroczeniekorektapodstawypolska()) * stawkipodatkowe.get(0).getStawka());
        if (nierezydent) {
            podatek = Z.z0(Z.z0(pasek.getBrutto()) * 0.2);
        } else if (pasek.isDo26lat()) {
            podatek = 0.0;
        } 
        if (podatek<=kwotawolna) {
            podatek = 0.0;
        } else {
            podatek = podatek-kwotawolna;
        }
        pasek.setPrzekroczenienowypodatek(podatek);
    }
     
        
     public void editpasek(Pasekwynagrodzen pasek) {
        if (pasek != null) {
               pasekwynagrodzenFacade.edit(pasek);
               Msg.dP();
        }
    }
    
    public TreeMap<Pracownik, List<Pasekwynagrodzen>> getPracownicyzpaskami() {
        return pracownicyzpaskami;
    }

    public void setPracownicyzpaskami(TreeMap<Pracownik, List<Pasekwynagrodzen>> pracownicyzpaskami) {
        this.pracownicyzpaskami = pracownicyzpaskami;
    }

   

    public Pracownik getSelectedpracownik() {
        return selectedpracownik;
    }

    public void setSelectedpracownik(Pracownik selectedpracownik) {
        this.selectedpracownik = selectedpracownik;
    }

    public List<Pasekwynagrodzen> getPaskiwybranego() {
        return paskiwybranego;
    }

    public void setPaskiwybranego(List<Pasekwynagrodzen> paskiwybranego) {
        this.paskiwybranego = paskiwybranego;
    }

    public Pasekwynagrodzen getSelectedpasek() {
        return selectedpasek;
    }

    public void setSelectedpasek(Pasekwynagrodzen selectedpasek) {
        this.selectedpasek = selectedpasek;
    }

    public PitKorektaNiemcy getPitKorektaNiemcy() {
        return pitKorektaNiemcy;
    }

    public void setPitKorektaNiemcy(PitKorektaNiemcy pitKorektaNiemcy) {
        this.pitKorektaNiemcy = pitKorektaNiemcy;
    }

    public List<PitKorektaNiemcy> getListpitKorektaNiemcy() {
        return listpitKorektaNiemcy;
    }

    public void setListpitKorektaNiemcy(List<PitKorektaNiemcy> listpitKorektaNiemcy) {
        this.listpitKorektaNiemcy = listpitKorektaNiemcy;
    }

    public PitKorektaNiemcy getPitKorektaNiemcyFirma() {
        return pitKorektaNiemcyFirma;
    }

    public void setPitKorektaNiemcyFirma(PitKorektaNiemcy pitKorektaNiemcyFirma) {
        this.pitKorektaNiemcyFirma = pitKorektaNiemcyFirma;
    }

   
    
    
    
}
