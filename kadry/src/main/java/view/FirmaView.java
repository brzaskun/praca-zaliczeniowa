/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DefinicjalistaplacFacade;
import dao.FirmaFacade;
import dao.KalendarzwzorFacade;
import dao.UprawnieniaFacade;
import dao.UzFacade;
import embeddable.Mce;
import entity.Definicjalistaplac;
import entity.FirmaKadry;
import entity.Kalendarzwzor;
import entity.Uprawnienia;
import entity.Uz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class FirmaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private FirmaKadry selected;
    private FirmaKadry selectedlista;
    private FirmaKadry selectedeast;
    private List<FirmaKadry> lista;
    @Inject
    private FirmaFacade firmaFacade;
    @Inject
    private UprawnieniaFacade uprawnieniaFacade;
    @Inject
    private UzFacade uzFacade;
    @Inject
    private WpisView wpisView;
    @Inject
    private PracownikView pracownikView;
    @Inject
    private AngazView angazView;
    @Inject
    private PracodawcaDaneView pracodawcaDaneView;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    @Inject
    private DefinicjalistaplacFacade definicjalistaplacFacade;
    
    @PostConstruct
    private void init() {
        lista  = firmaFacade.findAll();
        if (wpisView.getFirma()!=null) {
            selectedeast = wpisView.getFirma();
        }
    }

    public void create() {
      if (selected!=null) {
          try {
            firmaFacade.create(selected);
            lista.add(selected);
            wpisView.setFirma(selected);
            Msg.msg("Dodano nową firmę");
            Uprawnienia uprawnienia = uprawnieniaFacade.findByNazwa("Pracodawca");
            Uz uzer = new Uz(selected, uprawnienia);
            selected = new FirmaKadry();
            Msg.msg("Dodano nowy angaż");
            uzFacade.create(uzer);
            Msg.msg("Dodano nowego użytkownika");
            globalnie("2020");
            globalnie("2021");
            listyplac("2020");
            listyplac("2021");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowej firmy");
          }
      }
    }
    
    public void globalnie(String rok) {
        if (wpisView.getFirma()!=null) {
            FirmaKadry firmaglobalna = firmaFacade.findByNIP("8511005008");
            for (String mce: Mce.getMceListS()) {
                Kalendarzwzor kal = new Kalendarzwzor();
                kal.setRok(rok);
                kal.setMc(mce);
                kal.setFirma(wpisView.getFirma());
                Kalendarzwzor kalmiesiac = kalendarzwzorFacade.findByFirmaRokMc(wpisView.getFirma(), kal.getRok(), mce);
                if (kalmiesiac==null) {
                    Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(firmaglobalna, kal.getRok(), mce);
                    if (znaleziono!=null) {
                        kal.generujdnizglobalnego(znaleziono);
                        kalendarzwzorFacade.create(kal);
                    } else {
                        Msg.msg("e","Brak kalendarza globalnego za "+mce);
                    }
                }
            }
            Msg.msg("Pobrano dane z kalendarza globalnego z bazy danych i utworzono kalendarz wzorcowy firmy za rok "+rok);
        } else {
            Msg.msg("e","Nie wybrano firmy");
        }
    }
    
    public void listyplac(String rok) {
        if (rok!=null&&wpisView.getFirma()!=null) {
            FirmaKadry firmaglobalna = firmaFacade.findByNIP("8511005008");
            List<Definicjalistaplac> lista = definicjalistaplacFacade.findByFirmaRok(firmaglobalna, rok);
            List<Definicjalistaplac> zwrot = new ArrayList<>();
            for (Definicjalistaplac p : lista) {
                zwrot.add(new Definicjalistaplac(p, wpisView.getFirma()));
            }
            definicjalistaplacFacade.createList(zwrot);
            Msg.msg("Wygenerowano definicje list za rok "+rok);
        } else {
            Msg.msg("e","Nie wybrano firmy");
        }
    }
    
    public void aktywuj(FirmaKadry firma) {
        if (firma!=null) {
            wpisView.setFirma(firma);
            if (firma.getAngazList()==null||firma.getAngazList().isEmpty()) {
                wpisView.setPracownik(null);
                wpisView.setAngaz(null);
                wpisView.setUmowa(null);
            }
            angazView.init();
            pracodawcaDaneView.init();
            Msg.msg("Aktywowano firmę "+firma.getNazwa());
        }
    }
    
    public void usun(FirmaKadry firma) {
        if (firma!=null) {
            if (wpisView.getFirma()!=null && wpisView.getFirma().equals(firma)) {
                wpisView.setFirma(null);
            }
            firmaFacade.remove(firma);
            lista.remove(firma);
            Msg.msg("Usunięto firmę");
        } else {
            Msg.msg("e","Nie usunięto firmy");
        }
    }
    
    public void edytuj(FirmaKadry firma) {
        if (firma!=null && firma.getEmail()!=null) {
            firmaFacade.edit(firma);
            Uz uz = uzFacade.findUzByPesel(firma.getNip());
            if (uz!=null) {
                uz.setEmail(firma.getEmail());
                uz.setNrtelefonu(firma.getTelefon());
                uzFacade.edit(uz);
            } else {
                Uprawnienia uprawnienia = uprawnieniaFacade.findByNazwa("Pracodawca");
                Uz uzer = new Uz(firma, uprawnienia);
                uzFacade.create(uzer);
            }
            Msg.msg("Edytowano firmę");
        } else {
            Msg.msg("e","Nie wybrano firmy");
        }
    }
    
     public void edytuj() {
        if (selectedlista!=null && selectedlista.getEmail()!=null) {
            firmaFacade.edit(selectedlista);
            Uz uz = uzFacade.findUzByPesel(selectedlista.getNip());
            if (uz!=null) {
                uz.setEmail(selectedlista.getEmail());
                uz.setNrtelefonu(selectedlista.getTelefon());
                uzFacade.edit(uz);
            } else {
                Uprawnienia uprawnienia = uprawnieniaFacade.findByNazwa("Pracodawca");
                Uz uzer = new Uz(selectedlista, uprawnienia);
                uzFacade.create(uzer);
            }
            Msg.msg("Edytowano firmę");
        } else {
            Msg.msg("e","Nie wybrano firmy");
        }
    }
    
    public void pobierzinfo() {
        if (selectedlista!=null) {
            Msg.msg("Pobrano firmę do edycji");
        }
    }
      
    public FirmaKadry getSelected() {
        return selected;
    }

    public void setSelected(FirmaKadry selected) {
        this.selected = selected;
    }

    public List<FirmaKadry> getLista() {
        return lista;
    }

    public void setLista(List<FirmaKadry> lista) {
        this.lista = lista;
    }

    public FirmaKadry getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(FirmaKadry selectedlista) {
        this.selectedlista = selectedlista;
    }

    public FirmaKadry getSelectedeast() {
        return selectedeast;
    }

    public void setSelectedeast(FirmaKadry selectedeast) {
        this.selectedeast = selectedeast;
    }
    
    
    
}
