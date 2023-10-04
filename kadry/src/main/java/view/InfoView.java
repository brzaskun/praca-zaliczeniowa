/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FirmaKadryFacade;
import dao.PasekwynagrodzenFacade;
import dao.RozwiazanieumowyFacade;
import dao.UmowaFacade;
import dao.UzFacade;
import embeddable.Mce;
import entity.FirmaKadry;
import entity.Pasekwynagrodzen;
import entity.Rozwiazanieumowy;
import entity.Umowa;
import entity.Uz;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class InfoView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private UzFacade uzFacade;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private FirmaKadryFacade firmaKadryFacade;
    @Inject
    private RozwiazanieumowyFacade rozwiazanieumowyFacade;
    private List<Uz> uzytkownicyPaski;
    private List<FirmaKadry> klienciPaski;
    private List<Uz> uzytkownicyUmowy;
    
    @PostConstruct
    private void init() {
        String rok = wpisView.getRokWpisu();
        List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByRok(rok);
        uzytkownicyPaski = uzFacade.findByUprawnienia("Administrator");
        for (Uz u : uzytkownicyPaski) {
            int suma = 0;
            for (String mc : Mce.getMceListS()) {
                try {
                    List<Pasekwynagrodzen> paskimc = paski.stream().filter(p->p.getMc().equals(mc)&&(p.getSporzadzil().equals(u.getLogin())||p.getSporzadzil().equals(u.getImieNazwisko()))).collect(Collectors.toList());
                    int paskimcilosc = paskimc.size();
                    switch (mc){
                        case "01":
                            u.setM1(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                        case "02":
                            u.setM2(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                        case "03":
                            u.setM3(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                        case "04":
                            u.setM4(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                        case "05":
                            u.setM5(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                         case "06":
                            u.setM6(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                         case "07":
                            u.setM7(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                         case "08":
                            u.setM8(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                         case "09":
                            u.setM9(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                         case "10":
                            u.setM10(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                         case "11":
                            u.setM11(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                        case "12":
                            u.setM12(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                    }
                } catch (Exception e) {}
            }
            u.setM13(suma);
        }
        List<Umowa> umowy = umowaFacade.findAll();
        List<Rozwiazanieumowy> rozwiazania = rozwiazanieumowyFacade.findAll();
        uzytkownicyUmowy = uzFacade.findByUprawnienia("Administrator");
        for (Uz u : uzytkownicyUmowy) {
            int suma = 0;
            for (String mc : Mce.getMceListS()) {
                List<Umowa> umowymc = umowy.stream().filter(p->p.getMcSt().equals(mc)&&p.getRokSt().equals(rok)&&(p.getUtworzyl().equals(u.getLogin())||p.getUtworzyl().equals(u.getImieNazwisko()))).collect(Collectors.toList());
                List<Rozwiazanieumowy> rozwiazaniamc = rozwiazania.stream().filter(p->p.getMcSt().equals(mc)&&p.getRokSt().equals(rok)&&(p.getUtworzyl().equals(u.getLogin())||p.getUtworzyl().equals(u.getImieNazwisko()))).collect(Collectors.toList());
                int umowymcilosc = umowymc.size();
                int rozwiazaniamcilosc = rozwiazaniamc.size();
                switch (mc){
                    case "01":
                        u.setM1(umowymcilosc);
                        suma = suma + umowymcilosc + rozwiazaniamcilosc;
                        break;
                    case "02":
                        u.setM2(umowymcilosc);
                        suma = suma + umowymcilosc + rozwiazaniamcilosc;
                        break;
                    case "03":
                        u.setM3(umowymcilosc);
                        suma = suma + umowymcilosc + rozwiazaniamcilosc;
                        break;
                    case "04":
                        u.setM4(umowymcilosc);
                        suma = suma + umowymcilosc + rozwiazaniamcilosc;
                        break;
                    case "05":
                        u.setM5(umowymcilosc);
                        suma = suma + umowymcilosc + rozwiazaniamcilosc;
                        break;
                     case "06":
                        u.setM6(umowymcilosc);
                        suma = suma + umowymcilosc + rozwiazaniamcilosc;
                        break;
                     case "07":
                        u.setM7(umowymcilosc);
                        suma = suma + umowymcilosc + rozwiazaniamcilosc;
                        break;
                     case "08":
                        u.setM8(umowymcilosc);
                        suma = suma + umowymcilosc + rozwiazaniamcilosc;
                        break;
                     case "09":
                        u.setM9(umowymcilosc);
                        suma = suma + umowymcilosc + rozwiazaniamcilosc;
                        break;
                     case "10":
                        u.setM10(umowymcilosc);
                        suma = suma + umowymcilosc + rozwiazaniamcilosc;
                        break;
                     case "11":
                        u.setM11(umowymcilosc);
                        suma = suma + umowymcilosc + rozwiazaniamcilosc;
                        break;
                    case "12":
                        u.setM12(umowymcilosc);
                        suma = suma + umowymcilosc + rozwiazaniamcilosc;
                        break;
                }
            }
            u.setM13(suma);
        }
        klienciPaski = firmaKadryFacade.findAll();
        klienciPaski.parallelStream().forEach(u->{
            int suma = 0;
            for (String mc : Mce.getMceListS()) {
                try {
                    List<Pasekwynagrodzen> paskimc = paski.stream().filter(p->p.getMc().equals(mc)&&(p.getAngaz().getFirma().equals(u))).collect(Collectors.toList());
                    int paskimcilosc = paskimc.size();
                    switch (mc){
                        case "01":
                            u.setM1(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                        case "02":
                            u.setM2(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                        case "03":
                            u.setM3(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                        case "04":
                            u.setM4(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                        case "05":
                            u.setM5(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                         case "06":
                            u.setM6(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                         case "07":
                            u.setM7(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                         case "08":
                            u.setM8(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                         case "09":
                            u.setM9(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                         case "10":
                            u.setM10(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                         case "11":
                            u.setM11(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                        case "12":
                            u.setM12(paskimcilosc);
                            suma = suma + paskimcilosc;
                            break;
                    }
                } catch (Exception e) {}
            }
            u.setM13(suma);
        });
        System.out.println("");
    }

    public List<Uz> getUzytkownicyPaski() {
        return uzytkownicyPaski;
    }

    public void setUzytkownicyPaski(List<Uz> uzytkownicyPaski) {
        this.uzytkownicyPaski = uzytkownicyPaski;
    }

    public List<Uz> getUzytkownicyUmowy() {
        return uzytkownicyUmowy;
    }

    public void setUzytkownicyUmowy(List<Uz> uzytkownicyUmowy) {
        this.uzytkownicyUmowy = uzytkownicyUmowy;
    }

    public List<FirmaKadry> getKlienciPaski() {
        return klienciPaski;
    }

    public void setKlienciPaski(List<FirmaKadry> klienciPaski) {
        this.klienciPaski = klienciPaski;
    }
    
    
    
    
}
