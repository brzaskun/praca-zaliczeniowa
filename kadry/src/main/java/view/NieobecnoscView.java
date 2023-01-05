/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.NieobecnosciBean;
import comparator.Pracownikcomparator;
import comparator.Rodzajnieobecnoscicomparator;
import dao.AngazFacade;
import dao.DzienFacade;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.NieobecnoscFacade;
import dao.PracownikFacade;
import dao.RodzajnieobecnosciFacade;
import dao.SwiadczeniekodzusFacade;
import dao.UmowaFacade;
import data.Data;
import entity.Angaz;
import entity.Dzien;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Nieobecnosc;
import entity.Pracownik;
import entity.Rodzajnieobecnosci;
import entity.Swiadczeniekodzus;
import entity.Umowa;
import generated.RaportEzla;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;
import msg.Msg;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import org.primefaces.model.DualListModel;
import zuszla.PobierzRaporty;
import zuszla.PobierzRaportyResponse;
import zuszla.WsdlPlatnikRaportyZlaPortType;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class NieobecnoscView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Nieobecnosc selected;
    @Inject
    private Nieobecnosc selectedlista;
    private List<Nieobecnosc> lista;
    private List<Rodzajnieobecnosci> listaabsencji;
    private List<Umowa> listaumowa;
    private List<Swiadczeniekodzus> swiadczeniekodzusLista;
    @Inject
    private NieobecnoscFacade nieobecnoscFacade;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private SwiadczeniekodzusFacade swiadczeniekodzusFacade;
    @Inject
    private RodzajnieobecnosciFacade rodzajnieobecnosciFacade;
    @Inject
    private SwiadczeniekodzusFacade nieobecnosckodzusFacade;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private DzienFacade dzienFacade;
    @Inject
    private KalendarzmiesiacView kalendarzmiesiacView;
    @Inject
    private WpisView wpisView;
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/zuszla.wsdl")
    private zuszla.WsdlPlatnikRaportyZla wsdlPlatnikRaportyZla;
    private  boolean pokazcalyrok;
    private org.primefaces.model.DualListModel<Pracownik> listapracownikow;
    private boolean delegacja;
    private int dniwykorzystanewroku;
    

    
    @PostConstruct
    public void init0() {
        listapracownikow = new org.primefaces.model.DualListModel<>();
        listapracownikow.setSource(new ArrayList<>());
        listapracownikow.setTarget(new ArrayList<>());
        delegacja = false;
    }
    
    public void init() {
        if (wpisView.getUmowa()!=null) {
            lista  = nieobecnoscFacade.findByAngaz(wpisView.getAngaz());
            selected.setAngaz(wpisView.getAngaz());
            if (pokazcalyrok==false) {
                lista = lista.stream().filter(p->p.getRokod().equals(wpisView.getRokWpisu())||p.getRokdo().equals(wpisView.getRokWpisu())).collect(Collectors.toList());
            }
        }
        listaumowa = umowaFacade.findPracownik(wpisView.getPracownik());
        listaabsencji = rodzajnieobecnosciFacade.findAll();
        Collections.sort(listaabsencji, new Rodzajnieobecnoscicomparator());
        delegacja = false;
        dniwykorzystanewroku = obliczdnichoroby(kalendarzmiesiacFacade.findByRokAngaz(wpisView.getAngaz(), wpisView.getRokWpisu()));
        //Collections.sort(listaabsencji, new Nieobecnoscikodzuscomparator());
    }
    
    public void initzbiorcze() {
        listaabsencji = rodzajnieobecnosciFacade.findAll();
        Collections.sort(listaabsencji, new Rodzajnieobecnoscicomparator());
        listapracownikow = new org.primefaces.model.DualListModel<>();
        listapracownikow.setSource(new ArrayList<>());
        listapracownikow.setTarget(new ArrayList<>());
        List<Angaz> angazList = wpisView.getFirma().getAngazList();
        List<Pracownik> listapracownikow = pobierzpracownikow (angazList);
        Collections.sort(listapracownikow, new Pracownikcomparator());
        if (listapracownikow != null) {
            this.listapracownikow.setSource(listapracownikow);
            this.listapracownikow.setTarget(new ArrayList<>());
        }
        delegacja = false;
    }

    private List<Pracownik> pobierzpracownikow(List<Angaz> angazList) {
        Set<Pracownik> zwrot = new HashSet<>();
        for (Angaz a : angazList) {
            zwrot.add(a.getPracownik());
        }
        return new ArrayList<Pracownik>(zwrot);
    }
    
    public void create() {
      if (selected!=null) {
          try {
            if (selected.getId()==null) {
                selected.setRokod(Data.getRok(selected.getDataod()));
                selected.setRokdo(Data.getRok(selected.getDatado()));
                selected.setMcod(Data.getMc(selected.getDataod()));
                selected.setMcdo(Data.getMc(selected.getDatado()));
                LocalDate oddata = LocalDate.parse(selected.getDataod());
                LocalDate dodata = LocalDate.parse(selected.getDatado());
                double iloscdni = DAYS.between(oddata,dodata);
                selected.setDnikalendarzowe(iloscdni+1.0);
                nieobecnoscFacade.create(selected);
                lista.add(selected);
                selected = new Nieobecnosc(wpisView.getAngaz());
                Msg.msg("Dodano nieobecność");
            } else {
                selected.setRokod(Data.getRok(selected.getDataod()));
                selected.setRokdo(Data.getRok(selected.getDatado()));
                selected.setMcod(Data.getMc(selected.getDataod()));
                selected.setMcdo(Data.getMc(selected.getDatado()));
                LocalDate oddata = LocalDate.parse(selected.getDataod());
                LocalDate dodata = LocalDate.parse(selected.getDatado());
                double iloscdni = DAYS.between(oddata,dodata);
                selected.setDnikalendarzowe(iloscdni+1.0);
                nieobecnoscFacade.edit(selected);
                selected = new Nieobecnosc(wpisView.getAngaz());
                Msg.msg("Edytowano nieobecność");
            }
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie dodano nowej nieobecnosci");
          }
      }
    }
    
    public void createzbiorczo(FirmaKadry firma) {
      if (listapracownikow.getTarget()!=null) {
          try {
            for (Pracownik p : listapracownikow.getTarget()) {
                Nieobecnosc nowa = new Nieobecnosc();
                Angaz angaz = angazFacade.findByFirmaPracownik(firma,p);
                nowa.setAngaz(angaz);
                nowa.setDataod(selected.getDataod());
                nowa.setDatado(selected.getDatado());
                nowa.setRokod(Data.getRok(selected.getDataod()));
                nowa.setRokdo(Data.getRok(selected.getDatado()));
                nowa.setMcod(Data.getMc(selected.getDataod()));
                nowa.setMcdo(Data.getMc(selected.getDatado()));
                nowa.setRodzajnieobecnosci(selected.getRodzajnieobecnosci());
                nowa.setKrajoddelegowania(selected.getKrajoddelegowania());
                nowa.setWalutadiety(selected.getWalutadiety());
                nowa.setDietaoddelegowanie(selected.getDietaoddelegowanie());
                nowa.setSwiadczeniekodzus(selected.getSwiadczeniekodzus());
                nowa.setKodzwolnienia(selected.getKodzwolnienia());
                nowa.setUzasadnienie(selected.getUzasadnienie());
                LocalDate oddata = LocalDate.parse(selected.getDataod());
                LocalDate dodata = LocalDate.parse(selected.getDatado());
                double iloscdni = DAYS.between(oddata,dodata);
                nowa.setDnikalendarzowe(iloscdni+1.0);
                nieobecnoscFacade.create(nowa);
                NieobecnosciBean.nanies(nowa, kalendarzmiesiacFacade, nieobecnoscFacade);
            }
            Msg.msg("Dodano nieobecności");
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie dodano nowej nieobecnosci");
          }
      } else {
          Msg.msg("e","Nie wybrano pracowników");
      }
    }
    
    public void nieniesnakalendarz() {
        if (wpisView.getUmowa() != null) {
            boolean czynaniesiono = false;
            for (Nieobecnosc nieobecnosc : lista) {
                nanies(nieobecnosc);
            }
            kalendarzmiesiacView.init();
            if (czynaniesiono) {
                Msg.msg("Naniesiono nieobecnosci");
            } else {
                Msg.msg("e","Nie ma nieobecności do naniesienia");
            }
        }
    }
    
    public void naniesrodzajnieobecnosci() {
        if (selected.getRodzajnieobecnosci()!=null) {
            swiadczeniekodzusLista = swiadczeniekodzusFacade.findByRodzajnieobecnosciAktiv(selected.getRodzajnieobecnosci());
            if (selected.getRodzajnieobecnosci().getKod().equals("Z")) {
                selected.setKrajoddelegowania("Niemcy");
                selected.setWalutadiety("EUR");
                selected.setDietaoddelegowanie(49.0);
                delegacja = true;
            } else {
                delegacja = false;
            }
            if (swiadczeniekodzusLista.size()==1) {
                selected.setSwiadczeniekodzus(swiadczeniekodzusLista.get(0));
            }
            Msg.msg("Wybrano rodzaj nieobecności");
        }
    }
    
    public void naniesprocent() {
         if (selected.getSwiadczeniekodzus()!=null) {
            if (selected.getSwiadczeniekodzus().getKod().equals("CH")) {
                selected.setZwolnienieprocent(80.0);
            }
        }
    }
    
    public String kolornieobecnosci(Nieobecnosc nieobecnosc) {
        String zwrot = "initial";
        if (nieobecnosc.getRokod().equals(wpisView.getRokWpisu())) {
            if (nieobecnosc.isNaniesiona()) {
                zwrot = "green";
            } else {
                zwrot = "red";
            }
        }
        if (nieobecnosc.isImportowana()) {
            zwrot = "#008B8B";
        }
        return zwrot;
    }

