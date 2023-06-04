/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.IPaddress;
import comparator.Pracownikcomparator;
import converter.AngazConverter;
import dao.AngazFacade;
import dao.PracownikFacade;
import dao.SMTPSettingsFacade;
import data.Data;
import entity.Angaz;
import entity.Pracownik;
import entity.SMTPSettings;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import mail.Mail;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PracodawcaDaneView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Pracownik> listapracownikow;
    private List<Pracownik> listafiltered;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private SMTPSettingsFacade sMTPSettingsFacade;
    @Inject
    private Pracownik selected;
    private Pracownik selectedlista;
    @Inject
    private WpisView wpisView;
    @Inject
    private PracownikDane2View pracownikDane2View;
    @Inject
    private AngazConverter angazConverter;
    private String rok;
    private String mc;
    
    @PostConstruct
    public void init() {
        listapracownikow = angazFacade.findPracownicyByFirma(wpisView.getFirma());
        Collections.sort(listapracownikow, new Pracownikcomparator());
    }

    
    public void create() {
      if (selected!=null) {
          try {
            selected.setNazwisko(selected.getNazwisko().trim());
            selected.setImie(selected.getImie().trim());
            selected.setIpusera(IPaddress.getIpAddr((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()));
            selected.setDatalogowania(Data.aktualnaDataCzas());
            selected.setModyfikowal(wpisView.getUzer().getImieNazwisko());
            selected.setPlec(pleczPesel(selected.getPesel()));
            pracownikFacade.create(selected);
            listapracownikow.add(selected);
            Collections.sort(listapracownikow, new Pracownikcomparator());
            //wpisView.setPracownik(selected);
            maildodanonowegoprac(selected);
            createAngaz();
            angazConverter.init();
            selected = new Pracownik();
            pracownikDane2View.init();
            Msg.msg("Dodano nowego pracownika");
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie dodano nowego pracownika");
          }
      }
    }
     public void createAngaz() {
        if (selected != null && wpisView.getFirma() != null) {
            Angaz angaz = new Angaz();
            angaz.setPracownik(selected);
            if (angaz.getPracownik().getEmail() == null || angaz.getPracownik().getEmail().equals("")) {
                angaz.getPracownik().setEmail(generujemail(angaz.getPracownik()));
                angaz.getPracownik().setFikcyjnymail(true);
                pracownikFacade.edit(angaz.getPracownik());
                Msg.msg("w", "Pracownik nie posiada adresu email. Generuje zastępnczy adres mailowy");
            }
            try {
                angaz.setDatadodania(new Date());
                angaz.setUtworzyl(wpisView.getUzer().getImieNazwisko());
                angaz.setFirma(wpisView.getFirma());
                angaz.setKosztyuzyskaniaprocent(100.0);
                angaz.setKwotawolnaprocent(100.0);
                angaz.setOdliczaculgepodatkowa(true);
                try {
                    angazFacade.create(angaz);
                    Msg.msg("Dodano nowy angaż");
                } catch (Exception e){
                    Msg.msg("e", "Taki angaż już istnieje");
                }
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie dodano nowego angażu");
            }
                    }
    }
     
     private String generujemail(Pracownik pracownik) {
        String zwrot = "brak adresu";
        if (pracownik!=null) {
            zwrot = pracownik.getNazwisko()+pracownik.getImie()+"@taxman.biz.pl";
            zwrot = zwrot.toLowerCase(new Locale("pl","PL"));
        }
        return zwrot;
    }
    
    private String pleczPesel(String pesel) {
        String zwrot = "M";
        char chara = pesel.charAt(9);
        int liczbakontrolna = Character.getNumericValue(chara);
        boolean isEven = liczbakontrolna % 2 == 0;
        if (isEven) {
            zwrot = "K";
        }
        return zwrot;
    }
    public void edytuj(Pracownik pracownik) {
      if (pracownik!=null) {
          try {
            pracownik.setIpusera(IPaddress.getIpAddr((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()));
            pracownik.setDatalogowania(Data.aktualnaDataCzas());
            pracownik.setModyfikowal(wpisView.getUzer().getImieNazwisko());
            pracownikFacade.edit(pracownik);
            wpisView.setPracownik(pracownik);
            Msg.msg("Zachowano zmienione dane pracownika");
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie zachowano zmian pracownika");
          }
      }
    }
    
    public void mailbiuro() {
        SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
        Mail.updateemailpracownik(wpisView.getFirma(),"a.barczyk@taxman.biz.pl", null, findSprawaByDef);
    }
    
    public void maildodanonowegoprac(Pracownik pracownik) {
        SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
        Mail.updateemailnowypracownik(wpisView.getFirma(),pracownik,"info@taxman.biz.pl", null, findSprawaByDef);
    }
    
    public List<Pracownik> getListapracownikow() {
        return listapracownikow;
    }

    public void setListapracownikow(List<Pracownik> listapracownikow) {
        this.listapracownikow = listapracownikow;
    }

    public Pracownik getSelected() {
        return selected;
    }

    public void setSelected(Pracownik selected) {
        this.selected = selected;
    }

    public List<Pracownik> getListafiltered() {
        return listafiltered;
    }

    public void setListafiltered(List<Pracownik> listafiltered) {
        this.listafiltered = listafiltered;
    }

    public Pracownik getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Pracownik selectedlista) {
        this.selectedlista = selectedlista;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }
    
    
}
