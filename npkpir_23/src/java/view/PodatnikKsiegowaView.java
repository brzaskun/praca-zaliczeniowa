/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansFaktura.FakturaOkresowaGenNum;
import comparator.Podatnikcomparator;
import comparator.Uzcomparator;
import dao.FakturyokresoweDAO;
import dao.FakturywystokresoweDAO;
import dao.PodatnikDAO;
import dao.UzDAO;
import entity.Fakturyokresowe;
import entity.Fakturywystokresowe;
import entity.Podatnik;
import entity.Uz;
import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PodatnikKsiegowaView implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Podatnik> listapodatnikow;
    private List<Uz> listaksiegowych;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private FakturywystokresoweDAO fakturywystokresoweDAO;
    private String rok;
    private boolean bezzerowych;
    private boolean edycja;
    

    public void init() {
        listapodatnikow = podatnikDAO.findAll();
        Collections.sort(listapodatnikow, new Podatnikcomparator());
        listaksiegowych = uzDAO.findByUprawnienia("Bookkeeper");
        listaksiegowych.addAll(uzDAO.findByUprawnienia("BookkeeperFK"));
        Collections.sort(listaksiegowych, new Uzcomparator());
        List<Fakturywystokresowe> okresowe = fakturywystokresoweDAO.findPodatnikBiezace("GRZELCZYK", rok);
        for (Podatnik p : listapodatnikow) {
            List<Fakturywystokresowe> fakt = okresowe.stream().filter(r->r.getNipodbiorcy().equals(p.getNip())).collect(Collectors.toList());
            double suma = 0.0;
            if (!fakt.isEmpty()) {
                for (Fakturywystokresowe s: fakt) {
                    suma += s.getNetto();
                }
            }
            p.setCena(Z.z(suma));
        }
        if (bezzerowych) {
            for (Iterator<Podatnik> it=listapodatnikow.iterator(); it.hasNext();) {
                Podatnik p = it.next();
               if (p.getCena() == 0) {
                   it.remove();
               }
            }
        }
        for (Uz r: listaksiegowych) {
            double suma = 0.0;
            int liczba = 0;
            for (Iterator<Podatnik> it=listapodatnikow.iterator(); it.hasNext();) {
                Podatnik p = it.next();
                if (p.getKsiegowa()!=null && p.getKsiegowa().equals(r)) {
                    suma += p.getCena();
                    liczba++;
                }
            }
            r.setSumafaktur(suma);
            r.setLiczbapodatnikow(liczba);
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

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public boolean isBezzerowych() {
        return bezzerowych;
    }

    public void setBezzerowych(boolean bezzerowych) {
        this.bezzerowych = bezzerowych;
    }

    public boolean isEdycja() {
        return edycja;
    }

    public void setEdycja(boolean edycja) {
        this.edycja = edycja;
    }
    
    
}
