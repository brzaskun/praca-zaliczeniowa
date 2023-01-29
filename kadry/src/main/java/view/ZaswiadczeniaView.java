/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.OkresBean;
import comparator.Etatcomparator;
import comparator.Stanowiskocomparator;
import comparator.Umowacomparator;
import dao.AngazFacade;
import dao.PracownikFacade;
import dao.UmowaFacade;
import data.Data;
import embeddable.Okres;
import entity.Angaz;
import entity.EtatPrac;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Kartawynagrodzen;
import entity.Pracownik;
import entity.Stanowiskoprac;
import entity.Umowa;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ZaswiadczeniaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private List<Angaz> listaeast2;
    private Angaz selectedeast;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private PracownikFacade pracownikFacade;
    private String dataod;
    private String datado;
    private boolean zatrudnienie;
    private boolean zarobki;
    private String rodzajumowy;
    private String czastrwania;
    private String stanowisko;
    private String etat;
    private List<Kartawynagrodzen> paski;
    
    @PostConstruct
    public void init() {
        listaeast2 = angazFacade.findByFirmaAktywni(wpisView.getFirma());
        zatrudnienie = true;
        zarobki = true;
        String[] poprzedniokres = Data.poprzedniOkres(Data.aktualnaData());
        datado = Data.ostatniDzien(poprzedniokres[1], poprzedniokres[0]);
        poprzedniokres = Data.poprzedniOkres(poprzedniokres[0], poprzedniokres[1]);
        poprzedniokres = Data.poprzedniOkres(poprzedniokres[0], poprzedniokres[1]);
        dataod = Data.pierwszyDzien(poprzedniokres[1], poprzedniokres[0]);
        Pracownik pracownik = wpisView.getPracownik();
        List<Umowa> umowy = umowaFacade.findByAngaz(wpisView.getAngaz());
        if (umowy !=null) {
            Collections.sort(umowy, new Umowacomparator());
            Umowa pierwsza = umowy.get(umowy.size()-1);
            Umowa ostatnia = umowy.get(0);
            if (pracownik.getDatazatrudnienia()==null||pracownik.getDatazatrudnienia().equals("")) {
                pracownik.setDatazatrudnienia(pierwsza.getDataod());
                pracownik.setDatazwolnienia(ostatnia.getDatado());
                pracownikFacade.edit(pracownik);
            }
            rodzajumowy = ostatnia.getUmowakodzus().isPraca()?"umowa o pracę":"umowa zlecenia";
            czastrwania = ostatnia.getCzastrwania();
            List<EtatPrac> etatList = wpisView.getAngaz().getEtatList();
            if (etatList!=null&&etatList.size()==1) {
                EtatPrac etata = etatList.get(0);
                etat = etata.getEtat();
            } else if (etatList!=null&&etatList.size()>1) {
                Collections.sort(etatList, new Etatcomparator());
                EtatPrac etata = etatList.get(0);
                etat = etata.getEtat();
            }
            List<Stanowiskoprac> stanowiskopracList = wpisView.getAngaz().getStanowiskopracList();
            if (stanowiskopracList!=null&&stanowiskopracList.size()==1) {
                Stanowiskoprac stano = stanowiskopracList.get(0);
                stanowisko = stano.getOpis();
            } else if (stanowiskopracList!=null&&stanowiskopracList.size()>1) {
                Collections.sort(stanowiskopracList, new Stanowiskocomparator());
                Stanowiskoprac stano = stanowiskopracList.get(0);
                stanowisko = stano.getOpis();
            }
            paski = pobierzkarty(dataod, datado);
            System.out.println("");
        }
    }
    
    private List<Kartawynagrodzen> pobierzkarty(String dataod, String datado) {
        List<Kalendarzmiesiac> kalendarzmiesiacList = wpisView.getAngaz().getKalendarzmiesiacList();
        List<Okres> okresylista = OkresBean.pobierzokresy(dataod, datado);
        return null;
    }
    
    
    
    public void aktywujPracAngaze(FirmaKadry firma) {
        if (firma!=null) {
            wpisView.setFirma(firma);
            if (firma.getAngazList()==null||firma.getAngazList().isEmpty()) {
                wpisView.setPracownik(null);
                wpisView.setAngaz(null);
                wpisView.setUmowa(null);
            } else {
                Angaz angaz = firma.getAngazList().get(0);
                wpisView.setPracownik(angaz.getPracownik());
                wpisView.setAngaz(angaz);
                List<Umowa> umowy = angaz.getUmowaList();
                if (umowy!=null && umowy.size()==1) {
                    wpisView.setUmowa(umowy.get(0));
                } else if (umowy!=null) {
                    try {
                        wpisView.setUmowa(umowy.stream().filter(p->p.isAktywna()).findFirst().get());
                    } catch (Exception e){}
                }
            }
            init();
            Msg.msg("Aktywowano firmę "+firma.getNazwa());
        }
    }
    
    public void aktywuj(Angaz angaz) {
        if (angaz!=null) {
            wpisView.setAngaz(angaz);
            wpisView.setPracownik(angaz.getPracownik());
            List<Umowa> umowy = wpisView.getAngaz().getUmowaList();
            if (umowy==null) {
                try {
                    umowy = umowaFacade.findByAngaz(angaz);
                } catch (Exception ex){}
            }
            if (umowy!=null && umowy.size()==1) {
                wpisView.setUmowa(umowy.get(0));
            } else if (umowy!=null&&!umowy.isEmpty()) {
                Umowa umowaaktywna = null;
                Optional badanie  = umowy.stream().filter(p->p.isAktywna()).findFirst();
                if (badanie.isPresent()) {
                    umowaaktywna = (Umowa) badanie.get();
                }
                if (umowaaktywna==null) {
                    Collections.sort(umowy, new Umowacomparator());
                    umowaaktywna = umowy.get(0);
                    umowaaktywna.setAktywna(true);
                    umowaFacade.edit(umowaaktywna);
                }
                wpisView.setUmowa(umowaaktywna);
            } else {
                Msg.msg("e","Nie ma żadnej umowy do angażu");
                wpisView.setUmowa(null);
                System.out.println("Nie pobrano umów do angażu");
            }
            init();
            Msg.msg("Aktywowano pracownika");
        }
    }

    public List<Angaz> getListaeast2() {
        return listaeast2;
    }

    public void setListaeast2(List<Angaz> listaeast2) {
        this.listaeast2 = listaeast2;
    }

    public Angaz getSelectedeast() {
        return selectedeast;
    }

    public void setSelectedeast(Angaz selectedeast) {
        this.selectedeast = selectedeast;
    }

    public String getDataod() {
        return dataod;
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }

    public boolean isZatrudnienie() {
        return zatrudnienie;
    }

    public void setZatrudnienie(boolean zatrudnienie) {
        this.zatrudnienie = zatrudnienie;
    }

    public boolean isZarobki() {
        return zarobki;
    }

    public void setZarobki(boolean zarobki) {
        this.zarobki = zarobki;
    }

    public String getRodzajumowy() {
        return rodzajumowy;
    }

    public void setRodzajumowy(String rodzajumowy) {
        this.rodzajumowy = rodzajumowy;
    }

    public String getCzastrwania() {
        return czastrwania;
    }

    public void setCzastrwania(String czastrwania) {
        this.czastrwania = czastrwania;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getStanowisko() {
        return stanowisko;
    }

    public void setStanowisko(String stanowisko) {
        this.stanowisko = stanowisko;
    }

    
}
