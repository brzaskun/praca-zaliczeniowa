/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.SrodekTrwcomparatorData;
import dao.STRDAO;
import embeddable.Mce;
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
import javax.faces.bean.ViewScoped;
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
@ViewScoped
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
    private List<SrodekTrw> listaWnip;
    //srodki trwale wykaz rok biezacy
    private List<STRtabela> strtabela;
    private List<STRtabela> wniptabela;
    /**
     * Dane informacyjne gora strony srodkitablica.xhtml
     */
    private int iloscsrodkow;
    private int zakupionewbiezacyrok;
    private String west;

    public STREwidencja() {
        listaSrodkiTrwale = new ArrayList<>();
        listaWyposazenia = new ArrayList<>();
        listaWnip = new ArrayList<>();
        strtabela = new ArrayList<>();
        wniptabela = new ArrayList<>();
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

                    } else if (przegladanySrodek.getTyp() != null && przegladanySrodek.getTyp().equals("wnip")) {
                        przegladanySrodek.setNrsrodka(i++);
                        listaWnip.add(przegladanySrodek);

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
        stworzpozycjeSrodka(lista, rokdzisiejszyI, strtabela);
        podsumowanieewidencji(strtabela);
        lista = new ArrayList<>();
        lista.addAll(listaWnip);
        stworzpozycjeSrodka(lista, rokdzisiejszyI, wniptabela);
        podsumowanieewidencji(wniptabela);
    }

    
    private void stworzpozycjeSrodka(List<SrodekTrw> lista, int rokdzisiejszyI, List<STRtabela> tabela) {
        int i = 1;
        for (SrodekTrw str : lista) {
            STRtabela strdocelowy = new STRtabela(i, str);
            try {
                Iterator<Umorzenie> itX = str.getUmorzWyk().iterator();
                BigDecimal umorzenianarastajaco = new BigDecimal(0);
                while (itX.hasNext()) {
                    Umorzenie um = itX.next();
                    if (um.getRokUmorzenia().equals(rokdzisiejszyI)) {
                        String mc = Mce.getNumberToMiesiac().get(um.getMcUmorzenia());
                        strdocelowy.getM().put(mc, um.getKwota().doubleValue());
                        strdocelowy.setOdpisrok(strdocelowy.getOdpisrok() + strdocelowy.getM().get(mc));
                    } else if (um.getRokUmorzenia() < wpisView.getRokWpisu()) {
                        umorzenianarastajaco = umorzenianarastajaco.add(um.getKwota());
                    } else if (um.getRokUmorzenia() > wpisView.getRokWpisu()) {
                        break;
                    }
                }
                strdocelowy.setUmorzeniaDo(umorzenianarastajaco.add(new BigDecimal(str.getUmorzeniepoczatkowe())));
            } catch (Exception e) { 
                E.e(e); 
                strdocelowy.setUmorzeniaDo(new BigDecimal(BigInteger.ZERO));
            }
            strdocelowy.setPozostaloDoUmorzenia(new BigDecimal(strdocelowy.getNetto()).subtract(strdocelowy.getUmorzeniaDo().add(new BigDecimal(strdocelowy.getOdpisrok()))));
            tabela.add(strdocelowy);
            i++;
        }
        Collections.sort(tabela, new SrodekTrwcomparatorData());
    }
    
    private void podsumowanieewidencji(List<STRtabela> tabela) {
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
        for (STRtabela p : tabela) {
            podsumowanie.setNetto(podsumowanie.getNetto() + p.getNetto());
            podsumowanie.setOdpisrok(podsumowanie.getOdpisrok() + p.getOdpisrok());
            podsumowanie.setUmorzeniaDo(podsumowanie.getUmorzeniaDo().add(p.getUmorzeniaDo()));
            for (String mc : Mce.getMceListS()) {
                podsumowanie.getM().put(mc, podsumowanie.getM().get(mc) + p.getM().get(mc));
            }
        }
        tabela.add(podsumowanie);
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
    
    public void drukewidencjaWnip() {
        try {
            PdfSTR.drukuj(wpisView, wniptabela);
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

    public List<STRtabela> getWniptabela() {
        return wniptabela;
    }

    public void setWniptabela(List<STRtabela> wniptabela) {
        this.wniptabela = wniptabela;
    }
    
}
