/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import dao.KadryfakturapozycjaFacade;
import dao.PasekwynagrodzenFacade;
import dao.WierszFakturyFacade;
import data.Data;
import entity.Angaz;
import entity.Fakturaopisuslugi;
import entity.Kadryfakturapozycja;
import entity.Pasekwynagrodzen;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import webservice.WierszFaktury;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class WierszFakturaBean {
    @Inject
    private KadryfakturapozycjaFacade kadryfakturapozycjaFacade;
     @Inject
    private WierszFakturyFacade wierszFakturyFacade;
     @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
     
    @Schedule(dayOfWeek = "Mon-Fri", hour = "8-16", minute = "0", persistent = false)
    private void przetworzuslugiwmiesiacu() {
        String rok = Data.aktualnyRok();
        String mc = Data.aktualnyMc();
        String[] poprzedniokres = Data.poprzedniOkres(mc, rok);
        rok = poprzedniokres[1];
        mc = poprzedniokres[0];
        String rokdopobrania = poprzedniokres[1];
        List<WierszFaktury> listawierszfaktury = wierszFakturyFacade.findbyRokMc(rok, mc);
        if (mc.equals("12")) {
            rokdopobrania = String.valueOf(Integer.parseInt(rokdopobrania)+1);
        }
        List<Kadryfakturapozycja> listauslugklientcena = kadryfakturapozycjaFacade.findByRok(rokdopobrania);
        for (Kadryfakturapozycja k : listauslugklientcena) {
            List<Pasekwynagrodzen> paskitmp = pasekwynagrodzenFacade.findByRokMcNip(rok, mc, k.getFirmakadry().getNip());
              Set<Angaz> unikalneAngazy = new HashSet<>();
        
        // Filtruj listę, pozostawiając tylko te paski, których angaz nie był wcześniej przetworzony
            List<Pasekwynagrodzen> paski = paskitmp.stream()
                .filter(pasek -> unikalneAngazy.add(pasek.getKalendarzmiesiac().getAngaz()))
                .collect(Collectors.toList());
           
            WierszFaktury wierszpobrany = pobierzwiersz(listawierszfaktury, k, rok, mc);
            if (wierszpobrany.isNowacena()) {
                wierszFakturyFacade.edit(wierszpobrany);
            }
            naniesusluge(wierszpobrany, k.getOpisuslugi(), paski);
            if (wierszpobrany.getIlosc() > 0 && wierszpobrany.getId() == null) {
                listawierszfaktury.add(wierszpobrany);
                wierszFakturyFacade.create(wierszpobrany);
            } else if (wierszpobrany.getId() != null) {
                wierszFakturyFacade.edit(wierszpobrany);
            }
            //System.out.println(k.getOpisuslugi().getClass() + " " + wierszpobrany.getIlosc());
        }
        System.out.println("Koniec przetworzuslugiwmiesiacu()");
    }
     
     public static WierszFaktury pobierzwiersz(List<WierszFaktury> listawierszfaktury, Kadryfakturapozycja kadryfakturapozycja,String rok, String mc) {
        WierszFaktury zwrot = new WierszFaktury(kadryfakturapozycja, rok, mc);
        for (WierszFaktury w : listawierszfaktury) {
            if (w.getNip().equals(kadryfakturapozycja.getFirmakadry().getNip())&&w.getOpis().equals(kadryfakturapozycja.getOpisuslugi().getOpis())) {
                if (w.getKwota()!=kadryfakturapozycja.getCena()) {
                    w.setKwota(kadryfakturapozycja.getCena());
                    w.setSymbolwaluty(kadryfakturapozycja.getWaluta().getSymbolwaluty());
                    w.setNowacena(true);
                }
                zwrot = w;
            }
        }
        return zwrot;
    }
     
      public static void naniesusluge(WierszFaktury wierszpobrany, Fakturaopisuslugi opisuslugi, List<Pasekwynagrodzen> paski) {
        wierszpobrany.setIlosc(0);
        if (opisuslugi.isListawz()||opisuslugi.isListaza()) {
            List<Pasekwynagrodzen> paskitmp = new ArrayList<>(paski);
            Predicate<Pasekwynagrodzen> isQualified = item->(item.isPraca()||item.isZasilek());
            paskitmp.removeIf(isQualified.negate());
            wierszpobrany.setIlosc(wierszpobrany.getIlosc()+paskitmp.size());
        } 
        if (opisuslugi.isListauz()) {
            List<Pasekwynagrodzen> paskitmp = new ArrayList<>(paski);
            Predicate<Pasekwynagrodzen> isQualified = item->item.isZlecenie();
            paskitmp.removeIf(isQualified.negate());
            wierszpobrany.setIlosc(wierszpobrany.getIlosc()+paskitmp.size());
        }
        if (opisuslugi.isListaos()) {
            List<Pasekwynagrodzen> paskitmp = new ArrayList<>(paski);
            Predicate<Pasekwynagrodzen> isQualified = item->item.isFunkcja();
            paskitmp.removeIf(isQualified.negate());
            wierszpobrany.setIlosc(wierszpobrany.getIlosc()+paskitmp.size());
        }
    }
}
