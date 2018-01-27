/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansPodpis.Xad;
import dao.DeklaracjavatUEDAO;
import deklaracje.vatue.m4.VATUEM4Bean;
import deklaracje.vatuek.m4.VATUEKM4Bean;
import embeddable.Kwartaly;
import embeddable.TKodUS;
import embeddable.VatUe;
import entity.DeklaracjavatUE;
import error.E;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class VATUEDeklaracjaView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private TKodUS tKodUS;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value="#{vatUeFKView}")
    private VatUeFKView vatUeFKView;
    @Inject
    private DeklaracjavatUEDAO deklaracjavatUEDAO;
    
    public void tworzdeklaracjekorekta(List<VatUe> lista) {
        DeklaracjavatUE stara = deklaracjavatUEDAO.findbyPodatnikRokMc(wpisView);
        if (stara.getDatazlozenia() == null) {
            Msg.msg("e", "Pierwotna deklaracja nie została wysłana, nie można zrobić korekty");
        } else {
            List<VatUe> staralista = stara.getPozycje();
            boolean robickorekte = false;
            int nrkolejny = 0;
            for (VatUe s : staralista) {
                for (VatUe t : lista) {
                    if (t.getKontrahent() != null && s.getKontrahent() != null && t.getKontrahent().equals(s.getKontrahent())) {
                        if (Z.z(t.getNetto()) != Z.z(s.getNetto())) {
                            t.setKorekta(true);
                            robickorekte = true;
                            nrkolejny = stara.getNrkolejny()+1;
                            break;
                        }
                    }
                }
            }
            if (robickorekte) {
                Msg.msg("w","Przygotowano korektę dekalaracji VAT-UE");
            } else {
                Msg.msg("w","Nie ma różnic w pozycjach deklaracji. Nie ma sensu robic korekty");
            }
            robdeklaracjekorekta(lista, staralista, true, nrkolejny);
        }
    }
    
    public void tworzdeklaracje(List<VatUe> lista) {
        robdeklaracje(lista, false, 0);
    }
    
    public void robdeklaracje(List<VatUe> lista, boolean korekta, int nrkolejny) {
        try {
            String deklaracja = sporzadz(lista, korekta);
            Object[] podpisanadeklaracja = podpiszDeklaracje(deklaracja);
            if (podpisanadeklaracja != null) {
                DeklaracjavatUE deklaracjavatUE = generujdeklaracje(podpisanadeklaracja);
                deklaracjavatUE.setNrkolejny(nrkolejny);
                deklaracjavatUE.setPozycje(lista);
                deklaracjavatUEDAO.dodaj(deklaracjavatUE);
                vatUeFKView.getDeklaracjeUE().add(deklaracjavatUE);
                Msg.msg("Sporządzono deklarację VAT-UE miesięczną wersja 4");
            } else {
                Msg.msg("e","Wystąpił błąd. Niesporządzono deklaracji VAT-UE. Sprawdź czy włożono kartę z podpisem! Sprawdź oznaczenia krajów i NIP-y");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił błąd. Niesporządzono deklaracji VAT-UE miesięczną wersja 4");
        }
    }
    
     public void robdeklaracjekorekta(List<VatUe> lista, List<VatUe> staralista, boolean korekta, int nrkolejny) {
        try {
            String deklaracja = sporzadzkorekta(lista, staralista, korekta);
            Object[] podpisanadeklaracja = podpiszDeklaracje(deklaracja);
            if (podpisanadeklaracja != null) {
                DeklaracjavatUE deklaracjavatUE = generujdeklaracje(podpisanadeklaracja);
                deklaracjavatUE.setNrkolejny(nrkolejny);
                deklaracjavatUE.setPozycje(lista);
                deklaracjavatUEDAO.dodaj(deklaracjavatUE);
                vatUeFKView.getDeklaracjeUE().add(deklaracjavatUE);
                Msg.msg("Sporządzono deklarację VAT-UEK miesięczną wersja 4");
            } else {
                Msg.msg("e","Wystąpił błąd. Niesporządzono deklaracji VAT-UEK. Sprawdź czy włożono kartę z podpisem! Sprawdź oznaczenia krajów i NIP-y");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił błąd. Niesporządzono deklaracji VAT-UEK miesięczną wersja 4");
        }
    }
    
    private String sporzadz(List<VatUe> lista, boolean korekta) {
        deklaracje.vatue.m4.Deklaracja deklaracja = new deklaracje.vatue.m4.Deklaracja();
        String kodurzedu = tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy());
        deklaracja.setNaglowek(VATUEM4Bean.tworznaglowek(wpisView.getMiesiacWpisu(),wpisView.getRokWpisuSt(),kodurzedu));
        deklaracja.setPodmiot1(VATUEM4Bean.podmiot1(wpisView));
        deklaracja.setPozycjeSzczegolowe(VATUEM4Bean.pozycjeszczegolowe(lista));
        deklaracja.setPouczenie(BigDecimal.ONE);
        return VATUEM4Bean.marszajuldoStringu(deklaracja, wpisView);
    }
    
    private String sporzadzkorekta(List<VatUe> lista, List<VatUe> staralista, boolean korekta) {
        List<VatUe> listaroznic = sporzadzroznice(lista, staralista);
        deklaracje.vatuek.m4.Deklaracja deklaracja = new deklaracje.vatuek.m4.Deklaracja();
        String kodurzedu = tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy());
        deklaracja.setNaglowek(VATUEKM4Bean.tworznaglowek(wpisView.getMiesiacWpisu(),wpisView.getRokWpisuSt(),kodurzedu));
        deklaracja.setPodmiot1(VATUEKM4Bean.podmiot1(wpisView));
        deklaracja.setPozycjeSzczegolowe(VATUEKM4Bean.pozycjeszczegolowe(listaroznic));
        deklaracja.setPouczenie(BigDecimal.ONE);
        return VATUEKM4Bean.marszajuldoStringu(deklaracja, wpisView);
    }

    private Object[] podpiszDeklaracje(String xml) {
        Object[] deklaracjapodpisana = null;
        try {
            deklaracjapodpisana = Xad.podpisz(xml);
        } catch (Exception e) {
            E.e(e);
        }
        return deklaracjapodpisana;
    }
    
    private DeklaracjavatUE generujdeklaracje(Object[] podpisanadeklaracja) {
        DeklaracjavatUE deklaracjavatUE = new DeklaracjavatUE();
        deklaracjavatUE.setPodatnik(wpisView.getPodatnikWpisu());
        deklaracjavatUE.setMiesiac(wpisView.getMiesiacWpisu());
        deklaracjavatUE.setRok(wpisView.getRokWpisuSt());
        deklaracjavatUE.setDeklaracja((String) podpisanadeklaracja[1]);
        deklaracjavatUE.setDeklaracjapodpisana((byte[]) podpisanadeklaracja[0]);
        deklaracjavatUE.setNrkwartalu(Kwartaly.getMapamckw().get(wpisView.getMiesiacWpisu()));
        deklaracjavatUE.setJestcertyfikat(true);
        deklaracjavatUE.setKodurzedu(tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy()));
        String sporzadzil = wpisView.getWprowadzil().getImie()+" "+wpisView.getWprowadzil().getNazw();
        deklaracjavatUE.setSporzadzil(sporzadzil);
        deklaracjavatUE.setWzorschemy("http://crd.gov.pl/wzor/2017/01/11/3846/");
        return deklaracjavatUE;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public VatUeFKView getVatUeFKView() {
        return vatUeFKView;
    }

    public void setVatUeFKView(VatUeFKView vatUeFKView) {
        this.vatUeFKView = vatUeFKView;
    }

    private List<VatUe> sporzadzroznice(List<VatUe> nowalista, List<VatUe> staralista) {
        List<VatUe> lista = new ArrayList<>();
        for (VatUe p : nowalista) {
            if (p.getKontrahent()!=null) {
                boolean niebylojeszcze = true;
                for (VatUe s : staralista) {
                    if (s.getKontrahent()!=null) {
                        if (p.getKontrahent().equals(s.getKontrahent())) {
                            if (Z.z(p.getNetto()) != Z.z(s.getNetto())) {
                                p.setNettoprzedkorekta(s.getNetto());
                                lista.add(p);
                            }
                            niebylojeszcze = false;
                            break;
                        }
                    }
                }
                if (niebylojeszcze) {
                    lista.add(p);
                }
            }
        }
        for (VatUe s : staralista) {
            if (s.getKontrahent()!=null) {
                boolean bylojuzaleniema = true;
                for (VatUe p : nowalista) {
                    if (p.getKontrahent()!=null) {
                        if (p.getKontrahent().equals(s.getKontrahent())) {
                            bylojuzaleniema = false;
                            break;
                        }
                    }
                }
                if (bylojuzaleniema) {
                    double wartoscprzedkorekta = s.getNetto();
                    s.setNettoprzedkorekta(wartoscprzedkorekta);
                    s.setNetto(0.0);
                    lista.add(s);
                }
            }
        }
        return lista;
    }

   

  
    
    
    
}
