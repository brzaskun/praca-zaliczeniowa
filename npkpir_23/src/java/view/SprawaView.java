/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Podatnikcomparator;
import dao.PodatnikDAO;
import dao.SprawaDAO;
import dao.UzDAO;
import entity.Podatnik;
import entity.Sprawa;
import entity.Uz;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
public class SprawaView  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private SprawaDAO sprawaDAO;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private Sprawa nowa;
    private List<Sprawa> sprawyOdebrane;
    private List<Sprawa> sprawyNadane;
    private List<Uz> odbiorcy;
    private List<Podatnik> klienci;
    private static final List<String> status;
    private int noweoedbrane;
    
    static {
        status = Collections.synchronizedList(new ArrayList<>());
        status.add("przeczytana");
        status.add("załatwiana");
        status.add("gotowa");
        status.add("musi czekać");
        status.add("czekamy na klienta");
    }
    
    
    @PostConstruct
    private void init() { //E.m(this);
        List<Sprawa> wszystkiesprawy = sprawaDAO.findAll();
        sprawyOdebrane = wszystkiesprawy.stream().filter(p->p.getOdbiorca().getNazwiskoImie().equals(wpisView.getUzer().getNazwiskoImie())).collect(Collectors.toList());
        for (Iterator<Sprawa> it = sprawyOdebrane.iterator();it.hasNext();) {
            Sprawa p = it.next();
            if (p.getStatus().equals("gotowa")) {
                it.remove();
            }
        }
        sprawyNadane = wszystkiesprawy.stream().filter(p->p.getNadawca().getNazwiskoImie().equals(wpisView.getUzer().getNazwiskoImie())).collect(Collectors.toList());
        for (Iterator<Sprawa> it = sprawyNadane.iterator();it.hasNext();) {
            Sprawa p = it.next();
            if (p.isUsunieta()) {
                it.remove();
            }
        }
        List<Sprawa> noweodebranelista = sprawyOdebrane.stream().filter(p->p.getStatus().equals("wysłana")).collect(Collectors.toList());
        noweoedbrane = noweodebranelista.size();
        List<Uz> odbiorcyPKPiR = uzDAO.findByUprawnienia("Bookkeeper");
        List<Uz> odbiorcyFK = uzDAO.findByUprawnienia("BookkeeperFK");
        odbiorcy = new ArrayList<>();
        if (odbiorcyPKPiR.isEmpty()==false){
            odbiorcy.addAll(odbiorcyPKPiR);
        }
        if (odbiorcyFK.isEmpty()==false){
            odbiorcy.addAll(odbiorcyFK);
        }
        Set<String> nazwiskoiimie = new HashSet<>();
        for(Iterator<Uz> it = odbiorcy.iterator(); it.hasNext();) {
            Uz s = it.next();
            if (s.getLoginglowny() != null) {
                it.remove();
            } else if (s.getUprawnienia().equals("Guest")||s.getUprawnienia().equals("GuestFK")||s.getUprawnienia().equals("GuestFKBook")||s.getUprawnienia().equals("GuestFaktura")||s.getUprawnienia().equals("Multiuser")||s.getUprawnienia().equals("MultiuserBook")||s.getUprawnienia().equals("Dedra")) {
                it.remove();
            } else if (nazwiskoiimie.contains(s.getNazwiskoImie())){
                it.remove();
            } else if (s.getNazwiskoImie().equals("Testowy Jan")){
                it.remove();
            }else {
                nazwiskoiimie.add(s.getNazwiskoImie());
            }
            
        }
        odbiorcy = odbiorcy.stream().sorted(Comparator.comparing(Uz::getNazwiskoImie)).collect(Collectors.toList());
        klienci = podatnikDAO.findAktywny();
        Collections.sort(klienci, new Podatnikcomparator());
    }
    
    public void dodaj() {
        try {
            Uz login = wpisView.getUzer().getLoginglowny() != null ? wpisView.getUzer().getLoginglowny() : wpisView.getUzer();
            nowa.setDatasporzadzenia(new Date());
            nowa.setNadawca(login);
            nowa.setStatus("wysłana");
            sprawaDAO.create(nowa);
            nowa = new Sprawa();
            Msg.msg("Dodano sprawę");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("Nie udało się dodać sprawy");
        }
    }
    
    public void nanies(Sprawa sprawa) {
        sprawa.setDatastatusu(new Date());
        sprawaDAO.edit(sprawa);
        Msg.msg("Odnotowano zmianę statusu");
    }
    
    public void niepokazuj(Sprawa sprawa) {
        System.out.println("ukrywamaaaa2222");
        sprawaDAO.edit(sprawa);
        Msg.msg("Oznaczono sprawę");
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Sprawa> getSprawyOdebrane() {
        return sprawyOdebrane;
    }

    public void setSprawyOdebrane(List<Sprawa> sprawyOdebrane) {
        this.sprawyOdebrane = sprawyOdebrane;
    }

    public List<Sprawa> getSprawyNadane() {
        return sprawyNadane;
    }

    public void setSprawyNadane(List<Sprawa> sprawyNadane) {
        this.sprawyNadane = sprawyNadane;
    }

    public Sprawa getNowa() {
        return nowa;
    }

    public void setNowa(Sprawa nowa) {
        this.nowa = nowa;
    }

    public List<Uz> getOdbiorcy() {
        return odbiorcy;
    }

    public void setOdbiorcy(List<Uz> odbiorcy) {
        this.odbiorcy = odbiorcy;
    }

    public List<Podatnik> getKlienci() {
        return klienci;
    }

    public void setKlienci(List<Podatnik> klienci) {
        this.klienci = klienci;
    }

    public List<String> getStatus() {
        return status;
    }

    public int getNoweoedbrane() {
        return noweoedbrane;
    }

    public void setNoweoedbrane(int noweoedbrane) {
        this.noweoedbrane = noweoedbrane;
    }
    
    
    
}
