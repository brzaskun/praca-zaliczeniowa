/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.OkresBean;
import beanstesty.PasekwynagrodzenBean;
import comparator.Etatcomparator;
import comparator.Pasekwynagrodzencomparator;
import comparator.Stanowiskocomparator;
import comparator.Umowacomparator;
import dao.AngazFacade;
import dao.PasekwynagrodzenFacade;
import dao.PracownikFacade;
import dao.SMTPSettingsFacade;
import dao.UmowaFacade;
import data.Data;
import embeddable.Okres;
import entity.Angaz;
import entity.EtatPrac;
import entity.FirmaKadry;
import entity.Pasekwynagrodzen;
import entity.Pracownik;
import entity.SMTPSettings;
import entity.Stanowiskoprac;
import entity.Umowa;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import pdf.PdfZaswiadczenieZarobki;
import z.Z;

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
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private SMTPSettingsFacade sMTPSettingsFacade;
    private String dataod;
    private String datado;
    private boolean zatrudnienie;
    private boolean mcwyplaty;
    private boolean zarobki;
    private String rodzajumowy;
    private String datarozpoczeciaostatnieumowy;
    private String datazakonczeniaostatnieumowy;
    private String czastrwania;
    private String stanowisko;
    private String etat;
    private double bruttosrednia;
    private double nettosrednia;
    private boolean czyjestkomornik;
    private List<Pasekwynagrodzen> paskiwynagrodzen;
    
    
    @PostConstruct
    public void init() {
        listaeast2 = angazFacade.findByFirmaAktywni(wpisView.getFirma());
        zatrudnienie = true;
        zarobki = true;
        String[] poprzedniokres = Data.poprzedniOkres(Data.aktualnaData());
        if (datado==null) {
            datado = Data.ostatniDzien(poprzedniokres[1], poprzedniokres[0]);
        }
        poprzedniokres = Data.poprzedniOkres(poprzedniokres[0], poprzedniokres[1]);
        poprzedniokres = Data.poprzedniOkres(poprzedniokres[0], poprzedniokres[1]);
        if (dataod==null) {
            dataod = Data.pierwszyDzien(poprzedniokres[1], poprzedniokres[0]);
        }
        Pracownik pracownik = wpisView.getPracownik();
        List<Umowa> umowy = umowaFacade.findByAngaz(wpisView.getAngaz());
        if (umowy !=null&&umowy.size()>0) {
            try {
                Collections.sort(umowy, new Umowacomparator());
                Umowa pierwsza = umowy.get(umowy.size()-1);
                Umowa ostatnia = umowy.get(0);
                //kiedys uzupelnial tylko jak data byla pusta, a przeciez powinien na biezaco
                    pracownik.setDatazatrudnienia(pierwsza.getDataod());
                    pracownik.setDatazwolnienia(ostatnia.getDatado());
                    pracownikFacade.edit(pracownik);

                rodzajumowy = ostatnia.getUmowakodzus().isPraca()?"umowa o pracę":"umowa zlecenia";
                czastrwania = ostatnia.getCzastrwania();
                datarozpoczeciaostatnieumowy = ostatnia.getDataod();
                datazakonczeniaostatnieumowy = ostatnia.getDatado();
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
                Object[] pdane = pobierzkarty(dataod, datado);
                bruttosrednia = (double) pdane[0];
                nettosrednia = (double) pdane[1];
                czyjestkomornik = (boolean) pdane[3];
                paskiwynagrodzen = (List<Pasekwynagrodzen>) pdane[2];
                if (paskiwynagrodzen!=null&&paskiwynagrodzen.size()>1) {
                    Pasekwynagrodzen suma = PasekwynagrodzenBean.sumujpaski(paskiwynagrodzen);
                    paskiwynagrodzen.add(suma);
                }
            } catch (Exception e) {}
        }
    }
    
    private Object[] pobierzkarty(String dataod, String datado) {
        Object[] zwrot = new Object[4];
        List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByAngaz(wpisView.getAngaz());
        List<Okres> okresylista = OkresBean.pobierzokresy(dataod, datado);
        Map<Okres, List<Pasekwynagrodzen>> okrespaski = new HashMap<>();
        if (paski!=null) {
            for (Okres ok : okresylista) {
                okrespaski.put(ok, new ArrayList<>());
            }
            double bruttosuma = 0.0;
            double nettosuma = 0.0;
            boolean czyjestkomornik= false;
            for (Okres ok : okresylista) {
                
                List<Pasekwynagrodzen> wybranyokres = okrespaski.get(ok);
                for (Pasekwynagrodzen pasek :paski) {
                    if (mcwyplaty==true&&pasek.getOkresWypl().equals(ok.getRokmc())) {
                        bruttosuma = bruttosuma + pasek.getBrutto();
                        nettosuma = nettosuma + pasek.getNetto();
                        czyjestkomornik = pasek.czyjestkomornik();
                        wybranyokres.add(pasek);
                    } else if (mcwyplaty==false&&pasek.getOkresNalezny().equals(ok.getRokmc())) {
                        bruttosuma = bruttosuma + pasek.getBrutto();
                        nettosuma = nettosuma + pasek.getNetto();
                        czyjestkomornik = pasek.czyjestkomornik();
                        wybranyokres.add(pasek);
                    }
                }
            }
            zwrot[0] = Z.z(bruttosuma/((double)okresylista.size()));
            zwrot[1] = Z.z(nettosuma/((double)okresylista.size()));
            List<Pasekwynagrodzen> listapaskow = new ArrayList<>();
            for (List<Pasekwynagrodzen> ala : okrespaski.values()) {
                listapaskow.addAll(ala);
            }
            Collections.sort(listapaskow, new Pasekwynagrodzencomparator());
            zwrot[2] = listapaskow;
            zwrot[3] = czyjestkomornik;
        }
        return zwrot;
    }
    
     public void drukuj () {
        if (wpisView.getPracownik()!=null) {
            Collections.sort(paskiwynagrodzen, new Pasekwynagrodzencomparator());
            ByteArrayOutputStream dra = PdfZaswiadczenieZarobki.drukujMini(wpisView.getFirma(), paskiwynagrodzen, wpisView.getPracownik(), dataod, datado, zatrudnienie,
                    zarobki, rodzajumowy, czastrwania, stanowisko, etat, bruttosrednia, nettosrednia, czyjestkomornik, datarozpoczeciaostatnieumowy, datazakonczeniaostatnieumowy);
        } else {
            Msg.msg("e","Błąd drukowania zaświadczenia.");
        }
    }
    
      public void mail() {
        ByteArrayOutputStream dra = PdfZaswiadczenieZarobki.drukuj(wpisView.getFirma(), paskiwynagrodzen, wpisView.getPracownik(), dataod, datado, 
                zatrudnienie, zarobki, rodzajumowy, czastrwania, stanowisko, etat, bruttosrednia, nettosrednia, czyjestkomornik, datarozpoczeciaostatnieumowy, datazakonczeniaostatnieumowy);
        if (dra != null && dra.size() > 0) {
            SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
             String nazwa = wpisView.getPracownik().getPesel() + "_zaswiadczenie_zarobki.pdf";
            mail.Mail.mailZaswiadczeniezarobki(wpisView.getFirma(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(), wpisView.getFirma().getEmail(), null, findSprawaByDef, dra.toByteArray(), nazwa, wpisView.getUzer().getEmail(), wpisView.getPracownik());
            Msg.msg("Wysłano listę płac do pracodawcy");
        } else {
            Msg.msg("e", "Błąd dwysyki DRA");
        }
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

    public double getBruttosrednia() {
        return bruttosrednia;
    }

    public void setBruttosrednia(double bruttosrednia) {
        this.bruttosrednia = bruttosrednia;
    }

    public double getNettosrednia() {
        return nettosrednia;
    }

    public void setNettosrednia(double nettosrednia) {
        this.nettosrednia = nettosrednia;
    }

    public boolean isCzyjestkomornik() {
        return czyjestkomornik;
    }

    public void setCzyjestkomornik(boolean czyjestkomornik) {
        this.czyjestkomornik = czyjestkomornik;
    }

    public List<Pasekwynagrodzen> getPaskiwynagrodzen() {
        return paskiwynagrodzen;
    }

    public void setPaskiwynagrodzen(List<Pasekwynagrodzen> paskiwynagrodzen) {
        this.paskiwynagrodzen = paskiwynagrodzen;
    }

    public boolean isMcwyplaty() {
        return mcwyplaty;
    }

    public void setMcwyplaty(boolean mcwyplaty) {
        this.mcwyplaty = mcwyplaty;
    }

    
}
