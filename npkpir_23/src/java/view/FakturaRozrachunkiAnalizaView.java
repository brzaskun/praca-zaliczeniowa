/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansFaktura.FakturaBean;
import beansMail.SMTPBean;
import comparator.FakturaPodatnikRozliczeniecomparator;
import comparator.Kliencicomparator;
import comparator.UzNazwiskocomparator;
import dao.EvewidencjaDAO;
import dao.FakturaDAO;
import dao.FakturaRozrachunkiDAO;
import dao.FakturadodelementyDAO;
import dao.FakturywystokresoweDAO;
import dao.KlienciDAO;
import dao.PodatnikDAO;
import dao.SMTPSettingsDAO;
import dao.TabelanbpDAO;
import dao.UzDAO;
import data.Data;
import embeddable.FakturaPodatnikRozliczenie;
import embeddable.Mce;
import embeddable.Pozycjenafakturzebazadanych;
import entity.Faktura;
import entity.FakturaRozrachunki;
import entity.Fakturadodelementy;
import entity.Fakturywystokresowe;
import entity.Klienci;
import entity.Podatnik;
import entity.SMTPSettings;
import entity.Uz;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UISelectOne;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import mail.MailAdmin;
import mail.MailFaktRozrach;
import msg.Msg;
import pdf.PdfFaktRozrach;
import sms.SmsSend;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class FakturaRozrachunkiAnalizaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Klienci> klienci;
    
    private Klienci szukanyklient;
    private List<FakturaPodatnikRozliczenie> nowepozycje;
    private List<FakturaPodatnikRozliczenie> archiwum;
    private List<FakturaPodatnikRozliczenie> saldanierozliczone;
    private List<FakturaPodatnikRozliczenie> saldanierozliczoneselected;
    private List<FakturaPodatnikRozliczenie> saldanierozliczonefiltered;
    private double sumasaldnierozliczonych;
    private List<FakturaPodatnikRozliczenie> selectedrozliczenia;
    @Inject
    private FakturaRozrachunki selected;
    @Inject
    private WpisView wpisView;
   @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private FakturadodelementyDAO fakturadodelementyDAO;
    @Inject
    private FakturaRozrachunkiDAO fakturaRozrachunkiDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private FakturywystokresoweDAO fakturywystokresoweDAO;
    @Inject
    private KlienciDAO klienciDAO;
    private int aktywnytab;
    private UISelectOne selectOneUI;
    private boolean pokaznadplaty;
    private boolean tylkoprzeterminowane;
    private boolean pobierzdwalata;
    private String tekstwiadomosci;
    private boolean dolaczrokpoprzedni;
    private boolean pokazrozliczonych;
    private boolean pokazrokpoprzedni;
    private String dodatkowyadresmailowy;
    List<Podatnik> podatnicy;
    private double razemwybrane;
     private List<Uz> listaksiegowych;
    @Inject
    private UzDAO uzDAO;
    private Uz wybranyksiegowy;
    private boolean pokazarchiwalne;
    private List<FakturaRozrachunki> platnosci;
    private List<FakturaRozrachunki> platnoscirokuprzedni;
    private List<Faktura> faktury;
    private List<Faktura> fakturyrokuprzedni;
    
    public FakturaRozrachunkiAnalizaView() {
        
    }

    @PostConstruct
    public void init() { //E.m(this);
        klienci = Collections.synchronizedList(new ArrayList<>());
        nowepozycje = Collections.synchronizedList(new ArrayList<>());
        archiwum = Collections.synchronizedList(new ArrayList<>());
        saldanierozliczone = Collections.synchronizedList(new ArrayList<>());
        podatnicy = podatnikDAO.findAll();
        klienci.addAll(pobierzkontrahentow(podatnicy));
        dodatkowyadresmailowy="m.januszewska@taxman.biz.pl";
        Collections.sort(klienci, new Kliencicomparator());
        listaksiegowych = uzDAO.findByUprawnienia("Bookkeeper");
        listaksiegowych.addAll(uzDAO.findByUprawnienia("BookkeeperFK"));
        listaksiegowych.addAll(uzDAO.findByUprawnienia("Administrator"));
        Collections.sort(listaksiegowych, new UzNazwiskocomparator());
        platnosci = fakturaRozrachunkiDAO.findByPodatnikrok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        platnoscirokuprzedni = fakturaRozrachunkiDAO.findByPodatnikrok(wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt());
        faktury = fakturaDAO.findFakturyByRokPodatnik(wpisView.getRokWpisuSt(),wpisView.getPodatnikObiekt());
        fakturyrokuprzedni = fakturaDAO.findFakturyByRokPodatnik(wpisView.getRokUprzedniSt(),wpisView.getPodatnikObiekt());
    
    }
    
    public void pobierzwszystkoKlienta() {
        selectedrozliczenia = null;
        String mc = wpisView.getMiesiacWpisu();
        for (Podatnik po : podatnicy) {
                if (po.getNip().equals(szukanyklient.getNip())) {
                    szukanyklient.setTelefon(po.getTelefonkontaktowy());
                    szukanyklient.setKsiegowa(po.getKsiegowa());
                    szukanyklient.setAktywny(po.isPodmiotaktywny());
                    szukanyklient.setPolecajacy(po.getPolecajacy());
                    break;
                }
            }
        if (szukanyklient.getEmail()==null ||szukanyklient.getEmail().equals("")) {
            szukanyklient.setEmail("brakmaila!@taxman.biz.pl");
        }
         Klienci klient = szukanyklient;
        // Bezpieczne przetwarzanie list z użyciem parallelStream
        List<FakturaRozrachunki> klientplatnosci = (platnosci != null ? platnosci.stream()
            .filter(item -> item != null && item.getKontrahent() != null && klient.getNip().equals(item.getKontrahent().getNip()))
            .collect(Collectors.toList()) : Collections.emptyList());

        List<FakturaRozrachunki> klientplatnoscirokuprzedni = (platnoscirokuprzedni != null ? platnoscirokuprzedni.stream()
            .filter(item -> item != null && item.getKontrahent() != null && klient.getNip().equals(item.getKontrahent().getNip()))
            .collect(Collectors.toList()) : Collections.emptyList());

        List<Faktura> klientfaktury = (faktury != null ? faktury.stream()
            .filter(item -> item != null && item.isTylkodlaokresowej()==false && item.getKontrahent() != null && klient.getNip().equals(item.getKontrahent().getNip()))
            .collect(Collectors.toList()) : Collections.emptyList());

        List<Faktura> klientfakturyrokuprzedni = (fakturyrokuprzedni != null ? fakturyrokuprzedni.stream()
            .filter(item -> item != null && item.isTylkodlaokresowej()==false && item.getKontrahent() != null && klient.getNip().equals(item.getKontrahent().getNip()))
            .collect(Collectors.toList()) : Collections.emptyList());

        // Pobieranie nowych pozycji
        nowepozycje = pobierzelementy(mc, false, klient, false, klientplatnosci, klientplatnoscirokuprzedni, klientfaktury, klientfakturyrokuprzedni);

        // Pobieranie archiwalnych pozycji
        archiwum = pobierzelementy(mc, true, klient, false, klientplatnosci, klientplatnoscirokuprzedni, klientfaktury, klientfakturyrokuprzedni);
        if (pokazarchiwalne==false) {
            nowepozycje = nowepozycje.stream().filter(p->p.isArchiwalny()==false).collect(Collectors.toList());
            archiwum = archiwum.stream().filter(p->p.isArchiwalny()==false).collect(Collectors.toList());
        }
     
        sortujsumuj(nowepozycje);
        sortujsumuj(archiwum);
        Msg.msg("Pobrano dane kontrahenta");

    }
    
    //tu zbiorczo
    public void pobierzwszystko(String mc, Klienci klient, List<FakturaRozrachunki> platnosciDuza, List<FakturaRozrachunki> platnoscirokuprzedniDuza, List<Faktura> fakturyDuza, List<Faktura> fakturyrokuprzedniDuza) {
        if (klient == null || klient.getNip() == null) {
            throw new IllegalArgumentException("Klient lub jego NIP jest null");
        }

        if (pobierzdwalata) {
            dolaczrokpoprzedni = true;
        }
       // Bezpieczne przetwarzanie list z użyciem parallelStream
        List<FakturaRozrachunki> platnosci = (platnosciDuza != null ? platnosciDuza.stream()
            .filter(item -> item != null && item.getKontrahent() != null && klient.getNip().equals(item.getKontrahent().getNip()))
            .collect(Collectors.toList()) : Collections.emptyList());

        List<FakturaRozrachunki> platnoscirokuprzedni = (platnoscirokuprzedniDuza != null ? platnoscirokuprzedniDuza.stream()
            .filter(item -> item != null && item.getKontrahent() != null && klient.getNip().equals(item.getKontrahent().getNip()))
            .collect(Collectors.toList()) : Collections.emptyList());

        List<Faktura> faktury = (fakturyDuza != null ? fakturyDuza.stream()
            .filter(item -> item != null && item.isTylkodlaokresowej()==false && item.getKontrahent() != null && klient.getNip().equals(item.getKontrahent().getNip()))
            .collect(Collectors.toList()) : Collections.emptyList());

        List<Faktura> fakturyrokuprzedni = (fakturyrokuprzedniDuza != null ? fakturyrokuprzedniDuza.stream()
            .filter(item -> item != null && item.isTylkodlaokresowej()==false && item.getKontrahent() != null && klient.getNip().equals(item.getKontrahent().getNip()))
            .collect(Collectors.toList()) : Collections.emptyList());
        // Pobieranie nowych pozycji
        nowepozycje = pobierzelementy(mc, false, klient, true, platnosci, platnoscirokuprzedni, faktury, fakturyrokuprzedni);

        // Pobieranie archiwalnych pozycji
        archiwum = pobierzelementy(mc, true, klient, true, platnosci, platnoscirokuprzedni, faktury, fakturyrokuprzedni);
        sortujsumuj(nowepozycje);
        sortujsumuj(archiwum);
    }
    
    public List<FakturaPodatnikRozliczenie> pobierzelementy(String mc, boolean nowe0archiwum, Klienci klient, boolean pominarchiwalne,
            List<FakturaRozrachunki> platnosci, List<FakturaRozrachunki> platnoscirokuprzedniDuza, List<Faktura> faktury, List<Faktura> fakturyrokuprzedni) {
        List<FakturaPodatnikRozliczenie> pozycje = null;
        if (klient != null) {
            if (pokazrokpoprzedni==false) {
                //List<FakturaRozrachunki> platnosci = fakturaRozrachunkiDAO.findByPodatnikKontrahentRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), klient);
                if (dolaczrokpoprzedni) {
                    //platnosci = fakturaRozrachunkiDAO.findByPodatnikKontrahentRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), klient);
                    for (Iterator<FakturaRozrachunki> it =platnosci.iterator();it.hasNext();) {
                        FakturaRozrachunki f = it.next();
                        if (f.getNrdokumentu().contains("bo")) {
                            it.remove();
                        }
                    }
                    platnosci.addAll(platnoscirokuprzedniDuza);
                }
                if (pominarchiwalne) {
                    for (Iterator<FakturaRozrachunki> it =platnosci.iterator();it.hasNext();) {
                        FakturaRozrachunki f = it.next();
                        if (f.isRozrachunekarchiwalny()) {
                            it.remove();
                        }
                    }
                }
                
                //problem jest zeby nie brac wczesniejszych niz 2016 wiec BO sie robi
                //List<Faktura> faktury = fakturaDAO.findbyKontrahentNipRok(klient.getNip(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                if (dolaczrokpoprzedni) {
                    //faktury = fakturaDAO.findbyKontrahentNipRok(klient.getNip(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                    faktury.addAll(fakturyrokuprzedni);
                }
                if (pominarchiwalne) {
                    for (Iterator<Faktura> it =faktury.iterator();it.hasNext();) {
                        Faktura f = it.next();
                        if (f.isRozrachunekarchiwalny()) {
                            it.remove();
                        }
                    }
                }
                pozycje = stworztabele(platnosci, faktury, nowe0archiwum);
            } else {
                //List<FakturaRozrachunki>  platnosci = fakturaRozrachunkiDAO.findByPodatnikKontrahentRok(wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt(), klient);
                if (pominarchiwalne) {
                    for (Iterator<FakturaRozrachunki> it =platnosci.iterator();it.hasNext();) {
                        FakturaRozrachunki f = it.next();
                        if (f.isRozrachunekarchiwalny()) {
                            it.remove();
                        }
                    }
                }
                //List<Faktura> faktury = fakturaDAO.findbyKontrahentNipRok(klient.getNip(), wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt());
                if (pominarchiwalne) {
                    for (Iterator<Faktura> it =faktury.iterator();it.hasNext();) {
                        Faktura f = it.next();
                        if (f.isRozrachunekarchiwalny()) {
                            it.remove();
                        }
                    }
                }
                pozycje = stworztabele(platnosci, faktury, nowe0archiwum);
            }
            
            usuninnemiesiace(wpisView.getRokWpisuSt(), mc, pozycje);
            int i = 1;
            for (FakturaPodatnikRozliczenie p : pozycje) {
                p.setLp(i++);
            }
        }
        return pozycje;
    }
    
     
    public void resetrozliczonych() {
        List<Klienci> wykazklientow = fakturaDAO.findKontrahentFaktury(wpisView.getPodatnikObiekt());
        for (Iterator<Klienci> it = wykazklientow.iterator(); it.hasNext();) {
            Klienci k = it.next();
           if (k!=null&&k.isRozliczonynakoniecroku() == true) {
               k.setRozliczonynakoniecroku(false);
            } else if (k==null) {
                it.remove();
            }
        }
        klienciDAO.editList(wykazklientow);
        klienci = wykazklientow;
        Msg.msg("Zresetowano klientów");
    }
   
     
    private List<Klienci> pobierzkontrahentow(List<Podatnik> podatnicy) {
        List kliencifaktury = Collections.synchronizedList(fakturaDAO.findKontrahentFaktury(wpisView.getPodatnikObiekt()));
        for (Iterator<Klienci> it = kliencifaktury.iterator(); it.hasNext();) {
            Klienci k = it.next();
            if (k == null) {
                it.remove();
            } else if (pokazrozliczonych == false && k.isRozliczonynakoniecroku() == true) {
                it.remove();
            } else if (k.isAktywnydlafaktrozrachunki() == false) {
                it.remove();
            } else {
                for (Podatnik po : podatnicy) {
                    if (po.getNip().equals(k.getNip())) {
                        if (po.isPodmiotaktywny() == false) {
                            it.remove();
                            break;
                        } else {
                            k.setNazwapodatnika(po.getPrintnazwa());
                            k.setTelefon(po.getTelefonkontaktowy());
                            k.setAktywny(po.isPodmiotaktywny());
                            if (po.getKsiegowa()!=null) {
                                k.setKsiegowa(po.getKsiegowa());
                            }
                            k.setPolecajacy(po.getPolecajacy());

                            break;
                        }
                    }
                }
                if (k.getNazwapodatnika()==null) {
                    k.setNazwapodatnika(k.getNazwabezCudzy());
                }
            }
        }
        return kliencifaktury;
    }
    
    public List<Klienci> completeKL(String query) {
        List<Klienci> results = Collections.synchronizedList(new ArrayList<>());
        if (query.length() > 1) {
            Pattern pattern = Pattern.compile("[A-Z]{2}\\d+");
            Matcher m = pattern.matcher(query.toUpperCase());
            boolean czynipzagraniczny = m.matches();
            if (czynipzagraniczny) {
                for (Klienci p : klienci) {
                    if (p.getNip().startsWith(query.toUpperCase())) {
                            results.add(p);
                    }
                }
            } else {
                try {
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    for (Klienci p : klienci) {
                        if (p.getNip().startsWith(query)) {
                            results.add(p);
                        }
                    }
                } catch (NumberFormatException e) {
                    for (Klienci p : klienci) {
                        if (p.getNpelna().toLowerCase().contains(query.toLowerCase())) {
                            results.add(p);
                        }
                    }
                }
            }
            results.add(new Klienci(-1, "nowy klient", "nowy klient", "0123456789", "11-111", "miejscowosc", "ulica", "1", "1"));
        }
        return results;
    }
    
    public void rozlicz(FakturaPodatnikRozliczenie p, boolean nowy0archiwum1) {
        if (selectedrozliczenia != null && selectedrozliczenia.size() > 0) {
            for (FakturaPodatnikRozliczenie r : selectedrozliczenia) {
                rozliczJednaPozycja(r, nowy0archiwum1);
            }
            selectedrozliczenia = null;
        } else {
            rozliczJednaPozycja(p, nowy0archiwum1);
        }
        sortujsumuj(nowepozycje);
        sortujsumuj(archiwum);
    }
    
    private void sortujsumuj(List<FakturaPodatnikRozliczenie> pozycje) {
        if (pozycje != null) {
            Collections.sort(pozycje, new FakturaPodatnikRozliczeniecomparator());
            obliczsaldo(pozycje);
        }
    }
    
    public void bezwezwania() {
        if (saldanierozliczone!=null) {
            saldanierozliczone = saldanierozliczone.stream().filter(p->p.isSwiezowezwany()==false).collect(Collectors.toList());
        }
    }
    
    private void obliczsaldo(List<FakturaPodatnikRozliczenie> nowepozycje) {
        double saldo = 0.0;
        double saldopln = 0.0;
        int iloscfakturbezplatnosci = 0;
        for (FakturaPodatnikRozliczenie p : nowepozycje) {
            try {
                if (p.isFaktura0rozliczenie1()) {
                    iloscfakturbezplatnosci --;
                    if (p.getRozliczenie().getKurs() != 0.0) {
                        saldo -= p.getKwota();
                        saldopln -= p.getKwotapln();
                    } else {
                        saldo -= p.getKwota();
                        saldopln -= p.getKwota();
                    }
                    iloscfakturbezplatnosci = 0;
                    
                } else {
                    iloscfakturbezplatnosci ++;
                    if (p.getFaktura() != null && p.getFaktura().getWalutafaktury() != null && p.getFaktura().getWalutafaktury().equals("PLN")) {
                        saldo += p.getKwota();
                        saldopln += p.getKwota();
                    } else if (p.getFaktura() != null && p.getFaktura().getWalutafaktury() != null && !p.getFaktura().getWalutafaktury().equals("PLN")) {
                        saldo += p.getKwota();
                        saldopln += p.getKwotapln();
                    } else if (p.getRozliczenie() != null && p.getRozliczenie().getKurs() == 0.0) {//to jest dla dokumentu bo ktory jest traktowany jak faktura ale jest rozliczeniem
                        saldo += p.getKwota();
                        saldopln += p.getKwota();
                    } else if (p.getRozliczenie() != null && p.getRozliczenie().getKurs() != 0.0) {
                        saldo += p.getKwota();
                        saldopln += p.getKwotapln();
                    }
                }
                p.setSaldo(saldo);
                p.setSaldopln(saldopln);
                p.setIloscfakturbezplatnosci(iloscfakturbezplatnosci);
            } catch (Exception e) {
                E.e(e);
            }
        }
    }
    
    public void rozliczJednaPozycja(FakturaPodatnikRozliczenie p, boolean nowy0archiwum1) {
        try {
            archiwizuj(p, nowy0archiwum1);
            if (nowy0archiwum1) {
                nowepozycje.remove(p);
                archiwum.add(p);
            } else {
                nowepozycje.add(p);
                archiwum.remove(p);
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    private void archiwizuj(FakturaPodatnikRozliczenie p, boolean nowy0archiwum1) {
        if (p.isFaktura0rozliczenie1() == false) {
            p.getFaktura().setNowy0archiwum1(nowy0archiwum1);
            fakturaDAO.edit(p.getFaktura());
        } else {
            p.getRozliczenie().setNowy0archiwum1(nowy0archiwum1);
            fakturaRozrachunkiDAO.edit(p.getRozliczenie());
        }
    }
    
    public void archiwizujdok() {
        if (selectedrozliczenia.isEmpty()==false) {
            selectedrozliczenia.stream().forEach(p->{
                p.setArchiwalny(true);
                nanieszmianaarchiwalny(p);
            });
            selectedrozliczenia = null;
            pobierzwszystkoKlienta();
            Msg.msg("Zarchiwizowano wybrane pozycje");
        } else {
            Msg.msg("e","Wybieramy pozycje do archiwizacji po lewej stronie");
        }
    }
    
     public void nanieszmianaarchiwalny(FakturaPodatnikRozliczenie item) {
        if (item !=null) {
            Faktura faktura = item.getFaktura();
            if (faktura!=null) {
                faktura.setRozrachunekarchiwalny(item.isArchiwalny());
                fakturaDAO.edit(faktura);
            }
            FakturaRozrachunki rozliczenie = item.getRozliczenie();
            if (rozliczenie!=null) {
                rozliczenie.setRozrachunekarchiwalny(item.isArchiwalny());
                fakturaRozrachunkiDAO. edit(rozliczenie);
            }
            //Msg.msg("Naniesiono zmianę");
        } else {
            Msg.msg("e","Nie wybrano pozycji");
        }
    }
    
    
    private List<FakturaPodatnikRozliczenie> stworztabele(List<FakturaRozrachunki> platnosci, List<Faktura> faktury, boolean nowe0archiwum1) {
        List<FakturaPodatnikRozliczenie> l = Collections.synchronizedList(new ArrayList<>());
        FakturaPodatnikRozliczenie ostatniaplatnosc = null;
        if (platnosci != null) {
            for (FakturaRozrachunki p : platnosci) {
                if (p.isNowy0archiwum1() == nowe0archiwum1) {
                    ostatniaplatnosc = new FakturaPodatnikRozliczenie(p);
                    l.add(ostatniaplatnosc);
                }
            }
        }
        if (faktury != null) {
            for (Faktura r : faktury) {
                if (tylkoprzeterminowane && r.isNowy0archiwum1() == false && !r.getMc().equals(wpisView.getMiesiacWpisu())) {
                    l.add(new FakturaPodatnikRozliczenie(r));
                } else if (!tylkoprzeterminowane && r.isNowy0archiwum1() == nowe0archiwum1) {
                    l.add(new FakturaPodatnikRozliczenie(r));
                }
            }
        }
        Collections.sort(l, new FakturaPodatnikRozliczeniecomparator());
        if (!l.isEmpty() && ostatniaplatnosc != null) {
            FakturaPodatnikRozliczenie o = l.get(l.size()-1);
            o.setOstatniaplatnoscdata(ostatniaplatnosc.getData());
            o.setOstatniaplatnosckwota(ostatniaplatnosc.getKwota());
        }
        return l;
    }
    
    public void zestawieniezbiorcze() {
        klienci = new ArrayList<>();
        klienci.addAll(pobierzkontrahentow(podatnicy));
        Collections.sort(klienci, new Kliencicomparator());
        List<Podatnik> podatnicy = podatnikDAO.findAllManager();
        saldanierozliczone = Collections.synchronizedList(new ArrayList<>());
        if (wybranyksiegowy!=null) {
            for (Iterator<Klienci> itk = klienci.iterator();itk.hasNext();) {
                Klienci k = itk.next();
                boolean niemapodatnika = true;
                for (Iterator<Podatnik> it = podatnicy.iterator();it.hasNext();) {
                    Podatnik pod = it.next();
                    if (k.getNip().equals(pod.getNip())) {
                        niemapodatnika = false;
                        if (pod.getKsiegowa()!=null&&!pod.getKsiegowa().equals(wybranyksiegowy)) {
                            itk.remove();
                        } else if (pod.getKsiegowa()==null) {
                            itk.remove();
                        }
                        it.remove();
                        break;
                    }
                }
                if (niemapodatnika&&wybranyksiegowy.getUprawnienia().equals("Administrator")==false) {;
                    itk.remove();
                }
            }
        }
        for (Klienci k : klienci) {
            for (Iterator<Podatnik> it = podatnicy.iterator();it.hasNext();) {
                Podatnik pod = it.next();
                if (k.getNip().equals(pod.getNip())) {
                    k.setZnacznik1(pod.getTelefonkontaktowy());
                    it.remove();
                    break;
                }
            }
        }
        klienci.stream().forEach(p -> {
//            if (szukanyklient.getNpelna().equals("\"KONSBUD\" PROJEKTOWANIE I REALIZACJA KONSTRUKCJI BUDOWLANYCH Przemysław Żurowski")) {
//                error.E.s("");
//            }
            pobierzwszystko(wpisView.getMiesiacWpisu(), p, platnosci, platnoscirokuprzedni, faktury, fakturyrokuprzedni);
            if (nowepozycje.size() > 0) {
                FakturaPodatnikRozliczenie r = nowepozycje.get(nowepozycje.size()-1);
                r.setNrtelefonu(p.getZnacznik1());
                if (r.getSaldopln() != 0.0) {
                    if (r.isFaktura0rozliczenie1()) {
                        r.setColor2("green");
                    } else {
                        r.setColor("initial");
                    }
                    if (r.getDataupomnienia()!=null || r.getDatatelefon()!=null) {
                        r.setColor("blue");
                        r.setSwiezowezwany(true);
                    } else {
                        r.setColor("initial");
                        r.setSwiezowezwany(false);
                    }
                    //r.setLp(i++);
                    if (pokaznadplaty == true) {
                        saldanierozliczone.add(r);
                        
                    } else if (r.getSaldopln() > 0.0) {
                        saldanierozliczone.add(r);
                    }
                }
            }
        });
        sumasaldnierozliczonych = 0.0;
        int i = 1;
        for (Iterator<FakturaPodatnikRozliczenie> it =  saldanierozliczone.iterator(); it.hasNext();) {
            FakturaPodatnikRozliczenie r = it.next();
            if (r.getSaldopln()==0.0) {
                it.remove();
            } else {
                r.setLp(i++);
                sumasaldnierozliczonych += r.getSaldopln();
            }
        }
        
    }
    
    public void sumujwybrane() {
        sumasaldnierozliczonych = 0.0;
        if (saldanierozliczoneselected != null) {
            for (FakturaPodatnikRozliczenie p : saldanierozliczoneselected) {
                sumasaldnierozliczonych += p.getSaldopln();
            }
        }
    }

    public void pobierzszczegoly(FakturaPodatnikRozliczenie p) {
        if (p.getRozliczenie()!=null) {
            szukanyklient = p.getRozliczenie().getKontrahent();
        } else {
            szukanyklient = p.getFaktura().getKontrahent();
        }
        for (Podatnik po : podatnicy) {
            if (po.getNip().equals(szukanyklient.getNip())) {
                szukanyklient.setNazwapodatnika(po.getPrintnazwa());
                szukanyklient.setTelefon(po.getTelefonkontaktowy());
                szukanyklient.setKsiegowa(po.getKsiegowa());
                szukanyklient.setAktywny(po.isPodmiotaktywny());
                szukanyklient.setPolecajacy(po.getPolecajacy());
                break;
            }
        }
        if (szukanyklient.getNazwapodatnika()==null) {
            szukanyklient.setNazwapodatnika(szukanyklient.getNazwabezCudzy());
        }
        pobierzwszystko(wpisView.getMiesiacWpisu(), szukanyklient, platnosci, platnoscirokuprzedni, faktury, fakturyrokuprzedni);
        selectOneUI.setValue(szukanyklient);
        aktywnytab = 3;
        selectedrozliczenia = null;
    }
    
     public void pobierzszczegolyWyciag(FakturaRozrachunki p) {
        szukanyklient = p.getKontrahent();
        for (Podatnik po : podatnicy) {
            if (po.getNip().equals(szukanyklient.getNip())) {
                szukanyklient.setNazwapodatnika(po.getPrintnazwa());
                szukanyklient.setTelefon(po.getTelefonkontaktowy());
                szukanyklient.setKsiegowa(po.getKsiegowa());
                szukanyklient.setAktywny(po.isPodmiotaktywny());
                szukanyklient.setPolecajacy(po.getPolecajacy());
                break;
            }
        }
        if (szukanyklient.getNazwapodatnika()==null) {
            szukanyklient.setNazwapodatnika(szukanyklient.getNazwabezCudzy());
        }
        pobierzwszystko(wpisView.getMiesiacWpisu(), szukanyklient, platnosci, platnoscirokuprzedni, faktury, fakturyrokuprzedni);
        selectOneUI.setValue(szukanyklient);
        aktywnytab = 3;
        selectedrozliczenia = null;
    }
    
    public void usunfakture(FakturaPodatnikRozliczenie fakturaPodatnikRozliczenie) {
        Faktura faktura = fakturaPodatnikRozliczenie.getFaktura();
        FakturaRozrachunki rozrachunek = fakturaPodatnikRozliczenie.getRozliczenie();
        if (faktura!=null) {
            fakturaDAO.remove(faktura);
            nowepozycje.remove(fakturaPodatnikRozliczenie);
            Msg.msg("Usunięto fakturę");
        } else if (rozrachunek!=null){
            fakturaRozrachunkiDAO.remove(rozrachunek);
            nowepozycje.remove(fakturaPodatnikRozliczenie);
            Msg.msg("Usunięto rozliczenie");
        } else {
            Msg.msg("e","Nie wybrano faktury/rozrachunku");
        }
    }
    
    public void zaksiegujjakoBOselected() {
        if (saldanierozliczoneselected!=null && !saldanierozliczoneselected.isEmpty()) {
            for (FakturaPodatnikRozliczenie p : saldanierozliczoneselected) {
                zaksiegujjakoBO(p);
            }
            Msg.msg("Przeniesiono salda do BO");
        } else {
            Msg.msg("e","Nie wybrano faktur do BO");
        }
    }
    
     
     
    public void zaksiegujjakoBO(FakturaPodatnikRozliczenie p) {
        List<Faktura> klientfaktury = (faktury != null ? faktury.stream()
                .filter(item -> item != null && item.isTylkodlaokresowej() == false && item.getKontrahent() != null && p.getKontrahentNip().equals(item.getKontrahent().getNip()))
                .filter(item -> item.isRozrachunekarchiwalny() == false)
                .collect(Collectors.toList()) : Collections.emptyList());
        for (Faktura faktura : klientfaktury) {
            FakturaRozrachunki zapisbo = new FakturaRozrachunki();
            zapisbo.setData(faktura.getDatawystawienia());
            zapisbo.setKontrahent(faktura.getKontrahent());
            if (faktura.getTabelanbp()!=null) {
                zapisbo.setWaluta(faktura.getTabelanbp().getWaluta().getSymbolwaluty());
            } else {
                zapisbo.setWaluta("PLN");
            }
            if (faktura.getTabelanbp()!=null) {
                zapisbo.setKurs(faktura.getTabelanbp().getKurssredniPrzelicznik());
            }
            zapisbo.setKwotapln(faktura.getBrutto());
            zapisbo.setKwotawwalucie(faktura.getBrutto());
            zapisbo.setRok(wpisView.getRokNastepnySt());
            zapisbo.setMc("01");
            zapisbo.setWystawca(wpisView.getPodatnikObiekt());
            zapisbo.setWprowadzil(wpisView.getUzer());
            zapisbo.setZaplata0korekta1(true);
            zapisbo.setRodzajdokumentu("ka");
            String nr = "bo/" + faktura.getNumerkolejny();
            zapisbo.setNrdokumentu(nr);
            zapisbo.setPrzeniesionosaldo(true);
            faktura.setPrzeniesionosaldo(true);
            fakturaRozrachunkiDAO.create(zapisbo);
        }
        //bo oznaczylismy ze przeniesiono saldo do bo
        fakturaDAO.editList(klientfaktury);
        aktywnytab = 3;
        Msg.msg("Zaksięgowano w bo");
    }
    
//    public void zaksiegujjakoBO (FakturaPodatnikRozliczenie p) {
//        double saldo = p.getSaldo();
//        FakturaRozrachunki f = new FakturaRozrachunki();
//        f.setData(wpisView.getRokNastepnySt()+"-01-01");
//        if (p.isFaktura0rozliczenie1()) {
//            FakturaRozrachunki fr = p.getRozliczenie();
//            szukanyklient = fr.getKontrahent();
//            fr.setPrzeniesionosaldo(true);
//            fakturaRozrachunkiDAO.edit(fr);
//        } else {
//            Faktura fa = p.getFaktura();
//            szukanyklient = fa.getKontrahent();
//            fa.setPrzeniesionosaldo(true);
//            fakturaDAO.edit(fa);
//        }
//        f.setKontrahent(szukanyklient);
//        f.setKwotapln(p.getSaldopln());
//        f.setKwotawwalucie(p.getSaldo());
//        f.setRok(wpisView.getRokNastepnySt());
//        f.setMc("01");
//        f.setWystawca(wpisView.getPodatnikObiekt());
//        f.setWprowadzil(wpisView.getUzer());
//        f.setZaplata0korekta1(true);
//        f.setRodzajdokumentu("ka");
//        String nr = "bo/"+wpisView.getPodatnikWpisu().substring(0,1)+"/"+wpisView.getMiesiacWpisu();
//        f.setNrdokumentu(nr);
//        f.setPrzeniesionosaldo(true);
//        selectOneUI.setValue(szukanyklient);
//        fakturaRozrachunkiDAO.create(f);
//        aktywnytab = 3;
//        Msg.msg("Zaksięgowano w bo");
//    }
    
    
    
    public void korygujsaldo(FakturaPodatnikRozliczenie p) {
        double saldopln = p.getSaldopln();
        double saldowaluta = p.getSaldo();
        if (saldopln != 0.0) {
            FakturaRozrachunki f = new FakturaRozrachunki();
            f.setData(Data.aktualnaData());
            if (p.getRozliczenie()!= null) {
                szukanyklient = p.getRozliczenie().getKontrahent();
            } else {
                szukanyklient = p.getFaktura().getKontrahent();
            }
            f.setKontrahent(szukanyklient);
            f.setWaluta(p.getWalutafaktury());
            f.setKwotawwalucie(-p.getSaldo());
            f.setKwotapln(-p.getSaldopln());
            if (p.getSaldo()!=0.0 && p.getSaldopln()!=0.0 && Z.z(p.getSaldo())!=Z.z(p.getSaldopln())) {
                f.setKurs(p.getSaldopln()/p.getSaldo());
            }
            f.setRok(wpisView.getRokWpisuSt());
            f.setMc(wpisView.getMiesiacWpisu());
            f.setWystawca(wpisView.getPodatnikObiekt());
            f.setWprowadzil(wpisView.getUzer());
            f.setZaplata0korekta1(true);
            f.setRodzajdokumentu("ka");
            f.setRozrachunekarchiwalny(true);
            String nr = "ka/"+wpisView.getPodatnikWpisu().substring(0,1)+"/"+wpisView.getMiesiacWpisu();
            f.setNrdokumentu(nr);
            selectOneUI.setValue(szukanyklient);
            fakturaRozrachunkiDAO.create(f);
            saldanierozliczone.remove(p);
            aktywnytab = 3;
        } else {
            Msg.msg("w", "Saldo zerowe, nie ma czego korygować");
        }
    }
    
    public void drukujKlienci() {
        try {
            List<FakturaPodatnikRozliczenie> lista = selectedrozliczenia!=null&&!selectedrozliczenia.isEmpty()?selectedrozliczenia:nowepozycje;
            PdfFaktRozrach.drukujKlienci(szukanyklient, lista, archiwum, wpisView);
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd. Wydruk nieudany");
            E.e(e);
        }
    }
    
    
    public void mailKlienci() {
        if (szukanyklient != null && !selectedrozliczenia.isEmpty()) {
            FakturaPodatnikRozliczenie p = selectedrozliczenia.get(selectedrozliczenia.size()-1);
            FakturaRozrachunki r = p.getRozliczenie();
            Faktura f = p.getFaktura();
            double saldo = sumujwybraneroz(selectedrozliczenia);
            Map<String,Double> saldawaluty = sumujwybranerozwal(selectedrozliczenia);
            if (saldo > 0) {
                //obetnijliste(p);
                try {
                    //String nazwa = PdfFaktRozrach.drukujKlienciSilent(szukanyklient, selectedrozliczenia, wpisView, saldo);
                    Fakturadodelementy stopka = fakturadodelementyDAO.findFaktStopkaPodatnik(wpisView.getPodatnikWpisu());
                    MailFaktRozrach.rozrachunek(szukanyklient.getNpelna(), szukanyklient.getEmail(), dodatkowyadresmailowy, szukanyklient.getTelefon(),
                            selectedrozliczenia, wpisView, fakturaDAO, saldo, stopka.getTrescelementu(), SMTPBean.pobierzSMTP(sMTPSettingsDAO, wpisView.getUzer()), sMTPSettingsDAO.findSprawaByDef(), tekstwiadomosci, saldawaluty);
                    if (r != null) {
                        r.setDataupomnienia(new Date());
                        p.setDataupomnienia(new Date());
                        fakturaRozrachunkiDAO.edit(r);
                    } else {
                        f.setDataupomnienia(new Date());
                        p.setDataupomnienia(new Date());
                        fakturaDAO.edit(f);
                    }
                    Msg.msg("Wysłano upomnienie do klienta");
                    Map<String, String> zwrot = SmsSend.wyslijSMSyFaktura(f.getKontrahent().getNip(),f.getKontrahent().getEmail(), "Na adres firmy wysłano wezwanie do zapłaty.", podatnikDAO);
                    if (zwrot.size()>0) {
                        Msg.msg("e","Błąd podczas wysyłki wezwania do zapłaty "+zwrot.size());
                        for (String t : zwrot.keySet()) {
                            error.E.s("nr "+t+" treść: "+zwrot.get(t));
                        }
                    }
                } catch (Exception e){}
            } else {
                Msg.msg("e", "Saldo zerowe, nie ma po co wysyłac maila");
            }
        } else {
            Msg.msg("e", "Nie wybrano faktur do wysyłki");
        }
    }
    
    public void mailKliencitest() {
        if (szukanyklient != null && !selectedrozliczenia.isEmpty()) {
            FakturaPodatnikRozliczenie p = selectedrozliczenia.get(selectedrozliczenia.size()-1);
            FakturaRozrachunki r = p.getRozliczenie();
            Faktura f = p.getFaktura();
            double saldo = sumujwybraneroz(selectedrozliczenia);
            Map<String,Double> saldawaluty = sumujwybranerozwal(selectedrozliczenia);
            if (saldo > 0) {
                //obetnijliste(p);
                try {
                    //String nazwa = PdfFaktRozrach.drukujKlienciSilent(szukanyklient, selectedrozliczenia, wpisView, saldo);
                    Fakturadodelementy stopka = fakturadodelementyDAO.findFaktStopkaPodatnik(wpisView.getPodatnikWpisu());
                    MailFaktRozrach.rozrachunek(szukanyklient.getNpelna(),"info@taxman.biz.pl", dodatkowyadresmailowy, szukanyklient.getTelefon(),
                            selectedrozliczenia, wpisView, fakturaDAO, saldo, stopka.getTrescelementu(), SMTPBean.pobierzSMTP(sMTPSettingsDAO, wpisView.getUzer()), sMTPSettingsDAO.findSprawaByDef(), tekstwiadomosci, saldawaluty);
                    if (r != null) {
                        r.setDataupomnienia(new Date());
                        p.setDataupomnienia(new Date());
                        fakturaRozrachunkiDAO.edit(r);
                    } else {
                        f.setDataupomnienia(new Date());
                        p.setDataupomnienia(new Date());
                        fakturaDAO.edit(f);
                    }
                    Msg.msg("Wysłano upomnienie do klienta");
                    Map<String, String> zwrot = SmsSend.wyslijSMSyFaktura("8511005008","info@taxman.biz.pl", "Na adres firmy wysłano wezwanie do zapłaty.", podatnikDAO);
                    if (zwrot.size()>0) {
                        Msg.msg("e","Błąd podczas wysyłki wezwania do zapłaty "+zwrot.size());
                        for (String t : zwrot.keySet()) {
                            error.E.s("nr "+t+" treść: "+zwrot.get(t));
                        }
                    }
                } catch (Exception e){}
            } else {
                Msg.msg("e", "Saldo zerowe, nie ma po co wysyłac maila");
            }
        } else {
            Msg.msg("e", "Nie wybrano faktur do wysyłki");
        }
    }
    
    
    private double sumujwybraneroz(List<FakturaPodatnikRozliczenie> selectedrozliczenia) {
        double zwrot = 0.0;
        for (FakturaPodatnikRozliczenie p : selectedrozliczenia) {
            if (p.isFaktura0rozliczenie1()) {
                zwrot -= p.getKwota();
            } else {
                zwrot += p.getKwota();
            }
        }
        return zwrot;
    }
    
    private Map<String, Double> sumujwybranerozwal(List<FakturaPodatnikRozliczenie> selectedrozliczenia) {
        Map<String, Double> zwrot = new HashMap<>();
        double sumapln = 0.0;
        double sumaeur = 0.0;
        for (FakturaPodatnikRozliczenie p : selectedrozliczenia) {
            if (p.isFaktura0rozliczenie1()) {
                if (p.getWalutafaktury().equals("PLN")) {
                    sumapln -= p.getKwota();
                } else {
                    sumaeur -= p.getKwota();
                }
            } else {
                if (p.getWalutafaktury().equals("PLN")) {
                    sumapln += p.getKwota();
                } else {
                    sumaeur += p.getKwota();
                }
            }
        }
        zwrot.put("PLN", sumapln);
        zwrot.put("EUR", sumaeur);
        return zwrot;
    }
    
    public void telefonKlienci() {
        if (szukanyklient != null && !nowepozycje.isEmpty()) {
            FakturaPodatnikRozliczenie p = nowepozycje.get(nowepozycje.size()-1);
            FakturaRozrachunki r = p.getRozliczenie();
            Faktura f = p.getFaktura();
            double saldo = p.getSaldopln();
            if (saldo > 0) {
                if (r != null) {
                    r.setDatatelefon(new Date());
                    p.setDatatelefon(new Date());
                    fakturaRozrachunkiDAO.edit(r);
                } else {
                    f.setDatatelefon(new Date());
                    p.setDatatelefon(new Date());
                    fakturaDAO.edit(f);
                }
                Msg.msg("Naniesiono informacje o rozmowie z klientem");
            } else {
                Msg.msg("e", "Saldo zerowe, nie ma po co dzwonić");
            }
        }
    }
    
    private void obetnijliste(FakturaPodatnikRozliczenie p) {
        for (Iterator<FakturaPodatnikRozliczenie> it = nowepozycje.iterator(); it.hasNext();) {
            if (it.next().getLp() > p.getLp()) {
                it.remove();
            }
        }
    }
    
    public void drukujszczegolyzbiorcze() {
        List<FakturaPodatnikRozliczenie> lista = saldanierozliczoneselected != null && saldanierozliczoneselected.size()>0 ? saldanierozliczoneselected : saldanierozliczone;
        Map<Klienci, List<FakturaPodatnikRozliczenie>> klista = new HashMap<>();
        for (FakturaPodatnikRozliczenie p : lista) {
            if (p.getRozliczenie()!=null) {
                szukanyklient = p.getRozliczenie().getKontrahent();
            } else {
                szukanyklient = p.getFaktura().getKontrahent();
            }
            pobierzwszystko(wpisView.getMiesiacWpisu(), szukanyklient, platnosci, platnoscirokuprzedni, faktury, fakturyrokuprzedni);
            klista.put(szukanyklient,nowepozycje);
        }
        PdfFaktRozrach.drukujKliencihurt(klista, wpisView);
        Msg.msg("Wydrukowano listę");
    }
    
    public void drukujKlienciZbiorcze() {
        try {
            if (saldanierozliczoneselected != null && saldanierozliczoneselected.size() > 0){
                PdfFaktRozrach.drukujKlienciZbiorcze(saldanierozliczoneselected, wpisView);
            } else {
                PdfFaktRozrach.drukujKlienciZbiorcze(saldanierozliczone, wpisView);
            }
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd. Wydruk nieudany");
            E.e(e);
        }
    }
    
    public void mailwezwaniezbiorcze() {
        if (saldanierozliczoneselected != null && saldanierozliczoneselected.size() > 0) {
            Map<Klienci, List<FakturaPodatnikRozliczenie>> klista = new HashMap<>();
            for (FakturaPodatnikRozliczenie p : saldanierozliczoneselected) {
                try {
                    if (p.isFaktura0rozliczenie1()) {
                        szukanyklient = p.getRozliczenie().getKontrahent();
                    } else {
                        szukanyklient = p.getFaktura().getKontrahent();
                    }
                    pobierzwszystko(wpisView.getMiesiacWpisu(), szukanyklient, platnosci, platnoscirokuprzedni, faktury, fakturyrokuprzedni);
                    mailKlienci();
                } catch (Exception e) {
                    Msg.msg("e", "Bład przy jednej pozycji");
                }
            }
        } else {
            Msg.msg("e", "Nie wybrano pozycji");
        }
    }
    private void usuninnemiesiace(String rokWpisuSt, String mc, List<FakturaPodatnikRozliczenie> pozycje) {
        for (Iterator<FakturaPodatnikRozliczenie> it = pozycje.iterator();it.hasNext();) {
            FakturaPodatnikRozliczenie f = it.next();
            if (rokWpisuSt.equals(f.getRok())) {
                int granica = Mce.getMiesiacToNumber().get(mc);
                int mcpozycji = Mce.getMiesiacToNumber().get(f.getMc());
                if (mcpozycji > granica) {
                    it.remove();
                }
            }
        }
    }

   public void zmienmailkontrahenta(ValueChangeEvent ex) {
       try {
           szukanyklient.setEmail((String)ex.getNewValue());
           klienciDAO.edit(szukanyklient);
           Msg.msg("Zmieniono adres mail klienta");
       } catch (Exception e) {
           Msg.msg("e","Wytstąpił błąd, nie zmieniono adres mail klienta");
       }
   }
   
   public void zmienteleofnkontrahenta(ValueChangeEvent ex) {
       try {
           szukanyklient.setTelefon((String)ex.getNewValue());
           Podatnik podatnik = podatnikDAO.findPodatnikByNIP(szukanyklient.getNip());
           if (podatnik!=null) {
               podatnik.setTelefonkontaktowy(szukanyklient.getTelefon());
               podatnikDAO.edit(podatnik);
           }
           Msg.msg("Zmieniono nr tel. klienta");
       } catch (Exception e) {
           Msg.msg("e","Wytstąpił błąd, nie zmieniono nr tel. klienta");
       }
   }
    

//<editor-fold defaultstate="collapsed" desc="comment">
    public List<Klienci> getKlienci() {
        return klienci;
    }
    
    public void setKlienci(List<Klienci> klienci) {
        this.klienci = klienci;
    }

    public String getDodatkowyadresmailowy() {
        return dodatkowyadresmailowy;
    }

    public void setDodatkowyadresmailowy(String dodatkowyadresmailowy) {
        this.dodatkowyadresmailowy = dodatkowyadresmailowy;
    }

    public boolean isDolaczrokpoprzedni() {
        return dolaczrokpoprzedni;
    }

    public void setDolaczrokpoprzedni(boolean dolaczrokpoprzedni) {
        this.dolaczrokpoprzedni = dolaczrokpoprzedni;
    }

    public boolean isPokazrokpoprzedni() {
        return pokazrokpoprzedni;
    }

    public void setPokazrokpoprzedni(boolean pokazrokpoprzedni) {
        this.pokazrokpoprzedni = pokazrokpoprzedni;
    }

    public String getTekstwiadomosci() {
        return tekstwiadomosci;
    }

    public void setTekstwiadomosci(String tekstwiadomosci) {
        this.tekstwiadomosci = tekstwiadomosci;
    }


    public boolean isPokaznadplaty() {
        return pokaznadplaty;
    }

    public void setPokaznadplaty(boolean pokaznadplaty) {
        this.pokaznadplaty = pokaznadplaty;
    }

    public Klienci getSzukanyklient() {
        return szukanyklient;
    }

    public void setSzukanyklient(Klienci szukanyklient) {
        this.szukanyklient = szukanyklient;
    }

    public double getSumasaldnierozliczonych() {
        return sumasaldnierozliczonych;
    }

    public void setSumasaldnierozliczonych(double sumasaldnierozliczonych) {
        this.sumasaldnierozliczonych = sumasaldnierozliczonych;
    }

    public FakturaRozrachunki getSelected() {
        return selected;
    }

    public void setSelected(FakturaRozrachunki selected) {
        this.selected = selected;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<FakturaPodatnikRozliczenie> getSelectedrozliczenia() {
        return selectedrozliczenia;
    }

    public void setSelectedrozliczenia(List<FakturaPodatnikRozliczenie> selectedrozliczenia) {
        this.selectedrozliczenia = selectedrozliczenia;
    }

    public List<FakturaPodatnikRozliczenie> getArchiwum() {
        return archiwum;
    }

    public void setArchiwum(List<FakturaPodatnikRozliczenie> archiwum) {
        this.archiwum = archiwum;
    }

    public List<FakturaPodatnikRozliczenie> getSaldanierozliczone() {
        return saldanierozliczone;
    }

    public void setSaldanierozliczone(List<FakturaPodatnikRozliczenie> saldanierozliczone) {
        this.saldanierozliczone = saldanierozliczone;
    }

    public int getAktywnytab() {
        return aktywnytab;
    }

    public void setAktywnytab(int aktywnytab) {
        this.aktywnytab = aktywnytab;
    }
    
    public List<FakturaPodatnikRozliczenie> getNowepozycje() {
        return nowepozycje;
    }

    public void setNowepozycje(List<FakturaPodatnikRozliczenie> nowepozycje) {
        this.nowepozycje = nowepozycje;
    }

    public boolean isTylkoprzeterminowane() {
        return tylkoprzeterminowane;
    }

    public void setTylkoprzeterminowane(boolean tylkoprzeterminowane) {
        this.tylkoprzeterminowane = tylkoprzeterminowane;
    }

    public List<FakturaPodatnikRozliczenie> getSaldanierozliczoneselected() {
        return saldanierozliczoneselected;
    }

    public void setSaldanierozliczoneselected(List<FakturaPodatnikRozliczenie> saldanierozliczoneselected) {
        this.saldanierozliczoneselected = saldanierozliczoneselected;
    }


    public UISelectOne getSelectOneUI() {
        return selectOneUI;
    }

    public void setSelectOneUI(UISelectOne selectOneUI) {
        this.selectOneUI = selectOneUI;
    }
    
  
   
//</editor-fold>

    public void sumawartosci() {
        if (selectedrozliczenia!=null) {
            razemwybrane = 0.0;
            for (FakturaPodatnikRozliczenie p : selectedrozliczenia) {
                if (p.isFaktura0rozliczenie1()) {
                    razemwybrane = razemwybrane-p.getKwota();
                } else {
                    razemwybrane = razemwybrane+p.getKwota();
                }
            }
        }
    }
    
    
            
    public void usunokresowa() {
        if (szukanyklient!=null) {
            Podatnik podatnik = podatnikDAO.findPodatnikByNIP(szukanyklient.getNip());
            try {
                List<Fakturywystokresowe> findPodatnikBiezace = fakturywystokresoweDAO.findPodatnikBiezace(podatnik.getNazwapelna(), wpisView.getRokWpisuSt());
                if (findPodatnikBiezace.isEmpty()==false) {
                    fakturywystokresoweDAO.removeList(findPodatnikBiezace);
                }
            } catch (Exception ex){}
            Msg.msg("Usunięto faktury okresowe podatnika");
        } else {
            Msg.msg("e","Nie pobrano podatnika");
        }
    }

    public void dezaktywujpodatnika() {
        if (szukanyklient!=null) {
            Podatnik podatnik = podatnikDAO.findPodatnikByNIP(szukanyklient.getNip());
            podatnik.setPodmiotaktywny(!podatnik.isPodmiotaktywny());
            podatnikDAO.edit(podatnik);
            Klienci klientposzukiwany = klienciDAO.findKlientById(szukanyklient.getId());
            klientposzukiwany.setAktywny(podatnik.isPodmiotaktywny());
            klienciDAO.edit(klientposzukiwany);
            szukanyklient.setAktywny(podatnik.isPodmiotaktywny());
            SMTPSettings ogolne = SMTPBean.pobierzSMTPDef(sMTPSettingsDAO);
             for (Podatnik po : podatnicy) {
                if (po.getNip().equals(szukanyklient.getNip())) {
                    szukanyklient.setKsiegowa(po.getKsiegowa());
                    break;
                }
            }
            String mail = szukanyklient.getKsiegowa()!=null?szukanyklient.getKsiegowa().getEmail():"info@taxman.biz.pl";
            MailAdmin.dezaktywacjaPodanikamail(podatnik, ogolne, "w.daniluk@taxman.biz.pl", mail);
            Msg.msg("Zmieniono aktywacje podatnika");
        } else {
            Msg.msg("e","Nie pobrano podatnika");
        }
    }
    
     public void oznaczrozliczenie() {
        if (szukanyklient!=null) {
            szukanyklient.setRozliczonynakoniecroku(!szukanyklient.isRozliczonynakoniecroku());
            klienciDAO.edit(szukanyklient);
            Msg.msg("Zmieniono rozliczenie klienta");
        } else {
            Msg.msg("e","Nie naniesiono rozliczenie klienta");
        }
    }
    
    
    

    public void korygujnazero() {
        if (selectedrozliczenia != null) {
            List<Faktura> korekty = new ArrayList<>();
            for (FakturaPodatnikRozliczenie p : selectedrozliczenia) {
                Faktura faktura = p.getFaktura();
                if (faktura != null) {
                    Faktura selected = serialclone.SerialClone.clone(faktura);
                    String pelnadata = FakturaBean.obliczdatawystawienia(wpisView);
                    selected.setDatawystawienia(pelnadata);
                    selected.setTerminzaplaty(FakturaBean.obliczterminzaplaty(wpisView.getPodatnikObiekt(), pelnadata));
                    selected.setZaksiegowana(false);
                    selected.setWyslana(false);
                    selected.setPrzyczynakorekty("Korekta faktury nr " + faktura.getNumerkolejny() + " z dnia " + faktura.getDatawystawienia() + " z powodu: brak wykonanej usługi");
                    selected.setNumerkolejny(selected.getNumerkolejny() + "/KOR");
                    selected.setPozycjepokorekcie(utworznowepozycje(faktura.getPozycjenafakturze()));
                    edytuj(selected);
                    korekty.add(selected);
                }
            }
            fakturaDAO.createList(korekty);
            pobierzwszystkoKlienta();
            Msg.msg("Tworzenie korekty faktury "+korekty.size());
        }

    }

    private List<Pozycjenafakturzebazadanych> utworznowepozycje(List<Pozycjenafakturzebazadanych> pozycjenafakturze) {
        List<Pozycjenafakturzebazadanych> zwrot = new ArrayList<>();
        if (pozycjenafakturze != null) {
            for (Pozycjenafakturzebazadanych p : pozycjenafakturze) {
                Pozycjenafakturzebazadanych pozycja = new Pozycjenafakturzebazadanych(p);
                pozycja.setIlosc(0);
                zwrot.add(new Pozycjenafakturzebazadanych(pozycja));
            }
        }
        return zwrot;
    }
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    public void edytuj(Faktura selected) {
        try {
            FakturaBean.dodajtabelenbp(selected, tabelanbpDAO);
            FakturaBean.ewidencjavat(selected, evewidencjaDAO);
            FakturaBean.ewidencjavatkorekta(selected, evewidencjaDAO);
            selected.setKontrahent_nip(selected.getKontrahent().getNip());
            selected.setRok(Data.getCzescDaty(selected.getDatawystawienia(), 0));
            selected.setMc(Data.getCzescDaty(selected.getDatawystawienia(), 1));
            Podatnik podatnikobiekt = wpisView.getPodatnikObiekt();
            if (wpisView.getPodatnikObiekt().getWystawcafaktury() != null && wpisView.getPodatnikObiekt().getWystawcafaktury().equals("brak")) {
                selected.setPodpis("");
            } else if (wpisView.getPodatnikObiekt().getWystawcafaktury() != null && !wpisView.getPodatnikObiekt().getWystawcafaktury().equals("")) {
                selected.setPodpis(wpisView.getPodatnikObiekt().getWystawcafaktury());
            } else {
                selected.setPodpis(wpisView.getUzer().getImie() + " " + wpisView.getUzer().getNazw());
            }
            if (selected.getNazwa() != null && selected.getNazwa().equals("")) {
                selected.setNazwa(null);
            }
            selected.setWygenerowanaautomatycznie(false);
            selected.setRecznaedycja(false);
            selected.setTylkodlaokresowej(false);

        } catch (EJBException e) {
            E.e(e);
            Msg.msg("e", "Nie można zachowac zmian. Sprawdź czy numer faktury jest unikalny");
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e", "Błąd. Niedokonano edycji faktury.");
        }
    }
    
    public double getRazemwybrane() {
        return razemwybrane;
    }

    public void setRazemwybrane(double razemwybrane) {
        this.razemwybrane = razemwybrane;
    }

    public boolean isPobierzdwalata() {
        return pobierzdwalata;
    }

    public void setPobierzdwalata(boolean pobierzdwalata) {
        this.pobierzdwalata = pobierzdwalata;
    }

    public List<FakturaPodatnikRozliczenie> getSaldanierozliczonefiltered() {
        return saldanierozliczonefiltered;
    }

    public void setSaldanierozliczonefiltered(List<FakturaPodatnikRozliczenie> saldanierozliczonefiltered) {
        this.saldanierozliczonefiltered = saldanierozliczonefiltered;
    }

    public Uz getWybranyksiegowy() {
        return wybranyksiegowy;
    }

    public void setWybranyksiegowy(Uz wybranyksiegowy) {
        this.wybranyksiegowy = wybranyksiegowy;
    }

    public List<Uz> getListaksiegowych() {
        return listaksiegowych;
    }

    public void setListaksiegowych(List<Uz> listaksiegowych) {
        this.listaksiegowych = listaksiegowych;
    }

    public boolean isPokazarchiwalne() {
        return pokazarchiwalne;
    }

    public void setPokazarchiwalne(boolean pokazarchiwalne) {
        this.pokazarchiwalne = pokazarchiwalne;
    }

    public boolean isPokazrozliczonych() {
        return pokazrozliczonych;
    }

    public void setPokazrozliczonych(boolean pokazrozliczonych) {
        this.pokazrozliczonych = pokazrozliczonych;
    }

    

    
    
}
