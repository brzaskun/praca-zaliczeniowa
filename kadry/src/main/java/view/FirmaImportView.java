/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DAOsuperplace.UrzadFacade;
import comparator.FirmaKadrycomparator;
import dao.DefinicjalistaplacFacade;
import dao.FirmaKadryFacade;
import dao.KalendarzwzorFacade;
import dao.RodzajlistyplacFacade;
import dao.UprawnieniaFacade;
import dao.UzFacade;
import daoplatnik.ZusdraDAO;
import data.Data;
import embeddable.Mce;
import embeddable.TKodUS;
import entity.Angaz;
import entity.Definicjalistaplac;
import entity.FirmaKadry;
import entity.Kalendarzwzor;
import entity.Rodzajlistyplac;
import entity.Umowa;
import entity.UprawnieniaUz;
import entity.Uz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import kadryiplace.Firma;
import kadryiplace.Urzad;
import msg.Msg;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import viewsuperplace.OsobaBean;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class FirmaImportView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private FirmaKadry selected;
    private Firma firmaSPimport;
    private FirmaKadry selectedlista;
    private FirmaKadry selectedeast;
    private List<FirmaKadry> lista;
    @Inject
    private FirmaKadryFacade firmaKadryFacade;
//    @Inject
//    private FirmaFacade firmaFacade;
    @Inject
    private UprawnieniaFacade uprawnieniaFacade;
    @Inject
    private UzFacade uzFacade;
    @Inject
    private UrzadFacade urzadFacade;
    @Inject
    private WpisView wpisView;
    @Inject
    private AngazView angazView;
    @Inject
    private PracodawcaDaneView pracodawcaDaneView;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    @Inject
    private DefinicjalistaplacFacade definicjalistaplacFacade;
    @Inject
    private RodzajlistyplacFacade rodzajlistyplacFacade;
    @Inject
    private TKodUS tKodUS;
    private List<Firma> firmysuperplace;
    
    @PostConstruct
    private void init() {
        lista  = firmaKadryFacade.findAll();
        Collections.sort(lista,new FirmaKadrycomparator());
        if (wpisView.getFirma()!=null) {
            selectedeast = wpisView.getFirma();
        }
        firmysuperplace  = new ArrayList<>();
        //firmysuperplace = firmaFacade.findAll();
        //opipi();
    }
    
    @Inject
    private ZusdraDAO zusdraDAO;
    
    public void opipi() {
        List<entityplatnik.ZUSDRA> miasto = zusdraDAO.findByNip("8511005008");
        for (entityplatnik.ZUSDRA p : miasto) {
            System.out.println(p.toString());
        }
        zusdraDAO.find();
        System.out.println("");
    }

    public void create() {
      if (selected!=null) {
          try {
            selected.setNip(selected.getNip().trim());
            firmaKadryFacade.create(selected);
            lista.add(selected);
            wpisView.setFirma(selected);
            Msg.msg("Dodano nową firmę");
            UprawnieniaUz uprawnienia = uprawnieniaFacade.findByNazwa("Pracodawca");
            Uz uzer = new Uz(selected, uprawnienia);
            selected = new FirmaKadry();
            Msg.msg("Dodano nowyego użytkownika z loginem "+selected.getLoginfirmy());
            uzFacade.create(uzer);
            Msg.msg("Dodano nowego użytkownika");
            String rok = Data.aktualnyRok();
            String rokuprzedni = Data.poprzednirok(rok);
            globalnie(rokuprzedni);
            globalnie(rok);
            listywszystkie(rokuprzedni, selected);
            listywszystkie(rok, selected);
            selected = new FirmaKadry();
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie dodano nowej firmy");
          }
      }
    }
    
    public void globalnie(String rok) {
        if (wpisView.getFirma()!=null) {
            FirmaKadry firmaglobalna = firmaKadryFacade.findByNIP("0000000000");
            for (String mce: Mce.getMceListS()) {
                Kalendarzwzor kal = new Kalendarzwzor();
                kal.setRok(rok);
                kal.setMc(mce);
                kal.setFirma(wpisView.getFirma());
                Kalendarzwzor kalmiesiac = kalendarzwzorFacade.findByFirmaRokMc(wpisView.getFirma(), kal.getRok(), mce);
                if (kalmiesiac==null) {
                    Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(firmaglobalna, kal.getRok(), mce);
                    if (znaleziono!=null) {
                        kal.setNorma(znaleziono.getNorma());
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
    
    public void roblistynowyrok() {
        List<Definicjalistaplac> findByFirmaRok = definicjalistaplacFacade.findByFirmaRok(wpisView.getFirma(), wpisView.getRokWpisu());
        if (findByFirmaRok.isEmpty()) {
            listywszystkie(wpisView.getRokWpisu(), wpisView.getFirma());
        } else {
            Msg.msg("e","Firma ma już wygenerowane listy na rok "+wpisView.getRokWpisu());
        }
    }
    
   private static final Logger LOG = Logger.getLogger(FirmaImportView.class.getName());

    public void generujListyWszystkieFirmy() {
        List<FirmaKadry> listafirm = firmaKadryFacade.findAktywne();
        String rokWpisu = wpisView.getRokWpisu();

        if (rokWpisu != null && rokWpisu.equals("2025") && listafirm != null) {
            for (FirmaKadry firme : listafirm) {
                try {
                    listywszystkie(rokWpisu.toString(), firme);
                    LOG.log(Level.INFO, "Ukoczończono tworzenie nowych firm na nowy rok dla " + firme.getNazwa());
                } catch (Exception e) {
                    // Logujemy błąd wraz z NIP-em firmy, aby móc później przeanalizować problem
                    LOG.log(Level.SEVERE, "Błąd przy generowaniu list dla firmy o NIP: " + firme.getNazwa() + " w roku " + rokWpisu, e);
                    Msg.msg("e","Błąd przy generowaniu list dla firmy o NIP: " + firme.getNazwa() + " w roku " + rokWpisu);
                }
            }
            Msg.msg("Ukoczończono tworzenie nowych firm na nowy rok");
            LOG.log(Level.INFO, "Ukoczończono tworzenie nowych firm na nowy rok");
        } else {
            if (rokWpisu == null) {
                LOG.warning("Nie podano roku, nie można wygenerować list.");
            }
            if (listafirm == null) {
                LOG.warning("Lista firm jest pusta lub null, nie można wygenerować list.");
            }
        }
    }

    
    public void listywszystkie(String rok, FirmaKadry firma) {
        if (rok != null && firma != null) {
            Rodzajlistyplac umowaoprace = rodzajlistyplacFacade.findUmowaoPrace();
            Rodzajlistyplac umowazlecenia = rodzajlistyplacFacade.findUmowaZlecenia();
            Rodzajlistyplac udzialworganach = rodzajlistyplacFacade.findByTyt_serial(15);
            Rodzajlistyplac zasilki = rodzajlistyplacFacade.findByTyt_serial(1006);
            Rodzajlistyplac zagraniczneryczaly = rodzajlistyplacFacade.findByTyt_serial(1032);
            Rodzajlistyplac umowaodzielo = rodzajlistyplacFacade.findByTyt_serial(10);
            Rodzajlistyplac swiadczeniarzeczowe = rodzajlistyplacFacade.findByTyt_serial(8);

            // Listy dla umowy o pracę
            List<Definicjalistaplac> listyUmowaOPrace = new ArrayList<>();
            for (String mc : Mce.getMceListS()) {
                String datawyplaty = OsobaBean.zrobdatawyplaty(mc, rok, firma);
                Definicjalistaplac definicjalistaplac = OsobaBean.nowalista(rok, mc, umowaoprace, firma, datawyplaty);
                listyUmowaOPrace.add(definicjalistaplac);
            }
            definicjalistaplacFacade.createList(listyUmowaOPrace);

            // Listy dla umowy zlecenia
            List<Definicjalistaplac> listyUmowaZlecenia = new ArrayList<>();
            for (String mc : Mce.getMceListS()) {
                String datawyplaty = OsobaBean.zrobdatawyplaty(mc, rok, firma);
                Definicjalistaplac definicjalistaplac = OsobaBean.nowalista(rok, mc, umowazlecenia, firma, datawyplaty);
                listyUmowaZlecenia.add(definicjalistaplac);
            }
            definicjalistaplacFacade.createList(listyUmowaZlecenia);

            // Listy dla udziałów w organach
            List<Definicjalistaplac> listyUdzialwOrganach = new ArrayList<>();
            for (String mc : Mce.getMceListS()) {
                String datawyplaty = OsobaBean.zrobdatawyplaty(mc, rok, firma);
                Definicjalistaplac definicjalistaplac = OsobaBean.nowalista(rok, mc, udzialworganach, firma, datawyplaty);
                listyUdzialwOrganach.add(definicjalistaplac);
            }
            definicjalistaplacFacade.createList(listyUdzialwOrganach);

            // Listy dla zasiłków
            List<Definicjalistaplac> listyZasilki = new ArrayList<>();
            for (String mc : Mce.getMceListS()) {
                String datawyplaty = OsobaBean.zrobdatawyplaty(mc, rok, firma);
                Definicjalistaplac definicjalistaplac = OsobaBean.nowalista(rok, mc, zasilki, firma, datawyplaty);
                listyZasilki.add(definicjalistaplac);
            }
            definicjalistaplacFacade.createList(listyZasilki);

            // Listy dla zagranicznych ryczałtów
            List<Definicjalistaplac> listyZagraniczneRyczalty = new ArrayList<>();
            for (String mc : Mce.getMceListS()) {
                String datawyplaty = OsobaBean.zrobdatawyplaty(mc, rok, firma);
                Definicjalistaplac definicjalistaplac = OsobaBean.nowalista(rok, mc, zagraniczneryczaly, firma, datawyplaty);
                listyZagraniczneRyczalty.add(definicjalistaplac);
            }
            definicjalistaplacFacade.createList(listyZagraniczneRyczalty);

            // Listy dla umowy o dzieło
            List<Definicjalistaplac> listyUmowaODzielo = new ArrayList<>();
            for (String mc : Mce.getMceListS()) {
                String datawyplaty = OsobaBean.zrobdatawyplaty(mc, rok, firma);
                Definicjalistaplac definicjalistaplac = OsobaBean.nowalista(rok, mc, umowaodzielo, firma, datawyplaty);
                listyUmowaODzielo.add(definicjalistaplac);
            }
            definicjalistaplacFacade.createList(listyUmowaODzielo);

            // Listy dla świadczeń rzeczowych
            List<Definicjalistaplac> listySwiadczeniaRzeczowe = new ArrayList<>();
            for (String mc : Mce.getMceListS()) {
                String datawyplaty = OsobaBean.zrobdatawyplaty(mc, rok, firma);
                Definicjalistaplac definicjalistaplac = OsobaBean.nowalista(rok, mc, swiadczeniarzeczowe, firma, datawyplaty);
                listySwiadczeniaRzeczowe.add(definicjalistaplac);
            }
            definicjalistaplacFacade.createList(listySwiadczeniaRzeczowe);

            Msg.msg("Wygenerowano definicje list za rok " + rok);
        } else {
            Msg.msg("e", "Nie wybrano firmy");
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
                    try {
                        wpisView.setUmowa(umowy.stream().filter(p->p.isAktywna()).findFirst().get());
                    } catch (Exception e){}
                }
            }
            angazView.init();
            //pracodawcaDaneView.init();
            //pasekwynagrodzenView.init();
            Msg.msg("Aktywowano firmę "+firma.getNazwa());
        }
    }
    
    public void aktywujPracAngaze(FirmaKadry firma) {
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
                    try {
                        wpisView.setUmowa(umowy.stream().filter(p->p.isAktywna()).findFirst().get());
                    } catch (Exception e){}
                }
            }
            angazView.init();
            pracodawcaDaneView.init();
            //pasekwynagrodzenView.init();
            Msg.msg("Aktywowano firmę "+firma.getNazwa());
        }
    }
    
    
    
    public void usun(FirmaKadry firma) {
        if (firma!=null) {
                wpisView.setFirma(null);
                wpisView.setAngaz(null);
                wpisView.setUmowa(null);
                wpisView.usunMemory();
            try {
                firmaKadryFacade.remove(firma);
                lista.remove(firma);
                Msg.msg("Usunięto firmę. Wybierz nową firmę");
            } catch (Exception e) {
                Msg.msg("e","Wystąpił błąd. Nie usunięto firmy");
            }
        } else {
            Msg.msg("e","Nie wybrano firmy");
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
                UprawnieniaUz uprawnienia = uprawnieniaFacade.findByNazwa("Pracodawca");
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
            selectedlista.setNip(selectedlista.getNip().trim());
            firmaKadryFacade.edit(selectedlista);
            Uz uz = uzFacade.findUzByPesel(selectedlista.getNip());
            if (uz!=null) {
                uz.setEmail(selectedlista.getEmail());
                uz.setNrtelefonu(selectedlista.getTelefon());
                uzFacade.edit(uz);
            } else {
                try {
                    UprawnieniaUz uprawnienia = uprawnieniaFacade.findByNazwa("Pracodawca");
                    Uz uzer = new Uz(selectedlista, uprawnienia);
                    uzFacade.create(uzer);
                } catch (Exception e){}
            }
            Msg.msg("Edytowano firmę");
        } else {
            Msg.msg("e","Nie wybrano firmy");
        }
    }
     
     public void uzuspelnijsuperplace(Firma odnaleziona) {
        if (odnaleziona!=null) {
            selected.setNazwa(odnaleziona.getFirNazwaSkr());
            String firNazwisko = odnaleziona.getFirNazwisko();
            if (firNazwisko!=null&&!firNazwisko.equals("")) {
                selected.setNazwisko(StringUtils.capitalize(firNazwisko.toLowerCase()));
            }
            String firImie = odnaleziona.getFirImie1();
            if (firImie!=null&&!firImie.equals("")) {
                selected.setImie(StringUtils.capitalize(firImie.toLowerCase()));
                selected.setReprezentant(selected.getImie()+" "+selected.getNazwisko());
            }
            selected.setKraj("Polska");
            selected.setNip(ladnynip(odnaleziona.getFirNip()));
            selected.setWojewodztwo(odnaleziona.getFirWojewodztwo());
            selected.setPowiat(odnaleziona.getFirPowiat());
            selected.setGmina(odnaleziona.getFirGmina());
            selected.setMiasto(odnaleziona.getFirMiaSerial().getMiaNazwa());
            selected.setUlica(odnaleziona.getFirUlica());
            selected.setDom(odnaleziona.getFirDom());
            selected.setLokal(odnaleziona.getFirMieszkanie());
            if (selected.getLokal()==null) {
                selected.setLokal("-");
            }
            selected.setKod(odnaleziona.getFirKod());
            selected.setPoczta(odnaleziona.getFirPoczta());
            selected.setDataurodzenia(Data.data_yyyyMMdd(odnaleziona.getFirDataUrodz()));
            Integer firUrzSerial = odnaleziona.getFirUrzSerial();
            if (firUrzSerial!=null) {
                Urzad urzad = urzadFacade.findByUrzSerial(firUrzSerial);
                if (urzad!=null) {
                    selected.setKodurzeduskarbowego(String.valueOf(urzad.getUrzVchar1()));
                    selected.setNazwaurzeduskarbowego(TKodUS.getNazwaUrzedu(selected.getKodurzeduskarbowego()));
                } 
            } else {
                Msg.msg("e","Brak urzedu skarbowego dla firmy w Superłacach. Uzupełnij");
            }
            if (odnaleziona.getFirPesel()!=null&&!odnaleziona.getFirPesel().equals("")) {
                selected.setOsobafizyczna(true);
            }
            selected.setRegon(odnaleziona.getFirRegon());
            selected.setFir_serial(String.valueOf(odnaleziona.getFirSerial()));
            Msg.msg("Pobrano dane z Super Płac. Zweryfikuj i zachowaj");
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
    public List<Firma> complete(String query) {
        List<Firma> results = new ArrayList<>();
        try {
            String q = query.substring(0, 1);
            int i = Integer.parseInt(q);
            for (Firma p : firmysuperplace) {
                if (ladnynip(p.getFirNip()).startsWith(query)) {
                    results.add(p);
                }
            }
        } catch (NumberFormatException e) {
            for (Firma p : firmysuperplace) {
                if (p.getFirNazwaPel().toLowerCase().contains(query.toLowerCase())) {
                    results.add(p);
                }
            }
        }
        return results;
    }
    public void sprawdzmail() {
        if (selected.getLoginfirmy()!=null) {
            Uz uzer = uzFacade.findUzByLogin(selected.getLoginfirmy());
            if (uzer!=null) {
                Msg.msg("e","Login dla firmy musi być unikalny! Jest już taki login w programie");
                selected.setLoginfirmy(null);
                PrimeFaces.current().ajax().update("FirmaCreateForm:login");
            }
        }
    }
    
    
    public void sprawdznip() {
        if (firmaSPimport.getFirNip()!=null) {
            String nip = ladnynip(firmaSPimport.getFirNip());
            FirmaKadry firma = firmaKadryFacade.findByNIP(nip);
            if (firma!=null) {
                Msg.msg("e","NIP musi być unikalny! Jest już taki NIP firmy w programie");
                selected.setNip(null);
                PrimeFaces.current().ajax().update("FirmaCreateForm:nip");
            } else {
                uzuspelnijsuperplace(firmaSPimport);
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

    public Firma getFirmaSPimport() {
        return firmaSPimport;
    }

    public void setFirmaSPimport(Firma firmaSPimport) {
        this.firmaSPimport = firmaSPimport;
    }
    
    
    
    
}
