/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AngazFacade;
import dao.SMTPSettingsFacade;
import dao.UmowaFacade;
import entity.Angaz;
import entity.FirmaKadry;
import entity.SMTPSettings;
import entity.Umowa;
import entity.Zmiennawynagrodzenia;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
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
    private SMTPSettingsFacade sMTPSettingsFacade;
    private List<Umowa> listaumowy;
    
    @PostConstruct
    private void init() {
        if (wpisView.getFirma()!=null) {
            List<Angaz> angaze = angazFacade.findByFirma(wpisView.getFirma());
            listaumowy = new ArrayList<>();
            for (Angaz a : angaze) {
                listaumowy.addAll(a.getUmowaList().stream().filter(p->p.isAktywna()).collect(Collectors.toList()));
            }
        }
    }
    
    public void drukujaneks(Zmiennawynagrodzenia p) {
        if (p != null) {
            PdfUmowaoPrace.drukujaneks(p);
            Msg.msg("Wydrukowano aneks");
        } else {
            Msg.msg("e", "Błąd drukowania. Nie można wydrukować aneksu");
        }
    }
    
    public void drukwszystkie() {
        if (listaumowy != null) {
            PdfUmowaoPrace.drukujanekswszystkie(listaumowy, wpisView.getFirma());
        } else {
            Msg.msg("e", "Błąd drukowania. Nie można wydrukować aneksów");
        }
    }
    
     public void mailAneksy() {
        if (listaumowy != null && listaumowy.size() > 0) {
            boolean wysylac = false;
            for (Umowa p : listaumowy) {
                if (p.getZmiennaZasadniczego().getNowakwota()>0) {
                    wysylac = true;
                    break;
                }
            }
            if (wysylac) {
                FirmaKadry firmaKadry = wpisView.getFirma();
                String nazwa = firmaKadry.getNip()+"aneksy.pdf";
                ByteArrayOutputStream drukujmail = PdfUmowaoPrace.drukujanekswszystkieMail(listaumowy, wpisView.getFirma());
                SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
                mail.Mail.mailAneksydoUmowy(wpisView.getFirma(), wpisView.getFirma().getEmail(), null, findSprawaByDef, drukujmail.toByteArray(), nazwa, wpisView.getUzer().getEmail());
                Msg.msg("Wysłano listę płac do pracodawcy");
            } else {
                Msg.msg("e", "Nie wprowadzono, żadnych nowych kwot wynagrodzeń");
            }
        } else {
            Msg.msg("e", "Błąd drukowania. Brak pasków");
        }
    }
    
    public List<Umowa> getListaumowy() {
        return listaumowy;
    }

    public void setListaumowy(List<Umowa> listaumowy) {
        this.listaumowy = listaumowy;
    }


    
}
