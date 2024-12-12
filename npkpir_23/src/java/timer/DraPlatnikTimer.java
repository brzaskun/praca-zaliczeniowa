/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timer;

import dao.DraSumyDAO;
import dao.PodatnikDAO;
import dao.PodmiotDAO;
import daoplatnik.UbezpZusrcaDAO;
import daoplatnik.ZusdraDAO;
import daoplatnik.ZusrcaDAO;
import daosuperplace.FirmaFacade;
import daosuperplace.RokFacade;
import data.Data;
import embeddable.Mce;
import entity.DraSumy;
import entity.Podatnik;
import entity.Podmiot;
import entityplatnik.UbezpZusrca;
import entityplatnik.Zusdra;
import entityplatnik.Zusrca;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class DraPlatnikTimer implements Serializable {
    
    @Inject
    private DraSumyDAO draSumyDAO;
    @Inject
    private ZusdraDAO zusdraDAO;
    @Inject
    private ZusrcaDAO zusrcaDAO;
    @Inject
    private UbezpZusrcaDAO ubezpZusrcaDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private PodmiotDAO podmiotDAO;
    @Inject
    private FirmaFacade firmaFacade;
    @Inject
    private RokFacade rokFacade;
    
    
    @Schedule(dayOfWeek = "1-5", hour = "*", minute = "28", persistent = false)
    public void autozus() {
        List<String> miesiaceGranica = Mce.getMiesiaceGranica(Data.aktualnyMc());
        String rok = Data.aktualnyRok();
        List<kadryiplace.Firma> firmy = firmaFacade.findAll();
        List<Podatnik> podatnicy = podatnikDAO.findAllManager();
        List<Podmiot> podmioty = podmiotDAO.findAll();
        if (miesiaceGranica!=null) {
            for (String mie : miesiaceGranica) {
                String mc = mie;
                try {
                    podsumujDRA(rok, mc, firmy, podatnicy, podmioty);
                } catch (Exception e) {
                    System.out.println(E.e(e));
                    //wadzil
                }
            }
        }
    }
    //nowe
    
     public void podsumujDRA(String rok, String mc, List<kadryiplace.Firma> firmy, List<Podatnik> podatnicy, List<Podmiot> podmioty) {
        if (mc==null) {
            mc = Data.poprzedniMc();
        }
        if (draSumyDAO!=null) {
            try {
                podsumujDRAF(mc, rok, firmy, podatnicy, podmioty);
            } catch (Exception e){
                System.out.println(E.e(e));
            }
        }
     }
     
      public void podsumujDRAF(String mc, String rok, List<kadryiplace.Firma> firmy, List<Podatnik> podatnicy, List<Podmiot> podmioty) {
        List<DraSumy> drasumy = new ArrayList<>();
        if (mc==null) {
            mc = Data.poprzedniMc();
        }
        String okres = mc+rok;
        
        List<Zusdra> zusdra = zusdraDAO.findByOkres(okres);
        List<Zusrca> zusrca = zusrcaDAO.findByOkres(okres);
        int i = 1;
        for (Zusdra z : zusdra) {
            try {
                //trzeba pobrac jak juz istnieje!!!
                // to nie dzialalo, wyrzucalo blad przy baziedanych
                DraSumy dras = pobierzdrasumy(z.getIdDokument());
                if (dras.getId()==null) {
                    dras = new DraSumy();
                }
                dras.setRok(rok);
                dras.setMc(mc);
                for (Podatnik za : podatnicy) {
                    if (za.getNip()!=null&&za.getNip().equals(z.getIi1Nip())) {
                        dras.setPodatnik(za);
                        break;
                    }
                }
                if (dras.getPodatnik()==null) {
                    for (Podatnik za : podatnicy) {
                        if (za.getPesel()!=null && za.getPesel().equals(z.getIi3Pesel())) {
                            dras.setPodatnik(za);
                            break;
                        }
                    }
                }
                if (dras.getPodatnik()==null && podmioty!=null) {
                    for (Podmiot za : podmioty) {
                        if (za.getNip()!=null && za.getNip().equals(z.getIi1Nip())) {
                            dras.setPodmiot(za);
                            break;
                        }
                    }
                    if (dras.getPodmiot()==null) {
                        for (Podmiot za : podmioty) {
                            if (za.getPesel()!=null && za.getPesel().equals(z.getIi3Pesel())) {
                                dras.setPodmiot(za);
                                break;
                            }
                        }
                    }
                }
                dras.setZusdra(z);
                dras.setIddokument(z.getIdDokument());
                dras.setNazwa(dras.getNazwaF());
                //nie rozumie tego bo przeciez pobiera wsczecniej
//                if (bazadanych!=null&&!bazadanych.isEmpty()) {
//                    dras = pobierzbaza(dras,bazadanych);
//                }
                dras.setZusdra(z);
                dras.setIddokument(z.getIdDokument());
                for (Zusrca rca : zusrca) {
                    if (rca.getI12okrrozl().equals(z.getI22okresdeklar()) && rca.getIdPlatnik()==z.getIdPlatnik()) {
                        dras.setZusrca(rca);
                        try {
                            List<UbezpZusrca> zalezne = ubezpZusrcaDAO.findByIdDokNad(rca);
                            dras.setUbezpZusrcaList(zalezne);
                        } catch (Exception e){}
                        break;
                    }
                }
                if (dras.getUbezpZusrcaList()!=null && !dras.getUbezpZusrcaList().isEmpty()) {
                    for (UbezpZusrca u : dras.getUbezpZusrcaList()) {
                        if (u.getIiiA4Identyfik().equals(z.getIi3Pesel())) {
                            dras.setUbezpZusrca(u);
                        }
                    }
                }
                dras.setUbezpieczeni(dras.getUbezpieczeniF());
                dras.setPrzedsiebiorcy(dras.getPrzedsiebiorcyF());
                dras.setPracownicy(dras.getPracownicyF());
                dras.setPracownicyzerowi(dras.getPracownicyZerowiF());
                dras.setZleceniobiorcy(dras.getZleceniobiorcyF());
                dras.setZleceniobiorcyzerowi(dras.getZleceniobiorcyZerowiF());
                dras.setInnetytuly(dras.getInnetytulyF());
                dras.setKod(dras.getKodF());
                dras.setSpoleczne(dras.getSpoleczneF());
                dras.setZdrowotne(dras.getZdrowotneF());
                dras.setData(Data.data_yyyyMMdd(z.getXii8Datawypel()));
                  dras.setNr(z.getI21iddekls());
                  dras.setOkres(z.getI22okresdeklar());
                dras.setDraprzychody(dras.getDraprzychodyF());
                dras.setDraprzychodyRR(dras.getDraprzychodyRRF());
//                System.out.println("okres "+dras.getOkres());
//                System.out.println("nazwa "+dras.getNazwa());
//                System.out.println("id "+z.getIdDokument());
                double kwota = z.getIx2Kwdozaplaty()!=null?z.getIx2Kwdozaplaty().doubleValue():0.0;
                dras.setDozaplaty(kwota);
                double kwotafp = z.getViii3KwzaplViii()!=null?z.getViii3KwzaplViii().doubleValue():0.0;
                dras.setFp(kwotafp);
                //dodajpit4DRA(dras, firmy);
                if (dras.getId()!=null) {
                    draSumyDAO.edit(dras);
                } else {
                    draSumyDAO.create(dras);
                }
                //System.out.println("zus ok podsumujDRAF "+dras.getPodatnik().getPrintnazwa());
                drasumy.add(dras);
            } catch (Exception e){
                E.m(e);
                System.out.println("Blad DraPlatnikTimer 191");
            }
            
        }
        //draSumyDAO.editList(drasumy);
        System.out.println("**********************************");
        System.out.println("Koniec DRA");
    }
    

    private DraSumy pobierzdrasumy(Integer idDokument) {
        DraSumy zwrot = new DraSumy();
        try {
            if (idDokument!=null&&draSumyDAO!=null) {
                zwrot = draSumyDAO.findByIddokument(idDokument);
            }
        } catch (Exception e){
            //System.out.println("pobierzdrasumy blad id "+idDokument);
            //System.out.println(E.e(e));
                   
        }
        return zwrot;
    }
}
