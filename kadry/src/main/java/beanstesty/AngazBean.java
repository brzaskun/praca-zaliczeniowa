/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Angaz;
import entity.Umowa;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Osito
 */
public class AngazBean {
    
    public static Angaz angaz;
    
    public static Angaz create() {
        if (angaz==null) {
            angaz = new Angaz();
            angaz.setFirma(FirmaBean.create());
            angaz.setPracownik(PracownikBean.create());
            angaz.setSkladnikwynagrodzeniaList(new ArrayList<>());
            angaz.getSkladnikwynagrodzeniaList().add(SkladnikwynagrodzeniaBean.createWynagrodzenie());
            //umowa.getSkladnikwynagrodzeniaList().add(SkladnikwynagrodzeniaBean.createPremiaUznaniowa());
            angaz.setSkladnikpotraceniaList(new ArrayList<>());
            angaz.getSkladnikpotraceniaList().add(SkladnikpotraceniaBean.create());
        }
        return angaz;
    }
    

    public static List<Angaz> filterAngazListByUmowaDate(List<Angaz> angazList, String selectedYear, String selectedMonth) {
        List<Angaz> filteredAngazList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Create the selected month as YearMonth for comparison
        YearMonth selectedYearMonth = YearMonth.of(Integer.parseInt(selectedYear), Integer.parseInt(selectedMonth));

        for (Angaz angaz : angazList) {
            List<Umowa> umowaList = angaz.getUmowaList();
            if (umowaList != null) {
                for (Umowa umowa : umowaList) {
                    LocalDate dataod = null;
                    LocalDate datado = null;

                    // Parse dataod (start date)
                    String dataodStr = umowa.getDataod();
                    if (dataodStr != null && !dataodStr.isEmpty()) {
                        try {
                            dataod = LocalDate.parse(dataodStr, formatter);
                        } catch (DateTimeParseException e) {
                            e.printStackTrace();
                            continue; // Skip this Umowa if dataod is invalid
                        }
                    } else {
                        // If dataod is null or empty, use LocalDate.MIN
                        dataod = LocalDate.MIN;
                    }

                    // Parse datado (end date)
                    String datadoStr = umowa.getDatado();
                    if (datadoStr != null && !datadoStr.isEmpty()) {
                        try {
                            datado = LocalDate.parse(datadoStr, formatter);
                        } catch (DateTimeParseException e) {
                            e.printStackTrace();
                            continue; // Skip this Umowa if datado is invalid
                        }
                    } else {
                        // If datado is null or empty, use LocalDate.MAX
                        datado = LocalDate.MAX;
                    }

                    // Check if Umowa is active during the selected month
                    if (isUmowaActiveInSelectedMonth(dataod, datado, selectedYearMonth)) {
                        filteredAngazList.add(angaz);
                        break; // No need to check other Umowa for this Angaz
                    }
                }
            }
        }

        return filteredAngazList;
    }

    private static boolean isUmowaActiveInSelectedMonth(LocalDate dataod, LocalDate datado, YearMonth selectedYearMonth) {
        // Get the first and last day of the selected month
        LocalDate monthStart = selectedYearMonth.atDay(1);
        LocalDate monthEnd = selectedYearMonth.atEndOfMonth();

        // Check for overlap
        return ( !dataod.isAfter(monthEnd) ) && ( !datado.isBefore(monthStart) );
    }
}

