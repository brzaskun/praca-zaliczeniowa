/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Umowacomparator;
import dao.AngazFacade;
import dao.FirmaKadryFacade;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.PracownikFacade;
import dao.SMTPSettingsFacade;
import dao.UmowaFacade;
import dao.UprawnieniaFacade;
import dao.UzFacade;
import entity.Angaz;
import entity.FirmaKadry;
import entity.Pracownik;
import entity.SMTPSettings;
import entity.Umowa;
import entity.UprawnieniaUz;
import entity.Uz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import mail.Mail;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class AngazView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Angaz selected;
    @Inject
    private Angaz selectedlista;
    private Angaz selectedeast;
    private List<Angaz> lista;
    private List<Angaz> listaeast;
    private List<FirmaKadry> listafirm;
    private List<Pracownik> listapracownikow;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private FirmaKadryFacade firmaFacade;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private UzFacade uzFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private UprawnieniaFacade uprawnieniaFacade;
    @Inject
    private SMTPSettingsFacade sMTPSettingsFacade;
    @Inject
    private WpisView wpisView;
    @Inject
    private PracownikView pracownikView;
    @Inject
    private UpdateClassView updateClassView;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    private boolean pokazwszystkich;

    
    @PostConstruct
    public void init() {
        lista  = angazFacade.findAll();
        listafirm = firmaFacade.findAll();
        listapracownikow = pracownikFacade.findAll();
        if (wpisView.getFirma()!=null) {
            lista = angazFacade.findByFirma(wpisView.getFirma());
            listaeast = angazFacade.findByFirmaAktywni(wpisView.getFirma());
        }
         if (wpisView.getAngaz()!=null) {
            selectedeast = wpisView.getAngaz();
        }
    }
    
    public void pobierzpracownikow() {
        if (pokazwszystkich) {
            listaeast = angazFacade.findByFirma(wpisView.getFirma());
        } else {
            listaeast = angazFacade.findByFirmaAktywni(wpisView.getFirma());
        }
    }

    public void create() {
        if (selected != null && wpisView.getFirma() != null) {
            if (selected.getPracownik().getEmail() == null || selected.getPracownik().getEmail().equals("")) {
                Msg.msg("e", "Pracownik nie posiada adresu email. Nie można stworzyć angażu. Email musi być unikalny");
            } else {
                try {
                    selected.setDatadodania(new Date());
                    selected.setUtworzyl(wpisView.getUzer().getImieNazwisko());
                    selected.setFirma(wpisView.getFirma());
                    angazFacade.create(selected);
                    lista.add(selected);
                    wpisView.setAngaz(selected);
                    wpisView.setUmowa(null);
                    UprawnieniaUz uprawnienia = uprawnieniaFacade.findByNazwa("Pracownik");
                    Uz uzer = new Uz(selected, uprawnienia);
                    selected = new Angaz();
                    Msg.msg("Dodano nowy angaż");
                    uzFacade.create(uzer);
                    //nie moze tu byc bo nie ma umowy
                    //generujkalendarze();
                    Msg.msg("Dodano nowego użytkownika");
                } catch (Exception e) {
                    Msg.msg("e", "Błąd - nie dodano nowego angażu");
                }
            }
        }
    }
    
