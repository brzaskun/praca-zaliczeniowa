/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.SrodekTrwcomparatorData;
import dao.STRDAO;
import embeddable.STRtabela;
import embeddable.Umorzenie;
import entity.SrodekTrw;
import error.E;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import mail.MailOther;
import pdf.PdfSTR;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "STREwidencja")
@RequestScoped
public class STREwidencja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private STRDAO sTRDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    //tablica obiektw danego klienta
    private List<SrodekTrw> listaSrodkiTrwale;
    //wyposazenie
    private List<SrodekTrw> listaWyposazenia;
    //srodki trwale wykaz rok biezacy
    private List<STRtabela> strtabela;
    /**
     * Dane informacyjne gora strony srodkitablica.xhtml
     */
    private int iloscsrodkow;
    private int zakupionewbiezacyrok;
    private String west;

    public STREwidencja() {
        listaSrodkiTrwale = new ArrayList<>();
        listaWyposazenia = new ArrayList<>();
        strtabela = new ArrayList<>();
    }
  

    @PostConstruct
    public void init() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request.isUserInRole("Guest")) {
            west = "sub/layoutTablicaSrodkiGuest/west.xhtml";
        } else  if (request.isUserInRole("Multiuser")) {
            west = "sub/layoutTablicaSrodkiGuest/west.xhtml";
        } else if (request.isUserInRole("Bookkeeper")) {
            west = "sub/layoutTablicaSrodkiKsiegowa/west.xhtml";
        }
