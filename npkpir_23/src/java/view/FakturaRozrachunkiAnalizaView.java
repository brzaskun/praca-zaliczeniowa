/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansFaktura.FakturaBean;
import beansMail.SMTPBean;
import comparator.FakturaPodatnikRozliczeniecomparator;
import comparator.KlienciNPcomparator;
import dao.EvewidencjaDAO;
import dao.FakturaDAO;
import dao.FakturaRozrachunkiDAO;
import dao.FakturadodelementyDAO;
import dao.KlienciDAO;
import dao.PodatnikDAO;
import dao.SMTPSettingsDAO;
import dao.TabelanbpDAO;
import data.Data;
import embeddable.FakturaPodatnikRozliczenie;
import embeddable.Mce;
import embeddable.Pozycjenafakturzebazadanych;
import entity.Faktura;
import entity.FakturaRozrachunki;
import entity.Fakturadodelementy;
import entity.Klienci;
import entity.Podatnik;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
    private KlienciDAO klienciDAO;
    private int aktywnytab;
    private UISelectOne selectOneUI;
    private boolean pokaznadplaty;
    private boolean tylkoprzeterminowane;
    private String tekstwiadomosci;
    private boolean dolaczrokpoprzedni;
    private String dodatkowyadresmailowy;
    List<Podatnik> podatnicy;
    private double razemwybrane;
    
    public FakturaRozrachunkiAnalizaView() {
        
    }

    @PostConstruct
    public void init() { //E.m(this);
        klienci = Collections.synchronizedList(new ArrayList<>());
        nowepozycje = Collections.synchronizedList(new ArrayList<>());
        archiwum = Collections.synchronizedList(new ArrayList<>());
        saldanierozliczone = Collections.synchronizedList(new ArrayList<>());
        klienci.addAll(pobierzkontrahentow());
        dodatkowyadresmailowy="recepcja@taxman.biz.pl";
        Collections.sort(klienci, new KlienciNPcomparator());
        podatnicy = podatnikDAO.findAll();
     }
    
    public void pobierzwszystkoKlienta() {
        selectedrozliczenia = null;
        String mc = wpisView.getMiesiacWpisu();
        for (Podatnik po : podatnicy) {
            if (po.getNip().equals(szukanyklient.getNip())) {
                szukanyklient.setTelefon(po.getTelefonkontaktowy());
                szukanyklient.setKsiegowa(po.getKsiegowa());
                szukanyklient.setPolecajacy(po.getPolecajacy());
                break;
            }
        }
        if (szukanyklient.getEmail()==null ||szukanyklient.getEmail().equals("")) {
            szukanyklient.setEmail("brakmaila!@taxman.biz.pl");
        }
        nowepozycje = pobierzelementy(mc, false, szukanyklient);
        archiwum = pobierzelementy(mc, true, szukanyklient);
        sortujsumuj(nowepozycje);
        sortujsumuj(archiwum);
        Msg.msg("Pobrano dane kontrahenta");

    }
    
    //tu zbiorczo
    public void pobierzwszystko(String mc, Klienci klient) {
        nowepozycje = pobierzelementy(mc, false, klient);
        archiwum = pobierzelementy(mc, true, klient);
        sortujsumuj(nowepozycje);
        sortujsumuj(archiwum);
    }
    
    public List<FakturaPodatnikRozliczenie> pobierzelementy(String mc, boolean nowe0archiwum, Klienci klient) {
        List<FakturaPodatnikRozliczenie> pozycje = null;
        if (klient != null) {
            List<FakturaRozrachunki> platnosci = fakturaRozrachunkiDAO.findByPodatnikKontrahentRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), klient);
            if (dolaczrokpoprzedni) {
                platnosci = fakturaRozrachunkiDAO.findByPodatnikKontrahentRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), klient);
                for (Iterator<FakturaRozrachunki> it =platnosci.iterator();it.hasNext();) {
                    FakturaRozrachunki f = it.next();
                    if (f.getNrdokumentu().contains("bo")) {
                        it.remove();
                    }
                }
                platnosci.addAll(fakturaRozrachunkiDAO.findByPodatnikKontrahentRok(wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt(), klient));
            }
            //problem jest zeby nie brac wczesniejszych niz 2016 wiec BO sie robi
            List<Faktura> faktury = fakturaDAO.findbyKontrahentNipRok(klient.getNip(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            if (dolaczrokpoprzedni) {
                faktury = fakturaDAO.findbyKontrahentNipRok(klient.getNip(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                faktury.addAll(fakturaDAO.findbyKontrahentNipRok(klient.getNip(), wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt()));
            }
            pozycje = stworztabele(platnosci, faktury, nowe0archiwum);
            usuninnemiesiace(wpisView.getRokWpisuSt(), mc, pozycje);
            int i = 1;
            for (FakturaPodatnikRozliczenie p : pozycje) {
                p.setLp(i++);
            }
        }
        return pozycje;
    }
    
   
     
    private Collection<? extends Klienci> pobierzkontrahentow() {
        Collection p = fakturaDAO.findKontrahentFaktury(wpisView.getPodatnikObiekt());
        List<Podatnik> podatnicy = podatnikDAO.findAll();
        for (Iterator<Klienci> it = p.iterator(); it.hasNext();) {
            Klienci k = it.next();
            if (k == null) {
                it.remove();
            } else if (k.isAktywnydlafaktrozrachunki() == false) {
                it.remove();
            } else {
                for (Podatnik po : podatnicy) {
                    if (po.getNip().equals(k.getNip())) {
                        k.setNazwapodatnika(po.getPrintnazwa());
                        k.setTelefon(po.getTelefonkontaktowy());
                        k.setKsiegowa(po.getKsiegowa());
                        k.setPolecajacy(po.getPolecajacy());
                        break;
                    }
                }
                if (k.getNazwapodatnika()==null) {
                    k.setNazwapodatnika(k.getNazwabezCudzy());
                }
            }
        }
        return p;
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
        for (FakturaPodatnikRozliczenie p : nowepozycje) {
            try {
                if (p.isFaktura0rozliczenie1()) {
                    if (p.getRozliczenie().getKurs() != 0.0) {
                        saldo -= p.getKwotapln();
                        saldopln -= p.getKwotapln();
                    } else {
                        saldo -= p.getKwota();
                        saldopln -= p.getKwota();
                    }
                    
                } else {
                    if (p.getFaktura() != null && p.getFaktura().getWalutafaktury() != null && p.getFaktura().getWalutafaktury().equals("PLN")) {
                        saldo += p.getKwota();
                        saldopln += p.getKwota();
                    } else if (p.getFaktura() != null && p.getFaktura().getWalutafaktury() != null && !p.getFaktura().getWalutafaktury().equals("PLN")) {
                        saldo += p.getKwotapln();
                        saldopln += p.getKwotapln();
                    } else if (p.getRozliczenie() != null && p.getRozliczenie().getKurs() == 0.0) {//to jest dla dokumentu bo ktory jest traktowany jak faktura ale jest rozliczeniem
                        saldo += p.getKwota();
                        saldopln += p.getKwota();
                    } else if (p.getRozliczenie() != null && p.getRozliczenie().getKurs() != 0.0) {
                        saldo += p.getKwotapln();
                        saldopln += p.getKwotapln();
                    }
                }
                p.setSaldo(saldo);
                p.setSaldopln(saldopln);
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
        List<Podatnik> podatnicy = podatnikDAO.findAllManager();
        saldanierozliczone = Collections.synchronizedList(new ArrayList<>());
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
            pobierzwszystko(wpisView.getMiesiacWpisu(), p);
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
                szukanyklient.setPolecajacy(po.getPolecajacy());
                break;
            }
        }
        if (szukanyklient.getNazwapodatnika()==null) {
            szukanyklient.setNazwapodatnika(szukanyklient.getNazwabezCudzy());
        }
        pobierzwszystko(wpisView.getMiesiacWpisu(), szukanyklient);
        selectOneUI.setValue(szukanyklient);
        aktywnytab = 3;
    }
    
    public void usunfakture(FakturaPodatnikRozliczenie fakturaPodatnikRozliczenie) {
        Faktura faktura = fakturaPodatnikRozliczenie.getFaktura();
        if (faktura!=null) {
            fakturaDAO.remove(faktura);
            nowepozycje.remove(fakturaPodatnikRozliczenie);
            Msg.msg("Usunięto proformę");
        } else {
            Msg.msg("e","Nie wybrano faktury/Faktura nie jest proformą");
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
    
    public void zaksiegujjakoBO (FakturaPodatnikRozliczenie p) {
        double saldo = p.getSaldo();
        FakturaRozrachunki f = new FakturaRozrachunki();
        f.setData(wpisView.getRokNastepnySt()+"-01-01");
        if (p.isFaktura0rozliczenie1()) {
            FakturaRozrachunki fr = p.getRozliczenie();
            szukanyklient = fr.getKontrahent();
            fr.setPrzeniesionosaldo(true);
            fakturaRozrachunkiDAO.edit(fr);
        } else {
            Faktura fa = p.getFaktura();
            szukanyklient = fa.getKontrahent();
            fa.setPrzeniesionosaldo(true);
            fakturaDAO.edit(fa);
        }
        f.setKontrahent(szukanyklient);
        f.setKwotapln(p.getSaldopln());
        f.setKwotawwalucie(p.getSaldo());
        f.setRok(wpisView.getRokNastepnySt());
        f.setMc("01");
        f.setWystawca(wpisView.getPodatnikObiekt());
        f.setWprowadzil(wpisView.getUzer());
        f.setZaplata0korekta1(true);
        f.setRodzajdokumentu("ka");
        String nr = "bo/"+wpisView.getPodatnikWpisu().substring(0,1)+"/"+wpisView.getMiesiacWpisu();
        f.setNrdokumentu(nr);
        f.setPrzeniesionosaldo(true);
        selectOneUI.setValue(szukanyklient);
        fakturaRozrachunkiDAO.create(f);
        aktywnytab = 3;
        Msg.msg("Zaksięgowano w bo");
    }
    
    public void korygujsaldo(FakturaPodatnikRozliczenie p) {
        double saldopln = p.getSaldopln();
        if (saldopln != 0.0) {
            FakturaRozrachunki f = new FakturaRozrachunki();
            f.setData(Data.aktualnaData());
            if (p.getRozliczenie()!= null) {
                szukanyklient = p.getRozliczenie().getKontrahent();
            } else {
                szukanyklient = p.getFaktura().getKontrahent();
            }
            f.setKontrahent(szukanyklient);
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
            if (saldo > 0) {
                //obetnijliste(p);
                try {
                    //String nazwa = PdfFaktRozrach.drukujKlienciSilent(szukanyklient, selectedrozliczenia, wpisView, saldo);
                    Fakturadodelementy stopka = fakturadodelementyDAO.findFaktStopkaPodatnik(wpisView.getPodatnikWpisu());
                    MailFaktRozrach.rozrachunek(szukanyklient.getNpelna(), szukanyklient.getEmail(), dodatkowyadresmailowy, szukanyklient.getTelefon(), selectedrozliczenia, wpisView, fakturaDAO, saldo, stopka.getTrescelementu(), SMTPBean.pobierzSMTP(sMTPSettingsDAO, wpisView.getUzer()), sMTPSettingsDAO.findSprawaByDef(), tekstwiadomosci);
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
            if (saldo > 0) {
                //obetnijliste(p);
                try {
                    //String nazwa = PdfFaktRozrach.drukujKlienciSilent(szukanyklient, selectedrozliczenia, wpisView, saldo);
                    Fakturadodelementy stopka = fakturadodelementyDAO.findFaktStopkaPodatnik(wpisView.getPodatnikWpisu());
                    MailFaktRozrach.rozrachunek(szukanyklient.getNpelna(),"info@taxman.biz.pl", dodatkowyadresmailowy, szukanyklient.getTelefon(), selectedrozliczenia, wpisView, fakturaDAO, saldo, stopka.getTrescelementu(), SMTPBean.pobierzSMTP(sMTPSettingsDAO, wpisView.getUzer()), sMTPSettingsDAO.findSprawaByDef(), tekstwiadomosci);
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
            pobierzwszystko(wpisView.getMiesiacWpisu(), szukanyklient);
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
                    pobierzwszystko(wpisView.getMiesiacWpisu(), szukanyklient);
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

    

    
    
}
