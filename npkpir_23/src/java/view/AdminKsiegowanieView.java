/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import beansDok.DayUpdater;
import comparator.PodatnikRecordcomparator;
import comparator.UzNazwiskocomparator;
import dao.DokDAO;
import dao.DokDAOfk;
import dao.PitDAO;
import dao.PodatnikDAO;
import dao.RyczDAO;
import dao.UPODAO;
import dao.UzDAO;
import dao.WynikFKRokMcDAO;
import embeddable.PodatnikRecord;
import entity.Dok;
import entity.Pitpoz;
import entity.Podatnik;
import entity.Ryczpoz;
import entity.UPO;
import entity.Uz;
import entityfk.Dokfk;
import entityfk.WynikFKRokMc;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private List<UPO> listajpkupo;
    private List<Podatnik> listapodatnikow;
    private List<PodatnikRecord> zestawienierekordow;
    private List<PodatnikRecord> summarizedRecords;
    private PodatnikRecord selectedRecord;
    private Uz wybranaksiegowa;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private PitDAO pitpozDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private RyczDAO ryczpozDAO;
    @Inject
    private WynikFKRokMcDAO wynikFKRokMcDAO;
    @Inject
    private UPODAO uPODAO;

    @PostConstruct
    public void init() {
        listaksiegowychwybor = uzDAO.findByUprawnieniaAktywny("Bookkeeper");
        listaksiegowychwybor.addAll(uzDAO.findByUprawnieniaAktywny("BookkeeperFK"));
        Collections.sort(listaksiegowychwybor, new UzNazwiskocomparator());
        listapodatnikow = podatnikDAO.findAktywny();
        zestawienierekordow = new ArrayList<>();
    }

    public void pobierz() {
        if (wybranaksiegowa != null) {
            listajpkupo = uPODAO.findUPORokMCDataKsiegowania(rok, mc);
            List<Dok> listaDokTmp = dokDAO.findDokRokMCDataKsiegowania(rok, mc);
            List<Dokfk> listaDokfkTmp = dokDAOfk.findDokRokMCDataKsiegowania(rok, mc);
            List<Podatnik> listapodatnikowfiltered = listapodatnikow.stream()
                    .filter(podatnik -> podatnik.getKsiegowa() != null && podatnik.getKsiegowa().equals(wybranaksiegowa))
                    .collect(Collectors.toList());
            zestawienierekordow = new ArrayList<>();
            // Step 1: Group documents by Podatnik, filtering by the selected ksiegowa
            Map<Podatnik, List<Dok>> dokumentyByPodatnik = listaDokTmp.stream()
                    .filter(dok -> listapodatnikowfiltered.contains(dok.getPodatnik()))
                    .collect(Collectors.groupingBy(Dok::getPodatnik));

            Map<Podatnik, List<Dokfk>> dokumentyFKByPodatnik = listaDokfkTmp.stream()
                    .filter(dokfk -> listapodatnikowfiltered.contains(dokfk.getPodatnikObj()))
                    .collect(Collectors.groupingBy(Dokfk::getPodatnikObj));

// Step 2: For each Podatnik, create a PodatnikRecord and populate day fields
            int numerid = 1;
            int iddokumentu = 1;
            for (Map.Entry<Podatnik, List<Dok>> entry : dokumentyByPodatnik.entrySet()) {
                Podatnik podatnik = entry.getKey();
                List<Dok> dokumentyPodatnika = entry.getValue();

                // Create a new PodatnikRecord for this Podatnik, set zamkniecie as required
                PodatnikRecord recorda = new PodatnikRecord();
                recorda.setPodatnik(podatnik);
                recorda.setId(numerid++); // Assuming Podatnik has an `id` field

                // Step 3: Count documents for each day and populate the day fields
                recorda.setWykazdokumentow(new ArrayList<>());
                for (Dok dok : dokumentyPodatnika) {
                    recorda.dodajDokument(iddokumentu++, dok.getDokSN(), dok.getKontr().getNpelna(), dok.getBrutto(), dok.getDataK());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dok.getDataK());
                    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    DayUpdater.incrementDay(recorda, dayOfMonth);
                }
                ustawDateZamkniecia(recorda, rok, mc);
                findFilteredUPORokMCDataKsiegowania(recorda, rok, mc);
                // Step 4: Add the populated PodatnikRecord to the list
                
                zestawienierekordow.add(recorda);
                Msg.msg("Pobrano dane");
            }
            for (Map.Entry<Podatnik, List<Dokfk>> entry : dokumentyFKByPodatnik.entrySet()) {
                Podatnik podatnik = entry.getKey();
                List<Dokfk> dokumentyPodatnika = entry.getValue();

                // Create a new PodatnikRecord for this Podatnik, set zamkniecie as required
                PodatnikRecord recorda = new PodatnikRecord();
                recorda.setPodatnik(podatnik);
                recorda.setId(numerid++); // Assuming Podatnik has an `id` field
                recorda.setWykazdokumentow(new ArrayList<>());
                // Step 3: Count documents for each day and populate the day fields
                for (Dokfk dok : dokumentyPodatnika) {
                    recorda.dodajDokument(iddokumentu++, dok.getDokfkSN(), dok.getKontr().getNpelna(), dok.getWartoscdokumentu(), dok.getDataujecia());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dok.getDataujecia());
                    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    DayUpdater.incrementDay(recorda, dayOfMonth);
                }
                // Step 4: Add the populated PodatnikRecord to the list
                ustawDateZamkniecia(recorda, rok, mc);
                findFilteredUPORokMCDataKsiegowania(recorda, rok, mc);
                zestawienierekordow.add(recorda);
                Msg.msg("Pobrano dane");
            }
            
        // Step 5: Add empty records for taxpayers with no documents
        for (Podatnik podatnik : listapodatnikowfiltered) { 
            if (podatnik.getKsiegowa().equals(wybranaksiegowa)) {
                if (!dokumentyByPodatnik.containsKey(podatnik) && !dokumentyFKByPodatnik.containsKey(podatnik)) {
                    PodatnikRecord emptyRecord = new PodatnikRecord();
                    emptyRecord.setPodatnik(podatnik);
                    emptyRecord.setId(numerid++);
                    emptyRecord.setWykazdokumentow(new ArrayList<>()); // No documents to add
                    ustawDateZamkniecia(emptyRecord, rok, mc);
                    zestawienierekordow.add(emptyRecord);
                }
            }
        }
            Collections.sort(zestawienierekordow, new PodatnikRecordcomparator());
        } else {
            Msg.msg("Nie wybrano księgowej");
        }
    }

    public void ustawDateZamkniecia(PodatnikRecord recorda, String rok, String miesiac) {
        int year = Integer.parseInt(rok);
        int month = Integer.parseInt(miesiac) - 1; // W Calendar miesiące są indeksowane od 0

        // Ustal zakres dat na podstawie roku i miesiąca
        Calendar start = new GregorianCalendar(year, month, 1, 0, 0, 0);
        Date startDate = start.getTime();

        Calendar end = new GregorianCalendar(year, month + 1, 1, 0, 0, 0);
        Date endDate = end.getTime();

        // Pobierz obiekt Pitpoz dla danego podatnika, który ma datapit w określonym roku i miesiącu
        Pitpoz pitpoz = pitpozDAO.findByPodatnikAndDateRange(recorda.getPodatnik(), startDate, endDate);
        if (pitpoz != null) {
            recorda.setZamkniecie(pitpoz.getDatapit());
        } else {
            // Jeśli brak odpowiedniego Pitpoz, pobierz obiekt Ryczpoz w tym samym zakresie
            Ryczpoz ryczpoz = ryczpozDAO.findByPodatnikAndDateRange(recorda.getPodatnik(), startDate, endDate);
            if (ryczpoz != null) {
                recorda.setZamkniecie(ryczpoz.getDatapit());
            } else {
                // Jeśli brak odpowiedniego Ryczpoz, pobierz obiekt WynikFKRokMc w tym samym zakresie
                WynikFKRokMc wynikFKRokMc = wynikFKRokMcDAO.findByPodatnikAndDateRange(recorda.getPodatnik(), startDate, endDate);
                if (wynikFKRokMc != null) {
                    recorda.setZamkniecie(wynikFKRokMc.getData());
                }
            }
        }
    }

    public List<UPO> findFilteredUPORokMCDataKsiegowania(PodatnikRecord recorda, String roks, String mcs) {
    List<UPO> wynik = new ArrayList<>();
    try {
        int rok = Integer.parseInt(roks);
        int mc = Integer.parseInt(mcs) - 1; // Miesiące w Calendar są indeksowane od 0

        // Początek miesiąca
        Calendar start = new GregorianCalendar(rok, mc, 1, 0, 0, 0);
        Date startDate = start.getTime();

        // Pierwszy dzień kolejnego miesiąca
        Calendar end = new GregorianCalendar(rok, mc + 1, 1, 0, 0, 0);
        Date endDate = end.getTime();

        // Filtracja listy
        wynik = listajpkupo.stream()
                .filter(upo -> upo.getDataupo() != null)
                .filter(upo -> upo.getDataupo().after(startDate) && upo.getDataupo().before(endDate))
                .filter(upo -> upo.getPodatnik().equals(recorda.getPodatnik()))
                .collect(Collectors.toList());
        if (wynik!=null&&wynik.size()>0) {
            recorda.setJpk(wynik.get(0).getDatajpk());
        }

    } catch (Exception e) {
        e.printStackTrace(); // Dodaj logowanie wyjątku
    }
    return wynik;
}

    public void pobierzSumaryczneDlaWszystkichKsiegowych() {
    List<Dok> listaDokTmp = dokDAO.findDokRokMCDataKsiegowania(rok, mc);
    List<Dokfk> listaDokfkTmp = dokDAOfk.findDokRokMCDataKsiegowania(rok, mc);

    summarizedRecords = new ArrayList<>();

   // Iterate over each ksiegowa in wykazksiegowych
for (Uz ksiegowa : listaksiegowychwybor) {
    // Step 1: Filter documents for the current ksiegowa
    List<Dok> dokumentyKsiegowej = Optional.ofNullable(listaDokTmp).orElse(Collections.emptyList()).stream()
            .filter(dok -> dok.getWprowadzil().equals(ksiegowa.getLogin()))
            .collect(Collectors.toList());
    Map<Integer, List<Dok>> dokumentyByDay = new HashMap<>();
    List<Dokfk> dokumentyFKKsiegowej = Optional.ofNullable(listaDokfkTmp).orElse(Collections.emptyList()).stream()
            .filter(dokfk -> dokfk.getWprowadzil().equals(ksiegowa.getLogin()))
            .collect(Collectors.toList());
    Map<Integer, List<Dokfk>> dokumentyFKByDay = new HashMap<>();
    // Step 2: Group documents by day
    if (dokumentyKsiegowej!=null) {
        dokumentyByDay = dokumentyKsiegowej.stream()
                .collect(Collectors.groupingBy(dok -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dok.getDataK());
                    return calendar.get(Calendar.DAY_OF_MONTH);
                }));
    }
    if (dokumentyFKKsiegowej!=null) {
        dokumentyFKByDay = dokumentyFKKsiegowej.stream()
                .collect(Collectors.groupingBy(dokfk -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dokfk.getDataujecia());
                    return calendar.get(Calendar.DAY_OF_MONTH);
                }));
    }

    // Step 3: Create a PodatnikRecord for the ksiegowa and populate day fields
    PodatnikRecord recorda = new PodatnikRecord();
    recorda.setKsiegowa(ksiegowa);  // Set the ksiegowa for this record

    for (int day = 1; day <= 31; day++) {
        int totalDocs = dokumentyByDay.getOrDefault(day, Collections.emptyList()).size();
        int totalDocsFk = dokumentyFKByDay.getOrDefault(day, Collections.emptyList()).size();

        // Use DayUpdater or equivalent method to populate each day's total in the record
        DayUpdater.incrementDay(recorda, day, totalDocs + totalDocsFk);
    }
    summarizedRecords.add(recorda);
}

    Msg.msg("Pobrano dane sumaryczne dla wszystkich księgowych.");
}

    

    public int getTotalSumDays() {
        return zestawienierekordow != null ? zestawienierekordow.stream().mapToInt(PodatnikRecord::getTotalDays).sum() : 0;
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

    public int getSumDay1() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay1).sum();
    }

    public int getSumDay2() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay2).sum();
    }

    public int getSumDay3() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay3).sum();
    }

    public int getSumDay4() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay4).sum();
    }

    public int getSumDay5() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay5).sum();
    }

    public int getSumDay6() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay6).sum();
    }

    public int getSumDay7() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay7).sum();
    }

    public int getSumDay8() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay8).sum();
    }

    public int getSumDay9() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay9).sum();
    }

    public int getSumDay10() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay10).sum();
    }

    public int getSumDay11() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay11).sum();
    }

    public int getSumDay12() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay12).sum();
    }

    public int getSumDay13() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay13).sum();
    }

    public int getSumDay14() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay14).sum();
    }

    public int getSumDay15() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay15).sum();
    }

    public int getSumDay16() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay16).sum();
    }

    public int getSumDay17() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay17).sum();
    }

    public int getSumDay18() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay18).sum();
    }

    public int getSumDay19() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay19).sum();
    }

    public int getSumDay20() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay20).sum();
    }

    public int getSumDay21() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay21).sum();
    }

    public int getSumDay22() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay22).sum();
    }

    public int getSumDay23() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay23).sum();
    }

    public int getSumDay24() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay24).sum();
    }

    public int getSumDay25() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay25).sum();
    }

    public int getSumDay26() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay26).sum();
    }

    public int getSumDay27() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay27).sum();
    }

    public int getSumDay28() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay28).sum();
    }

    public int getSumDay29() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay29).sum();
    }

    public int getSumDay30() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay30).sum();
    }

    public int getSumDay31() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay31).sum();
    }
    
    public int getSumTotalDay1() {
        return zestawienierekordow.stream().mapToInt(PodatnikRecord::getDay1).sum();
    }

    public int getSumTotalDay2() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay2).sum() : 0;
    }

    public int getSumTotalDay3() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay3).sum() : 0;
    }

    public int getSumTotalDay4() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay4).sum() : 0;
    }

    public int getSumTotalDay5() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay5).sum() : 0;
    }

    public int getSumTotalDay6() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay6).sum() : 0;
    }

    public int getSumTotalDay7() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay7).sum() : 0;
    }

    public int getSumTotalDay8() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay8).sum() : 0;
    }

    public int getSumTotalDay9() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay9).sum() : 0;
    }

    public int getSumTotalDay10() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay10).sum() : 0;
    }

    public int getSumTotalDay11() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay11).sum() : 0;
    }

    public int getSumTotalDay12() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay12).sum() : 0;
    }

    public int getSumTotalDay13() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay13).sum() : 0;
    }

    public int getSumTotalDay14() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay14).sum() : 0;
    }

    public int getSumTotalDay15() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay15).sum() : 0;
    }

    public int getSumTotalDay16() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay16).sum() : 0;
    }

    public int getSumTotalDay17() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay17).sum() : 0;
    }

    public int getSumTotalDay18() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay18).sum() : 0;
    }

    public int getSumTotalDay19() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay19).sum() : 0;
    }

    public int getSumTotalDay20() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay20).sum() : 0;
    }

    public int getSumTotalDay21() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay21).sum() : 0;
    }

    public int getSumTotalDay22() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay22).sum() : 0;
    }

    public int getSumTotalDay23() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay23).sum() : 0;
    }

    public int getSumTotalDay24() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay24).sum() : 0;
    }

    public int getSumTotalDay25() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay25).sum() : 0;
    }

    public int getSumTotalDay26() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay26).sum() : 0;
    }

    public int getSumTotalDay27() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay27).sum() : 0;
    }

    public int getSumTotalDay28() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay28).sum() : 0;
    }

    public int getSumTotalDay29() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay29).sum() : 0;
    }

    public int getSumTotalDay30() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay30).sum() : 0;
    }

    public int getSumTotalDay31() {
        return summarizedRecords !=null ? summarizedRecords.stream().mapToInt(PodatnikRecord::getDay31).sum() : 0;
    }

    public List<PodatnikRecord> getSummarizedRecords() {
        return summarizedRecords;
    }

    public void setSummarizedRecords(List<PodatnikRecord> summarizedRecords) {
        this.summarizedRecords = summarizedRecords;
    }

    public PodatnikRecord getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(PodatnikRecord selectedRecord) {
        this.selectedRecord = selectedRecord;
    }
    
    
}
