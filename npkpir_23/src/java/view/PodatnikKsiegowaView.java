/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Podatnikcomparator;
import comparator.Uzcomparator;
import dao.DokDAO;
import dao.FakturaDAO;
import dao.PodatnikDAO;
import dao.UzDAO;
import dao.WierszDAO;
import embeddable.Pozycjenafakturzebazadanych;
import entity.Dok;
import entity.Faktura;
import entity.Podatnik;
import entity.Uz;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import waluty.Z;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PodatnikKsiegowaView implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Podatnik> listapodatnikow;
    private List<Podatnik> listapodatnikowfilter;
    private List<Uz> listaksiegowych;
    private List<Uz> listaksiegowychZapis;
    private List<Uz> listaksiegowychwybor;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private WierszDAO wierszDAO;
    @Inject
    private FakturaDAO fakturaDAO;
    private String rok;
    private String mc;
    private boolean bezzerowych;
    private boolean edycja;
    private double razemksiegowosc;
    private double razemkadry;
    // Total accumulators
    double totalSuma;
    double totalSumakadry;
    double totalDokpkpir;
    double totalWiersze;
    int totalLiczba;
    int totalLiczbafk;
    double totalWynagrodzenie;
    

    public void init() { //E.m(this);
        totalSuma = 0.0;
        totalSumakadry = 0.0;
        totalDokpkpir = 0.0;
        totalWiersze = 0.0;
        totalLiczba = 0;
        totalLiczbafk = 0;
        totalWynagrodzenie = 0.0;
        listapodatnikow = podatnikDAO.findAktywny();
        Collections.sort(listapodatnikow, new Podatnikcomparator());
        listaksiegowych = uzDAO.findByUprawnienia("Bookkeeper");
        listaksiegowych.addAll(uzDAO.findByUprawnienia("BookkeeperFK"));
        listaksiegowychwybor = new ArrayList<>(listaksiegowych);
        listaksiegowychZapis = new ArrayList<>(listaksiegowych);
        Podatnik wystawca = podatnikDAO.findPodatnikByNIP("8511005008");
        Collections.sort(listaksiegowych, new Uzcomparator());
//        List<Faktura> fakturyWystawione = fakturaDAO.findFakturyByRokPodatnik(rok, wystawca);
        List<Faktura> fakturyWystawione = fakturaDAO.findFakturyByRokMcWystawca(rok, mc, wystawca);
        for (Podatnik p : listapodatnikow) {
            if (p.isPodmiotaktywny()) {
                if (p.getNip().equals("9552524929")) {
                    error.E.s("");
                }
                List<Faktura> fakt = fakturyWystawione.stream().filter(r->r.getKontrahent().getNip().equals(p.getNip())).collect(Collectors.toList());
                double suma = 0.0;
                double sumakadry = 0.0;
                if (!fakt.isEmpty()) {
                    for (Faktura s: fakt) {
                        if (s.getPrzyczynakorekty()==null) {
                            for (Pozycjenafakturzebazadanych poz : s.getPozycjenafakturze()) {
                                double kwota = s.getTabelanbp()!=null?poz.getNetto(s.getTabelanbp()):poz.getNetto();
                                if (poz.getJednostka().equals("osb.")) {
                                    sumakadry = sumakadry + kwota;
                                } else {
                                    suma = suma + kwota;
                                }
                            }
                        } else {
                            for (Pozycjenafakturzebazadanych poz : s.getPozycjepokorekcie()) {
                                double kwota = s.getTabelanbp()!=null?poz.getNetto(s.getTabelanbp()):poz.getNetto();
                                if (poz.getJednostka().equals("osb.")) {
                                    sumakadry = sumakadry - kwota;
                                } else {
                                    suma = suma - kwota;
                                }
                            }
                        }
                    }
                }
                p.setCena(Z.z(suma));
                p.setCenakadry(Z.z(sumakadry));
            }
        }
        if (bezzerowych) {
            for (Iterator<Podatnik> it=listapodatnikow.iterator(); it.hasNext();) {
                Podatnik p = it.next();
               if (p.getCena() == 0&&p.getCenakadry()==0.0) {
                   it.remove();
               }
            }
        }
//        List<Dok> pkpir = dokDAO.findDokRok(rok);
//        List<Wiersz> fk = wierszDAO.findWierszeRok(rok);
        List<Dok> pkpir = dokDAO.findDokRokMC(rok,mc);
        List<Wiersz> fk = wierszDAO.findWierszeRokMc(rok, mc);
        for (Iterator<Podatnik> it=listapodatnikow.iterator(); it.hasNext();) {
           Podatnik p = it.next();
           if (p.getCena() == 0&&p.isNiesprawdzajfaktury()==false) {
              // p.setPodmiotaktywny(false);
           } else {
               List<Dok> znalezionedok = pkpir.stream().filter(pd->pd.getPodatnik().equals(p)).collect(Collectors.toList());
               if (znalezionedok!=null&&znalezionedok.isEmpty()==false) {
                    p.setLiczbadok(znalezionedok.size());
               } else {
                   List<Wiersz> znalezionewiersze = fk.stream().filter(pd->pd.getDokfk().getPodatnikObj().equals(p)).collect(Collectors.toList());
                   if (znalezionewiersze!=null&&znalezionewiersze.isEmpty()==false) {
                        p.setLiczbawierszy(znalezionewiersze.size());
                   }
               }
           }
        }
        podatnikDAO.editList(listapodatnikow);
        List<Uz> dousuniecia = new ArrayList<>();
        razemksiegowosc = 0.0;
        razemkadry = 0.0;

        // You can now use aggregatedList for your final output or display.

        for (Uz r: listaksiegowych) {
            double suma = 0.0;
            double sumakadry = 0.0;
            int dokpkpir = 0;
            int wiersze = 0;
            int liczba = 0;
            int liczbafk = 0;
            r.setPrzyporzadkowanipodatnicy(new ArrayList<>());
            for (Iterator<Podatnik> it=listapodatnikow.iterator(); it.hasNext();) {
                Podatnik p = it.next();
                if (p.getKsiegowa()!=null && p.getKsiegowa().equals(r)) {
                    suma += p.getCena();
                    sumakadry += p.getCenakadry();
                    dokpkpir += p.getLiczbadok();
                    wiersze += p.getLiczbawierszy();
                    r.getPrzyporzadkowanipodatnicy().add(p);
                    if (wiersze>0 || dokpkpir>0) {
                        if (wiersze==0) {
                            liczba++;
                        } else {
                            liczbafk++;
                        }
                    }
                }
            }
            r.setDokpkpir(dokpkpir);
            r.setWierszefk(wiersze);
            r.setSumafaktur(suma);
            razemksiegowosc = razemksiegowosc + suma;
            r.setSumafakturkadry(sumakadry);
            razemkadry = razemkadry + sumakadry;
            r.setLiczbapodatnikow(liczba);
            r.setLiczbapodatnikowfk(liczbafk);
            if (Z.z(suma)==0.0) {
                dousuniecia.add(r);
            }
        }
        listaksiegowych.removeAll(dousuniecia);
        Map<String, Uz> uniqueKsiegowychMap = new HashMap<>();

        for (Uz r : listaksiegowych) {
            String imieNazwisko = r.getImieNazwisko();
            Uz aggregatedRecord = uniqueKsiegowychMap.get(imieNazwisko);

            // Initialize a new Uz instance if this imieNazwisko hasn't been processed yet
            if (aggregatedRecord == null) {
                aggregatedRecord = new Uz();
                aggregatedRecord.setId(r.getId());
                aggregatedRecord.setImie(r.getImie());
                aggregatedRecord.setNazw(r.getNazw());
                aggregatedRecord.setPrzyporzadkowanipodatnicy(new ArrayList<>());
                uniqueKsiegowychMap.put(imieNazwisko, aggregatedRecord);
            }

            // Aggregate values
            double suma = aggregatedRecord.getSumafaktur() + r.getSumafaktur();
            double sumakadry = aggregatedRecord.getSumafakturkadry() + r.getSumafakturkadry();
            double dokpkpir = aggregatedRecord.getDokpkpir() + r.getDokpkpir();
            double wiersze = aggregatedRecord.getWierszefk() + r.getWierszefk();
            int liczba = aggregatedRecord.getLiczbapodatnikow() + r.getLiczbapodatnikow();
            int liczbafk = aggregatedRecord.getLiczbapodatnikowfk() + r.getLiczbapodatnikowfk();
            // Update aggregated Uz instance with summed values
            aggregatedRecord.setSumafaktur(suma);
            aggregatedRecord.setSumafakturkadry(sumakadry);
            aggregatedRecord.setDokpkpir(dokpkpir);
            aggregatedRecord.setWierszefk(wiersze);
            aggregatedRecord.setLiczbapodatnikow(liczba);
            aggregatedRecord.setLiczbapodatnikowfk(liczbafk);
            aggregatedRecord.getPrzyporzadkowanipodatnicy().addAll(r.getPrzyporzadkowanipodatnicy());
            aggregatedRecord.setWynagrodzenieobecne(r.getWynagrodzenieobecne());
            aggregatedRecord.setProcent(r.getProcent());
            aggregatedRecord.setWynagrodzenieprocentowe(r.getWynagrodzenieprocentowe());
        }

        // Convert the map values to a list if needed
        listaksiegowych = new ArrayList<>(uniqueKsiegowychMap.values());
        // Iterate over the aggregatedList to calculate total sums
        for (Uz uz : listaksiegowych) {
            totalSuma += uz.getSumafaktur();
            totalSumakadry += uz.getSumafakturkadry();
            totalDokpkpir += uz.getDokpkpir();
            totalWiersze += uz.getWierszefk();
            totalLiczba += uz.getLiczbapodatnikow();
            totalLiczbafk += uz.getLiczbapodatnikowfk();
            totalWynagrodzenie += uz.getWynagrodzenieobecne();
        }
    }
    