//    public void generujkalendarze() {
//        if (selected!=null && selected.getRok()!=null && selected.getMc()!=null) {
//            for (String mce: Mce.getMceListS()) {
//                if (Integer.valueOf(mce)>=Integer.valueOf(selected.getMc())) {
//                    Kalendarzmiesiac kal = new Kalendarzmiesiac();
//                    kal.setRok(wpisView.getRokWpisu());
//                    kal.setMc(mce);
//                    kal.setUmowa(wpisView.getUmowa());
//                    Kalendarzmiesiac kalmiesiac = kalendarzmiesiacFacade.findByRokMcUmowa(wpisView.getUmowa(), wpisView.getRokWpisu(), mce);
//                    if (kalmiesiac==null) {
//                        Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(kal.getUmowa().getAngaz().getFirma(), kal.getRok(), mce);
//                        if (znaleziono!=null) {
//                            kal.ganerujdnizwzrocowego(znaleziono, null);
//                            kalendarzmiesiacFacade.create(kal);
//                        } else {
//                            Msg.msg("e","Brak kalendarza wzorcowego za "+mce);
//                        }
//                    }
//                }
//            }
//            Msg.msg("Pobrano dane z kalendarza wzorcowego z bazy danych i utworzono kalendarze pracownika");
//        } else {
//            Msg.msg("e","Nie wybrano pracownika i umowy");
//        }
//    }
   
   public void aktywuj() {
        if (selectedlista!=null) {
            wpisView.setAngaz(selectedlista);
            wpisView.setPracownik(selectedlista.getPracownik());
            List<Umowa> umowy = wpisView.getAngaz().getUmowaList();
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
                wpisView.setUmowa(null);
            }
            updateClassView.updateUmowa();
            Msg.msg("Aktywowano pracownika");
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
                Msg.msg("e","Nie pobrano umów do angażu");
                wpisView.setUmowa(null);
                System.out.println("Nie pobrano umów do angażu");
            }
            updateClassView.updateUmowa();
            Msg.msg("Aktywowano pracownika");
        }
    }
   public void aktywujPlace(Angaz angaz) {
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
                Msg.msg("e","Nie pobrano umów do angażu");
                wpisView.setUmowa(null);
                System.out.println("Nie pobrano umów do angażu");
            }
            updateClassView.updateUmowaPlace();
            Msg.msg("Aktywowano pracownika");
        }
    }
    
    public void usun(Angaz angaz) {
        if (angaz != null) {
            //if (wpisView.getAngaz() != null && wpisView.getAngaz().equals(angaz)) {
                wpisView.setAngaz(null);
                wpisView.setUmowa(null);
                wpisView.setPracownik(null);
            //}
            Pracownik prac = angaz.getPracownik();
            angazFacade.remove(angaz);
            lista.remove(angaz);
            pracownikView.init();
            Msg.msg("Usunięto angaż");
            try {
                Uz uzer = uzFacade.findUzByLogin(prac.getEmail());
                uzFacade.remove(uzer);
            } catch (Exception ex) {
            }
            List<Angaz> angazList = angazFacade.findByFirma(wpisView.getFirma());
            if (angazList != null && angazList.size() > 0) {
                Angaz angaza = angazList.get(0);
                wpisView.setAngaz(angaza);
                List<Umowa> umowy = angaza.getUmowaList();
                if (umowy != null && !umowy.isEmpty()) {
                    Umowa umowaaktywna = null;
                    Optional badanie = umowy.stream().filter(p -> p.isAktywna()).findFirst();
                    if (badanie.isPresent()) {
                        umowaaktywna = (Umowa) badanie.get();
                    }
                    if (umowaaktywna == null) {
                        Collections.sort(umowy, new Umowacomparator());
                        umowaaktywna = umowy.get(0);
                        umowaaktywna.setAktywna(true);
                        umowaFacade.edit(umowaaktywna);
                    }
                    wpisView.setUmowa(umowaaktywna);
                    wpisView.setPracownik(angaza.getPracownik());
                }
                wpisView.memorize();
                Msg.msg("Pobrano kolejny angaż");
            }
        } else {
            Msg.msg("e", "Nie wybrano angażu");
        }
    }
    
    public void findByFirma(FirmaKadry firma) {
        if (firma!=null) {
            listaeast = angazFacade.findByFirma(firma);
            Msg.msg("Pobrano pracowników firmy");
        } else {
            Msg.msg("e", "Błąd nie wybrano firmy");
        }
    }
    
    public List<Pracownik> complete(String query) {
        List<Pracownik> results = new ArrayList<>();
        try {
            String q = query.substring(0, 1);
            int i = Integer.parseInt(q);
            for (Pracownik p : listapracownikow) {
                if (p.getPesel().startsWith(query)) {
                    results.add(p);
                }
            }
        } catch (NumberFormatException e) {
            for (Pracownik p : listapracownikow) {
                if (p.getNazwisko().toLowerCase().contains(query.toLowerCase())) {
                    results.add(p);
                }
            }
        }
        return results;
    }
    
    public void zapisz(Pracownik pracownik) {
        if (pracownik!=null) {
            pracownikFacade.edit(pracownik);
            Msg.msg("Zaktualizowano danepracownika");
        }
    }
    
    public void zapiszmail(Pracownik pracownik) {
        if (pracownik!=null) {
            pracownikFacade.edit(pracownik);
            Msg.msg("Zaktualizowano email pracownika");
            Uz uzer = uzFacade.findUzByPesel(pracownik.getPesel());
            if (uzer==null) {
                Msg.msg("e","Pracownik nie ma swojego konta, nie będzie mógł się zalogować");
            } else {
                uzer.setLogin(pracownik.getEmail());
                uzer.setEmail(pracownik.getEmail());
                uzFacade.edit(uzer);
            }
        }
    }
    
    public void mail(Pracownik pracownik) {
        SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
        Mail.ankieta(pracownik.getEmail(), null, findSprawaByDef);
    }
    
    public Angaz getSelected() {
        return selected;
    }

    public void setSelected(Angaz selected) {
        this.selected = selected;
    }

    public List<Angaz> getLista() {
        return lista;
    }

    public void setLista(List<Angaz> lista) {
        this.lista = lista;
    }

    public Angaz getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Angaz selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<FirmaKadry> getListafirm() {
        return listafirm;
    }

    public void setListafirm(List<FirmaKadry> listafirm) {
        this.listafirm = listafirm;
    }

    public List<Pracownik> getListapracownikow() {
        return listapracownikow;
    }

    public void setListapracownikow(List<Pracownik> listapracownikow) {
        this.listapracownikow = listapracownikow;
    }

    public List<Angaz> getListaeast() {
        return listaeast;
    }

    public void setListaeast(List<Angaz> listaeast) {
        this.listaeast = listaeast;
    }


    public Angaz getSelectedeast() {
        return selectedeast;
    }

    public void setSelectedeast(Angaz selectedeast) {
        this.selectedeast = selectedeast;
    }

    public boolean isPokazwszystkich() {
        return pokazwszystkich;
    }

    public void setPokazwszystkich(boolean pokazwszystkich) {
        this.pokazwszystkich = pokazwszystkich;
    }

   
    
    
}
