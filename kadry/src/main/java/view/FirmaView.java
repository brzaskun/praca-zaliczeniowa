/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DAOsuperplace.FirmaFacade;
import DAOsuperplace.MiastoFacade;
import DAOsuperplace.UrzadFacade;
import dao.DefinicjalistaplacFacade;
import dao.FirmaKadryFacade;
import dao.KalendarzwzorFacade;
import dao.UprawnieniaFacade;
import dao.UzFacade;
import data.Data;
import embeddable.Mce;
import embeddable.TKodUS;
import entity.Angaz;
import entity.Definicjalistaplac;
import entity.FirmaKadry;
import entity.Kalendarzwzor;
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
import kadryiplace.Firma;
import kadryiplace.Urzad;
import msg.Msg;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;

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
    private FirmaKadryFacade firmaKadryFacade;
    @Inject
    private FirmaFacade firmaFacade;
    @Inject
    private MiastoFacade miastoFacade;
    @Inject
    private UprawnieniaFacade uprawnieniaFacade;
    @Inject
    private UzFacade uzFacade;
    @Inject
    private UrzadFacade urzadFacade;
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
    @Inject
    private TKodUS tKodUS;
    
    @PostConstruct
    private void init() {
        lista  = firmaKadryFacade.findAll();
        if (wpisView.getFirma()!=null) {
            selectedeast = wpisView.getFirma();
        }
    }

    public void create() {
      if (selected!=null) {
          try {
            firmaKadryFacade.create(selected);
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
            listywszystkie("2020");
            listywszystkie("2021");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowej firmy");
          }
      }
    }
    
    public void globalnie(String rok) {
        if (wpisView.getFirma()!=null) {
            FirmaKadry firmaglobalna = firmaKadryFacade.findByNIP("8511005008");
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
    
    public void listywszystkie(String rok) {
        if (rok!=null&&wpisView.getFirma()!=null) {
            FirmaKadry firmaglobalna = firmaKadryFacade.findByNIP("8511005008");
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
            } else {
                Angaz angaz = firma.getAngazList().get(0);
                wpisView.setPracownik(angaz.getPracownik());
                wpisView.setAngaz(angaz);
                List<Umowa> umowy = angaz.getUmowaList();
                if (umowy!=null && umowy.size()==1) {
                    wpisView.setUmowa(umowy.get(0));
                } else if (umowy!=null) {
                    wpisView.setUmowa(umowy.stream().filter(p->p.isAktywna()).findFirst().get());
                }
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
            firmaKadryFacade.remove(firma);
            lista.remove(firma);
            Msg.msg("Usunięto firmę");
        } else {
            Msg.msg("e","Nie usunięto firmy");
        }
    }
    
    public void edytuj(FirmaKadry firma) {
        if (firma!=null && firma.getEmail()!=null) {
            firmaKadryFacade.edit(firma);
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
            String nazwaurzedu = selectedlista.getNazwaurzeduskarbowego();
            selectedlista.setKodurzeduskarbowego(tKodUS.getMapaUrzadKod().get(nazwaurzedu));
            firmaKadryFacade.edit(selectedlista);
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
     
     public void uzuspelnijsuperplace(FirmaKadry nowa) {
         if (nowa!=null) {
            List<Firma> firmysuperplace = firmaFacade.findAll();
            Firma odnaleziona = firmysuperplace.stream().filter(p->ladnynip(p.getFirNip()).equals(nowa.getNip())).findFirst().orElse(null);
                if (odnaleziona!=null) {
                    nowa.setNazwa(odnaleziona.getFirNazwaSkr());
                    String firNazwisko = odnaleziona.getFirNazwisko();
                    if (firNazwisko!=null&&!firNazwisko.equals("")) {
                        nowa.setNazwisko(StringUtils.capitalize(firNazwisko.toLowerCase()));
                    }
                    String firImie = odnaleziona.getFirImie1();
                    if (firImie!=null&&!firImie.equals("")) {
                        nowa.setImie(StringUtils.capitalize(firImie.toLowerCase()));
                        nowa.setReprezentant(nowa.getImie()+" "+nowa.getNazwisko());
                    }
                    nowa.setKraj("Polska");
                    nowa.setWojewodztwo(odnaleziona.getFirWojewodztwo());
                    nowa.setPowiat(odnaleziona.getFirPowiat());
                    nowa.setGmina(odnaleziona.getFirGmina());
                    nowa.setMiasto(odnaleziona.getFirMiaSerial().getMiaNazwa());
                    nowa.setUlica(odnaleziona.getFirUlica());
                    nowa.setDom(odnaleziona.getFirDom());
                    nowa.setLokal(odnaleziona.getFirMieszkanie());
                    nowa.setKod(odnaleziona.getFirKod());
                    nowa.setPoczta(odnaleziona.getFirPoczta());
                    nowa.setDataurodzenia(Data.data_yyyyMMdd(odnaleziona.getFirDataUrodz()));
                    Urzad urzad = urzadFacade.findByUrzSerial(odnaleziona.getFirUrzSerial());
                    if (urzad!=null) {
                        nowa.setKodurzeduskarbowego(String.valueOf(urzad.getUrzVchar1()));
                        nowa.setNazwaurzeduskarbowego(TKodUS.getNazwaUrzedu(nowa.getKodurzeduskarbowego()));
                    }
                    if (odnaleziona.getFirPesel()!=null&&!odnaleziona.getFirPesel().equals("")) {
                        nowa.setOsobafizyczna(true);
                    }
                    nowa.setRegon(odnaleziona.getFirRegon());
                    Msg.msg("Pobrano dane z Super Płac. Zweryfikuj i zachowaj");
                } else {
                    Msg.msg("e","Nie odnaleziono firmy w Super Płace");
                }
         } else {
             Msg.msg("e","Nie wybrano firmy do edycji");
         }
     }

    private String ladnynip(String firNip) {
        String zwrot = firNip;
        if (zwrot!=null) {
            zwrot = firNip.replace("-","");
        }
        return zwrot;
    }
             
    
    public void pobierzinfo() {
        if (selectedlista!=null) {
            Msg.msg("Pobrano firmę do edycji");
        }
    }
    
    public void sprawdzmail() {
        if (selected.getEmail()!=null) {
            Uz uzer = uzFacade.findUzByLogin(selected.getEmail());
            if (uzer!=null) {
                Msg.msg("e","Adres mail musi być unikalny! Jest już taki adres w programie");
                selected.setEmail(null);
                PrimeFaces.current().ajax().update("FirmaCreateForm:email");
            }
        }
    }
    
    public void sprawdznip() {
        if (selected.getNip()!=null) {
            FirmaKadry firma = firmaKadryFacade.findByNIP(selected.getNip());
            if (firma!=null) {
                Msg.msg("e","NIP musi być unikalny! Jest już taki NIP firmy w programie");
                selected.setNip(null);
                PrimeFaces.current().ajax().update("FirmaCreateForm:nip");
            } else {
                uzuspelnijsuperplace(selected);
            }
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
