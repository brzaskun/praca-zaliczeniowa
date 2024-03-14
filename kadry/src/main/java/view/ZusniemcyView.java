/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import comparator.Pasekwynagrodzencomparator;
import dao.PasekwynagrodzenFacade;
import entity.FirmaKadry;
import entity.Pasekwynagrodzen;
import entity.Pracownik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import pdf.PdfZaswiadczenieZusNiemcy;
import z.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ZusniemcyView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Pasekwynagrodzen> paski;
    @Inject
    private WpisView wpisView;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    
    public void init() {
        String rok = wpisView.getRokWpisu();
        FirmaKadry firma = wpisView.getFirma();
        List<Pasekwynagrodzen> listapaski = pasekwynagrodzenFacade.findByRokWyplFirma(rok, firma);
        Predicate<Pasekwynagrodzen> isQualified = item->item.getAngaz().getPrzekroczenierok()==null;
        listapaski.removeIf(isQualified);
        isQualified = item->item.getAngaz().getPrzekroczenierok().equals("");
        listapaski.removeIf(isQualified);
        Collections.sort(listapaski, new Pasekwynagrodzencomparator());
        Map<Pracownik, List<Pasekwynagrodzen>> pracownicyzpask = listapaski.stream().collect(Collectors.groupingBy(Pasekwynagrodzen::getPracownik));
        paski = new ArrayList<>();
        sumujpaski(pracownicyzpask);
//        pracownicyzpaskami = new TreeMap<>(new Pracownikcomparator());
//        pracownicyzpaskami.putAll(pracownicyzpask);
        
        System.out.println("");
    }

    private void sumujpaski(Map<Pracownik, List<Pasekwynagrodzen>> pracownicyzpask) {
        for (Pracownik pracownik : pracownicyzpask.keySet()) {
            paski.add(sumujpracownika(pracownik, pracownicyzpask.get(pracownik)));
            System.out.println("");
        }
    }

    private Pasekwynagrodzen sumujpracownika(Pracownik pracownik, List<Pasekwynagrodzen> paskipracownika) {
        Pasekwynagrodzen pasekwynagrodzen = new Pasekwynagrodzen();
        if (paskipracownika.isEmpty()==false) {
            for (Pasekwynagrodzen pasek : paskipracownika) {
                pasekwynagrodzen.setLiczbapaskow(pasekwynagrodzen.getLiczbapaskow()+1.0);
                pasekwynagrodzen.setKalendarzmiesiac(pasek.getKalendarzmiesiac());
                double spoleczneudzialoddelegowanie = razemspolecznepracownik(pasek);
                double kurspaska = pasek.getKurs()>0.0?pasek.getKurs():4.6;
                pasekwynagrodzen.setOddelegowaniewaluta(pasekwynagrodzen.getOddelegowaniewaluta()+pasek.getPrzekroczeniepodstawaniemiecka());
                pasekwynagrodzen.setPodatekdochodowyzagranicawaluta(pasekwynagrodzen.getPodatekdochodowyzagranicawaluta()+pasek.getPodatekdochodowyzagranicawaluta()+pasek.getPrzekroczeniepodatekniemiecki());
                pasekwynagrodzen.setSpoleczneudzialoddelegowanie(pasekwynagrodzen.getSpoleczneudzialoddelegowanie()+spoleczneudzialoddelegowanie);
                pasekwynagrodzen.setPracemerytalneOddelegowanie(Z.z(pasekwynagrodzen.getPracemerytalneOddelegowanie()+pasek.getPracemerytalne()*spoleczneudzialoddelegowanie));
                pasekwynagrodzen.setPracchoroboweOddelegowanie(Z.z(pasekwynagrodzen.getPracchoroboweOddelegowanie()+pasek.getPracchorobowe()*spoleczneudzialoddelegowanie));
                pasekwynagrodzen.setPracrentoweOddelegowanie(Z.z(pasekwynagrodzen.getPracrentoweOddelegowanie()+pasek.getPracrentowe()*spoleczneudzialoddelegowanie));
                pasekwynagrodzen.setEmerytalneOddelegowanie(Z.z(pasekwynagrodzen.getEmerytalneOddelegowanie()+pasek.getEmerytalne()*spoleczneudzialoddelegowanie));
                pasekwynagrodzen.setRentoweOddelegowanie(Z.z(pasekwynagrodzen.getRentoweOddelegowanie()+pasek.getRentowe()*spoleczneudzialoddelegowanie));
                pasekwynagrodzen.setPraczdrowotneOddelegowanie(Z.z(pasekwynagrodzen.getPraczdrowotneOddelegowanie()+pasek.getPraczdrowotne()*spoleczneudzialoddelegowanie));
                pasekwynagrodzen.setPracemerytalneOddelegowanieEuro(Z.z(pasekwynagrodzen.getPracemerytalneOddelegowanieEuro()+pasek.getPracemerytalne()/kurspaska*spoleczneudzialoddelegowanie));
                pasekwynagrodzen.setPracchoroboweOddelegowanieEuro(Z.z(pasekwynagrodzen.getPracchoroboweOddelegowanieEuro()+pasek.getPracchorobowe()/kurspaska*spoleczneudzialoddelegowanie));
                pasekwynagrodzen.setPracrentoweOddelegowanieEuro(Z.z(pasekwynagrodzen.getPracrentoweOddelegowanieEuro()+pasek.getPracrentowe()/kurspaska*spoleczneudzialoddelegowanie));
                pasekwynagrodzen.setEmerytalneOddelegowanieEuro(Z.z(pasekwynagrodzen.getEmerytalneOddelegowanieEuro()+pasek.getEmerytalne()/kurspaska*spoleczneudzialoddelegowanie));
                pasekwynagrodzen.setRentoweOddelegowanieEuro(Z.z(pasekwynagrodzen.getRentoweOddelegowanieEuro()+pasek.getRentowe()/kurspaska*spoleczneudzialoddelegowanie));
                pasekwynagrodzen.setPraczdrowotneOddelegowanieEuro(Z.z(pasekwynagrodzen.getPraczdrowotneOddelegowanieEuro()+pasek.getPraczdrowotne()/kurspaska*spoleczneudzialoddelegowanie));
            }
        }
        pasekwynagrodzen.setSpoleczneudzialoddelegowanie(Z.z6(pasekwynagrodzen.getSpoleczneudzialoddelegowanie()/pasekwynagrodzen.getLiczbapaskow()));
        return pasekwynagrodzen;
    }
    
     private static double razemspolecznepracownik(Pasekwynagrodzen pasek) {
        double proporcjaniemcy = 0.0;
        if (pasek.getPrzychodypodatekpolska()>0.0&&pasek.getPodstawaskladkizus()>0.0) {
            double proporcjaprzychodow = pasek.getPrzychodypodatekpolska()/pasek.getPodstawaskladkizus();
            //to musi byc bo jest przeciez ograniczenie wysokosci skladki zus
            if (proporcjaprzychodow>1.0) {
                proporcjaprzychodow = 1.0;
            } else {
                proporcjaniemcy = 1.0-proporcjaprzychodow;
            }
        }
        return proporcjaniemcy;
    }
     
    public void drukuj() {
        if (paski.isEmpty() == false) {
            PdfZaswiadczenieZusNiemcy.drukuj(wpisView.getFirma(), paski, wpisView.getRokWpisu(), wpisView.getUzer().getImieNazwiskoTelefon());
        } else {
            Msg.msg("e", "Błąd drukowania. Brak pasków");
        }
    }
       

    public List<Pasekwynagrodzen> getPaski() {
        return paski;
    }

    public void setPaski(List<Pasekwynagrodzen> paski) {
        this.paski = paski;
    }
    
    
}
