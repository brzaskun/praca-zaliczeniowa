/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import dao.PitDAO;
import dao.PodStawkiDAO;
import dao.PodatnikDAO;
import dao.ZobowiazanieDAO;
import entity.Dok;
import entity.Pitpoz;
import entity.Podatnik;
import entity.Podstawki;
import entity.Zobowiazanie;
import entity.Zusstawki;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean(name="ZestawienieView")
@RequestScoped
public class ZestawienieView implements Serializable{
    @Inject
    private DokDAO dokDAO;
    @Inject
    private PitDAO pitDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    //bieżący pit
    private Pitpoz pitpoz;
    //sumowanie poprzednich pitów jeżeli są zachowane
    private Pitpoz narPitpoz;
    //lista pitow
    private List<Pitpoz> listapit;
    
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    
    List<Double> styczen;
    List<Double> luty;
    List<Double> marzec;
    List<Double> kwiecien;
    List<Double> maj;
    List<Double> czerwiec;
    List<Double> lipiec;
    List<Double> sierpien;
    List<Double> wrzesien;
    List<Double> pazdziernik;
    List<Double> listopad;
    List<Double> grudzien;
    
    List<Double> Ipolrocze;
    List<Double> IIpolrocze;
    List<Double> rok;
    
    private List<Dok> lista;
    private List<Pitpoz> pobierzPity;
    private List<List> zebranieMcy;
    @Inject
    private Pitpoz biezacyPit;
    @Inject
    private PodStawkiDAO podstawkiDAO;
    @Inject
    private ZobowiazanieDAO zobowiazanieDAO;

