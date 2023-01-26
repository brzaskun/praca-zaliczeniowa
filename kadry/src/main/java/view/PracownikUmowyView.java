/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Umowacomparator;
import dao.AngazFacade;
import dao.DokumentyFacade;
import dao.SMTPSettingsFacade;
import dao.UmowaFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import entity.Angaz;
import entity.FirmaKadry;
import entity.SMTPSettings;
import entity.Skladnikwynagrodzenia;
import entity.Umowa;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.apache.commons.collections4.CollectionUtils;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PracownikUmowyView  implements Serializable {
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
    
    @PostConstruct
    public void init() {
        if (wpisView.getFirma() != null) {
            List<Angaz> angaze = angazFacade.findByFirma(wpisView.getFirma());
            listaumowy = new ArrayList<>();
            for (Angaz a : angaze) {
                List<Umowa> umowy = a.getUmowaList().stream().filter(p -> p.isAktywna()).collect(Collectors.toList());
                if (umowy == null || umowy.isEmpty()) {
                    umowy = a.getUmowaList();
                    if (umowy.size() > 0) {
                        Collections.sort(umowy, new Umowacomparator());
                        Umowa wybrana = umowy.get(0);
                        listaumowy.add(wybrana);
                    }
                } else if (umowy != null && umowy.size() == 1) {
                    Umowa wybrana = umowy.get(0);
                    listaumowy.add(wybrana);
                }
            }
            for (Umowa u : listaumowy) {
                Skladnikwynagrodzenia zwrot = null;
                List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList1 = u.getAngaz().getSkladnikwynagrodzeniaList();
                if (skladnikwynagrodzeniaList1 != null) {
                    for (Skladnikwynagrodzenia s : skladnikwynagrodzeniaList1) {
                        if (s.getRodzajwynagrodzenia().getKod().equals("11") || s.getRodzajwynagrodzenia().getKod().equals("50")) {
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
    public void aktywujPracUmowy(FirmaKadry firma) {
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
    
     public void mailAneksy() {
        List<Umowa> lysta = CollectionUtils.isNotEmpty(listaumowyfiltered)? listaumowyfiltered: listaumowy;
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
                ByteArrayOutputStream drukujmail = null;
                //ByteArrayOutputStream drukujmail = PdfUmowaoPrace.drukujanekswszystkieMail(lysta, wpisView.getFirma(), dataaneksu);
                SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
                mail.Mail.mailAneksydoUmowy(wpisView.getFirma(), wpisView.getFirma().getEmail(), null, findSprawaByDef, drukujmail.toByteArray(), nazwa, wpisView.getUzer().getEmail());
                Msg.msg("Wysłano listę płac do pracodawcy");
            } else {
                Msg.msg("e", "Nie wprowadzono żadnych nowych kwot wynagrodzeń");
            }
        } else {
            Msg.msg("e", "Błąd drukowania.");
        }
    }
     
     
     
    public List<Umowa> getListaumowy() {
        return listaumowy;
    }

    public void setListaumowy(List<Umowa> listaumowy) {
        this.listaumowy = listaumowy;
    }


    public List<Umowa> getListaumowyfiltered() {
        return listaumowyfiltered;
    }

    public void setListaumowyfiltered(List<Umowa> listaumowyfiltered) {
        this.listaumowyfiltered = listaumowyfiltered;
    }


    
}
