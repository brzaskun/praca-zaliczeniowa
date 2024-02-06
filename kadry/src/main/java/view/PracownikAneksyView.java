/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AngazFacade;
import dao.DokumentyFacade;
import dao.SMTPSettingsFacade;
import dao.UmowaFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import data.Data;
import entity.Angaz;
import entity.Dokumenty;
import entity.FirmaKadry;
import entity.Rodzajwynagrodzenia;
import entity.SMTPSettings;
import entity.Skladnikwynagrodzenia;
import entity.Umowa;
import entity.Zmiennawynagrodzenia;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.apache.commons.collections4.CollectionUtils;
import pdf.PdfUmowaoPrace;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PracownikAneksyView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private DokumentyFacade dokumentyFacade;
    @Inject
    private SMTPSettingsFacade sMTPSettingsFacade;
    @Inject
    private ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade;
    private List<Umowa> listaumowy;
    private List<Umowa> listaumowyfiltered;
    private List<Umowa> listaumowyselected;
    private String dataaneksu;
    private String odkiedyzmiana;
    private double kwotaaneksu;
    private String innewarunkizatrudnienia;
    private Rodzajwynagrodzenia wybranyrodzaj;
    private List<Rodzajwynagrodzenia> rodzajewynagrodzen;
    
    @PostConstruct
    private void init() {
        if (wpisView.getFirma() != null) {
            List<Angaz> angaze = angazFacade.findByFirma(wpisView.getFirma());
            listaumowy = new ArrayList<>();
            for (Iterator<Angaz> it = angaze.iterator(); it.hasNext();) {
                    Angaz angaz = it.next();
                    if (angaz.jestumowaAktywna(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu())==false) {
                        it.remove();
                    }
            }
            for (Angaz a : angaze) {
                Umowa umowaAktywna = a.pobierzumowaAktywna(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                if (umowaAktywna!=null) {
                    listaumowy.add(umowaAktywna);
                }
            }
            rodzajewynagrodzen = pobierzrodzajewyn(angaze);
        }
    }
    
    private List<Rodzajwynagrodzenia> pobierzrodzajewyn(List<Angaz> angaze) {
        Set<Rodzajwynagrodzenia> rodzajeset = new HashSet<>();
        for (Angaz p : angaze) {
            List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList = p.getSkladnikwynagrodzeniaList();
            if (skladnikwynagrodzeniaList!=null) {
                for (Skladnikwynagrodzenia skl : skladnikwynagrodzeniaList) {
                    rodzajeset.add(skl.getRodzajwynagrodzenia());
                }
            }
            
        }
        return new ArrayList<>(rodzajeset);
    }
    

    public void tworzzmienne() {
        if (wybranyrodzaj!=null && listaumowy!=null) {
            for (Umowa u : listaumowy) {
                Skladnikwynagrodzenia zwrot = null;
                List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList1 = u.getAngaz().getSkladnikwynagrodzeniaList();
                if (skladnikwynagrodzeniaList1 != null) {
                    for (Skladnikwynagrodzenia s : skladnikwynagrodzeniaList1) {
                        if (s.getRodzajwynagrodzenia().equals(wybranyrodzaj)) {
                            if (s.getOstatniaZmienna() != null) {
                                u.setZmiennawynagrodzenia(s.getOstatniaZmienna());
                                u.setNetto0brutto1(s.getOstatniaZmienna().isNetto0brutto1());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
        
    
   public void aktywujPracAneksy(FirmaKadry firma) {
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
            //angazView.init();
            //pracodawcaDaneView.init();
            //pasekwynagrodzenView.init();
            init();
            Msg.msg("Aktywowano firmę "+firma.getNazwa());
        }
    }
    
    public void drukujaneks(Zmiennawynagrodzenia nowewynagrodzenie, boolean netto0brutto1) {
        if (nowewynagrodzenie != null && nowewynagrodzenie.getId()!=null) {
            if (nowewynagrodzenie.getNowakwota()>0.0 || (innewarunkizatrudnienia!=null&&!innewarunkizatrudnienia.isEmpty())) {
                PdfUmowaoPrace.drukujaneks(nowewynagrodzenie, innewarunkizatrudnienia, dataaneksu, odkiedyzmiana, netto0brutto1);
                Msg.msg("Wydrukowano aneks");
            } else {
                Msg.msg("e", "Nie wprowadzono nowej kwoty wynagrodzenia dla umowy");
            }
        } else {
            Msg.msg("e", "Brak zmiennej z wynagrodzeniem. Nie można wydrukować aneksu");
        }
    }
    
    public void drukwszystkie() {
        List<Umowa> lysta = listaumowyselected;
        if (lysta==null||lysta.isEmpty()) {
            lysta = CollectionUtils.isNotEmpty(listaumowyfiltered)? listaumowyfiltered: listaumowy;
        }
        if (lysta != null) {
            PdfUmowaoPrace.drukujanekswszystkie(lysta, innewarunkizatrudnienia,  wpisView.getFirma(), dataaneksu, odkiedyzmiana);
        } else {
            Msg.msg("e", "Błąd drukowania. Nie można wydrukować aneksów");
        }
    }
    
     public void mailAneksy() {
        List<Umowa> lysta = listaumowyselected;
        if (lysta==null||lysta.isEmpty()) {
            lysta = CollectionUtils.isNotEmpty(listaumowyfiltered)? listaumowyfiltered: listaumowy;
        }
        if (lysta != null && lysta.size() > 0) {
            boolean wysylac = false;
            for (Umowa p : lysta) {
                if (p.getZmiennawynagrodzenia()!=null&&p.getZmiennawynagrodzenia().getNowakwota()>0) {
                    wysylac = true;
                    break;
                }
            }
            if (wysylac) {
                FirmaKadry firmaKadry = wpisView.getFirma();
                String nazwa = firmaKadry.getNip()+"aneksy.pdf";
                ByteArrayOutputStream drukujmail = PdfUmowaoPrace.drukujanekswszystkieMail(lysta, innewarunkizatrudnienia, wpisView.getFirma(), dataaneksu, odkiedyzmiana);
                SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
                findSprawaByDef.setUseremail(wpisView.getUzer().getEmail());
                findSprawaByDef.setPassword(wpisView.getUzer().getEmailhaslo());
                mail.Mail.mailAneksydoUmowy(wpisView.getFirma(), wpisView.getFirma().getEmail(), null, findSprawaByDef, drukujmail.toByteArray(), nazwa, wpisView.getUzer().getEmail());
                Msg.msg("Wysłano listę płac do pracodawcy");
            } else {
                Msg.msg("e", "Nie wprowadzono żadnych nowych kwot wynagrodzeń");
            }
        } else {
            Msg.msg("e", "Błąd drukowania.");
        }
    }
     
      public void archiwizujAneksy() {
        List<Umowa> lysta = listaumowyselected;
        if (lysta==null||lysta.isEmpty()) {
            lysta = CollectionUtils.isNotEmpty(listaumowyfiltered)? listaumowyfiltered: listaumowy;
        }
        if (lysta != null && lysta.size() > 0) {
            boolean wysylac = false;
            for (Umowa p : lysta) {
                if (p.getZmiennawynagrodzenia()!=null&&p.getZmiennawynagrodzenia().getNowakwota()>0) {
                    ByteArrayOutputStream plik = PdfUmowaoPrace.drukujaneksMail(p.getZmiennawynagrodzenia(), innewarunkizatrudnienia, dataaneksu, odkiedyzmiana, p.isNetto0brutto1());
                    Dokumenty dokument = new Dokumenty();
                    dokument.setDokument(plik.toByteArray());
                    dokument.setData(Data.aktualnaData());
                    dokument.setAngaz(p.getAngaz());
                    dokument.setAneks(true);
                    dokumentyFacade.create(dokument);
                    wysylac = true;
                }
            }
            if (wysylac) {
                Msg.msg("Archiwizuje aneksy");
            } else {
                Msg.msg("e", "Nie wprowadzono żadnych nowych kwot wynagrodzeń");
            }
        } else {
            Msg.msg("e", "Błąd archiwizowanai.");
        }
    }
     
      public void naniesZmienne() {
        List<Umowa> lysta = listaumowyselected;
        if (lysta==null||lysta.isEmpty()) {
            lysta = CollectionUtils.isNotEmpty(listaumowyfiltered)? listaumowyfiltered: listaumowy;
        }
        if (lysta != null && lysta.size() > 0) {
            boolean naniesc = false;
            for (Umowa p : lysta) {
                Zmiennawynagrodzenia zmienna = p.getZmiennawynagrodzenia();
                if (zmienna!=null&&zmienna.getNowakwota()>0) {
                    Zmiennawynagrodzenia nowazmienna = new Zmiennawynagrodzenia(zmienna, odkiedyzmiana);
                    nowazmienna.setAktywna(true);
                    nowazmienna.setAneks(true);
                    nowazmienna.setNetto0brutto1(p.isNetto0brutto1());
                    nowazmienna.setDatadodania(new Date());
                    nowazmienna.setUtworzyl(wpisView.getUzer().getImieNazwisko());
                    zmiennaWynagrodzeniaFacade.create(nowazmienna);
                    zmienna.setAktywna(false);
                    zmienna.setDatado(Data.odejmijdni(odkiedyzmiana,1));
                    zmiennaWynagrodzeniaFacade.edit(zmienna);
                    naniesc = true;
                    break;
                }
            }
            if (naniesc) {
                Msg.msg("Naniesiono aneksy na zmienne");
            } else {
                Msg.msg("e", "Nie wprowadzono żadnych nowych kwot wynagrodzeń");
            }
        } else {
            Msg.msg("e", "Błąd drukowania.");
        }
    }
    
    public void nanieskwote() {
        if (kwotaaneksu>0.0) {
            List<Umowa> lysta = listaumowyselected;
            if (lysta==null||lysta.isEmpty()) {
                lysta = CollectionUtils.isNotEmpty(listaumowyfiltered)? listaumowyfiltered: listaumowy;
            }
            for (Umowa u :lysta) {
                Zmiennawynagrodzenia zmiennaZasadniczego = u.getZmiennawynagrodzenia();
                if (zmiennaZasadniczego!=null&&zmiennaZasadniczego.getId()!=null) {
                    zmiennaZasadniczego.setNowakwota(kwotaaneksu);
                }
            }
        } else {
            Msg.msg("e", "Nie wpisano kwoty");
        }
    }
    
    public void naniesinnewarunkizatrudnienia() {
        if (kwotaaneksu>0.0) {
            List<Umowa> lysta = listaumowyselected;
            if (lysta==null||lysta.isEmpty()) {
                lysta = CollectionUtils.isNotEmpty(listaumowyfiltered)? listaumowyfiltered: listaumowy;
            }
            for (Umowa u :lysta) {
                Zmiennawynagrodzenia zmiennaZasadniczego = u.getZmiennawynagrodzenia();
                if (zmiennaZasadniczego!=null&&zmiennaZasadniczego.getId()!=null) {
                    zmiennaZasadniczego.setNowakwota(kwotaaneksu);
                }
            }
        } else {
            Msg.msg("e", "Nie wpisano kwoty");
        }
    }
     
    public List<Umowa> getListaumowy() {
        return listaumowy;
    }

    public void setListaumowy(List<Umowa> listaumowy) {
        this.listaumowy = listaumowy;
    }

    public String getDataaneksu() {
        return dataaneksu;
    }

    public void setDataaneksu(String dataaneksu) {
        this.dataaneksu = dataaneksu;
    }

    public double getKwotaaneksu() {
        return kwotaaneksu;
    }

    public void setKwotaaneksu(double kwotaaneksu) {
        this.kwotaaneksu = kwotaaneksu;
    }


    public List<Umowa> getListaumowyfiltered() {
        return listaumowyfiltered;
    }

    public void setListaumowyfiltered(List<Umowa> listaumowyfiltered) {
        this.listaumowyfiltered = listaumowyfiltered;
    }

    public String getInnewarunkizatrudnienia() {
        return innewarunkizatrudnienia;
    }

    public void setInnewarunkizatrudnienia(String innewarunkizatrudnienia) {
        String zwrot = innewarunkizatrudnienia.replace("<p>", "").replace("</p>", "");
        this.innewarunkizatrudnienia = zwrot;
    }

    public List<Umowa> getListaumowyselected() {
        return listaumowyselected;
    }

    public void setListaumowyselected(List<Umowa> listaumowyselected) {
        this.listaumowyselected = listaumowyselected;
    }

    public String getOdkiedyzmiana() {
        return odkiedyzmiana;
    }

    public void setOdkiedyzmiana(String odkiedyzmiana) {
        this.odkiedyzmiana = odkiedyzmiana;
    }

    public Rodzajwynagrodzenia getWybranyrodzaj() {
        return wybranyrodzaj;
    }

    public void setWybranyrodzaj(Rodzajwynagrodzenia wybranyrodzaj) {
        this.wybranyrodzaj = wybranyrodzaj;
    }

    public List<Rodzajwynagrodzenia> getRodzajewynagrodzen() {
        return rodzajewynagrodzen;
    }

    public void setRodzajewynagrodzen(List<Rodzajwynagrodzenia> rodzajewynagrodzen) {
        this.rodzajewynagrodzen = rodzajewynagrodzen;
    }


    
}
