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
import viewsuperplace.OsobaBean;

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
    private Firma firmaSPimport;
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
    private RodzajlistyplacFacade rodzajlistyplacFacade;
    @Inject
    private TKodUS tKodUS;
    private List<Firma> firmysuperplace;
    
    @PostConstruct
    private void init() {
        lista  = firmaKadryFacade.findAll();
        if (wpisView.getFirma()!=null) {
            selectedeast = wpisView.getFirma();
        }
        firmysuperplace = firmaFacade.findAll();
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
            firmaKadryFacade.create(selected);
            lista.add(selected);
            wpisView.setFirma(selected);
            Msg.msg("Dodano nową firmę");
            UprawnieniaUz uprawnienia = uprawnieniaFacade.findByNazwa("Pracodawca");
            Uz uzer = new Uz(selected, uprawnienia);
            selected = new FirmaKadry();
            Msg.msg("Dodano nowy angaż");
            uzFacade.create(uzer);
            Msg.msg("Dodano nowego użytkownika");
            globalnie("2020");
            globalnie("2021");
            globalnie("2022");
            listywszystkie("2020");
            listywszystkie("2021");
            listywszystkie("2022");
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
            Rodzajlistyplac umowaoprace =  rodzajlistyplacFacade.findUmowaoPrace();
            Rodzajlistyplac umowazlecenia =  rodzajlistyplacFacade.findUmowaZlecenia();
            Rodzajlistyplac udzialworganach =  rodzajlistyplacFacade.findByTyt_serial(15);
            Rodzajlistyplac zasilki =  rodzajlistyplacFacade.findByTyt_serial(1006);
            Rodzajlistyplac zagraniczneryczaly =  rodzajlistyplacFacade.findByTyt_serial(1032);
            Rodzajlistyplac umowaodzielo =  rodzajlistyplacFacade.findByTyt_serial(10);
            List<Definicjalistaplac> listy = new ArrayList<>();
            for (String mc : Mce.getMceListS()) {
                 String datawyplaty = OsobaBean.zrobdatawyplaty(mc, rok, wpisView.getFirma());
                 Definicjalistaplac definicjalistaplac = OsobaBean.nowalista(rok, mc, umowaoprace, wpisView.getFirma(), datawyplaty);
                 listy.add(definicjalistaplac);
            }
            definicjalistaplacFacade.createList(listy);
            listy = new ArrayList<>();
            for (String mc : Mce.getMceListS()) {
                String datawyplaty = OsobaBean.zrobdatawyplaty(mc, rok, wpisView.getFirma());
                 Definicjalistaplac definicjalistaplac = OsobaBean.nowalista(rok, mc, umowazlecenia, wpisView.getFirma(), datawyplaty);
                 listy.add(definicjalistaplac);
            }
            definicjalistaplacFacade.createList(listy);
            listy = new ArrayList<>();
            for (String mc : Mce.getMceListS()) {
                String datawyplaty = OsobaBean.zrobdatawyplaty(mc, rok, wpisView.getFirma());
                 Definicjalistaplac definicjalistaplac = OsobaBean.nowalista(rok, mc, udzialworganach, wpisView.getFirma(), datawyplaty);
                 listy.add(definicjalistaplac);
            }
            definicjalistaplacFacade.createList(listy);
            listy = new ArrayList<>();
            for (String mc : Mce.getMceListS()) {
                String datawyplaty = OsobaBean.zrobdatawyplaty(mc, rok, wpisView.getFirma());
                 Definicjalistaplac definicjalistaplac = OsobaBean.nowalista(rok, mc, zasilki, wpisView.getFirma(), datawyplaty);
                 listy.add(definicjalistaplac);
            }
            definicjalistaplacFacade.createList(listy);
            listy = new ArrayList<>();
            for (String mc : Mce.getMceListS()) {
                String datawyplaty = OsobaBean.zrobdatawyplaty(mc, rok, wpisView.getFirma());
                 Definicjalistaplac definicjalistaplac = OsobaBean.nowalista(rok, mc, zagraniczneryczaly, wpisView.getFirma(), datawyplaty);
                 listy.add(definicjalistaplac);
            }
            definicjalistaplacFacade.createList(listy);
            listy = new ArrayList<>();
            for (String mc : Mce.getMceListS()) {
                String datawyplaty = OsobaBean.zrobdatawyplaty(mc, rok, wpisView.getFirma());
                 Definicjalistaplac definicjalistaplac = OsobaBean.nowalista(rok, mc, umowaodzielo, wpisView.getFirma(), datawyplaty);
                 listy.add(definicjalistaplac);
            }
            definicjalistaplacFacade.createList(listy);
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
                wpisView.setAngaz(null);
                wpisView.setUmowa(null);
            }
            try {
                firmaKadryFacade.remove(firma);
                lista.remove(firma);
                Msg.msg("Usunięto firmę");
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
            firmaKadryFacade.edit(selectedlista);
            Uz uz = uzFacade.findUzByPesel(selectedlista.getNip());
            if (uz!=null) {
                uz.setEmail(selectedlista.getEmail());
                uz.setNrtelefonu(selectedlista.getTelefon());
                uzFacade.edit(uz);
            } else {
                UprawnieniaUz uprawnienia = uprawnieniaFacade.findByNazwa("Pracodawca");
                Uz uzer = new Uz(selectedlista, uprawnienia);
                uzFacade.create(uzer);
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
            Urzad urzad = urzadFacade.findByUrzSerial(odnaleziona.getFirUrzSerial());
            if (urzad!=null) {
                selected.setKodurzeduskarbowego(String.valueOf(urzad.getUrzVchar1()));
                selected.setNazwaurzeduskarbowego(TKodUS.getNazwaUrzedu(selected.getKodurzeduskarbowego()));
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
