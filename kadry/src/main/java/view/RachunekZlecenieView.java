/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.KalendarzmiesiacFacade;
import dao.RachunekdoumowyzleceniaFacade;
import dao.SkladnikWynagrodzeniaFacade;
import dao.TabelanbpFacade;
import dao.UmowaFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import data.Data;
import embeddable.Mce;
import entity.Kalendarzmiesiac;
import entity.Rachunekdoumowyzlecenia;
import entity.Tabelanbp;
import entity.Umowa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import viewsuperplace.OsobaBean;
import z.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class RachunekZlecenieView implements Serializable {

    private static final long serialVersionUID = 1L;

    private Umowa umowabiezaca;
    private Rachunekdoumowyzlecenia rachunekdoumowyzlecenia;
    private Rachunekdoumowyzlecenia selectedlista;
    private List<Rachunekdoumowyzlecenia> lista;
    private boolean trzebazrobicrachunek;
    @Inject
    private WpisView wpisView;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private RachunekdoumowyzleceniaFacade rachunekdoumowyzleceniaFacade;
    @Inject
    private SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade;
    @Inject
    private ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private TabelanbpFacade tabelanbpFacade;
    private String datawystawieniaZbiorcze;
    private double kurswalutyZbiorcze;
    private String datawalutyZbiorcze;
    private String symbolwalutyZbiorcze;

    public void initzbiorcze() {
        List<Umowa> umowyzlecenia = umowaFacade.findByFirmaZlecenie(wpisView.getFirma(), true);
        lista = new ArrayList<>();
        datawystawieniaZbiorcze = Data.aktualnaData();
        Tabelanbp zwrot = ustawtabelenbp(datawystawieniaZbiorcze);
        datawalutyZbiorcze = zwrot.getDatatabeli();
        kurswalutyZbiorcze = zwrot.getKurssredniPrzelicznik();
        symbolwalutyZbiorcze = zwrot.getWaluta().getSymbolwaluty();
        for (Umowa umowa : umowyzlecenia) {
            umowabiezaca = umowa;
            init();
            lista.add(robrachunek(umowa));
        }
        Msg.msg("wygenerowano/pobrano rachunki");
    }

    public void init() {
        Umowa umowabiezaca = wpisView.getAngaz().pobierzumowaZlecenia(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        if (umowabiezaca==null) {
            umowabiezaca = wpisView.getAngaz().pobierzumowaDzielo(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        }
        if (umowabiezaca!=null) {
            this.umowabiezaca = umowabiezaca;
            rachunekdoumowyzlecenia = robrachunek(umowabiezaca);
            wpisView.setUmowa(umowabiezaca);
        }
        
    }

    public Rachunekdoumowyzlecenia robrachunek(Umowa umowabiezaca) {
        Rachunekdoumowyzlecenia rachunekdoumowyzlecenia = null;
        if (umowabiezaca != null && (umowabiezaca.getUmowakodzus().isZlecenie()||umowabiezaca.getUmowakodzus().isDzielo())) {
            this.umowabiezaca = umowabiezaca;
            umowabiezaca = umowaFacade.findById(umowabiezaca.getId());
            String datado = umowabiezaca.getDatado();
            trzebazrobicrachunek = false;
            rachunekdoumowyzlecenia = rachunekdoumowyzleceniaFacade.findByRokMcUmowa(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(), umowabiezaca);
            if (rachunekdoumowyzlecenia == null) {
                if (Integer.parseInt(umowabiezaca.getRok()) < wpisView.getRokWpisuInt()) {
                    trzebazrobicrachunek = true;
                } else if (Integer.parseInt(umowabiezaca.getRok()) == wpisView.getRokWpisuInt()) {
                    if (Mce.getMiesiacToNumber().get(umowabiezaca.getMc()) <= Integer.parseInt(wpisView.getMiesiacWpisu())) {
                        trzebazrobicrachunek = true;
                    }
                }
                if (datado != null && !datado.equals("")) {
                    String rokdo = Data.getRok(datado);
                    String mcdo = Data.getMc(datado);
                    if (Integer.parseInt(rokdo) < wpisView.getRokWpisuInt()) {
                        trzebazrobicrachunek = false;
                    } else if (Integer.parseInt(rokdo) == wpisView.getRokWpisuInt()) {
                        if (Mce.getMiesiacToNumber().get(mcdo) < Integer.parseInt(wpisView.getMiesiacWpisu())) {
                            trzebazrobicrachunek = false;
                        }
                    }
                }
                Kalendarzmiesiac kalendarz = kalendarzmiesiacFacade.findByRokMcAngaz(umowabiezaca.getAngaz(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                if (trzebazrobicrachunek && kalendarz != null) {
                    rachunekdoumowyzlecenia = new Rachunekdoumowyzlecenia(umowabiezaca);
                    rachunekdoumowyzlecenia.setDataod(Data.pierwszyDzien(wpisView));
                    rachunekdoumowyzlecenia.setDatado(Data.ostatniDzien(wpisView));
                    rachunekdoumowyzlecenia.setRok(wpisView.getRokWpisu());
                    rachunekdoumowyzlecenia.setMc(wpisView.getMiesiacWpisu());
                    if (datado != null && !datado.equals("")) {
                        String mcdo = Data.getRok(datado);
                        if (mcdo.equals(wpisView.getMiesiacWpisu())) {
                            rachunekdoumowyzlecenia.setDatado(datado);
                        }
                    }
                    double kwotaPolska = umowabiezaca.getAngaz().pobierzwynagrodzenieKwota(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(), kalendarz);
                    double iloscgodzinzkalendarzaPolska = pobierzgodzinyzkalendarzaPolska();
                    rachunekdoumowyzlecenia.setIloscgodzin(iloscgodzinzkalendarzaPolska);
                    if (umowabiezaca.getAngaz().czywynagrodzeniegodzinoweRachunek()) {
                        rachunekdoumowyzlecenia.setWynagrodzeniegodzinowe(kwotaPolska);
                        rachunekdoumowyzlecenia.setKwota(Z.z(rachunekdoumowyzlecenia.getWynagrodzeniegodzinowe() * iloscgodzinzkalendarzaPolska));
                    } else {
                        rachunekdoumowyzlecenia.setWynagrodzeniemiesieczne(kwotaPolska);
                        rachunekdoumowyzlecenia.setKwota(Z.z(kwotaPolska));
                    }
                    double kwotaZagranicaWaluta = umowabiezaca.getAngaz().pobierzwynagrodzenieKwotaWaluta(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(), kalendarz);
                    rachunekdoumowyzlecenia.setWynagrodzeniegodzinoweoddelegowaniewaluta(kwotaZagranicaWaluta);
                    Tabelanbp tabelanbp = ustawtabelenbp(rachunekdoumowyzlecenia.getDatawystawienia());
                    rachunekdoumowyzlecenia.setKurswaluty(tabelanbp.getKurssredni());
                    rachunekdoumowyzlecenia.setDatawaluty(tabelanbp.getDatatabeli());
                    double kwotaZagranicaPLN = (kwotaZagranicaWaluta * tabelanbp.getKurssredni());
                    rachunekdoumowyzlecenia.setSymbolwaluty(tabelanbp.getWaluta().getSymbolwaluty());
                    double iloscgodzinzkalendarzaZagranica = pobierzgodzinyzkalendarzaZagranica();
                    rachunekdoumowyzlecenia.setIloscgodzinoddelegowanie(iloscgodzinzkalendarzaZagranica);
                    if (umowabiezaca.getAngaz().czywynagrodzeniegodzinoweRachunek()) {
                        rachunekdoumowyzlecenia.setWynagrodzeniegodzinoweoddelegowanie(kwotaZagranicaPLN);
                        rachunekdoumowyzlecenia.setKwotaoddelegowanie(Z.z(rachunekdoumowyzlecenia.getWynagrodzeniegodzinoweoddelegowanie() * iloscgodzinzkalendarzaZagranica));
                        rachunekdoumowyzlecenia.setKwotaoddelegowaniewaluta(Z.z(rachunekdoumowyzlecenia.getWynagrodzeniegodzinoweoddelegowaniewaluta()* iloscgodzinzkalendarzaZagranica));
                    } else {
                        rachunekdoumowyzlecenia.setWynagrodzeniemiesieczne(Z.z(rachunekdoumowyzlecenia.getWynagrodzeniemiesieczne()+kwotaZagranicaPLN));
                        rachunekdoumowyzlecenia.setKwota(Z.z(Z.z(rachunekdoumowyzlecenia.getWynagrodzeniemiesieczne()+kwotaZagranicaPLN)));
                    }
                    rachunekdoumowyzlecenia.setKwotasuma(Z.z(rachunekdoumowyzlecenia.getKwota() + rachunekdoumowyzlecenia.getKwotaoddelegowanie()));
                    double koszty = umowabiezaca.getAngaz().getKosztyuzyskaniaprocent()==100?20:50;
                    rachunekdoumowyzlecenia.setProcentkosztowuzyskania(koszty);
                    rachunekdoumowyzlecenia.setKoszt(Z.z(rachunekdoumowyzlecenia.getKwotasuma() * koszty / 100.0));
                } else {
                    Msg.msg("e","Brak kalendarza");
                }
            } else {
                Msg.msg("Pobrano zachowany rachunek");
            }
        }
        return rachunekdoumowyzlecenia;
    }
    
//    public void przeliczkoszty() {
//        double koszty = rachunekdoumowyzlecenia.getProcentkosztowuzyskania();
//        rachunekdoumowyzlecenia.setKoszt(Z.z(rachunekdoumowyzlecenia.getKwotasuma() * koszty / 100.0));
//        Msg.msg("Przeliczonp koszty");
//    }
    
    public void ustawtabelenbpRachZbiorcze() {
        if (lista!=null) {
            for (Rachunekdoumowyzlecenia r : lista) {
                Tabelanbp zwrot = ustawtabelenbp(datawystawieniaZbiorcze);
                datawalutyZbiorcze = zwrot.getDatatabeli();
                kurswalutyZbiorcze = zwrot.getKurssredniPrzelicznik();
                symbolwalutyZbiorcze = zwrot.getWaluta().getSymbolwaluty();
                r.setDatawystawienia(datawystawieniaZbiorcze);
                ustawtabelenbpRach(r);
                przeliczrachunek(r);
            }
            Msg.msg("Przeliczono waluty");
        }
    }

    public void ustawTabiPrzelicz(Rachunekdoumowyzlecenia rachunekdoumowyzlecenia) {
        ustawtabelenbpRach(rachunekdoumowyzlecenia);
        przeliczrachunek(rachunekdoumowyzlecenia);
    }
    
    public Tabelanbp ustawtabelenbpRach(Rachunekdoumowyzlecenia rachunekdoumowyzlecenia) {
        String datawyplaty = rachunekdoumowyzlecenia.getDatawystawienia();
        Tabelanbp zwrot = null;
        if (datawyplaty != null && datawyplaty.length() == 10) {
            String data = datawyplaty;
            boolean znaleziono = false;
            int zabezpieczenie = 0;
            while (!znaleziono && (zabezpieczenie < 365)) {
                data = Data.odejmijdni(data, 1);
                Tabelanbp tabelanbppobrana = tabelanbpFacade.findByDateWaluta(data, "EUR");
                if (tabelanbppobrana instanceof Tabelanbp) {
                    znaleziono = true;
                    zwrot = tabelanbppobrana;
                    break;
                }
                zabezpieczenie++;
            }
        }
        if (zwrot!=null) {
            rachunekdoumowyzlecenia.setKurswaluty(Z.z4(zwrot.getKurssredniPrzelicznik()));
            rachunekdoumowyzlecenia.setDatawaluty(zwrot.getDatatabeli());
            rachunekdoumowyzlecenia.setWynagrodzeniegodzinoweoddelegowanie(Z.z(rachunekdoumowyzlecenia.getWynagrodzeniegodzinoweoddelegowaniewaluta()*Z.z4(zwrot.getKurssredniPrzelicznik()))   );
        }
        return zwrot;
    }

    public Tabelanbp ustawtabelenbp(String datawyplaty) {
        Tabelanbp zwrot = null;
        if (datawyplaty != null && datawyplaty.length() == 10) {
            String data = datawyplaty;
            boolean znaleziono = false;
            int zabezpieczenie = 0;
            while (!znaleziono && (zabezpieczenie < 365)) {
                data = Data.odejmijdni(data, 1);
                Tabelanbp tabelanbppobrana = tabelanbpFacade.findByDateWaluta(data, "EUR");
                if (tabelanbppobrana instanceof Tabelanbp) {
                    znaleziono = true;
                    zwrot = tabelanbppobrana;
                    break;
                }
                zabezpieczenie++;
            }
        }
        return zwrot;
    }

    private double pobierzgodzinyzkalendarzaPolska() {
        double zwrot = 0;
        Kalendarzmiesiac kalendarz = kalendarzmiesiacFacade.findByRokMcAngaz(wpisView.getAngaz(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        double[] roboczegodz = kalendarz.roboczeOddelegowaniePolska();
        if (roboczegodz != null) {
            zwrot = roboczegodz[1];
        }
        return zwrot;
    }

    private double pobierzgodzinyzkalendarzaZagranica() {
        double zwrot = 0;
        Kalendarzmiesiac kalendarz = kalendarzmiesiacFacade.findByRokMcAngaz(wpisView.getAngaz(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        double[] roboczegodz = kalendarz.roboczeOddelegowanieZagranica();
        if (roboczegodz != null) {
            zwrot = roboczegodz[1];
        }
        return zwrot;
    }

    public void przeliczrachunek(Rachunekdoumowyzlecenia rachunekdoumowyzlecenia) {
        if (rachunekdoumowyzlecenia != null) {
            String datakonca26lat = OsobaBean.obliczdata26(rachunekdoumowyzlecenia.getUmowa().getPracownik().getDataurodzenia());
            boolean po26roku = Data.czyjestpoTerminData(datakonca26lat, rachunekdoumowyzlecenia.getDatawystawienia());
            rachunekdoumowyzlecenia.setDo26lat(!po26roku);
            rachunekdoumowyzlecenia.setSpoleczne(po26roku);
            rachunekdoumowyzlecenia.setZdrowotna(po26roku);
            if (rachunekdoumowyzlecenia.getWynagrodzeniemiesieczne() > 0.0) {
                rachunekdoumowyzlecenia.setKwota(rachunekdoumowyzlecenia.getWynagrodzeniemiesieczne());
                rachunekdoumowyzlecenia.setKwotasuma(Z.z(rachunekdoumowyzlecenia.getKwota() + rachunekdoumowyzlecenia.getKwotaoddelegowanie()));
            } else {
                rachunekdoumowyzlecenia.setKwota(Z.z(rachunekdoumowyzlecenia.getWynagrodzeniegodzinowe() * rachunekdoumowyzlecenia.getIloscgodzin()));
                rachunekdoumowyzlecenia.setKwotaoddelegowanie(Z.z(rachunekdoumowyzlecenia.getWynagrodzeniegodzinoweoddelegowanie() * rachunekdoumowyzlecenia.getIloscgodzinoddelegowanie()));
                rachunekdoumowyzlecenia.setKwotaoddelegowaniewaluta(Z.z(rachunekdoumowyzlecenia.getWynagrodzeniegodzinoweoddelegowaniewaluta()* rachunekdoumowyzlecenia.getIloscgodzinoddelegowanie()));
                rachunekdoumowyzlecenia.setKwotasuma(Z.z(rachunekdoumowyzlecenia.getKwota() + rachunekdoumowyzlecenia.getKwotaoddelegowanie()));
            }
            Msg.msg("Przeliczono kwotę rachunku");
        }
    }
    
     public void przeliczrachunekdomyslny() {
        if (rachunekdoumowyzlecenia != null) {
            if (rachunekdoumowyzlecenia.getWynagrodzeniemiesieczne() > 0.0) {
                rachunekdoumowyzlecenia.setKwota(rachunekdoumowyzlecenia.getWynagrodzeniemiesieczne());
                rachunekdoumowyzlecenia.setKwotasuma(Z.z(rachunekdoumowyzlecenia.getKwota() + rachunekdoumowyzlecenia.getKwotaoddelegowanie()));
            } else {
                rachunekdoumowyzlecenia.setKwota(Z.z(rachunekdoumowyzlecenia.getWynagrodzeniegodzinowe() * rachunekdoumowyzlecenia.getIloscgodzin()));
                rachunekdoumowyzlecenia.setKwotaoddelegowanie(Z.z(rachunekdoumowyzlecenia.getWynagrodzeniegodzinoweoddelegowanie() * rachunekdoumowyzlecenia.getIloscgodzinoddelegowanie()));
                rachunekdoumowyzlecenia.setKwotaoddelegowaniewaluta(Z.z(rachunekdoumowyzlecenia.getWynagrodzeniegodzinoweoddelegowaniewaluta()* rachunekdoumowyzlecenia.getIloscgodzinoddelegowanie()));
                rachunekdoumowyzlecenia.setKwotasuma(Z.z(rachunekdoumowyzlecenia.getKwota() + rachunekdoumowyzlecenia.getKwotaoddelegowanie()));
            }
            Msg.msg("Przeliczono kwotę rachunku");
        }
    }
     
     public void przeliczrachunekdomyslnyKwotyreczne() {
        if (rachunekdoumowyzlecenia != null) {
            rachunekdoumowyzlecenia.setKwotasuma(Z.z(rachunekdoumowyzlecenia.getKwota() + rachunekdoumowyzlecenia.getKwotaoddelegowanie()));
            Msg.msg("Przeliczono kwotę rachunku");
        }
    }

   

    public void zachowajrachunki() {
        for (Rachunekdoumowyzlecenia p : lista) {
            try {
                if (p.getId() == null) {
                    if (p.getKwotasuma() > 0.0) {
                        rachunekdoumowyzleceniaFacade.create(p);
                        //                    Skladnikwynagrodzenia skladnik = p.getUmowa().getAngaz().pobierzskladnikzlecenieMiesieczne();
                        //                    Zmiennawynagrodzenia zmienna = new Zmiennawynagrodzenia(skladnik);
                        //                    zmienna.setDataod(rachunekdoumowyzlecenia.getDataod());
                        //                    zmienna.setDatado(rachunekdoumowyzlecenia.getDatado());
                        //                    zmienna.s
                        //                    zmienna.setKwota(p.getKwota());
                        //                    zmiennaWynagrodzeniaFacade.edit(zmienna);
                        //                    rachunekdoumowyzleceniaFacade.edit(p);
                    }
                } else {
                    //                Skladnikwynagrodzenia skladnik = p.getUmowa().getAngaz().pobierzskladnikzlecenieMiesieczne();
                    //                Zmiennawynagrodzenia zmienna = skladnik.pobierzzmienna(p);
                    //                zmienna.setKwota(p.getKwota());
                    //                zmiennaWynagrodzeniaFacade.edit(zmienna);
                    rachunekdoumowyzleceniaFacade.edit(p);
                }
            } catch (Exception e) {
                Msg.msg("e", "Błąd. Nie zachowano rachunku " + p.getUmowa().getNazwiskoImie());
            }
        }
        Msg.msg("Zachowano rachunki");
    }
    
    public void edytujkwadraciki() {
        if (rachunekdoumowyzlecenia.getId() != null) {
            rachunekdoumowyzleceniaFacade.edit(rachunekdoumowyzlecenia);
            Msg.msg("Edytowano rachunek");
        }
    }

    public void zaksieguj() {
        if (rachunekdoumowyzlecenia != null) {
            rachunekdoumowyzleceniaFacade.create(rachunekdoumowyzlecenia);
            rachunekdoumowyzlecenia = null;
            Msg.msg("Zachowano rachunek");
        }
    }

    public void usun() {
        if (rachunekdoumowyzlecenia != null) {
            rachunekdoumowyzleceniaFacade.remove(rachunekdoumowyzlecenia);
            init();
            Msg.msg("Usunięto rachunek");
        }
    }

    public Umowa getUmowabiezaca() {
        return umowabiezaca;
    }

    public void setUmowabiezaca(Umowa umowabiezaca) {
        this.umowabiezaca = umowabiezaca;
    }

    public boolean isTrzebazrobicrachunek() {
        return trzebazrobicrachunek;
    }

    public void setTrzebazrobicrachunek(boolean trzebazrobicrachunek) {
        this.trzebazrobicrachunek = trzebazrobicrachunek;
    }

    public Rachunekdoumowyzlecenia getRachunekdoumowyzlecenia() {
        return rachunekdoumowyzlecenia;
    }

    public void setRachunekdoumowyzlecenia(Rachunekdoumowyzlecenia rachunekdoumowyzlecenia) {
        this.rachunekdoumowyzlecenia = rachunekdoumowyzlecenia;
    }

    public Rachunekdoumowyzlecenia getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Rachunekdoumowyzlecenia selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Rachunekdoumowyzlecenia> getLista() {
        return lista;
    }

    public void setLista(List<Rachunekdoumowyzlecenia> lista) {
        this.lista = lista;
    }

    public String getDatawystawieniaZbiorcze() {
        return datawystawieniaZbiorcze;
    }

    public void setDatawystawieniaZbiorcze(String datawystawieniaZbiorcze) {
        this.datawystawieniaZbiorcze = datawystawieniaZbiorcze;
    }

    public double getKurswalutyZbiorcze() {
        return kurswalutyZbiorcze;
    }

    public void setKurswalutyZbiorcze(double kurswalutyZbiorcze) {
        this.kurswalutyZbiorcze = kurswalutyZbiorcze;
    }

    public String getDatawalutyZbiorcze() {
        return datawalutyZbiorcze;
    }

    public void setDatawalutyZbiorcze(String datawalutyZbiorcze) {
        this.datawalutyZbiorcze = datawalutyZbiorcze;
    }

    public String getSymbolwalutyZbiorcze() {
        return symbolwalutyZbiorcze;
    }

    public void setSymbolwalutyZbiorcze(String symbolwalutyZbiorcze) {
        this.symbolwalutyZbiorcze = symbolwalutyZbiorcze;
    }

    
}