//        } else if (request.isUserInRole("GuestFK")) {
//            west = "sub/layoutFakturaGuestFK/west.xhtml";
//        }
        String rokdzisiejszyS = wpisView.getRokWpisuSt();
        int rokdzisiejszyI = wpisView.getRokWpisu();
        String podatnikwpisu = wpisView.getPodatnikWpisu();
        zakupionewbiezacyrok = 0;
        if (wpisView.getPodatnikWpisu() != null) {
            List<SrodekTrw> c = new ArrayList<>();
            try {
                c = sTRDAO.findStrPod(podatnikwpisu);
            } catch (Exception e) { E.e(e); 
            }
            if (!c.isEmpty()) {
                int i = 1;
                int j = 1;
                for (SrodekTrw przegladanySrodek : c) {
                    if (przegladanySrodek.getTyp() != null && przegladanySrodek.getTyp().equals("wyposazenie")) {
                        przegladanySrodek.setNrsrodka(i++);
                        listaWyposazenia.add(przegladanySrodek);

                    } else {
                        przegladanySrodek.setNrsrodka(j++);
                        if (przegladanySrodek.getDatazak().substring(0, 4).equals(rokdzisiejszyS)) {
                            zakupionewbiezacyrok++;
                        }
                        listaSrodkiTrwale.add(przegladanySrodek);
                    }
                }
                iloscsrodkow = listaSrodkiTrwale.size();
            }
        }
        List<SrodekTrw> lista = new ArrayList<>();
        lista.addAll(listaSrodkiTrwale);
        int i = 1;
        for (SrodekTrw str : lista) {
            STRtabela strdocelowy = new STRtabela();
            strdocelowy.setId(i);
            strdocelowy.setNazwa(str.getNazwa());
            strdocelowy.setKst(str.getKst());
            strdocelowy.setOdpisrok(0.0);
            strdocelowy.setSymbol(str.getSymbol());
            strdocelowy.setDatazak(str.getDatazak());
            strdocelowy.setDataprzek(str.getDataprzek());
            strdocelowy.setDatawy("");
            strdocelowy.setNetto(str.getNetto());
            strdocelowy.setPodatnik(str.getPodatnik());
            strdocelowy.setStyczen(0.0);
            strdocelowy.setLuty(0.0);
            strdocelowy.setMarzec(0.0);
            strdocelowy.setKwiecien(0.0);
            strdocelowy.setMaj(0.0);
            strdocelowy.setCzerwiec(0.0);
            strdocelowy.setLipiec(0.0);
            strdocelowy.setSierpien(0.0);
            strdocelowy.setWrzesien(0.0);
            strdocelowy.setPazdziernik(0.0);
            strdocelowy.setListopad(0.0);
            strdocelowy.setGrudzien(0.0);
            List<Double> miesiace = new ArrayList<>();
            try {
                Iterator itX;
                itX = str.getUmorzWyk().iterator();
                BigDecimal umorzenianarastajaco = new BigDecimal(0);
                while (itX.hasNext()) {
                    Umorzenie um = (Umorzenie) itX.next();
                    if (um.getRokUmorzenia().equals(rokdzisiejszyI)) {
                        Integer mc = um.getMcUmorzenia();
                        switch (mc) {
                            case 1:
                                strdocelowy.setStyczen(um.getKwota().doubleValue());
                                strdocelowy.setOdpisrok(strdocelowy.getOdpisrok() + strdocelowy.getStyczen());
                                break;
                            case 2:
                                strdocelowy.setLuty(um.getKwota().doubleValue());
                                strdocelowy.setOdpisrok(strdocelowy.getOdpisrok() + strdocelowy.getLuty());
                                break;
                            case 3:
                                strdocelowy.setMarzec(um.getKwota().doubleValue());
                                strdocelowy.setOdpisrok(strdocelowy.getOdpisrok() + strdocelowy.getMarzec());
                                break;
                            case 4:
                                strdocelowy.setKwiecien(um.getKwota().doubleValue());
                                strdocelowy.setOdpisrok(strdocelowy.getOdpisrok() + strdocelowy.getKwiecien());
                                break;
                            case 5:
                                strdocelowy.setMaj(um.getKwota().doubleValue());
                                strdocelowy.setOdpisrok(strdocelowy.getOdpisrok() + strdocelowy.getMaj());
                                break;
                            case 6:
                                strdocelowy.setCzerwiec(um.getKwota().doubleValue());
                                strdocelowy.setOdpisrok(strdocelowy.getOdpisrok() + strdocelowy.getCzerwiec());
                                break;
                            case 7:
                                strdocelowy.setLipiec(um.getKwota().doubleValue());
                                strdocelowy.setOdpisrok(strdocelowy.getOdpisrok() + strdocelowy.getLipiec());
                                break;
                            case 8:
                                strdocelowy.setSierpien(um.getKwota().doubleValue());
                                strdocelowy.setOdpisrok(strdocelowy.getOdpisrok() + strdocelowy.getSierpien());
                                break;
                            case 9:
                                strdocelowy.setWrzesien(um.getKwota().doubleValue());
                                strdocelowy.setOdpisrok(strdocelowy.getOdpisrok() + strdocelowy.getWrzesien());
                                break;
                            case 10:
                                strdocelowy.setPazdziernik(um.getKwota().doubleValue());
                                strdocelowy.setOdpisrok(strdocelowy.getOdpisrok() + strdocelowy.getPazdziernik());
                                break;
                            case 11:
                                strdocelowy.setListopad(um.getKwota().doubleValue());
                                strdocelowy.setOdpisrok(strdocelowy.getOdpisrok() + strdocelowy.getListopad());
                                break;
                            case 12:
                                strdocelowy.setGrudzien(um.getKwota().doubleValue());
                                strdocelowy.setOdpisrok(strdocelowy.getOdpisrok() + strdocelowy.getGrudzien());
                                break;
                        }
                    } else if (um.getRokUmorzenia() < wpisView.getRokWpisu()) {
                        umorzenianarastajaco = umorzenianarastajaco.add(um.getKwota());
                    } else if (um.getRokUmorzenia() > wpisView.getRokWpisu()) {
                        break;
                    }
                }
                strdocelowy.setUmorzeniaDo(umorzenianarastajaco.add(new BigDecimal(str.getUmorzeniepoczatkowe())));
            } catch (Exception e) { E.e(e); 
                strdocelowy.setUmorzeniaDo(new BigDecimal(BigInteger.ZERO));
            }
            strdocelowy.setPozostaloDoUmorzenia(new BigDecimal(strdocelowy.getNetto()).subtract(strdocelowy.getUmorzeniaDo().add(new BigDecimal(strdocelowy.getOdpisrok()))));
            strtabela.add(strdocelowy);
            i++;
        }
        Collections.sort(strtabela, new SrodekTrwcomparatorData());
        STRtabela podsumowanie = new STRtabela();
        podsumowanie.setId(0);
        podsumowanie.setNazwa("");
        podsumowanie.setKst("");
        podsumowanie.setSymbol("");
        podsumowanie.setDatazak("");
        podsumowanie.setDataprzek("podsumowanie");
        podsumowanie.setDatawy("");
        podsumowanie.setPodatnik("");
        podsumowanie.setNetto(0.0);
        podsumowanie.setOdpisrok(0.0);
        podsumowanie.setUmorzeniaDo(BigDecimal.ZERO);
        podsumowanie.setStyczen(0.0);
        podsumowanie.setLuty(0.0);
        podsumowanie.setMarzec(0.0);
        podsumowanie.setKwiecien(0.0);
        podsumowanie.setMaj(0.0);
        podsumowanie.setCzerwiec(0.0);
        podsumowanie.setLipiec(0.0);
        podsumowanie.setSierpien(0.0);
        podsumowanie.setWrzesien(0.0);
        podsumowanie.setPazdziernik(0.0);
        podsumowanie.setListopad(0.0);
        podsumowanie.setGrudzien(0.0);
        for (STRtabela p : strtabela) {
            podsumowanie.setNetto(podsumowanie.getNetto() + p.getNetto());
            podsumowanie.setOdpisrok(podsumowanie.getOdpisrok() + p.getOdpisrok());
            podsumowanie.setUmorzeniaDo(podsumowanie.getUmorzeniaDo().add(p.getUmorzeniaDo()));
            podsumowanie.setStyczen(podsumowanie.getStyczen() + p.getStyczen());
            podsumowanie.setLuty(podsumowanie.getLuty() + p.getLuty());
            podsumowanie.setMarzec(podsumowanie.getMarzec() + p.getMarzec());
            podsumowanie.setKwiecien(podsumowanie.getKwiecien() + p.getKwiecien());
            podsumowanie.setMaj(podsumowanie.getMaj() + p.getMaj());
            podsumowanie.setCzerwiec(podsumowanie.getCzerwiec() + p.getCzerwiec());
            podsumowanie.setLipiec(podsumowanie.getLipiec() + p.getLipiec());
            podsumowanie.setSierpien(podsumowanie.getSierpien() + p.getSierpien());
            podsumowanie.setWrzesien(podsumowanie.getWrzesien() + p.getWrzesien());
            podsumowanie.setPazdziernik(podsumowanie.getPazdziernik() + p.getPazdziernik());
            podsumowanie.setListopad(podsumowanie.getListopad() + p.getListopad());
            podsumowanie.setGrudzien(podsumowanie.getGrudzien() + p.getGrudzien());

        }
        strtabela.add(podsumowanie);
    }

    public void mailewidencjaSTR() {
        try {
            MailOther.ewidencjaSTR(wpisView);
        } catch (Exception e) { E.e(e); 

        }
    }

    public void drukewidencjaSTR() {
        try {
            PdfSTR.drukuj(wpisView, strtabela);
        } catch (Exception e) { E.e(e); 

        }
    }

    public STRDAO getsTRDAO() {
        return sTRDAO;
    }

    public void setsTRDAO(STRDAO sTRDAO) {
        this.sTRDAO = sTRDAO;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<SrodekTrw> getListaSrodkiTrwale() {
        return listaSrodkiTrwale;
    }

    public void setListaSrodkiTrwale(List<SrodekTrw> listaSrodkiTrwale) {
        this.listaSrodkiTrwale = listaSrodkiTrwale;
    }

    public List<SrodekTrw> getListaWyposazenia() {
        return listaWyposazenia;
    }

    public void setListaWyposazenia(List<SrodekTrw> listaWyposazenia) {
        this.listaWyposazenia = listaWyposazenia;
    }

    public List<STRtabela> getStrtabela() {
        return strtabela;
    }

    public void setStrtabela(List<STRtabela> strtabela) {
        this.strtabela = strtabela;
    }

    public int getIloscsrodkow() {
        return iloscsrodkow;
    }

    public int getZakupionewbiezacyrok() {
        return zakupionewbiezacyrok;
    }

    public String getWest() {
        return west;
    }

    public void setWest(String west) {
        this.west = west;
    }
    
}
