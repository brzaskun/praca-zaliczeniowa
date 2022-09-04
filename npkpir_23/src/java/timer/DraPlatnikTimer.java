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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import kadryiplace.Firma;
import kadryiplace.Okres;
import kadryiplace.Place;
import kadryiplace.Rok;

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
    @Inject
    private FirmaFacade firmaFacade;
    @Inject
    private RokFacade rokFacade;
    
    @Schedule(dayOfWeek = "1-5", hour = "*", persistent = false)
    public void autozus() {
        List<String> miesiaceGranica = Mce.getMiesiaceGranica(Data.aktualnyMc());
        String rok = Data.aktualnyRok();
        List<kadryiplace.Firma> firmy = firmaFacade.findAll();
        List<Podatnik> podatnicy = podatnikDAO.findAllManager();
        List<Podmiot> podmioty = podmiotDAO.findAll();
        if (miesiaceGranica!=null) {
            for (String mie : miesiaceGranica) {
                String mc = mie;
                podsumujDRA(rok, mc, firmy, podatnicy, podmioty);
            }
        }
    }
    
    
     public void podsumujDRA(String rok, String mc, List<kadryiplace.Firma> firmy, List<Podatnik> podatnicy, List<Podmiot> podmioty) {
        if (mc==null) {
            mc = Data.poprzedniMc();
        }
        List<DraSumy> bazadanych = draSumyDAO.zwrocRokMc(rok, mc);
         podsumujDRAF(mc, rok, bazadanych, firmy, podatnicy, podmioty);
     }
     
      public void podsumujDRAF(String mc, String rok, List<DraSumy> bazadanych, List<kadryiplace.Firma> firmy, List<Podatnik> podatnicy, List<Podmiot> podmioty) {
        List<DraSumy> drasumy = new ArrayList<>();
        if (mc==null) {
            mc = Data.poprzedniMc();
        }
        String okres = mc+rok;
        
        List<Zusdra> zusdra = zusdraDAO.findByOkres(okres);
        List<Zusrca> zusrca = zusrcaDAO.findByOkres(okres);
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
                    dras.setUbezpZusrcaList(zalezne);
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
//            System.out.println("okres "+dras.getOkres());
//            System.out.println("nazwa "+dras.getNazwa());
//            System.out.println("id "+z.getIdDokument());
            double kwota = z.getIx2Kwdozaplaty()!=null?z.getIx2Kwdozaplaty().doubleValue():0.0;
            dras.setDozaplaty(kwota);
            double kwotafp = z.getViii3KwzaplViii()!=null?z.getViii3KwzaplViii().doubleValue():0.0;
            dras.setFp(kwotafp);
            dodajpit4DRA(dras, firmy);
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
      
       private void dodajpit4DRA(DraSumy w, List<kadryiplace.Firma> firmy) {
        if (w.getPodatnik()!=null) {
            Firma firma = null;
            for (Firma f : firmy) {
                if (f.getFirNip()!=null) {
                    if (f.getFirNip().replace("-", "").equals(w.getPodatnik().getNip())) {
                        firma = f;
                        break;
                    }
                }
            }
            if (firma != null) {
                Rok rok = rokFacade.findByFirmaRok(firma, Integer.parseInt(w.getRok()));
                kadryiplace.Okres okres = null;
                for (Okres o : rok.getOkresList()) {
                    if (o.getOkrMieNumer() == Mce.getMiesiacToNumber().get(w.getMc())) {
                        okres = o;
                        break;
                    }
                }
                List<Place> placeList = okres.getPlaceList();
                int studenci = 0;
                double podatekpraca = 0.0;
                for (Place p : placeList) {
                    podatekpraca = podatekpraca+p.getLplZalDoch().doubleValue();
                    if (p.getLplKodTytU12().equals("0411") && p.getLplZalDoch().doubleValue() == 0.0) {
                        studenci = studenci + 1;
                    }
                    w.setStudenci(studenci);
                    w.setUbezpieczeni(w.getUbezpieczeni()+w.getStudenci());
                }
                w.setPit4(podatekpraca);
            }
        }
    }
}
