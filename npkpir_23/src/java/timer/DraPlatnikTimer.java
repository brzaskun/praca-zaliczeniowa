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
import data.Data;
import embeddable.Mce;
import entity.DraSumy;
import entity.Podatnik;
import entity.Podmiot;
import entityplatnik.UbezpZusrca;
import entityplatnik.Zusdra;
import entityplatnik.Zusrca;
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
public class DraPlatnikTimer {
    
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
    
    @Schedule(dayOfWeek = "1-5", hour = "*", persistent = false)
    public void autozus() {
        List<String> miesiaceGranica = Mce.getMiesiaceGranica(Data.aktualnyMc());
        String rok = Data.aktualnyRok();
        if (miesiaceGranica!=null) {
            for (String mie : miesiaceGranica) {
                String mc = mie;
                podsumujDRA(rok,mc);
            }
        }
    }
    
    
     public void podsumujDRA(String rok, String mc) {
        if (mc==null) {
            mc = Data.poprzedniMc();
        }
        List<DraSumy> bazadanych = draSumyDAO.zwrocRokMc(rok, mc);
         podsumujDRAF(mc, rok, bazadanych);
     }
     
      public void podsumujDRAF(String mc, String rok, List<DraSumy> bazadanych) {
        List<DraSumy> drasumy = new ArrayList<>();
        if (mc==null) {
            mc = Data.poprzedniMc();
        }
        String okres = mc+rok;
        List<Zusdra> zusdra = zusdraDAO.findByOkres(okres);
        List<Zusrca> zusrca = zusrcaDAO.findByOkres(okres);
        List<Podatnik> podatnicy = podatnikDAO.findAllManager();
        List<Podmiot> podmioty = podmiotDAO.findAll();
        int i = 1;
        for (Zusdra z : zusdra) {
            DraSumy dras = new DraSumy();
            dras.setRok(rok);
            dras.setMc(mc);
            for (Podatnik za : podatnicy) {
                if (za.getNip().equals(z.getIi1Nip())) {
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
                    if (za.getNip().equals(z.getIi1Nip())) {
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
            if (bazadanych!=null&&!bazadanych.isEmpty()) {
                dras = pobierzbaza(dras,bazadanych);
            }
            dras.setZusdra(z);
            dras.setIddokument(z.getIdDokument());
            for (Zusrca r : zusrca) {
                if (r.getI12okrrozl().equals(z.getI22okresdeklar()) && r.getIdPlatnik()==z.getIdPlatnik()) {
                    dras.setZusrca(r);
                    List<UbezpZusrca> zalezne = ubezpZusrcaDAO.findByIdDokNad(r);
                    dras.setUbezpZusrca(zalezne);
                    break;
                }
            }
            dras.setUbezpieczeni(dras.getUbezpieczeniF());
            dras.setPrzedsiebiorcy(dras.getPrzedsiebiorcyF());
            dras.setPracownicy(dras.getPracownicyF());
            dras.setZleceniobiorcy(dras.getZleceniobiorcyF());
            dras.setZleceniobiorcyzerowi(dras.getZleceniobiorcyZerowiF());
            dras.setInnetytuly(dras.getInnetytulyF());
            dras.setKod(dras.getKodF());
            dras.setSpoleczne(dras.getSpoleczneF());
            dras.setZdrowotne(dras.getZdrowotneF());
            dras.setData(Data.data_yyyyMMdd(z.getXii8Datawypel()));
            dras.setNr(z.getI21iddekls());
            dras.setOkres(z.getI22okresdeklar());
//            System.out.println("okres "+dras.getOkres());
//            System.out.println("nazwa "+dras.getNazwa());
//            System.out.println("id "+z.getIdDokument());
            double kwota = z.getIx2Kwdozaplaty()!=null?z.getIx2Kwdozaplaty().doubleValue():0.0;
            dras.setDozaplaty(kwota);
            drasumy.add(dras);
            
        }
        draSumyDAO.editList(drasumy);
        //System.out.println("Koniec DRA");
    }
      
      private DraSumy pobierzbaza(DraSumy dras, List<DraSumy> bazadanych) {
        DraSumy zwrot = dras;
        for (DraSumy p : bazadanych) {
            if (p.getPodatnik()!=null) {
                if ( p.getPodatnik().equals(dras.getPodatnik()) && p.getNr().equals(dras.getNr())) {
                    zwrot = p;
                    break;
                }
            } else 
            if (p.getPodmiot()!=null) {
                if ( p.getPodmiot().equals(dras.getPodmiot()) && p.getNr().equals(dras.getNr())) {
                    zwrot = p;
                    break;
                }
            } else {
                if ( p.getNazwa().equals(dras.getNazwa()) && p.getNr().equals(dras.getNr())) {
                    zwrot = p;
                    break;
                }
            }
        }
        return zwrot;
    }
}
