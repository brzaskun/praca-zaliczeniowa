/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PodatnikDAO;
import dao.PodmiotDAO;
import entity.Podatnik;
import entity.Podmiot;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import webservice.GUS;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PodmiotView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private PodmiotDAO podmiotDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    
    public void opipi() {
        System.out.println("poczatek");
        List<Podmiot> lista = new ArrayList<>();
        List<Podatnik> podatnicy = podatnikDAO.findAllManager();
        if (podatnicy != null) {
            podatnicy.forEach((p -> {
                Podmiot podmiot = new Podmiot(p);
                Map<String, String> dane = pobierzdane(podmiot.getNip());
                if (!dane.isEmpty() && dane.size() > 1) {
                    podmiot.setNazwa(zrobnazwe(dane, p.getNip()));
                    podmiot.setOsobafizyczna(czyfizyczna(dane, podmiot.getPesel(), podmiot.getKrs()));
                    podmiot.setPin("1234");
                    podmiot.setEmail(p.getEmail());
                    podmiot.setTelefon(p.getTelefonkontaktowy());
                    podmiot.setKrajrezydencji("PL");
                    if (podmiot.isOsobafizyczna() == false) {
                        podmiot.setKrs(zrobKRS(dane, p.getImie()));
                        podmiot.setImie(null);
                        podmiot.setNazwisko(null);
                    }
                    lista.add(podmiot);
                    System.out.println("NIP " + podmiot.getNip());
                }
            }));
        }
            System.out.println("koniec");
    }

   
    
            
    private String zrobKRS(Map<String, String> dane, String krs) {
        String zwrot = krs;
        if (!dane.isEmpty()) {
            zwrot = dane.get("praw_numerWrejestrzeEwidencji");
        }
        return zwrot;
    }
            
    
    private String zrobnazwe(Map<String, String> dane, String nip) {
        String nazwa = "brak";
        if (!dane.isEmpty() && nip!=null) {
            nazwa = dane.get("NazwaOryginal");
        }
        return nazwa;
    }

    private boolean czyfizyczna(Map<String, String> dane, String pesel, String krs) {
        boolean jestfizyczna = false;
        if (pesel!=null) {
            jestfizyczna = true;
        }
        if (krs!=null) {
            jestfizyczna = false;
        }
        if (dane.get("Typ").equals("F")) {
            jestfizyczna = true;
        }
        return jestfizyczna;
    }

    private Map<String, String> pobierzdane(String nip) {
        Map<String, String> zwrot = new HashMap<>();
        try {
            GUS poc = new GUS();
            zwrot = poc.pobierz(nip);
        } catch (Exception e) {
        }
        return zwrot;
        }

    
    
}

