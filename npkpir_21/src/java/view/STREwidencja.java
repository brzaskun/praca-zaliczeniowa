/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.STRDAO;
import embeddable.STRtabela;
import embeddable.Umorzenie;
import entity.SrodekTrw;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean(name="STREwidencja")
@RequestScoped
public class STREwidencja implements Serializable{
    @Inject
    private STRDAO sTRDAO;
      
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    
    //tablica obiektów
    private List<SrodekTrw> obiektDOKjsf;
    //tablica obiektw danego klienta
    private List<SrodekTrw> obiektDOKjsfSel;
    //wyposazenie
    private List<SrodekTrw> obiektDOKmrjsfSelWyposazenie;
    //srodki trwale wykaz rok biezacy
    private List<STRtabela> strtabela;
    /**
     * Dane informacyjne gora strony srodkitablica.xhtml
     */
    private int iloscsrodkow;
    private int zakupionewbiezacyrok;
    
   
    
    public STREwidencja() {
        obiektDOKjsf = new ArrayList<>();
        obiektDOKjsfSel = new ArrayList<>();
        obiektDOKmrjsfSelWyposazenie = new ArrayList<>();
        strtabela = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        String rokdzisiejszy = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        zakupionewbiezacyrok = 0;
        if (wpisView.getPodatnikWpisu() != null) {
            List<SrodekTrw> c = new ArrayList<>();
            try {
                c = sTRDAO.findStrPod(wpisView.getPodatnikWpisu());
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            if (!c.isEmpty()) {
                int i = 1;
                int j = 1;
                for (SrodekTrw tmp : c) {
                    obiektDOKjsf.add(tmp);
                    if (tmp.getPodatnik().equals(wpisView.getPodatnikWpisu())) {
                        if (tmp.getTyp().equals("wyposazenie")) {
                            tmp.setNrsrodka(i++);
                            obiektDOKmrjsfSelWyposazenie.add(tmp);

                        } else {
                            tmp.setNrsrodka(j++);
                            if(tmp.getDatazak().substring(0, 4).equals(rokdzisiejszy)){
                                zakupionewbiezacyrok++;
                            }
                            obiektDOKjsfSel.add(tmp);
                        }
                    }
                }
                iloscsrodkow = obiektDOKjsfSel.size();
            }
        }
         List<SrodekTrw> lista = new ArrayList<>();
        lista.addAll(obiektDOKjsfSel);
        int i=1;
        for (SrodekTrw str : lista){
            STRtabela strdocelowy = new STRtabela();
            strdocelowy.setId(i);
            strdocelowy.setNazwa(str.getNazwa());
            strdocelowy.setKst(str.getKst());
            strdocelowy.setOdpisrok(str.getOdpisrok());
            strdocelowy.setSymbol(str.getSymbol());
            strdocelowy.setDatazak(str.getDatazak());
            strdocelowy.setDataprzek(str.getDataprzek());
            strdocelowy.setDatawy(str.getDatawy());
            strdocelowy.setNetto(str.getNetto());
            strdocelowy.setPodatnik(str.getPodatnik());
            List<Double> miesiace = new ArrayList<>();
            Iterator itX;
            itX = str.getUmorzWyk().iterator();
            BigDecimal umnar = new BigDecimal(0);
            while (itX.hasNext()) {
                Umorzenie um = (Umorzenie) itX.next();
                if (um.getRokUmorzenia().equals(wpisView.getRokWpisu())) {
                    Integer mc = um.getMcUmorzenia();
                    switch (mc) {
                        case 1:
                            strdocelowy.setStyczen(um.getKwota().doubleValue());
                            break;
                        case 2:
                            strdocelowy.setLuty(um.getKwota().doubleValue());
                            break;
                        case 3:
                            strdocelowy.setMarzec(um.getKwota().doubleValue());
                            break;
                        case 4:
                            strdocelowy.setKwiecien(um.getKwota().doubleValue());
                            break;
                        case 5:
                            strdocelowy.setMaj(um.getKwota().doubleValue());
                            break;
                        case 6:
                            strdocelowy.setCzerwiec(um.getKwota().doubleValue());
                            break;
                        case 7:
                            strdocelowy.setLipiec(um.getKwota().doubleValue());
                            break;
                        case 8:
                            strdocelowy.setSierpien(um.getKwota().doubleValue());
                            break;
                        case 9:
                            strdocelowy.setWrzesien(um.getKwota().doubleValue());
                            break;
                        case 10:
                            strdocelowy.setPazdziernik(um.getKwota().doubleValue());
                            break;
                        case 11:
                            strdocelowy.setListopad(um.getKwota().doubleValue());
                            break;
                        case 12:
                            strdocelowy.setGrudzien(um.getKwota().doubleValue());
                            break;
                    }
                } else if (um.getRokUmorzenia()<wpisView.getRokWpisu()) {
                    umnar = umnar.add(um.getKwota());
                }
            }
            try{
            strdocelowy.setUmorzeniaDo(umnar.add(new BigDecimal(str.getUmorzeniepoczatkowe())));
            } catch (Exception e){
                strdocelowy.setUmorzeniaDo(new BigDecimal(BigInteger.ZERO) );
            }
            strtabela.add(strdocelowy);            
            i++;
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

   public List<SrodekTrw> getObiektDOKjsf() {
        return obiektDOKjsf;
    }

    public void setObiektDOKjsf(List<SrodekTrw> obiektDOKjsf) {
        this.obiektDOKjsf = obiektDOKjsf;
    }

    public List<SrodekTrw> getObiektDOKjsfSel() {
        return obiektDOKjsfSel;
    }

    public void setObiektDOKjsfSel(List<SrodekTrw> obiektDOKjsfSel) {
        this.obiektDOKjsfSel = obiektDOKjsfSel;
    }

    public List<SrodekTrw> getObiektDOKmrjsfSelWyposazenie() {
        return obiektDOKmrjsfSelWyposazenie;
    }

    public void setObiektDOKmrjsfSelWyposazenie(List<SrodekTrw> obiektDOKmrjsfSelWyposazenie) {
        this.obiektDOKmrjsfSelWyposazenie = obiektDOKmrjsfSelWyposazenie;
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
   
}