    public ZestawienieView() {
        styczen = Arrays.asList(new Double[7]);
        styczen = Arrays.asList(new Double[7]);
        luty = Arrays.asList(new Double[7]);
        marzec = Arrays.asList(new Double[7]);
        kwiecien = Arrays.asList(new Double[7]);
        maj = Arrays.asList(new Double[7]);
        czerwiec = Arrays.asList(new Double[7]);
        lipiec = Arrays.asList(new Double[7]);
        sierpien = Arrays.asList(new Double[7]);
        wrzesien = Arrays.asList(new Double[7]);
        pazdziernik = Arrays.asList(new Double[7]);
        listopad = Arrays.asList(new Double[7]);
        grudzien = Arrays.asList(new Double[7]);
        pobierzPity = new ArrayList<>();
        zebranieMcy = new ArrayList<>();
        listapit = new ArrayList<>();
     }
  
    
   
    
    @PostConstruct
    public void init() {
        if (wpisView.getPodatnikWpisu() != null) {
            Collection c = null;
            try {
                c = dokDAO.zwrocBiezacegoKlientaRok(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            if (c != null) {
                for(int i = 0; i<7;i++){
                    styczen.set(i, 0.0);
                    luty.set(i, 0.0);
                    marzec.set(i, 0.0);
                    kwiecien.set(i, 0.0);
                    maj.set(i, 0.0);
                    czerwiec.set(i, 0.0);
                    lipiec.set(i, 0.0);
                    sierpien.set(i, 0.0);
                    wrzesien.set(i, 0.0);
                    pazdziernik.set(i, 0.0);
                    listopad.set(i, 0.0);
                    grudzien.set(i, 0.0);
                }
                lista = new ArrayList<>();
                lista.addAll(c);
                Iterator it;
                it = lista.iterator();
                while (it.hasNext()) {
                    Dok tmp = (Dok) it.next();
                    String selekcja = tmp.getPkpirM();
                    String selekcja2 = tmp.getPkpirKol();
                    Double kwota = tmp.getKwota();
                    Double temp = 0.0;
                    switch (selekcja) {
                        case "01":
                            switch (selekcja2) {
                                case "przych. sprz":
                                    temp = styczen.get(0);
                                    temp = temp+kwota;
                                    styczen.set(0,temp);
                                    break;
                                case "pozost. przych.":
                                    temp = styczen.get(1);
                                    temp = temp+kwota;
                                    styczen.set(1,temp);
                                    break;
                                 case "zakup tow. i mat.":
                                    temp = styczen.get(2);
                                    temp = temp+kwota;
                                    styczen.set(2,temp);
                                    break;
                                 case "koszty ub.zak.":
                                    temp = styczen.get(3);
                                    temp = temp+kwota;
                                    styczen.set(3,temp);
                                    break;
                                 case "wynagrodzenia":
                                    temp = styczen.get(4);
                                    temp = temp+kwota;
                                    styczen.set(4,temp);
                                    break;
                                 case "poz. koszty":
                                    temp = styczen.get(5);
                                    temp = temp+kwota;
                                    styczen.set(5,temp);
                                    break;
                                  case "inwestycje":
                                    temp = styczen.get(6);
                                    temp = temp+kwota;
                                    styczen.set(6,temp);
                                    break;   
                            }
                           break;
                        case "02":
                             switch (selekcja2) {
                                case "przych. sprz":
                                    temp = luty.get(0);
                                    temp = temp+kwota;
                                    luty.set(0,temp);
                                    break;
                                case "pozost. przych.":
                                    temp = luty.get(1);
                                    temp = temp+kwota;
                                    luty.set(1,temp);
                                    break;
                                 case "zakup tow. i mat.":
                                    temp = luty.get(2);
                                    temp = temp+kwota;
                                    luty.set(2,temp);
                                    break;
                                 case "koszty ub.zak.":
                                    temp = luty.get(3);
                                    temp = temp+kwota;
                                    luty.set(3,temp);
                                    break;
                                 case "wynagrodzenia":
                                    temp = luty.get(4);
                                    temp = temp+kwota;
                                    luty.set(4,temp);
                                    break;
                                 case "poz. koszty":
                                    temp = luty.get(5);
                                    temp = temp+kwota;
                                    luty.set(5,temp);
                                    break;
                                  case "inwestycje":
                                    temp = luty.get(6);
                                    temp = temp+kwota;
                                    luty.set(6,temp);
                                    break;   
                            }
                           break;
                        case "03":
                            switch (selekcja2) {
                                case "przych. sprz":
                                    temp = marzec.get(0);
                                    temp = temp+kwota;
                                    marzec.set(0,temp);
                                    break;
                                case "pozost. przych.":
                                    temp = marzec.get(1);
                                    temp = temp+kwota;
                                    marzec.set(1,temp);
                                    break;
                                 case "zakup tow. i mat.":
                                    temp = marzec.get(2);
                                    temp = temp+kwota;
                                    marzec.set(2,temp);
                                    break;
                                 case "koszty ub.zak.":
                                    temp = marzec.get(3);
                                    temp = temp+kwota;
                                    marzec.set(3,temp);
                                    break;
                                 case "wynagrodzenia":
                                    temp = marzec.get(4);
                                    temp = temp+kwota;
                                    marzec.set(4,temp);
                                    break;
                                 case "poz. koszty":
                                    temp = marzec.get(5);
                                    temp = temp+kwota;
                                    marzec.set(5,temp);
                                    break;
                                  case "inwestycje":
                                    temp = marzec.get(6);
                                    temp = temp+kwota;
                                    marzec.set(6,temp);
                                    break;   
                            }
                           break;
                        case "04":
                            switch (selekcja2) {
                                case "przych. sprz":
                                    temp = kwiecien.get(0);
                                    temp = temp+kwota;
                                    kwiecien.set(0,temp);
                                    break;
                                case "pozost. przych.":
                                    temp = kwiecien.get(1);
                                    temp = temp+kwota;
                                    kwiecien.set(1,temp);
                                    break;
                                 case "zakup tow. i mat.":
                                    temp = kwiecien.get(2);
                                    temp = temp+kwota;
                                    kwiecien.set(2,temp);
                                    break;
                                 case "koszty ub.zak.":
                                    temp = kwiecien.get(3);
                                    temp = temp+kwota;
                                    kwiecien.set(3,temp);
                                    break;
                                 case "wynagrodzenia":
                                    temp = kwiecien.get(4);
                                    temp = temp+kwota;
                                    kwiecien.set(4,temp);
                                    break;
                                 case "poz. koszty":
                                    temp = kwiecien.get(5);
                                    temp = temp+kwota;
                                    kwiecien.set(5,temp);
                                    break;
                                  case "inwestycje":
                                    temp = kwiecien.get(6);
                                    temp = temp+kwota;
                                    kwiecien.set(6,temp);
                                    break;   
                            }
                           break;
                        case "05":
                             switch (selekcja2) {
                                case "przych. sprz":
                                    temp = maj.get(0);
                                    temp = temp+kwota;
                                    maj.set(0,temp);
                                    break;
                                case "pozost. przych.":
                                    temp = maj.get(1);
                                    temp = temp+kwota;
                                    maj.set(1,temp);
                                    break;
                                 case "zakup tow. i mat.":
                                    temp = maj.get(2);
                                    temp = temp+kwota;
                                    maj.set(2,temp);
                                    break;
                                 case "koszty ub.zak.":
                                    temp = maj.get(3);
                                    temp = temp+kwota;
                                    maj.set(3,temp);
                                    break;
                                 case "wynagrodzenia":
                                    temp = maj.get(4);
                                    temp = temp+kwota;
                                    maj.set(4,temp);
                                    break;
                                 case "poz. koszty":
                                    temp = maj.get(5);
                                    temp = temp+kwota;
                                    maj.set(5,temp);
                                    break;
                                  case "inwestycje":
                                    temp = maj.get(6);
                                    temp = temp+kwota;
                                    maj.set(6,temp);
                                    break;   
                            }
                           break;
                        case "06":
                            switch (selekcja2) {
                                case "przych. sprz":
                                    temp = czerwiec.get(0);
                                    temp = temp+kwota;
                                    czerwiec.set(0,temp);
                                    break;
                                case "pozost. przych.":
                                    temp = czerwiec.get(1);
                                    temp = temp+kwota;
                                    czerwiec.set(1,temp);
                                    break;
                                 case "zakup tow. i mat.":
                                    temp = czerwiec.get(2);
                                    temp = temp+kwota;
                                    czerwiec.set(2,temp);
                                    break;
                                 case "koszty ub.zak.":
                                    temp = czerwiec.get(3);
                                    temp = temp+kwota;
                                    czerwiec.set(3,temp);
                                    break;
                                 case "wynagrodzenia":
                                    temp = czerwiec.get(4);
                                    temp = temp+kwota;
                                    czerwiec.set(4,temp);
                                    break;
                                 case "poz. koszty":
                                    temp = czerwiec.get(5);
                                    temp = temp+kwota;
                                    czerwiec.set(5,temp);
                                    break;
                                  case "inwestycje":
                                    temp = czerwiec.get(6);
                                    temp = temp+kwota;
                                    czerwiec.set(6,temp);
                                    break;   
                            }
                           break;
                        case "07":
                             switch (selekcja2) {
                                case "przych. sprz":
                                    temp = lipiec.get(0);
                                    temp = temp+kwota;
                                    lipiec.set(0,temp);
                                    break;
                                case "pozost. przych.":
                                    temp = lipiec.get(1);
                                    temp = temp+kwota;
                                    lipiec.set(1,temp);
                                    break;
                                 case "zakup tow. i mat.":
                                    temp = lipiec.get(2);
                                    temp = temp+kwota;
                                    lipiec.set(2,temp);
                                    break;
                                 case "koszty ub.zak.":
                                    temp = lipiec.get(3);
                                    temp = temp+kwota;
                                    lipiec.set(3,temp);
                                    break;
                                 case "wynagrodzenia":
                                    temp = lipiec.get(4);
                                    temp = temp+kwota;
                                    lipiec.set(4,temp);
                                    break;
                                 case "poz. koszty":
                                    temp = lipiec.get(5);
                                    temp = temp+kwota;
                                    lipiec.set(5,temp);
                                    break;
                                  case "inwestycje":
                                    temp = lipiec.get(6);
                                    temp = temp+kwota;
                                    lipiec.set(6,temp);
                                    break;   
                            }
                           break;
                        case "08":
                            switch (selekcja2) {
                                case "przych. sprz":
                                    temp = sierpien.get(0);
                                    temp = temp+kwota;
                                    sierpien.set(0,temp);
                                    break;
                                case "pozost. przych.":
                                    temp = sierpien.get(1);
                                    temp = temp+kwota;
                                    sierpien.set(1,temp);
                                    break;
                                 case "zakup tow. i mat.":
                                    temp = sierpien.get(2);
                                    temp = temp+kwota;
                                    sierpien.set(2,temp);
                                    break;
                                 case "koszty ub.zak.":
                                    temp = sierpien.get(3);
                                    temp = temp+kwota;
                                    sierpien.set(3,temp);
                                    break;
                                 case "wynagrodzenia":
                                    temp = sierpien.get(4);
                                    temp = temp+kwota;
                                    sierpien.set(4,temp);
                                    break;
                                 case "poz. koszty":
                                    temp = sierpien.get(5);
                                    temp = temp+kwota;
                                    sierpien.set(5,temp);
                                    break;
                                  case "inwestycje":
                                    temp = sierpien.get(6);
                                    temp = temp+kwota;
                                    sierpien.set(6,temp);
                                    break;   
                            }
                           break;
                        case "09":
                             switch (selekcja2) {
                                case "przych. sprz":
                                    temp = wrzesien.get(0);
                                    temp = temp+kwota;
                                    wrzesien.set(0,temp);
                                    break;
                                case "pozost. przych.":
                                    temp = wrzesien.get(1);
                                    temp = temp+kwota;
                                    wrzesien.set(1,temp);
                                    break;
                                 case "zakup tow. i mat.":
                                    temp = wrzesien.get(2);
                                    temp = temp+kwota;
                                    wrzesien.set(2,temp);
                                    break;
                                 case "koszty ub.zak.":
                                    temp = wrzesien.get(3);
                                    temp = temp+kwota;
                                    wrzesien.set(3,temp);
                                    break;
                                 case "wynagrodzenia":
                                    temp = wrzesien.get(4);
                                    temp = temp+kwota;
                                    wrzesien.set(4,temp);
                                    break;
                                 case "poz. koszty":
                                    temp = wrzesien.get(5);
                                    temp = temp+kwota;
                                    wrzesien.set(5,temp);
                                    break;
                                  case "inwestycje":
                                    temp = wrzesien.get(6);
                                    temp = temp+kwota;
                                    wrzesien.set(6,temp);
                                    break;   
                            }
                           break;
                        case "10":
                            switch (selekcja2) {
                                case "przych. sprz":
                                    temp = pazdziernik.get(0);
                                    temp = temp+kwota;
                                    pazdziernik.set(0,temp);
                                    break;
                                case "pozost. przych.":
                                    temp = pazdziernik.get(1);
                                    temp = temp+kwota;
                                    pazdziernik.set(1,temp);
                                    break;
                                 case "zakup tow. i mat.":
                                    temp = pazdziernik.get(2);
                                    temp = temp+kwota;
                                    pazdziernik.set(2,temp);
                                    break;
                                 case "koszty ub.zak.":
                                    temp = pazdziernik.get(3);
                                    temp = temp+kwota;
                                    pazdziernik.set(3,temp);
                                    break;
                                 case "wynagrodzenia":
                                    temp = pazdziernik.get(4);
                                    temp = temp+kwota;
                                    pazdziernik.set(4,temp);
                                    break;
                                 case "poz. koszty":
                                    temp = pazdziernik.get(5);
                                    temp = temp+kwota;
                                    pazdziernik.set(5,temp);
                                    break;
                                  case "inwestycje":
                                    temp = pazdziernik.get(6);
                                    temp = temp+kwota;
                                    pazdziernik.set(6,temp);
                                    break;   
                            }
                           break;
                        case "11":
                            switch (selekcja2) {
                                case "przych. sprz":
                                    temp = listopad.get(0);
                                    temp = temp+kwota;
                                    listopad.set(0,temp);
                                    break;
                                case "pozost. przych.":
                                    temp = listopad.get(1);
                                    temp = temp+kwota;
                                    listopad.set(1,temp);
                                    break;
                                 case "zakup tow. i mat.":
                                    temp = listopad.get(2);
                                    temp = temp+kwota;
                                    listopad.set(2,temp);
                                    break;
                                 case "koszty ub.zak.":
                                    temp = listopad.get(3);
                                    temp = temp+kwota;
                                    listopad.set(3,temp);
                                    break;
                                 case "wynagrodzenia":
                                    temp = listopad.get(4);
                                    temp = temp+kwota;
                                    listopad.set(4,temp);
                                    break;
                                 case "poz. koszty":
                                    temp = listopad.get(5);
                                    temp = temp+kwota;
                                    listopad.set(5,temp);
                                    break;
                                  case "inwestycje":
                                    temp = listopad.get(6);
                                    temp = temp+kwota;
                                    listopad.set(6,temp);
                                    break;   
                            }
                           break;
                        case "12":
                             switch (selekcja2) {
                                case "przych. sprz":
                                    temp = grudzien.get(0);
                                    temp = temp+kwota;
                                    grudzien.set(0,temp);
                                    break;
                                case "pozost. przych.":
                                    temp = grudzien.get(1);
                                    temp = temp+kwota;
                                    grudzien.set(1,temp);
                                    break;
                                 case "zakup tow. i mat.":
                                    temp = grudzien.get(2);
                                    temp = temp+kwota;
                                    grudzien.set(2,temp);
                                    break;
                                 case "koszty ub.zak.":
                                    temp = grudzien.get(3);
                                    temp = temp+kwota;
                                    grudzien.set(3,temp);
                                    break;
                                 case "wynagrodzenia":
                                    temp = grudzien.get(4);
                                    temp = temp+kwota;
                                    grudzien.set(4,temp);
                                    break;
                                 case "poz. koszty":
                                    temp = grudzien.get(5);
                                    temp = temp+kwota;
                                    grudzien.set(5,temp);
                                    break;
                                  case "inwestycje":
                                    temp = grudzien.get(6);
                                    temp = temp+kwota;
                                    grudzien.set(6,temp);
                                    break;   
                            }
                           break;
                    }
                    
                    if (tmp.getKwotaX() != 0) {
                    String selekcja2X = tmp.getPkpirKolX();
                    Double kwotaX = tmp.getKwotaX();
                    Double tempX = 0.0;
                    switch (selekcja) {
                        case "01":
                            switch (selekcja2X) {
                                case "przych. sprz":
                                    tempX = styczen.get(0);
                                    tempX = tempX+kwotaX;
                                    styczen.set(0,tempX);
                                    break;
                                case "pozost. przych.":
                                    tempX = styczen.get(1);
                                    tempX = tempX+kwotaX;
                                    styczen.set(1,tempX);
                                    break;
                                 case "zakup tow. i mat.":
                                    tempX = styczen.get(2);
                                    tempX = tempX+kwotaX;
                                    styczen.set(2,tempX);
                                    break;
                                 case "koszty ub.zak.":
                                    tempX = styczen.get(3);
                                    tempX = tempX+kwotaX;
                                    styczen.set(3,tempX);
                                    break;
                                 case "wynagrodzenia":
                                    tempX = styczen.get(4);
                                    tempX = tempX+kwotaX;
                                    styczen.set(4,tempX);
                                    break;
                                 case "poz. koszty":
                                    tempX = styczen.get(5);
                                    tempX = tempX+kwotaX;
                                    styczen.set(5,tempX);
                                    break;
                                  case "inwestycje":
                                    tempX = styczen.get(6);
                                    tempX = tempX+kwotaX;
                                    styczen.set(6,tempX);
                                    break;   
                            }
                           break;
                        case "02":
                             switch (selekcja2X) {
                                case "przych. sprz":
                                    tempX = luty.get(0);
                                    tempX = tempX+kwotaX;
                                    luty.set(0,tempX);
                                    break;
                                case "pozost. przych.":
                                    tempX = luty.get(1);
                                    tempX = tempX+kwotaX;
                                    luty.set(1,tempX);
                                    break;
                                 case "zakup tow. i mat.":
                                    tempX = luty.get(2);
                                    tempX = tempX+kwotaX;
                                    luty.set(2,tempX);
                                    break;
                                 case "koszty ub.zak.":
                                    tempX = luty.get(3);
                                    tempX = tempX+kwotaX;
                                    luty.set(3,tempX);
                                    break;
                                 case "wynagrodzenia":
                                    tempX = luty.get(4);
                                    tempX = tempX+kwotaX;
                                    luty.set(4,tempX);
                                    break;
                                 case "poz. koszty":
                                    tempX = luty.get(5);
                                    tempX = tempX+kwotaX;
                                    luty.set(5,tempX);
                                    break;
                                  case "inwestycje":
                                    tempX = luty.get(6);
                                    tempX = tempX+kwotaX;
                                    luty.set(6,tempX);
                                    break;   
                            }
                           break;
                        case "03":
                            switch (selekcja2X) {
                                case "przych. sprz":
                                    tempX = marzec.get(0);
                                    tempX = tempX+kwotaX;
                                    marzec.set(0,tempX);
                                    break;
                                case "pozost. przych.":
                                    tempX = marzec.get(1);
                                    tempX = tempX+kwotaX;
                                    marzec.set(1,tempX);
                                    break;
                                 case "zakup tow. i mat.":
                                    tempX = marzec.get(2);
                                    tempX = tempX+kwotaX;
                                    marzec.set(2,tempX);
                                    break;
                                 case "koszty ub.zak.":
                                    tempX = marzec.get(3);
                                    tempX = tempX+kwotaX;
                                    marzec.set(3,tempX);
                                    break;
                                 case "wynagrodzenia":
                                    tempX = marzec.get(4);
                                    tempX = tempX+kwotaX;
                                    marzec.set(4,tempX);
                                    break;
                                 case "poz. koszty":
                                    tempX = marzec.get(5);
                                    tempX = tempX+kwotaX;
                                    marzec.set(5,tempX);
                                    break;
                                  case "inwestycje":
                                    tempX = marzec.get(6);
                                    tempX = tempX+kwotaX;
                                    marzec.set(6,tempX);
                                    break;   
                            }
                           break;
                        case "04":
                            switch (selekcja2X) {
                                case "przych. sprz":
                                    tempX = kwiecien.get(0);
                                    tempX = tempX+kwotaX;
                                    kwiecien.set(0,tempX);
                                    break;
                                case "pozost. przych.":
                                    tempX = kwiecien.get(1);
                                    tempX = tempX+kwotaX;
                                    kwiecien.set(1,tempX);
                                    break;
                                 case "zakup tow. i mat.":
                                    tempX = kwiecien.get(2);
                                    tempX = tempX+kwotaX;
                                    kwiecien.set(2,tempX);
                                    break;
                                 case "koszty ub.zak.":
                                    tempX = kwiecien.get(3);
                                    tempX = tempX+kwotaX;
                                    kwiecien.set(3,tempX);
                                    break;
                                 case "wynagrodzenia":
                                    tempX = kwiecien.get(4);
                                    tempX = tempX+kwotaX;
                                    kwiecien.set(4,tempX);
                                    break;
                                 case "poz. koszty":
                                    tempX = kwiecien.get(5);
                                    tempX = tempX+kwotaX;
                                    kwiecien.set(5,tempX);
                                    break;
                                  case "inwestycje":
                                    tempX = kwiecien.get(6);
                                    tempX = tempX+kwotaX;
                                    kwiecien.set(6,tempX);
                                    break;   
                            }
                           break;
                        case "05":
                             switch (selekcja2X) {
                                case "przych. sprz":
                                    tempX = maj.get(0);
                                    tempX = tempX+kwotaX;
                                    maj.set(0,tempX);
                                    break;
                                case "pozost. przych.":
                                    tempX = maj.get(1);
                                    tempX = tempX+kwotaX;
                                    maj.set(1,tempX);
                                    break;
                                 case "zakup tow. i mat.":
                                    tempX = maj.get(2);
                                    tempX = tempX+kwotaX;
                                    maj.set(2,tempX);
                                    break;
                                 case "koszty ub.zak.":
                                    tempX = maj.get(3);
                                    tempX = tempX+kwotaX;
                                    maj.set(3,tempX);
                                    break;
                                 case "wynagrodzenia":
                                    tempX = maj.get(4);
                                    tempX = tempX+kwotaX;
                                    maj.set(4,tempX);
                                    break;
                                 case "poz. koszty":
                                    tempX = maj.get(5);
                                    tempX = tempX+kwotaX;
                                    maj.set(5,tempX);
                                    break;
                                  case "inwestycje":
                                    tempX = maj.get(6);
                                    tempX = tempX+kwotaX;
                                    maj.set(6,tempX);
                                    break;   
                            }
                           break;
                        case "06":
                            switch (selekcja2X) {
                                case "przych. sprz":
                                    tempX = czerwiec.get(0);
                                    tempX = tempX+kwotaX;
                                    czerwiec.set(0,tempX);
                                    break;
                                case "pozost. przych.":
                                    tempX = czerwiec.get(1);
                                    tempX = tempX+kwotaX;
                                    czerwiec.set(1,tempX);
                                    break;
                                 case "zakup tow. i mat.":
                                    tempX = czerwiec.get(2);
                                    tempX = tempX+kwotaX;
                                    czerwiec.set(2,tempX);
                                    break;
                                 case "koszty ub.zak.":
                                    tempX = czerwiec.get(3);
                                    tempX = tempX+kwotaX;
                                    czerwiec.set(3,tempX);
                                    break;
                                 case "wynagrodzenia":
                                    tempX = czerwiec.get(4);
                                    tempX = tempX+kwotaX;
                                    czerwiec.set(4,tempX);
                                    break;
                                 case "poz. koszty":
                                    tempX = czerwiec.get(5);
                                    tempX = tempX+kwotaX;
                                    czerwiec.set(5,tempX);
                                    break;
                                  case "inwestycje":
                                    tempX = czerwiec.get(6);
                                    tempX = tempX+kwotaX;
                                    czerwiec.set(6,tempX);
                                    break;   
                            }
                           break;
                        case "07":
                             switch (selekcja2X) {
                                case "przych. sprz":
                                    tempX = lipiec.get(0);
                                    tempX = tempX+kwotaX;
                                    lipiec.set(0,tempX);
                                    break;
                                case "pozost. przych.":
                                    tempX = lipiec.get(1);
                                    tempX = tempX+kwotaX;
                                    lipiec.set(1,tempX);
                                    break;
                                 case "zakup tow. i mat.":
                                    tempX = lipiec.get(2);
                                    tempX = tempX+kwotaX;
                                    lipiec.set(2,tempX);
                                    break;
                                 case "koszty ub.zak.":
                                    tempX = lipiec.get(3);
                                    tempX = tempX+kwotaX;
                                    lipiec.set(3,tempX);
                                    break;
                                 case "wynagrodzenia":
                                    tempX = lipiec.get(4);
                                    tempX = tempX+kwotaX;
                                    lipiec.set(4,tempX);
                                    break;
                                 case "poz. koszty":
                                    tempX = lipiec.get(5);
                                    tempX = tempX+kwotaX;
                                    lipiec.set(5,tempX);
                                    break;
                                  case "inwestycje":
                                    tempX = lipiec.get(6);
                                    tempX = tempX+kwotaX;
                                    lipiec.set(6,tempX);
                                    break;   
                            }
                           break;
                        case "08":
                            switch (selekcja2X) {
                                case "przych. sprz":
                                    tempX = sierpien.get(0);
                                    tempX = tempX+kwotaX;
                                    sierpien.set(0,tempX);
                                    break;
                                case "pozost. przych.":
                                    tempX = sierpien.get(1);
                                    tempX = tempX+kwotaX;
                                    sierpien.set(1,tempX);
                                    break;
                                 case "zakup tow. i mat.":
                                    tempX = sierpien.get(2);
                                    tempX = tempX+kwotaX;
                                    sierpien.set(2,tempX);
                                    break;
                                 case "koszty ub.zak.":
                                    tempX = sierpien.get(3);
                                    tempX = tempX+kwotaX;
                                    sierpien.set(3,tempX);
                                    break;
                                 case "wynagrodzenia":
                                    tempX = sierpien.get(4);
                                    tempX = tempX+kwotaX;
                                    sierpien.set(4,tempX);
                                    break;
                                 case "poz. koszty":
                                    tempX = sierpien.get(5);
                                    tempX = tempX+kwotaX;
                                    sierpien.set(5,tempX);
                                    break;
                                  case "inwestycje":
                                    tempX = sierpien.get(6);
                                    tempX = tempX+kwotaX;
                                    sierpien.set(6,tempX);
                                    break;   
                            }
                           break;
                        case "09":
                             switch (selekcja2X) {
                                case "przych. sprz":
                                    tempX = wrzesien.get(0);
                                    tempX = tempX+kwotaX;
                                    wrzesien.set(0,tempX);
                                    break;
                                case "pozost. przych.":
                                    tempX = wrzesien.get(1);
                                    tempX = tempX+kwotaX;
                                    wrzesien.set(1,tempX);
                                    break;
                                 case "zakup tow. i mat.":
                                    tempX = wrzesien.get(2);
                                    tempX = tempX+kwotaX;
                                    wrzesien.set(2,tempX);
                                    break;
                                 case "koszty ub.zak.":
                                    tempX = wrzesien.get(3);
                                    tempX = tempX+kwotaX;
                                    wrzesien.set(3,tempX);
                                    break;
                                 case "wynagrodzenia":
                                    tempX = wrzesien.get(4);
                                    tempX = tempX+kwotaX;
                                    wrzesien.set(4,tempX);
                                    break;
                                 case "poz. koszty":
                                    tempX = wrzesien.get(5);
                                    tempX = tempX+kwotaX;
                                    wrzesien.set(5,tempX);
                                    break;
                                  case "inwestycje":
                                    tempX = wrzesien.get(6);
                                    tempX = tempX+kwotaX;
                                    wrzesien.set(6,tempX);
                                    break;   
                            }
                           break;
                        case "10":
                            switch (selekcja2X) {
                                case "przych. sprz":
                                    tempX = pazdziernik.get(0);
                                    tempX = tempX+kwotaX;
                                    pazdziernik.set(0,tempX);
                                    break;
                                case "pozost. przych.":
                                    tempX = pazdziernik.get(1);
                                    tempX = tempX+kwotaX;
                                    pazdziernik.set(1,tempX);
                                    break;
                                 case "zakup tow. i mat.":
                                    tempX = pazdziernik.get(2);
                                    tempX = tempX+kwotaX;
                                    pazdziernik.set(2,tempX);
                                    break;
                                 case "koszty ub.zak.":
                                    tempX = pazdziernik.get(3);
                                    tempX = tempX+kwotaX;
                                    pazdziernik.set(3,tempX);
                                    break;
                                 case "wynagrodzenia":
                                    tempX = pazdziernik.get(4);
                                    tempX = tempX+kwotaX;
                                    pazdziernik.set(4,tempX);
                                    break;
                                 case "poz. koszty":
                                    tempX = pazdziernik.get(5);
                                    tempX = tempX+kwotaX;
                                    pazdziernik.set(5,tempX);
                                    break;
                                  case "inwestycje":
                                    tempX = pazdziernik.get(6);
                                    tempX = tempX+kwotaX;
                                    pazdziernik.set(6,tempX);
                                    break;   
                            }
                           break;
                        case "11":
                            switch (selekcja2X) {
                                case "przych. sprz":
                                    tempX = listopad.get(0);
                                    tempX = tempX+kwotaX;
                                    listopad.set(0,tempX);
                                    break;
                                case "pozost. przych.":
                                    tempX = listopad.get(1);
                                    tempX = tempX+kwotaX;
                                    listopad.set(1,tempX);
                                    break;
                                 case "zakup tow. i mat.":
                                    tempX = listopad.get(2);
                                    tempX = tempX+kwotaX;
                                    listopad.set(2,tempX);
                                    break;
                                 case "koszty ub.zak.":
                                    tempX = listopad.get(3);
                                    tempX = tempX+kwotaX;
                                    listopad.set(3,tempX);
                                    break;
                                 case "wynagrodzenia":
                                    tempX = listopad.get(4);
                                    tempX = tempX+kwotaX;
                                    listopad.set(4,tempX);
                                    break;
                                 case "poz. koszty":
                                    tempX = listopad.get(5);
                                    tempX = tempX+kwotaX;
                                    listopad.set(5,tempX);
                                    break;
                                  case "inwestycje":
                                    tempX = listopad.get(6);
                                    tempX = tempX+kwotaX;
                                    listopad.set(6,tempX);
                                    break;   
                            }
                           break;
                        case "12":
                             switch (selekcja2X) {
                                case "przych. sprz":
                                    tempX = grudzien.get(0);
                                    tempX = tempX+kwotaX;
                                    grudzien.set(0,tempX);
                                    break;
                                case "pozost. przych.":
                                    tempX = grudzien.get(1);
                                    tempX = tempX+kwotaX;
                                    grudzien.set(1,tempX);
                                    break;
                                 case "zakup tow. i mat.":
                                    tempX = grudzien.get(2);
                                    tempX = tempX+kwotaX;
                                    grudzien.set(2,tempX);
                                    break;
                                 case "koszty ub.zak.":
                                    tempX = grudzien.get(3);
                                    tempX = tempX+kwotaX;
                                    grudzien.set(3,tempX);
                                    break;
                                 case "wynagrodzenia":
                                    tempX = grudzien.get(4);
                                    tempX = tempX+kwotaX;
                                    grudzien.set(4,tempX);
                                    break;
                                 case "poz. koszty":
                                    tempX = grudzien.get(5);
                                    tempX = tempX+kwotaX;
                                    grudzien.set(5,tempX);
                                    break;
                                  case "inwestycje":
                                    tempX = grudzien.get(6);
                                    tempX = tempX+kwotaX;
                                    grudzien.set(6,tempX);
                                    break;   
                            }
                           break;
                    }
                    }
                }
            }
            //pobierzPity();
            zebranieMcy.add(styczen);
            zebranieMcy.add(luty);
            zebranieMcy.add(marzec);
            zebranieMcy.add(kwiecien);
            zebranieMcy.add(maj);
            zebranieMcy.add(czerwiec);
            zebranieMcy.add(lipiec);
            zebranieMcy.add(sierpien);
            zebranieMcy.add(wrzesien);
            zebranieMcy.add(pazdziernik);
            zebranieMcy.add(listopad);
            zebranieMcy.add(grudzien);
        }
        
    Ipolrocze = new ArrayList<>();   
    IIpolrocze = new ArrayList<>();
    rok = new ArrayList<>();
    
    for(int i = 0;i<7;i++){
        Ipolrocze.add(styczen.get(i)+luty.get(i)+marzec.get(i)+kwiecien.get(i)+maj.get(i)+czerwiec.get(i));
        IIpolrocze.add(lipiec.get(i)+sierpien.get(i)+wrzesien.get(i)+pazdziernik.get(i)+listopad.get(i)+grudzien.get(i));
        rok.add(Ipolrocze.get(i)+IIpolrocze.get(i));
    }
    }
    
    //oblicze pit i wkleja go do biezacego Pitu w celu wyswietlenia, nie zapisuje
    public void obliczPit(){
        try{
        biezacyPit = new Pitpoz();
        biezacyPit.setPodatnik(wpisView.getPodatnikWpisu());
        biezacyPit.setPkpirR(wpisView.getRokWpisu().toString());
        biezacyPit.setPkpirM(wpisView.getMiesiacWpisu());
        biezacyPit.setPrzychody(obliczprzychod());
        biezacyPit.setKoszty(obliczkoszt());
        biezacyPit.setWynik(biezacyPit.getPrzychody().subtract(biezacyPit.getKoszty()));
        biezacyPit.setStrata(BigDecimal.ZERO);
        String poszukiwany = wpisView.getPodatnikWpisu();
        Podatnik selected=podatnikDAO.find(poszukiwany);
        Iterator it;
        it = selected.getZusparametr().iterator();
        while(it.hasNext()){
            Zusstawki tmpX = (Zusstawki) it.next();
             if(tmpX.getZusstawkiPK().getRok().equals(wpisView.getRokWpisu().toString())
                    &&tmpX.getZusstawkiPK().getMiesiac().equals(wpisView.getMiesiacWpisu())){
                if(tmpX.getZus51ch()!=null){
                biezacyPit.setZus51(BigDecimal.valueOf(tmpX.getZus51ch()));
                } else {
                biezacyPit.setZus51(BigDecimal.valueOf(tmpX.getZus51bch()));
                }
                biezacyPit.setZus52(BigDecimal.valueOf(tmpX.getZus52()));
                break;
             }
        }
        
        Pitpoz sumapoprzednichmcy = skumulujpity(biezacyPit.getPkpirM());
        biezacyPit.setZus51(biezacyPit.getZus51().add(sumapoprzednichmcy.getZus51()));
        BigDecimal tmp = biezacyPit.getWynik().subtract(biezacyPit.getZus51());
        tmp = tmp.setScale(0, RoundingMode.HALF_EVEN);
        
        //wyliczenie podatku poczatek
        biezacyPit.setPodstawa(tmp);
        int index = selected.getPodatekdochodowy().size()-1;
        String opodatkowanie = selected.getPodatekdochodowy().get(index).getParametr();
        String rodzajop = opodatkowanie;
        Double stawka = 0.0;
        BigDecimal podatek = BigDecimal.ZERO;
        BigDecimal dochód = biezacyPit.getPodstawa();
        BigDecimal przychody = biezacyPit.getPrzychody();
        Podstawki tmpY;
        tmpY = podstawkiDAO.find(Integer.parseInt(biezacyPit.getPkpirR()));
        switch (rodzajop){
             case "zasady ogólne" :
                stawka = tmpY.getStawka1();
                podatek = (dochód.multiply(BigDecimal.valueOf(stawka)));
                podatek = podatek.subtract(BigDecimal.valueOf(tmpY.getKwotawolna()));
                        podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                break;
            case "podatek liniowy" :
                stawka = tmpY.getStawkaliniowy();
                podatek = (dochód.multiply(BigDecimal.valueOf(stawka)));
                        podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                break;
            case "ryczałt" :
                stawka = tmpY.getStawkaryczalt1();
                podatek = (przychody.multiply(BigDecimal.valueOf(stawka)));
                        podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                break;
        }
        biezacyPit.setPodatek(podatek);
        biezacyPit.setZus52(biezacyPit.getZus52().add(sumapoprzednichmcy.getZus52()));
        BigDecimal tmpX = podatek.subtract(biezacyPit.getZus52());
        tmpX = tmpX.setScale(0, RoundingMode.HALF_EVEN);
        if(tmpX.signum()==-1){
            biezacyPit.setPododpoczrok(BigDecimal.ZERO);
        } else {
        biezacyPit.setPododpoczrok(tmpX);
        }
        //wyliczenie podatku koniec
        
        biezacyPit.setNalzalodpoczrok(sumapoprzednichmcy.getNalzalodpoczrok());
        biezacyPit.setNaleznazal(biezacyPit.getPododpoczrok().subtract(biezacyPit.getNalzalodpoczrok()));
        if(biezacyPit.getNaleznazal().compareTo(BigDecimal.ZERO)==1){
        biezacyPit.setDozaplaty(biezacyPit.getNaleznazal());
        } else {
            biezacyPit.setDozaplaty(BigDecimal.ZERO);
        }
        } catch (Exception e){
           FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Brak wprowadzonych paramterów!! Nie można przeliczyć PIT za: ", biezacyPit.getPkpirM());
           FacesContext.getCurrentInstance().addMessage(null, msg);  
           biezacyPit = new Pitpoz();
        }
        try {
        Zobowiazanie data = zobowiazanieDAO.find(biezacyPit.getPkpirR(), biezacyPit.getPkpirM());
        biezacyPit.setTerminwplaty(data.getZobowiazaniePK().getRok()+"-"+data.getZobowiazaniePK().getMc()+"-"+data.getPitday());
        }  catch (Exception e){
           FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Brak wprowadzonych dat zobowiazan!! Nie można przeliczyć PIT za: ", biezacyPit.getPkpirM());
           FacesContext.getCurrentInstance().addMessage(null, msg);  
            biezacyPit = new Pitpoz();
            RequestContext.getCurrentInstance().update("formpit:");
        }
        
        
    }
    
    public void zachowajPit() {
         Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        if(biezacyPit.getWynik()!=null){
        try {
            Pitpoz find = pitDAO.find(biezacyPit.getPkpirR(), biezacyPit.getPkpirM(), biezacyPit.getPodatnik());
            pitDAO.destroy(find);
            pitDAO.dodaj(biezacyPit);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edytowano PIT za m-c:", biezacyPit.getPkpirM());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            pitDAO.dodaj(biezacyPit);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Zachowano PIT za m-c:", biezacyPit.getPkpirM());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    } else {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Brak danych do PIT za m-c:", biezacyPit.getPkpirM());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public Pitpoz skumulujpity(String mcDo){
        Pitpoz tmp = new Pitpoz();
        tmp.setZus51(BigDecimal.ZERO);
        tmp.setZus52(BigDecimal.ZERO);
        tmp.setNalzalodpoczrok(BigDecimal.ZERO);
        try{
        Collection c = pitDAO.getDownloaded();
        Iterator it;
        it = c.iterator();
        
        while(it.hasNext()){
            Pitpoz tmpX = (Pitpoz) it.next();
            if(!tmpX.getPkpirM().equals(mcDo)){
            tmp.setZus51(tmp.getZus51().add(tmpX.getZus51()));
            tmp.setZus52(tmp.getZus52().add(tmpX.getZus52()));
            tmp.setNalzalodpoczrok(tmp.getNalzalodpoczrok().add(tmpX.getNaleznazal()));
            }
        }
        
            return tmp;
        } catch (Exception e){
           
            return tmp;
        }
        
    }
    
     public void usun(){
        listapit.addAll(pitDAO.getDownloaded());
        int index = listapit.size()-1;
        Pitpoz selected = listapit.get(index);
        pitDAO.destroy(selected);
        listapit.remove(index);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usunieto parametr ZUS do podatnika za m-c:", selected.getPkpirM());
         FacesContext.getCurrentInstance().addMessage(":formzus:msgzus" , msg);
      
     }
    
    private BigDecimal obliczprzychod() {
        BigDecimal suma = new BigDecimal(0);
        String selekcja = wpisView.getMiesiacWpisu();
        switch(selekcja){
            case "01":
                for(int i = 0;i<1;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "02":
                for(int i = 0;i<2;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "03":
                for(int i = 0;i<3;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "04":
                for(int i = 0;i<4;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "05":
                for(int i = 0;i<5;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "06":
                for(int i = 0;i<6;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "07":
                for(int i = 0;i<7;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "08":
                for(int i = 0;i<8;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "09":
                for(int i = 0;i<9;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "10":
                for(int i = 0;i<10;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "11":
                for(int i = 0;i<11;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "12":
                for(int i = 0;i<12;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
        }
        return suma;
    }
    
    private BigDecimal obliczkoszt() {
        BigDecimal suma = new BigDecimal(0);
        String selekcja = wpisView.getMiesiacWpisu();
        switch(selekcja){
            case "01":
                for(int i = 0;i<1;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "02":
                for(int i = 0;i<2;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "03":
                for(int i = 0;i<3;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "04":
                for(int i = 0;i<4;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "05":
                for(int i = 0;i<5;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "06":
                for(int i = 0;i<6;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "07":
                for(int i = 0;i<7;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "08":
                for(int i = 0;i<8;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "09":
                for(int i = 0;i<9;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "10":
                for(int i = 0;i<10;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "11":
                for(int i = 0;i<11;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "12":
                for(int i = 0;i<12;i++){
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
        }
        return suma;
    }
    
    public void pobierzPity(){
        try{
        pobierzPity.addAll(pitDAO.getDownloaded());
        } catch (Exception e){}
        narPitpoz = new Pitpoz();
        int index=0;
        Iterator it;
        it = pobierzPity.iterator();
        while(it.hasNext()){
            Pitpoz tmpX = (Pitpoz) it.next();
            if(tmpX.getPkpirR().equals(wpisView.getRokWpisu().toString())
                    &&tmpX.getPkpirM().equals(wpisView.getMiesiacWpisu())){
                index = tmpX.getId()-1;
                break;
            }
        }
        //narPitpoz = pobierzPity.get(Mce.getMapamcyX().get(wpisView.getMiesiacWpisu()));
        narPitpoz = pobierzPity.get(index);
        biezacyPit = narPitpoz;
    }
    
    public DokDAO getDokDAO() {
        return dokDAO;
    }

    public void setDokDAO(DokDAO dokDAO) {
        this.dokDAO = dokDAO;
    }

    public PitDAO getPitDAO() {
        return pitDAO;
    }

    public void setPitDAO(PitDAO pitDAO) {
        this.pitDAO = pitDAO;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public List<Dok> getLista() {
        return lista;
    }

    public void setLista(List<Dok> lista) {
        this.lista = lista;
    }

    
    public List<Double> getStyczen() {
        return styczen;
    }

    public void setStyczen(List<Double> styczen) {
        this.styczen = styczen;
    }

    public List<Double> getLuty() {
        return luty;
    }

    public void setLuty(List<Double> luty) {
        this.luty = luty;
    }

    public List<Double> getMarzec() {
        return marzec;
    }

    public void setMarzec(List<Double> marzec) {
        this.marzec = marzec;
    }

    public List<Double> getKwiecien() {
        return kwiecien;
    }

    public void setKwiecien(List<Double> kwiecien) {
        this.kwiecien = kwiecien;
    }

    public List<Double> getMaj() {
        return maj;
    }

    public void setMaj(List<Double> maj) {
        this.maj = maj;
    }

    public List<Double> getCzerwiec() {
        return czerwiec;
    }

    public void setCzerwiec(List<Double> czerwiec) {
        this.czerwiec = czerwiec;
    }

    public List<Double> getLipiec() {
        return lipiec;
    }

    public void setLipiec(List<Double> lipiec) {
        this.lipiec = lipiec;
    }

    public List<Double> getSierpien() {
        return sierpien;
    }

    public void setSierpien(List<Double> sierpien) {
        this.sierpien = sierpien;
    }

    public List<Double> getWrzesien() {
        return wrzesien;
    }

    public void setWrzesien(List<Double> wrzesien) {
        this.wrzesien = wrzesien;
    }

    public List<Double> getPazdziernik() {
        return pazdziernik;
    }

    public void setPazdziernik(List<Double> pazdziernik) {
        this.pazdziernik = pazdziernik;
    }

    public List<Double> getListopad() {
        return listopad;
    }

    public void setListopad(List<Double> listopad) {
        this.listopad = listopad;
    }

    public List<Double> getGrudzien() {
        return grudzien;
    }

    public void setGrudzien(List<Double> grudzien) {
        this.grudzien = grudzien;
    }

    public List<Double> getIpolrocze() {
        return Ipolrocze;
    }

    public void setIpolrocze(List<Double> Ipolrocze) {
        this.Ipolrocze = Ipolrocze;
    }

    public List<Double> getIIpolrocze() {
        return IIpolrocze;
    }

    public void setIIpolrocze(List<Double> IIpolrocze) {
        this.IIpolrocze = IIpolrocze;
    }

    public List<Double> getRok() {
        return rok;
    }

    public void setRok(List<Double> rok) {
        this.rok = rok;
    }

    public Pitpoz getPitpoz() {
        return pitpoz;
    }

    public void setPitpoz(Pitpoz pitpoz) {
        this.pitpoz = pitpoz;
    }

    public Pitpoz getNarPitpoz() {
        return narPitpoz;
    }

    public void setNarPitpoz(Pitpoz narPitpoz) {
        this.narPitpoz = narPitpoz;
    }

    public List<Pitpoz> getPobierzPity() {
        return pobierzPity;
    }

    public void setPobierzPity(List<Pitpoz> pobierzPity) {
        this.pobierzPity = pobierzPity;
    }

    public List<List> getZebranieMcy() {
        return zebranieMcy;
    }

    public void setZebranieMcy(List<List> zebranieMcy) {
        this.zebranieMcy = zebranieMcy;
    }

    public Pitpoz getBiezacyPit() {
        return biezacyPit;
    }

    public void setBiezacyPit(Pitpoz biezacyPit) {
        this.biezacyPit = biezacyPit;
    }

    public PodatnikDAO getPodatnikDAO() {
        return podatnikDAO;
    }

    public void setPodatnikDAO(PodatnikDAO podatnikDAO) {
        this.podatnikDAO = podatnikDAO;
    }

    public PodStawkiDAO getPodstawkiDAO() {
        return podstawkiDAO;
    }

    public void setPodstawkiDAO(PodStawkiDAO podstawkiDAO) {
        this.podstawkiDAO = podstawkiDAO;
    }

    public ZobowiazanieDAO getZobowiazanieDAO() {
        return zobowiazanieDAO;
    }

    public void setZobowiazanieDAO(ZobowiazanieDAO zobowiazanieDAO) {
        this.zobowiazanieDAO = zobowiazanieDAO;
    }

  
    
    
}
