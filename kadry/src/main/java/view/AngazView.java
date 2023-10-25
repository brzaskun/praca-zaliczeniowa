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
import dao.MemoryFacade;
import dao.PracownikFacade;
import dao.SMTPSettingsFacade;
import dao.UmowaFacade;
import dao.UprawnieniaFacade;
import dao.UzFacade;
import data.Data;
import embeddable.Mce;
import entity.Angaz;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Memory;
import entity.Pracownik;
import entity.SMTPSettings;
import entity.Umowa;
import entity.UprawnieniaUz;
import entity.Uz;
import error.E;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import mail.Mail;
import msg.Msg;
import pdf.PdfHistoriaImp;

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
    private PasekwynagrodzenView pasekwynagrodzenView;
    @Inject
    private UpdateClassView updateClassView;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    @Inject
    private MemoryFacade memoryFacade;
    private boolean pokazwszystkich;

    
    @PostConstruct
    public void init() {
        lista  = angazFacade.findAll();
        //!!!Usunac 01.03
        //UmowaBean.naniesDatezasilkunaimportowane(lista, umowaFacade);
        listafirm = firmaFacade.findAll();
        listapracownikow = pracownikFacade.findAll();
        if (wpisView.getFirma()!=null) {
            lista = angazFacade.findByFirma(wpisView.getFirma());
            pobierzpracownikow();
         }
        
    }
    
     public void aktywujPracAngaze(FirmaKadry firma) {
        if (firma!=null&&!wpisView.getFirma().equals(firma)) {
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
            pobierzpracownikow();
            //init();
            updateClassView.updateAdminTab();
            Msg.msg("Aktywowano firmę "+firma.getNazwa());
        }
    }
     
      public void aktywujPracAngazeTylko(FirmaKadry firma) {
        if (firma!=null&&!wpisView.getFirma().equals(firma)) {
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
            pobierzpracownikow();
            //init();
            //updateClassView.updateAdminTab();
            Msg.msg("Aktywowano firmę "+firma.getNazwa());
        }
    }
    
    public void pobierzpracownikow() {
        List<Angaz> zwrot = new ArrayList<>();
        if (pokazwszystkich) {
            //tego nie ma juz sensu bo sie pokazuja naprawde archiwalni, teraz bedza ci z umowani i ci bez ale aktywni 15.04.2023 pozdro
            //zwrot = angazFacade.findByFirma(wpisView.getFirma());
            zwrot = angazFacade.findByFirmaAktywni(wpisView.getFirma());
        } else {
            zwrot = angazFacade.findByFirmaAktywni(wpisView.getFirma());
            for (Iterator<Angaz> it = zwrot.iterator(); it.hasNext();) {
                Angaz angaz = it.next();
                if (angaz.jestumowaAktywna(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu())==false) {
                    it.remove();
                }
            }
        }
        // TO NIE bo nie widac ukrytych tam gdzie je sie ukrywa 27.08.2023
        //lista = zwrot;
        lista= angazFacade.findByFirma(wpisView.getFirma());
        listaeast = zwrot;
        if (zwrot.isEmpty()) {
            listaeast = zwrot.stream().filter(p->p.getUmowaList()!=null&&!p.getUmowaList().isEmpty()).collect(Collectors.toList());
        }
         if (wpisView.getAngaz()!=null) {
            selectedeast = wpisView.getAngaz();
            selected.setRok(wpisView.getRokWpisu());
            selected.setMc(wpisView.getMiesiacWpisu());
        }
    }
    
   

    public void create() {
        List<String> log = new ArrayList<>();
        Pracownik pracownik = selected.getPracownik();
        String pesel = "brak peselu";
        String nazwiskoimie = "brak nazwiska i imienia";
        if (selected != null && wpisView.getFirma() != null&& pracownik!=null) {
            log.add("Wybrano pracownika rozpoczynam generowanie angazu");
            pesel = selected.getPracownik().getPesel();
            nazwiskoimie = selected.getPracownik().getNazwiskoImie();
            if (selected.getPracownik().getEmail() == null || selected.getPracownik().getEmail().equals("")) {
                selected.getPracownik().setEmail(generujemail(selected.getPracownik()));
                selected.getPracownik().setFikcyjnymail(true);
                log.add("Wygenerowano email pracownika "+wpisView.getFirma()+" "+nazwiskoimie);
                pracownikFacade.edit(selected.getPracownik());
                Msg.msg("w", "Pracownik nie posiada adresu email. Generuje zastępnczy adres mailowy");
            }
            try {
                selected.setDatadodania(new Date());
                selected.setUtworzyl(wpisView.getUzer().getImieNazwisko());
                selected.setFirma(wpisView.getFirma());
                selected.setKosztyuzyskaniaprocent(100.0);
                selected.setKwotawolnaprocent(100.0);
                selected.setOdliczaculgepodatkowa(true);
                log.add("Ustawiono zmienne");
                try {
                    angazFacade.create(selected);
                    log.add("Zachowano angaz w bazie");
                    lista.add(selected);
                    int ilosckalendarzy = generujKalendarzNowyAngaz(selected, log);
                    if (ilosckalendarzy==0) {
                        log.add("BŁĄD. Nie dodano kalendarzy  "+pracownik.getNazwisko()+" "+pracownik.getImie());
                    } else {
                        log.add("wygenerowano kalendarze w ilosci "+ilosckalendarzy);
                    }
                    Msg.msg("Stworzono kalendarz dla angażu");
                    wpisView.setAngaz(selected);
                    log.add("Dodano angaz do wpisView");
                    wpisView.setUmowa(null);
                    log.add("Wyzerowano umowe do wpisView");
                    selected = new Angaz();
                    Msg.msg("Dodano nowy angaż");
                    //nie moze tu byc bo nie ma umowy
                    //generujkalendarze();
                    try {
                        UprawnieniaUz uprawnienia = uprawnieniaFacade.findByNazwa("Pracownik");
                        Uz uzer = new Uz(selected, uprawnienia);
                        uzFacade.create(uzer);
                        log.add("Dodano nowego uzera - pracownika");
                        Msg.msg("Dodano nowego użytkownika");
                    }  catch (Exception e){
                        log.add("Nie dodano uzera - pracownika, taki uzer juz istnieje");
                    }
                } catch (Exception e){
                    log.add("BŁĄD. Inny");
                    log.add(E.e(e));
                    Msg.msg("e", "Taki angaż już istnieje");
                }
                log.add("Zakonczono procedure importu");
            } catch (Exception e) {
                log.add("BŁĄD. Nie dodano stawek angazu dla  "+pracownik.getNazwisko()+" "+pracownik.getImie());
                log.add(E.e(e));
                Msg.msg("e", "Błąd - nie dodano nowego angażu");
            } finally {
                PdfHistoriaImp.drukuj(log, wpisView.getFirma().getNazwa(), wpisView.getFirma().getNip());
                String nazwa = "log"+pesel+"angaz.pdf";
                ByteArrayOutputStream drukujmail = PdfHistoriaImp.drukujMail(log, wpisView.getFirma().getNazwa(), wpisView.getFirma().getNip());
                SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
                findSprawaByDef.setUseremail(wpisView.getUzer().getEmail());
                findSprawaByDef.setPassword(wpisView.getUzer().getEmailhaslo());
                mail.Mail.mailRaportzImportu(wpisView.getFirma(), wpisView.getUzer().getEmail(), null, findSprawaByDef, drukujmail.toByteArray(), nazwa, "info@taxman.biz.pl");
        }   }
    }
    
    
     public int generujKalendarzNowyAngaz(Angaz nowyangaz, List<String> log) {
        Kalendarzmiesiac kal = null;
        int ilekalendarzy = 0;
        if (nowyangaz != null && nowyangaz.getPracownik() != null) {
            String rok = nowyangaz.getRok();
            Integer rokI = Integer.parseInt(rok);
            Integer rokToday = Integer.parseInt(Data.getRok(Data.aktualnaData()));
            Integer mcod = Integer.parseInt(nowyangaz.getMc());
            for (int rokgen = rokI;rokgen<=rokToday;rokgen++) {
                log.add("Rok generowania "+rokgen);
                String mca = "nie rozpoczeto wyboru miesiecy ";
                for (String mc : Mce.getMceListS()) {
                    mca = mc;
                    try {
                        String rokbiezacy = String.valueOf(rokgen);
                        Integer kolejnymc = Integer.parseInt(mc);
                        if (kolejnymc >= mcod) {
                            kal = new Kalendarzmiesiac();
                            Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(nowyangaz.getFirma(), rokbiezacy, mc);
                            if (znaleziono != null) {
                                kal.setRok(rokbiezacy);
                                kal.setMc(mc);
                                kal.setAngaz(nowyangaz);
                                kal.ganerujdnizwzrocowego(znaleziono, null);
                                kalendarzmiesiacFacade.create(kal);
                                log.add("Wygenerowano kalendarz za mc "+mc);
                                ilekalendarzy++;
                            } else {
                                log.add("BŁĄD. Nie wygenerowano kalendarz za mc "+mca);
                                Msg.msg("e", "Brak kalendarza wzorcowego za " + mca);
                            }
                        }
                    } catch (Exception e) {
                        log.add(E.e(e));
                        log.add("BŁĄD. Podczas generoania kalendarza za mc "+mca);
                    }
                }
            }
            log.add("Pobrano dane z kalendarza wzorcowego z bazy danych i utworzono kalendarze pracownika");
            Msg.msg("Pobrano dane z kalendarza wzorcowego z bazy danych i utworzono kalendarze pracownika");
        } else {
            log.add("BŁĄD. Nie można wygenerować. Nie wybrano pracownika i umowy");
            Msg.msg("e", "Nie można wygenerować. Nie wybrano pracownika i umowy");
        }
        return ilekalendarzy;
    }
     
     public void sprawdzrok() {
         if (selected.getRok()!=null)  {
            int rokI = Integer.parseInt(selected.getRok());
            if (rokI<2022) {
                selected.setRok(null);
                Msg.msg("e","Angaż nie może być z wcześniejszego roku niż 2022");
            }
         }
     }
    
     public void ukryjangaz(Angaz angaz) {
         if (angaz!=null) {
            angazFacade.edit(angaz);
            Msg.msg("Ukryto/odkryto angaż");
        } else {
            Msg.msg("e", "Błąd nie wybrano angażu");
        }
     }
     
      public void edytuj(Angaz angaz) {
        if (angaz!=null) {
            pozamykajskladniki(angaz);
            angazFacade.edit(angaz);
            Msg.msg("Zapisano zmiany");
        } else {
            Msg.msg("e", "Błąd nie wybrano angażu");
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
//                    Kalendarzmiesiac kalmiesiac = kalendarzmiesiacFacade.findByRokMcAngaz(wpisView.getAngaz(), wpisView.getRokWpisu(), mce);
//                    if (kalmiesiac==null) {
//                        Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(kal.getAngaz().getFirma(), kal.getRok(), mce);
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
   
      
      
      //nie wiem po co to bylo - usunalem 28.08.2023
//      edycjadanych_angaz.xhtml
//  103:  <p:ajax event="rowSelect" listener="#{angazView.aktywuj}" partialSubmit="true" global="false" update="north Angazeditform"/>

      
//   public void aktywuj() {
//        if (selectedlista!=null) {
//            wpisView.setAngaz(selectedlista);
//            wpisView.setPracownik(selectedlista.getPracownik());
//            List<Umowa> umowy = wpisView.getAngaz().getUmowaList();
//            if (umowy!=null && umowy.size()==1) {
//                wpisView.setUmowa(umowy.get(0));
//            } else if (umowy!=null&&!umowy.isEmpty()) {
//                Umowa umowaaktywna = null;
//                Optional badanie  = umowy.stream().filter(p->p.isAktywna()).findFirst();
//                if (badanie.isPresent()) {
//                    umowaaktywna = (Umowa) badanie.get();
//                }
//                if (umowaaktywna==null) {
//                    Collections.sort(umowy, new Umowacomparator());
//                    umowaaktywna = umowy.get(0);
//                    umowaaktywna.setAktywna(true);
//                    umowaFacade.edit(umowaaktywna);
//                }
//                wpisView.setUmowa(umowaaktywna);
//            } else {
//                wpisView.setUmowa(null);
//            }
//            updateClassView.updateUmowa();
//            Msg.msg("Aktywowano pracownika");
//        }
//    }
    
    
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
            //updateClassView.updateUmowa();
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
            updateClassView.updateAdminTab();
            Msg.msg("Aktywowano pracownika");
        }
    }
    
    public void usunAngaz(Angaz angaz) {
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
    
    public void zapiszangaz() {
        if (selectedlista!=null) {
            if (selectedlista.getId()!=null) {
                if (selectedlista.getKosztyuzyskaniaprocent()>100) {
                    selectedlista.setKosztyuzyskania0podwyzszone(true);
                } else {
                    selectedlista.setKosztyuzyskania0podwyzszone(false);
                }
                if (selectedlista.getKwotawolnaprocent()>0) {
                    selectedlista.setOdliczaculgepodatkowa(true);
                } else {
                    selectedlista.setOdliczaculgepodatkowa(false);
                }
                pracownikFacade.edit(selectedlista);
                if (selectedlista.equals(wpisView.getAngaz())) {
                    wpisView.setAngaz(selectedlista);
                }
                Msg.msg("Zaktualizowano danepracownika");
            }
        } else {
            Msg.msg("e","Nie wybrano angażu do edycji");
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
        findSprawaByDef.setUseremail(wpisView.getUzer().getEmail());
        findSprawaByDef.setPassword(wpisView.getUzer().getEmailhaslo());
        Mail.ankieta(pracownik.getEmail(), null, findSprawaByDef);
    }
    
    private String generujemail(Pracownik pracownik) {
        String zwrot = "brak adresu";
        if (pracownik!=null) {
            zwrot = pracownik.getNazwisko()+pracownik.getImie()+"@taxman.biz.pl";
            zwrot = zwrot.toLowerCase(new Locale("pl","PL"));
        }
        return zwrot;
    }

    public void usunwszystkie() {
        if (lista!=null) {
            Memory findByUzer = memoryFacade.findByUzer(wpisView.getUzer());
            findByUzer.setAngaz(null);
            findByUzer.setPracownik(null);
            memoryFacade.edit(findByUzer);
            try {
                angazFacade.removeList(lista);
                lista = new ArrayList<>();
            } catch (Exception e) {}
            Msg.dP();
        }
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

    private void pozamykajskladniki(Angaz angaz) {
        System.out.println("brak funkcji kadry private void pozamykajskladniki(Angaz angaz) {");
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
   
    
    
}