//    public void zachowaj() {
//        try {
//            uzDAO.editList(listaksiegowych);
//            Msg.msg("Zaksiegowano zmiany księgowych");
//        } catch (Exception e) {
//            E.e(e);
//            Msg.msg("e", "Wystąpił błąd, nie naniesiono zmian księgowych");
//        }
//    }
//    
//    public void zapisz() {
//        try {
//            podatnikDAO.editList(listapodatnikow);
//            Msg.msg("Zaksiegowano zmiany w ustawieniach podatników");
//        } catch (Exception e) {
//            E.e(e);
//            Msg.msg("e", "Wystąpił błąd, nie naniesiono zmian.");
//        }
//    }
    
    public void przeliczdane(Uz ksiegowa) {
        try {
            double wynagrodzenie = ksiegowa.getWynagrodzenieobecne();
            double sumafaktur = ksiegowa.getSumafaktur();
            double procent = Z.z4(wynagrodzenie/sumafaktur);
            ksiegowa.setProcent(Z.z(procent*100));
            double wynwyliczone = Z.z(sumafaktur*procent);
            ksiegowa.setWynagrodzenieprocentowe(wynwyliczone);
            for (Uz uz : listaksiegowychZapis) {
                if (uz.getNazwiskoImie().equals(ksiegowa.getNazwiskoImie())) {
                    uz.setWynagrodzenieobecne(ksiegowa.getWynagrodzenieobecne());
                    uz.setProcent(ksiegowa.getProcent());
                    uz.setWynagrodzenieprocentowe(ksiegowa.getWynagrodzenieprocentowe());
                    uzDAO.edit(uz);
                }
            }
            Msg.msg("Przeliczono ksiegową nowe wynagrodzenie");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd, nie naniesiono zmian księgowej");
        }
    }
    
    public void przeliczdaneproc(Uz ksiegowa) {
        try {
            double sumafaktur = ksiegowa.getSumafaktur();
            double procent = ksiegowa.getProcent();
            double wynwyliczone = Z.z(sumafaktur*procent/100);
            ksiegowa.setWynagrodzenieprocentowe(wynwyliczone);
            for (Uz uz : listaksiegowychZapis) {
                if (uz.getNazwiskoImie().equals(ksiegowa.getNazwiskoImie())) {
                    uz.setWynagrodzenieobecne(ksiegowa.getWynagrodzenieobecne());
                    uz.setProcent(ksiegowa.getProcent());
                    uz.setWynagrodzenieprocentowe(ksiegowa.getWynagrodzenieprocentowe());
                    uzDAO.edit(uz);
                }
            }
            Msg.msg("Przeliczono ksiegową nowy procent");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd, nie naniesiono zmian księgowej");
        }
    }

    private double sumujfaktury(List<Uz> listaksiegowych, Uz ksiegowa) {
        double zwrot = 0.0;
        String pocz = ksiegowa.getLogin().substring(0,2);
        for (Uz p : listaksiegowych) {
            if (p.getLogin().startsWith(pocz)) {
                zwrot+=Z.z(p.getSumafaktur());
            }
        }
        return zwrot;
    }
    
    public double razem() {
        double zwrot = 0.0;
        if (listapodatnikowfilter!=null) {
            for (Podatnik p : listapodatnikowfilter) {
                zwrot = zwrot+p.getCena();
            }
        }
        return zwrot;
    }

    public double getRazemksiegowosc() {
        return razemksiegowosc;
    }

    public void setRazemksiegowosc(double razemksiegowosc) {
        this.razemksiegowosc = razemksiegowosc;
    }

    public double getRazemkadry() {
        return razemkadry;
    }

    public void setRazemkadry(double razemkadry) {
        this.razemkadry = razemkadry;
    }
    
    
    
    public List<Podatnik> getListapodatnikow() {
        return listapodatnikow;
    }

    public void setListapodatnikow(List<Podatnik> listapodatnikow) {
        this.listapodatnikow = listapodatnikow;
    }

    public List<Podatnik> getListapodatnikowfilter() {
        return listapodatnikowfilter;
    }

    public void setListapodatnikowfilter(List<Podatnik> listapodatnikowfilter) {
        this.listapodatnikowfilter = listapodatnikowfilter;
    }

    public List<Uz> getListaksiegowych() {
        return listaksiegowych;
    }

    public void setListaksiegowych(List<Uz> listaksiegowych) {
        this.listaksiegowych = listaksiegowych;
    }

    public List<Uz> getListaksiegowychwybor() {
        return listaksiegowychwybor;
    }

    public void setListaksiegowychwybor(List<Uz> listaksiegowychwybor) {
        this.listaksiegowychwybor = listaksiegowychwybor;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public boolean isBezzerowych() {
        return bezzerowych;
    }

    public void setBezzerowych(boolean bezzerowych) {
        this.bezzerowych = bezzerowych;
    }

    public boolean isEdycja() {
        return edycja;
    }

    public void setEdycja(boolean edycja) {
        this.edycja = edycja;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public double getTotalSuma() {
        return totalSuma;
    }

    public double getTotalSumakadry() {
        return totalSumakadry;
    }

    public double getTotalDokpkpir() {
        return totalDokpkpir;
    }

    public double getTotalWiersze() {
        return totalWiersze;
    }

    public int getTotalLiczba() {
        return totalLiczba;
    }

    public int getTotalLiczbafk() {
        return totalLiczbafk;
    }

    public double getTotalWynagrodzenie() {
        return totalWynagrodzenie;
    }

    
    
    
}
