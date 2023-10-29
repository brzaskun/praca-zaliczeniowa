/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Podatnikcomparator;
import comparator.Uzcomparator;
import dao.DokDAO;
import dao.DokDAOfk;
import dao.KlienciDAO;
import dao.PodatnikDAO;
import dao.UzDAO;
import data.Data;
import entity.Dok;
import entity.Klienci;
import entity.Podatnik;
import entity.Uz;
import entityfk.Dokfk;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
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
public class PodatnikKsiegowaShortView implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Podatnik> listapodatnikow;
    private List<Podatnik> listapodatnikowfiltered;
    private List<Uz> listaksiegowych;
    private List<Uz> listakadrowych;
    private List<Uz> listakadrowychfiltered;
    private Uz wybrany;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private DokDAOfk dokDAOfk;
    private String rok;
    private String mc;
    @Inject
    private KlienciDAO klienciDAO;
    List<Klienci> klienci;
    
    @PostConstruct
    public void init() { //E.m(this);
        listapodatnikow = podatnikDAO.findAktywny();
        Collections.sort(listapodatnikow, new Podatnikcomparator());
        listaksiegowych = uzDAO.findByUprawnienia("Bookkeeper");
        listaksiegowych.addAll(uzDAO.findByUprawnienia("BookkeeperFK"));
        listakadrowych = uzDAO.findByUprawnienia("HumanResources");
        Collections.sort(listaksiegowych, new Uzcomparator());
        Collections.sort(listakadrowych, new Uzcomparator());
        rok = Data.aktualnyRok();
        mc = Data.aktualnyMc();
        String[] okrespoprzedni = Data.poprzedniOkres(mc, rok);
        rok = okrespoprzedni[1];
        mc  = okrespoprzedni[0];
        klienci = klienciDAO.findAll();
    }
    
    public void init2() { //E.m(this);
        if (wybrany!=null) {
            listapodatnikow = podatnikDAO.findByKsiegowa(wybrany);
            Collections.sort(listapodatnikow, new Podatnikcomparator());
            Msg.msg("Pobrano klientów wg księgowej");
        } else {
            listapodatnikow = podatnikDAO.findAktywny();
            Collections.sort(listapodatnikow, new Podatnikcomparator());
            Msg.msg("Pobrano wszystkich klientów");
        }
    }
    
    public void zachowaj() {
        try {
            uzDAO.editList(listaksiegowych);
            Msg.msg("Zaksiegowano zmiany księgowych");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd, nie naniesiono zmian księgowych");
        }
    }
    
    public void zapisz() {
        try {
            podatnikDAO.editList(listapodatnikow);
            Msg.msg("Zaksiegowano zmiany w ustawieniach podatników");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd, nie naniesiono zmian.");
        }
    }
    
    public void edytujpodatnika(Podatnik podatnik) {
        try {
            podatnikDAO.edit(podatnik);
            Msg.msg("Zmieniono aktywacje podatnika");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd, nie naniesiono zmian.");
        }
    }
    
    public void aktualizuj() {
        if (listaksiegowych!=null&&rok!=null&&mc!=null) {
            Map<String, Uz> loginksiegowa = listaksiegowych.stream().collect(Collectors.toMap(Uz::getLogin, Function.identity()));
            List<Dok> dokumentypkpir = dokDAO.findDokRokMC(rok, mc);
            if (dokumentypkpir!=null) {
                //p->p.getWprowadzil() to jest login z Uz
                Map<Podatnik, String> firmaksiegowa = dokumentypkpir.stream().collect(Collectors.toMap(Dok::getPodatnik, Dok::getWprowadzil,(existing, replacement) -> existing));
                firmaksiegowa.forEach((k, v) -> {
                    System.out.println("Ksiegowa: " + v + ", podatnik: " + k.getPrintnazwa());
                    Uz ksiegowa = loginksiegowa.get(v);
                    if (ksiegowa!=null) {
                        k.setKsiegowa(ksiegowa);
                    }
                });
                Set<Podatnik> keySet = firmaksiegowa.keySet();
                podatnikDAO.editList(new ArrayList(keySet));
                Msg.msg("Zaktualizowano powiązania uproszczona");
                for (Podatnik pod: keySet) {
                    final String nip = pod.getNip();
                    Klienci get = klienci.parallelStream().filter(fa->fa.getNip().equals(nip)).findFirst().get();
                    get.setKsiegowadane(pod.getKsiegowa().getNazwiskoImie());
                    get.setTelefondb(pod.getTelefonkontaktowy());
                }
                klienciDAO.editList(klienci);
                Msg.msg("Naniesiono dane księgowej na klientów urposzczona");
                
            }
            List<Dokfk> dokumentyfk = dokDAOfk.findDokRokMC(rok, mc);
            if (dokumentypkpir!=null) {
                //p->p.getWprowadzil() to jest login z Uz
                Map<Podatnik, String> firmaksiegowa = dokumentyfk.stream().collect(Collectors.toMap(Dokfk::getPodatnikObj, Dokfk::getWprowadzil,(existing, replacement) -> existing));
                firmaksiegowa.forEach((k, v) -> {
                    System.out.println("Ksiegowa: " + v + ", podatnik: " + k.getPrintnazwa());
                    Uz ksiegowa = loginksiegowa.get(v);
                    if (ksiegowa!=null) {
                        k.setKsiegowa(ksiegowa);
                    }
                });
                Set<Podatnik> keySet = firmaksiegowa.keySet();
                podatnikDAO.editList(new ArrayList(keySet));
                Msg.msg("Zaktualizowano powiązania pełna");
                for (Podatnik pod: keySet) {
                    final String nip = pod.getNip();
                    Klienci get = klienci.parallelStream().filter(fa->fa.getNip().equals(nip)).findFirst().get();
                    get.setKsiegowadane(pod.getKsiegowa().getNazwiskoImie());
                    get.setTelefondb(pod.getTelefonkontaktowy());
                }
                klienciDAO.editList(klienci);
                Msg.msg("Naniesiono dane księgowej na klientów pełna");
                wybrany = null;
                init2();
            }
        } else {
            Msg.msg("e","Błąd. Brak księgowych lub rok, mc");
        }
    }
    
    public List<Podatnik> getListapodatnikow() {
        return listapodatnikow;
    }

    public void setListapodatnikow(List<Podatnik> listapodatnikow) {
        this.listapodatnikow = listapodatnikow;
    }

    public List<Uz> getListaksiegowych() {
        return listaksiegowych;
    }

    public void setListaksiegowych(List<Uz> listaksiegowych) {
        this.listaksiegowych = listaksiegowych;
    }

    public Uz getWybrany() {
        return wybrany;
    }

    public void setWybrany(Uz wybrany) {
        this.wybrany = wybrany;
    }

    public List<Uz> getListakadrowych() {
        return listakadrowych;
    }

    public void setListakadrowych(List<Uz> listakadrowych) {
        this.listakadrowych = listakadrowych;
    }

    public List<Uz> getListakadrowychfiltered() {
        return listakadrowychfiltered;
    }

    public void setListakadrowychfiltered(List<Uz> listakadrowychfiltered) {
        this.listakadrowychfiltered = listakadrowychfiltered;
    }

    public List<Podatnik> getListapodatnikowfiltered() {
        return listapodatnikowfiltered;
    }

    public void setListapodatnikowfiltered(List<Podatnik> listapodatnikowfiltered) {
        this.listapodatnikowfiltered = listapodatnikowfiltered;
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