//    public boolean nanieszbiorcze(Nieobecnosc nieobecnosc) {
//            boolean czynaniesiono = false;
//            if (nieobecnosc.isNaniesiona() == false) {
//                try {
//                    if (nieobecnosc.getRokod().equals(wpisView.getRokWpisu()) || nieobecnosc.getRokdo().equals(wpisView.getRokWpisu())) {
//                        String mcod = nieobecnosc.getMcod();
//                        if (nieobecnosc.getRokod().equals(wpisView.getRokUprzedni())) {
//                            mcod = "01";
//                        }
//                        String mcdo = nieobecnosc.getMcdo();
//                        for (String mc : Mce.getMceListS()) {
//                            if (Data.jestrownywiekszy(mc, mcod) && Data.jestrownywiekszy(mcdo, mc)) {
//                                Kalendarzmiesiac znaleziony = kalendarzmiesiacFacade.findByRokMcAngaz(nieobecnosc.getAngaz(), wpisView.getRokWpisu(), mc);
//                                if (znaleziony != null) {
//                                    if (nieobecnosc.getRokod().equals(wpisView.getRokWpisu()) || nieobecnosc.getRokdo().equals(wpisView.getRokWpisu())) {
//                                        int dniroboczenieobecnosci = znaleziony.naniesnieobecnosc(nieobecnosc, );
//                                        if (!nieobecnosc.isImportowana()) {
//                                            nieobecnosc.setDniroboczenieobecnosci(nieobecnosc.getDniroboczenieobecnosci()+dniroboczenieobecnosci);
//                                        }
//                                    }
//                                    nieobecnoscFacade.edit(nieobecnosc);
//                                    kalendarzmiesiacFacade.edit(znaleziony);
//                                    czynaniesiono = true;
//                                } else {
//                                    Msg.msg("e", "Brak kalendarza pracownika za miesiąc rozliczeniowy. Nie można nanieść nieobecności!");
//                                }
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    Msg.msg("e", "Wystąpił błąd podczas nanoszenia nieobecności");
//                }
//            }
//            return czynaniesiono;
//        }
//    
    public boolean nanies(Nieobecnosc nieobecnosc) {
        boolean czynaniesiono = NieobecnosciBean.nanies(nieobecnosc, kalendarzmiesiacFacade, nieobecnoscFacade);
        if (czynaniesiono==false) {
            Msg.msg("e", "Wystąpił błąd podczas nanoszenia nieobecności");
        }
        return czynaniesiono;
    }

    public void pobierzzzus() {
        try {
            javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
            new javax.net.ssl.HostnameVerifier(){

                public boolean verify(String hostname,
                        javax.net.ssl.SSLSession sslSession) {
                    return hostname.equals("193.105.143.40");
                }
            });
            PobierzRaporty parameters = new PobierzRaporty();
            parameters.setNip(wpisView.getFirma().getNip());
            parameters.setLogin("a.barczyk@taxman.biz.pl");
            parameters.setHaslo("Taxman2810*");
            String nowadata = Data.odejmijdniDzis(30);
            parameters.setDataOd(Data.dataoddo(nowadata));
            WsdlPlatnikRaportyZlaPortType port = wsdlPlatnikRaportyZla.getZusChannelPlatnikRaportyZlaWsdlPlatnikRaportyZlaPort();
            BindingProvider prov = (BindingProvider) port;
            prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "b2b_platnik_raporty_zla");
            prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "b2b_platnik_raporty_zla");
            PobierzRaportyResponse pobierzRaporty = port.pobierzRaporty(parameters);
            if (pobierzRaporty.getKod().equals("0")) {
                if (pobierzRaporty.getRaporty() == null) {
                    Msg.msg("w", "Brak zwolnien w ostatnich 30 dniach");
                } else {
                    Msg.msg("Pobrano zwolnienia z ostatnich 30 dni");
                }
            } else if (pobierzRaporty.getKod().equals("200")) {
                Msg.msg("e", "Serwer ZUS wyłączony");
            }
            zuszla.Raporty rap = pobierzRaporty.getRaporty();
            zuszla.Raport raport = rap.getRaport().get(3);
            Base64.Decoder dec = Base64.getDecoder();
            byte[] dane = raport.getZawartosc();
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            Path path = Paths.get(realPath + "resources/zla/raport.zip");
            Files.write(path, dane);
            ZipFile zp = new ZipFile(realPath + "resources/zla/raport.zip", "Taxman2810*".toCharArray());
            FileHeader fileHeader = zp.getFileHeader("raport.xml");
            InputStream inputStream = zp.getInputStream(fileHeader);
            //File targetFile = new File("src/main/resources/targetFile.tmp");
            RaportEzla zwrot = null;
            try {
                JAXBContext context = JAXBContext.newInstance(RaportEzla.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                zwrot = (RaportEzla) unmarshaller.unmarshal(inputStream);
            } catch (Exception ex) {
                error.E.s("");
            }
            if (zwrot != null) {
                Nieobecnosc nieob = new Nieobecnosc(zwrot, wpisView.getAngaz());
                nieob.setSwiadczeniekodzus(nieobecnosckodzusFacade.findByKod("CH"));
                nieob.setId(999);
                nieob.setPobranaZUS(true);
                nieob.setRokod(Data.getRok(selected.getDataod()));
                nieob.setRokdo(Data.getRok(selected.getDatado()));
                lista.add(nieob);
            }
        } catch (Exception e) {
        }
    }
    
    
    public void zdejmijzkalendarza(Nieobecnosc nieob) {
        if (nieob!=null) {
            List<Dzien> wzorcowe = dzienFacade.findByNrwrokuByData(nieob.getDataod(), nieob.getDatado(), wpisView.getFirma());
            for (Dzien d : nieob.getDzienList()) {
                d.setNieobecnosc(null);
                dzienFacade.edit(d);
                try {
                    d.nanieswzorcowe(wzorcowe);
                } catch (Exception e){}
                dzienFacade.edit(d);
            }
            nieob.setDzienList(null);
            nieob.setNaniesiona(false);
            nieob.setDniroboczenieobecnosci(0.0);
            nieobecnoscFacade.edit(nieob);
            kalendarzmiesiacView.init();
            Msg.msg("Zdjęto nieobecnośćz kalendarza.");
        } else {
            Msg.msg("e","Nie można usunąc nieobecnosci");
        }
    }
            
    public void edytuj(Nieobecnosc nieob) {
        if (nieob!=null) {
            selected = nieob;
            naniesrodzajnieobecnosci();
            Msg.msg("Wybrano do edycji");
        } else {
            Msg.msg("e","Nie można edytować nieobecności");
        }
    }
    public void usun(Nieobecnosc nieob) {
        if (nieob!=null) {
            List<Dzien> wzorcowe = dzienFacade.findByNrwrokuByData(nieob.getDataod(), nieob.getDatado(), wpisView.getFirma());
            for (Dzien d : nieob.getDzienList()) {
                d.setNieobecnosc(null);
                dzienFacade.edit(d);
                try {
                    d.nanieswzorcowe(wzorcowe);
                } catch (Exception e){}
                dzienFacade.edit(d);
            }
            nieob.setDzienList(null);
            nieobecnoscFacade.edit(nieob);
            nieobecnoscFacade.remove(nieob);
            lista.remove(nieob);
            kalendarzmiesiacView.init();
            Msg.msg("Usunięto nieobecność. Naniesiono zmiany w kalendarzu");
        } else {
            Msg.msg("e","Nie można usunąc nieobecności");
        }
    }

    private int obliczdnichoroby(List<Kalendarzmiesiac> kalendarze) {
        int dnichoroby = 0;
        for (Kalendarzmiesiac kal : kalendarze) {
            double[] chorobadnigodz = kal.chorobadnigodz();
            dnichoroby = (int) (dnichoroby + chorobadnigodz[0]);
        }
        return dnichoroby;
    }
      
    public Nieobecnosc getSelected() {
        return selected;
    }

    public void setSelected(Nieobecnosc selected) {
        this.selected = selected;
    }

    public List<Nieobecnosc> getLista() {
        return lista;
    }

    public void setLista(List<Nieobecnosc> lista) {
        this.lista = lista;
    }

    public Nieobecnosc getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Nieobecnosc selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Umowa> getListaumowa() {
        return listaumowa;
    }

    public void setListaumowa(List<Umowa> listaumowa) {
        this.listaumowa = listaumowa;
    }

    public List<Swiadczeniekodzus> getSwiadczeniekodzusLista() {
        return swiadczeniekodzusLista;
    }

    public void setSwiadczeniekodzusLista(List<Swiadczeniekodzus> swiadczeniekodzusLista) {
        this.swiadczeniekodzusLista = swiadczeniekodzusLista;
    }

    public List<Rodzajnieobecnosci> getListaabsencji() {
        return listaabsencji;
    }

    public void setListaabsencji(List<Rodzajnieobecnosci> listaabsencji) {
        this.listaabsencji = listaabsencji;
    }

    public boolean isPokazcalyrok() {
        return pokazcalyrok;
    }

    public void setPokazcalyrok(boolean pokazcalyrok) {
        this.pokazcalyrok = pokazcalyrok;
    }

    public DualListModel<Pracownik> getListapracownikow() {
        return listapracownikow;
    }

    public void setListapracownikow(DualListModel<Pracownik> listapracownikow) {
        this.listapracownikow = listapracownikow;
    }

    public boolean isDelegacja() {
        return delegacja;
    }

    public void setDelegacja(boolean delegacja) {
        this.delegacja = delegacja;
    }

    public int getDniwykorzystanewroku() {
        return dniwykorzystanewroku;
    }

    public void setDniwykorzystanewroku(int dniwykorzystanewroku) {
        this.dniwykorzystanewroku = dniwykorzystanewroku;
    }

    


   
    
    
}
