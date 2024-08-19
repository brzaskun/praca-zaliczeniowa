/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FirmaKadryFacade;
import dao.PasekwynagrodzenFacade;
import dao.RodzajlistyplacFacade;
import dao.RozwiazanieumowyFacade;
import dao.UmowaFacade;
import dao.UzFacade;
import embeddable.Mce;
import entity.FirmaKadry;
import entity.Pasekwynagrodzen;
import entity.Rodzajlistyplac;
import entity.Rozwiazanieumowy;
import entity.Umowa;
import entity.Uz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
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
    private RodzajlistyplacFacade rodzajlistyplacFacade;
    @Inject
    private RozwiazanieumowyFacade rozwiazanieumowyFacade;
    private List<Uz> uzytkownicyPaski;
    private List<FirmaKadry> klienciPaski;
    private List<Uz> uzytkownicyUmowy;
    private List<Rodzajlistyplac> listarodzajlistyplac;
    private Rodzajlistyplac rodzajlistyplac;
    private List<Pasekwynagrodzen> paski;
    
    @PostConstruct
    private void init() {
        String rok = wpisView.getRokWpisu();
        paski = pasekwynagrodzenFacade.findByRok(rok);
        uzytkownicyPaski = uzFacade.findByUprawnienia("Administrator");
        listarodzajlistyplac = rodzajlistyplacFacade.findAktywne();
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
        przeliczliczbapaskow(paski, rodzajlistyplac);
    }
    
    public void przelicz() {
        List<Pasekwynagrodzen> paskisave = new ArrayList<>(paski);
        przeliczliczbapaskow(paskisave, rodzajlistyplac);
    }
    
    private void przeliczliczbapaskow (List<Pasekwynagrodzen> paski, Rodzajlistyplac rodzajlistyplac) {
        klienciPaski.parallelStream().forEach(u->{
            int suma = 0;
            double sumakoszt = 0.0;
            for (String mc : Mce.getMceListS()) {
                try {
                    List<Pasekwynagrodzen> paskimc = paski.stream().filter(p->p.getMc().equals(mc)&&(p.getAngaz().getFirma().equals(u))).collect(Collectors.toList());
                    if (rodzajlistyplac!=null) {
                        Predicate<Pasekwynagrodzen> isQualified = item -> !item.getDefinicjalistaplac().getRodzajlistyplac().equals(rodzajlistyplac);
                        paskimc.removeIf(isQualified);
                    }
                    int paskimcilosc = paskimc.size();
                    double paskimckosztpracodawcy = obliczkoszt(paskimc);
                    switch (mc){
                        case "01":
                            u.setM1(paskimcilosc);
                            suma = suma + paskimcilosc;
                            u.setK1(paskimckosztpracodawcy);
                            sumakoszt= sumakoszt + paskimckosztpracodawcy;
                            break;
                        case "02":
                            u.setM2(paskimcilosc);
                            suma = suma + paskimcilosc;
                            u.setK2(paskimckosztpracodawcy);
                            sumakoszt= sumakoszt + paskimckosztpracodawcy;
                            break;
                        case "03":
                            u.setM3(paskimcilosc);
                            suma = suma + paskimcilosc;
                            u.setK3(paskimckosztpracodawcy);
                            sumakoszt= sumakoszt + paskimckosztpracodawcy;
                            break;
                        case "04":
                            u.setM4(paskimcilosc);
                            suma = suma + paskimcilosc;
                            u.setK4(paskimckosztpracodawcy);
                            sumakoszt= sumakoszt + paskimckosztpracodawcy;
                            break;
                        case "05":
                            u.setM5(paskimcilosc);
                            suma = suma + paskimcilosc;
                            u.setK5(paskimckosztpracodawcy);
                            sumakoszt= sumakoszt + paskimckosztpracodawcy;
                            break;
                         case "06":
                            u.setM6(paskimcilosc);
                            suma = suma + paskimcilosc;
                            u.setK6(paskimckosztpracodawcy);
                            sumakoszt= sumakoszt + paskimckosztpracodawcy;
                            break;
                         case "07":
                            u.setM7(paskimcilosc);
                            suma = suma + paskimcilosc;
                            u.setK7(paskimckosztpracodawcy);
                            sumakoszt= sumakoszt + paskimckosztpracodawcy;
                            break;
                         case "08":
                            u.setM8(paskimcilosc);
                            suma = suma + paskimcilosc;
                            u.setK8(paskimckosztpracodawcy);
                            sumakoszt= sumakoszt + paskimckosztpracodawcy;
                            break;
                         case "09":
                            u.setM9(paskimcilosc);
                            suma = suma + paskimcilosc;
                            u.setK9(paskimckosztpracodawcy);
                            sumakoszt= sumakoszt + paskimckosztpracodawcy;
                            break;
                         case "10":
                            u.setM10(paskimcilosc);
                            suma = suma + paskimcilosc;
                            u.setK10(paskimckosztpracodawcy);
                            sumakoszt= sumakoszt + paskimckosztpracodawcy;
                            break;
                         case "11":
                            u.setM11(paskimcilosc);
                            suma = suma + paskimcilosc;
                            u.setK11(paskimckosztpracodawcy);
                            sumakoszt= sumakoszt + paskimckosztpracodawcy;
                            break;
                        case "12":
                            u.setM12(paskimcilosc);
                            suma = suma + paskimcilosc;
                            u.setK12(paskimckosztpracodawcy);
                            sumakoszt= sumakoszt + paskimckosztpracodawcy;
                            break;
                    }
                } catch (Exception e) {}
            }
            u.setM13(suma);
            u.setK13(sumakoszt);
        });
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

    public List<Rodzajlistyplac> getListarodzajlistyplac() {
        return listarodzajlistyplac;
    }

    public void setListarodzajlistyplac(List<Rodzajlistyplac> listarodzajlistyplac) {
        this.listarodzajlistyplac = listarodzajlistyplac;
    }

    public Rodzajlistyplac getRodzajlistyplac() {
        return rodzajlistyplac;
    }

    public void setRodzajlistyplac(Rodzajlistyplac rodzajlistyplac) {
        this.rodzajlistyplac = rodzajlistyplac;
    }

    private double obliczkoszt(List<Pasekwynagrodzen> paskimc) {
        double suma = 0.0;
        if (paskimc!=null) {
            for (Pasekwynagrodzen p: paskimc) {
                suma  = suma +p.getKosztpracodawcy();
            }
        }
        return suma;
    }
    
    
    
    
    
}
