/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Pasekwynagrodzencomparator;
import comparator.Pracownikcomparator;
import dao.PasekwynagrodzenFacade;
import dao.PodatkiFacade;
import entity.FirmaKadry;
import entity.Pasekwynagrodzen;
import entity.Podatki;
import entity.Pracownik;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import z.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PasekwynagrodzenkorektaView  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    @Inject
    private PodatkiFacade podatkiFacade;
    private TreeMap<Pracownik, List<Pasekwynagrodzen>> pracownicyzpaskami;
    private List<Pasekwynagrodzen> paskiwybranego;
    @Inject
    private WpisView wpisView;
    private boolean dialogOtwarty;
    private Pracownik selectedpracownik;
    private Pasekwynagrodzen selectedpasek;
    
    public void open() {
        dialogOtwarty = true;
    }
    public void close() {
        dialogOtwarty = false;
    }
    
    public void reloadDialog() {
        boolean zwrot = false;
        if (dialogOtwarty) {
            init();
        }
    }
   
    public void init() {
        String rok = wpisView.getRokWpisu();
        FirmaKadry firma = wpisView.getFirma();
        List<Pasekwynagrodzen> listapaski = pasekwynagrodzenFacade.findByRokFirma(rok, firma);
        Predicate<Pasekwynagrodzen> isQualified = item->item.getAngaz().getPrzekroczenierok()==null;
        listapaski.removeIf(isQualified);
        isQualified = item->item.getAngaz().getPrzekroczenierok().equals("");
        listapaski.removeIf(isQualified);
        Collections.sort(listapaski, new Pasekwynagrodzencomparator());
        Map<Pracownik, List<Pasekwynagrodzen>> pracownicyzpask = listapaski.stream().collect(Collectors.groupingBy(Pasekwynagrodzen::getPracownik));
        pracownicyzpaskami = new TreeMap<>(new Pracownikcomparator());
        pracownicyzpaskami.putAll(pracownicyzpask);
        
        System.out.println("");
    }
    
    public void pobierzdane() {
        if (selectedpracownik!=null) {
            paskiwybranego = pracownicyzpaskami.get(selectedpracownik);
            List<Podatki> stawkipodatkowe = podatkiFacade.findByRokUmowa(wpisView.getRokWpisu(), "P");
            for (Pasekwynagrodzen pasek : paskiwybranego) {
                double nowapodstawa = Z.z(pasek.getPodstawaopodatkowania()-pasek.getOddelegowaniepln())>0.0?Z.z(pasek.getPodstawaopodatkowania()-pasek.getOddelegowaniepln()):0.0;
                pasek.setPrzekroczeniekorektapodstawypolska(nowapodstawa);
                if (pasek.isPraca()) {
                    obliczpodatekwstepnyDBStandard(pasek, pasek.getPrzekroczeniekorektapodstawypolska(), stawkipodatkowe, 0.0);
                } else {
                    obliczpodatekwstepnyZlecenieDB(pasek, stawkipodatkowe, pasek.isNierezydent());
                }
                naniespodstaweniemiecka(pasek);
            }
            Msg.msg("Wybrano pracownika "+selectedpracownik.getNazwiskoImie());
        }
    }

    private static void obliczpodatekwstepnyDBStandard(Pasekwynagrodzen pasek, double podstawaopodatkowania, List<Podatki> stawkipodatkowe, double sumapoprzednich) {
        double podatek = Z.z(Z.z0(podstawaopodatkowania) * stawkipodatkowe.get(0).getStawka());
        double drugiprog = stawkipodatkowe.get(0).getKwotawolnado();
        if (sumapoprzednich >= drugiprog) {
            podatek = Z.z(Z.z0(podstawaopodatkowania) * stawkipodatkowe.get(1).getStawka());
            pasek.setDrugiprog(true);
        } else if (sumapoprzednich < drugiprog) {
            double razemzbiezacym = sumapoprzednich + podstawaopodatkowania;
            if (razemzbiezacym > drugiprog) {
                double podatekdol = Z.z(Z.z0(drugiprog - sumapoprzednich) * stawkipodatkowe.get(0).getStawka());
                double podatekgora = Z.z(Z.z0(razemzbiezacym - drugiprog) * stawkipodatkowe.get(1).getStawka());
                podatek = podatekdol + podatekgora;
                pasek.setDrugiprog(true);
            } else {
                podatek = Z.z(Z.z0(podstawaopodatkowania) * stawkipodatkowe.get(0).getStawka());
            }
        }
        pasek.setPrzekroczenienowypodatek(podatek);
    }
    
     private static void obliczpodatekwstepnyZlecenieDB(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean nierezydent) {
        double podatek = Z.z(Z.z0(pasek.getPrzekroczeniekorektapodstawypolska()) * stawkipodatkowe.get(0).getStawka());
        if (nierezydent) {
            podatek = Z.z(Z.z0(pasek.getBrutto()) * 0.2);
        } else if (pasek.isDo26lat()&&pasek.getPrzychodypodatekpolska()==0.0) {
            podatek = 0.0;
        }
        pasek.setPrzekroczenienowypodatek(podatek);
    }
     
    private void naniespodstaweniemiecka(Pasekwynagrodzen pasek) {
        if (pasek.getPodstawaopodatkowaniazagranicawaluta()==0.0) {
            pasek.setPrzekroczeniepodstawaniemiecka(pasek.getOddelegowaniewaluta());
        }
    }
    
    public TreeMap<Pracownik, List<Pasekwynagrodzen>> getPracownicyzpaskami() {
        return pracownicyzpaskami;
    }

    public void setPracownicyzpaskami(TreeMap<Pracownik, List<Pasekwynagrodzen>> pracownicyzpaskami) {
        this.pracownicyzpaskami = pracownicyzpaskami;
    }

   

    public Pracownik getSelectedpracownik() {
        return selectedpracownik;
    }

    public void setSelectedpracownik(Pracownik selectedpracownik) {
        this.selectedpracownik = selectedpracownik;
    }

    public List<Pasekwynagrodzen> getPaskiwybranego() {
        return paskiwybranego;
    }

    public void setPaskiwybranego(List<Pasekwynagrodzen> paskiwybranego) {
        this.paskiwybranego = paskiwybranego;
    }

    public Pasekwynagrodzen getSelectedpasek() {
        return selectedpasek;
    }

    public void setSelectedpasek(Pasekwynagrodzen selectedpasek) {
        this.selectedpasek = selectedpasek;
    }

   
    
    
    
}
