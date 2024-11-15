/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import dao.DokDAO;
import dao.DokDAOfk;
import dao.PodatnikDAO;
import dao.UzDAO;
import embeddable.PodatnikRecord;
import entity.Dok;
import entity.Podatnik;
import entity.Uz;
import entityfk.Dokfk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class AdminKsiegowanieView implements Serializable {

    private static final long serialVersionUID = 1L;
    private String rok;
    private String mc;
    private List<Uz> listaksiegowychwybor;
    private List<Podatnik> listapodatnikow;
    private List<PodatnikRecord> zestawienierekordow;
    private Uz wybranaksiegowa;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private PodatnikDAO podatnikDAO;

    @PostConstruct
    public void init() {
        listaksiegowychwybor = uzDAO.findByUprawnienia("Bookkeeper");
        listaksiegowychwybor.addAll(uzDAO.findByUprawnienia("BookkeeperFK"));
        listapodatnikow = podatnikDAO.findAktywny();
    }

    public void pobierz() {
        if (wybranaksiegowa != null) {
            List<Dok> listaDok = dokDAO.findDokRokMC(rok, mc);
            List<Dokfk> listaDokfk = dokDAOfk.findDokRokMC(rok, mc);
            List<Podatnik> listapodatnikowfiltered = listapodatnikow.stream()
                    .filter(podatnik -> podatnik.getKsiegowa() != null && podatnik.getKsiegowa().equals(wybranaksiegowa))
                    .collect(Collectors.toList());
            List<PodatnikRecord> zestawienierekordow = new ArrayList<>();

// Step 1: Group documents by Podatnik
            Map<Podatnik, List<Dok>> dokumentyByPodatnik = listaDok.stream()
                    .collect(Collectors.groupingBy(Dok::getPodatnik));

// Step 2: For each Podatnik, create a PodatnikRecord and populate day fields
            int numerid = 1;
            for (Map.Entry<Podatnik, List<Dok>> entry : dokumentyByPodatnik.entrySet()) {
                Podatnik podatnik = entry.getKey();
                List<Dok> dokumentyPodatnika = entry.getValue();

                // Create a new PodatnikRecord for this Podatnik, set zamkniecie as required
                PodatnikRecord recorda = new PodatnikRecord();
                recorda.setPodatnik(podatnik);
                recorda.setId(numerid++); // Assuming Podatnik has an `id` field

                // Step 3: Count documents for each day and populate the day fields
                for (Dok dok : dokumentyPodatnika) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dok.getDataK());
                    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                    // Increment the correct day field in PodatnikRecord
                    switch (dayOfMonth) {
                        case 1:
                            recorda.setDay1(recorda.getDay1() + 1);
                            break;
                        case 2:
                            recorda.setDay2(recorda.getDay2() + 1);
                            break;
                        case 3:
                            recorda.setDay3(recorda.getDay3() + 1);
                            break;
                        case 4:
                            recorda.setDay4(recorda.getDay4() + 1);
                            break;
                        case 5:
                            recorda.setDay5(recorda.getDay5() + 1);
                            break;
                        case 6:
                            recorda.setDay6(recorda.getDay6() + 1);
                            break;
                        case 7:
                            recorda.setDay7(recorda.getDay7() + 1);
                            break;
                        case 8:
                            recorda.setDay8(recorda.getDay8() + 1);
                            break;
                        case 9:
                            recorda.setDay9(recorda.getDay9() + 1);
                            break;
                        case 10:
                            recorda.setDay10(recorda.getDay10() + 1);
                            break;
                        case 11:
                            recorda.setDay11(recorda.getDay11() + 1);
                            break;
                        case 12:
                            recorda.setDay12(recorda.getDay12() + 1);
                            break;
                        case 13:
                            recorda.setDay13(recorda.getDay13() + 1);
                            break;
                        case 14:
                            recorda.setDay14(recorda.getDay14() + 1);
                            break;
                        case 15:
                            recorda.setDay15(recorda.getDay15() + 1);
                            break;
                        case 16:
                            recorda.setDay16(recorda.getDay16() + 1);
                            break;
                        case 17:
                            recorda.setDay17(recorda.getDay17() + 1);
                            break;
                        case 18:
                            recorda.setDay18(recorda.getDay18() + 1);
                            break;
                        case 19:
                            recorda.setDay19(recorda.getDay19() + 1);
                            break;
                        case 20:
                            recorda.setDay20(recorda.getDay20() + 1);
                            break;
                        case 21:
                            recorda.setDay21(recorda.getDay21() + 1);
                            break;
                        case 22:
                            recorda.setDay22(recorda.getDay22() + 1);
                            break;
                        case 23:
                            recorda.setDay23(recorda.getDay23() + 1);
                            break;
                        case 24:
                            recorda.setDay24(recorda.getDay24() + 1);
                            break;
                        case 25:
                            recorda.setDay25(recorda.getDay25() + 1);
                            break;
                        case 26:
                            recorda.setDay26(recorda.getDay26() + 1);
                            break;
                        case 27:
                            recorda.setDay27(recorda.getDay27() + 1);
                            break;
                        case 28:
                            recorda.setDay28(recorda.getDay28() + 1);
                            break;
                        case 29:
                            recorda.setDay29(recorda.getDay29() + 1);
                            break;
                        case 30:
                            recorda.setDay30(recorda.getDay30() + 1);
                            break;
                        case 31:
                            recorda.setDay31(recorda.getDay31() + 1);
                            break;
                    }
                }
                // Step 4: Add the populated PodatnikRecord to the list
                zestawienierekordow.add(recorda);
                Msg.msg("Pobrano dane");
            }
        } else {
            Msg.msg("Nie wybrano księgowej");
        }
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public List<Uz> getListaksiegowychwybor() {
        return listaksiegowychwybor;
    }

    public void setListaksiegowychwybor(List<Uz> listaksiegowychwybor) {
        this.listaksiegowychwybor = listaksiegowychwybor;
    }

    public Uz getWybranaksiegowa() {
        return wybranaksiegowa;
    }

    public void setWybranaksiegowa(Uz wybranaksiegowa) {
        this.wybranaksiegowa = wybranaksiegowa;
    }

    public List<PodatnikRecord> getZestawienierekordow() {
        return zestawienierekordow;
    }

    public void setZestawienierekordow(List<PodatnikRecord> zestawienierekordow) {
        this.zestawienierekordow = zestawienierekordow;
    }

}

