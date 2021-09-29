/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Nieobecnoscikodzuscomparator;
import dao.DzienFacade;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.NieobecnoscFacade;
import dao.NieobecnosckodzusFacade;
import dao.UmowaFacade;
import data.Data;
import embeddable.Mce;
import entity.Dzien;
import entity.Kalendarzmiesiac;
import entity.Nieobecnosc;
import entity.Nieobecnosckodzus;
import entity.Umowa;
import generated.RaportEzla;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
    private List<Nieobecnosckodzus> listanieobecnosckodzus;
    private List<Umowa> listaumowa;
    @Inject
    private NieobecnoscFacade nieobecnoscFacade;
    @Inject
    private NieobecnosckodzusFacade nieobecnosckodzusFacade;
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
    
    
    public void init() {
        if (wpisView.getUmowa()==null) {
            List<Umowa> umowy = wpisView.getAngaz().getUmowaList();
            if (umowy!=null && umowy.size()==1) {
                wpisView.setUmowa(umowy.get(0));
            } else if (umowy!=null) {
                wpisView.setUmowa(umowy.stream().filter(p->p.isAktywna()).findFirst().get());
            }
        }
        lista  = nieobecnoscFacade.findByUmowa(wpisView.getUmowa());
        if (pokazcalyrok==false) {
            lista = lista.stream().filter(p->p.getRokod().equals(wpisView.getRokWpisu())||p.getRokdo().equals(wpisView.getRokWpisu())).collect(Collectors.toList());
        }
        listaumowa = umowaFacade.findPracownik(wpisView.getPracownik());
        listanieobecnosckodzus = nieobecnosckodzusFacade.findAll();
        selected.setUmowa(wpisView.getUmowa());
        Collections.sort(listanieobecnosckodzus, new Nieobecnoscikodzuscomparator());
    }

    public void create() {
      if (selected!=null) {
          try {
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
            selected = new Nieobecnosc(wpisView.getUmowa());
            Msg.msg("Dodano nieobecnośc");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowej nieobecnosci");
          }
      }
    }
    
    public void nieniesnakalendarz() {
        if (wpisView.getUmowa() != null) {
            boolean czynaniesiono = false;
            for (Nieobecnosc p : lista) {
                if (p.isNaniesiona()==false) {
                    try {
                        if (p.getRokod().equals(wpisView.getRokWpisu()) || p.getRokdo().equals(wpisView.getRokWpisu())) {
                            String mcod = p.getMcod();
                            if (p.getRokod().equals(wpisView.getRokUprzedni())) {
                                mcod = "01";
                            }
                            String mcdo = p.getMcdo();
                            for (String mc : Mce.getMceListS()) {
                                if (Data.jestrownywiekszy(mc, mcod) && Data.jestrownywiekszy(mcdo, mc)) {
                                    Kalendarzmiesiac znaleziony = kalendarzmiesiacFacade.findByRokMcUmowa(wpisView.getUmowa(), wpisView.getRokWpisu(), mc);
                                    if (znaleziony != null) {
                                        if (p.getRokod().equals(wpisView.getRokWpisu()) || p.getRokdo().equals(wpisView.getRokWpisu())) {
                                            znaleziony.naniesnieobecnosc(p);
                                            p.setNaniesiona(true);
                                        }
                                        nieobecnoscFacade.edit(p);
                                        kalendarzmiesiacFacade.edit(znaleziony);
                                        czynaniesiono = true;
                                    } else {
                                        Msg.msg("e", "Brak kalendarza pracownika za miesiąc rozliczeniowy. Nie można nanieść nieobecności!");
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        Msg.msg("e", "Wystąpił błąd podczas nanoszenia nieobecności");
                    }
                }
            }
            kalendarzmiesiacView.init();
            if (czynaniesiono) {
                Msg.msg("Naniesiono nieobecnosci");
            } else {
                Msg.msg("e","Nie ma nieobecności do naniesienia");
            }
        }
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
            System.out.println("");
            if (zwrot != null) {
                Nieobecnosc nieob = new Nieobecnosc(zwrot, wpisView.getUmowa());
                nieob.setNieobecnosckodzus(nieobecnosckodzusFacade.findByKod("331"));
                nieob.setId(999);
                nieob.setPobranaZUS(true);
                nieob.setRokod(Data.getRok(selected.getDataod()));
                nieob.setRokdo(Data.getRok(selected.getDatado()));
                lista.add(nieob);
            }
        } catch (Exception e) {
            System.out.println("");
        }
    }
    
    public void usun(Nieobecnosc nieob) {
        if (nieob!=null) {
            List<Dzien> wzorcowe = dzienFacade.findByNrwrokuByData(nieob.getDataod(), nieob.getDatado(), wpisView.getFirma());
            for (Dzien d : nieob.getDzienList()) {
                d.nanieswzorcowe(wzorcowe);
                dzienFacade.edit(d);
            }
            nieobecnoscFacade.remove(nieob);
            lista.remove(nieob);
            Msg.msg("Usunięto nieobecność. Naniesiono zmiany w kalendarzu");
        } else {
            Msg.msg("e","Nie można usunąc nieobecnosci");
        }
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

    public List<Nieobecnosckodzus> getListanieobecnosckodzus() {
        return listanieobecnosckodzus;
    }

    public void setListanieobecnosckodzus(List<Nieobecnosckodzus> listanieobecnosckodzus) {
        this.listanieobecnosckodzus = listanieobecnosckodzus;
    }

    public boolean isPokazcalyrok() {
        return pokazcalyrok;
    }

    public void setPokazcalyrok(boolean pokazcalyrok) {
        this.pokazcalyrok = pokazcalyrok;
    }

   
    
    
}
