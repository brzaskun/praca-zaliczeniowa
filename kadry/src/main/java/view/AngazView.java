/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AngazFacade;
import dao.FirmaFacade;
import dao.PracownikFacade;
import dao.SMTPSettingsFacade;
import dao.UprawnieniaFacade;
import dao.UzFacade;
import entity.Angaz;
import entity.FirmaKadry;
import entity.Pracownik;
import entity.SMTPSettings;
import entity.Umowa;
import entity.Uprawnienia;
import entity.Uz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    private FirmaFacade firmaFacade;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private UzFacade uzFacade;
    @Inject
    private UprawnieniaFacade uprawnieniaFacade;
    @Inject
    private SMTPSettingsFacade sMTPSettingsFacade;
    @Inject
    private WpisView wpisView;
    @Inject
    private UmowaView umowaView;
    @Inject
    private KalendarzmiesiacView kalendarzmiesiacView;
    @Inject
    private ZmiennaWynagrodzeniaView zmiennaWynagrodzeniaView;
    @Inject
    private SkladnikWynagrodzeniaView skladnikWynagrodzeniaView;
    @Inject
    private PracownikView pracownikView;
    @Inject
    private EtatView etatView;
    
    @PostConstruct
    public void init() {
        lista  = angazFacade.findAll();
        listafirm = firmaFacade.findAll();
        listapracownikow = pracownikFacade.findAll();
        if (wpisView.getFirma()!=null) {
            lista = angazFacade.findByFirma(wpisView.getFirma());
            listaeast = angazFacade.findByFirma(wpisView.getFirma());
        }
         if (wpisView.getAngaz()!=null) {
            selectedeast = wpisView.getAngaz();
        }
    }
    

    public void create() {
        if (selected != null && wpisView.getFirma() != null) {
            if (selected.getPracownik().getEmail() == null || selected.getPracownik().getEmail().equals("")) {
                Msg.msg("e", "Pracownik nie posiada adresu email. Nie można stworzyć angażu. Email musi być unikalny");
            } else {
                try {
                    selected.setFirma(wpisView.getFirma());
                    angazFacade.create(selected);
                    lista.add(selected);
                    wpisView.setAngaz(selected);
                    Uprawnienia uprawnienia = uprawnieniaFacade.findByNazwa("Pracownik");
                    Uz uzer = new Uz(selected, uprawnienia);
                    selected = new Angaz();
                    Msg.msg("Dodano nowy angaż");
                    uzFacade.create(uzer);
                    Msg.msg("Dodano nowego użytkownika");
                } catch (Exception e) {
                    System.out.println("");
                    Msg.msg("e", "Błąd - nie dodano nowego angażu");
                }
            }
        }
    }
    
   public void aktywuj(Angaz angaz) {
        if (angaz!=null) {
            wpisView.setAngaz(angaz);
            wpisView.setPracownik(angaz.getPracownik());
            List<Umowa> umowy = wpisView.getAngaz().getUmowaList();
            if (umowy!=null && umowy.size()==1) {
                wpisView.setUmowa(umowy.get(0));
            }
            umowaView.init();
            skladnikWynagrodzeniaView.init();
            zmiennaWynagrodzeniaView.init();
            kalendarzmiesiacView.init();
            etatView.init();
            Msg.msg("Aktywowano pracownika");
        }
    }
    
     public void usun(Angaz angaz) {
        if (angaz!=null) {
            if (wpisView.getAngaz()!=null && wpisView.getAngaz().equals(angaz)) {
                wpisView.setAngaz(null);
                wpisView.setUmowa(null);
                wpisView.memorize();
            }
            Pracownik prac = angaz.getPracownik();
            angazFacade.remove(angaz);
            lista.remove(angaz);
            Msg.msg("Usunięto angaż");
            try {
                Uz uzer = uzFacade.findUzByLogin(prac.getEmail());
                uzFacade.remove(uzer);
            } catch (Exception ex){}
        } else {
            Msg.msg("e","Nie wybrano angażu");
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
    
    public void zapiszmail(Pracownik pracownik) {
        if (pracownik!=null) {
            pracownikFacade.edit(pracownik);
            Msg.msg("Zaktualizowano email pracownika");
            Uz uzer = uzFacade.findUzByPesel(pracownik.getPesel());
            uzer.setLogin(pracownik.getEmail());
            uzer.setEmail(pracownik.getEmail());
            uzFacade.edit(uzer);
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

   
    
    
}
