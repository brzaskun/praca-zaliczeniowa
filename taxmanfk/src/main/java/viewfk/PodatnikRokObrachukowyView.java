/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.PlanKontFKKopiujBean;
import dao.KontoDAOfk;
import dao.PozycjaBilansDAO;
import dao.PozycjaRZiSDAO;
import dao.UkladBRDAO;
import entity.Podatnik;
import entityfk.Konto;
import entityfk.UkladBR;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import view.WpisView;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PodatnikRokObrachukowyView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private PlanKontView planKontView;
    @Inject
    private PozycjaBRKontaView pozycjaBRKontaView;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private PozycjaRZiSDAO pozycjaRZiSDAO;
    @Inject
    private PozycjaBilansDAO pozycjaBilansDAO;
    
    public void otworzrok() {
        try {
            int zwrot = 1;
            zwrot = kopiujplankont();
            if (zwrot==1) {
                Msg.msg("e", "Wystąpił błąd podczas kopiowanai planu kont");
            } else {
                zwrot = kopiujuklad();
                if (zwrot==0) {
                    Msg.msg("Otwarto rok "+wpisView.getRokWpisuSt());
                } else {
                    Msg.msg("w","Nie dokończono otwierania roku");
                }
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nieudało się otworzyć roku");
        }
    }
    
    
    public int kopiujplankont() {
        int zwrot = 1;
        Podatnik podatnikzrodlowy = wpisView.getPodatnikObiekt();
        Podatnik podatnikdocelowy = wpisView.getPodatnikObiekt();
        String rokzrodlowy = wpisView.getRokUprzedniSt();
        String rokdocelowy = wpisView.getRokWpisuSt();
        List<Konto> plankontdocelowy = kontoDAOfk.findWszystkieKontaPodatnika(podatnikzrodlowy, rokdocelowy);
        if (plankontdocelowy != null && !plankontdocelowy.isEmpty()) {
            Msg.msg("e", "W bieżącym roku istnieje już plan kont firmy. Usuń go najpierw, aby skopiować plan kont z innego roku/firmy");
        } else if (podatnikzrodlowy.equals(podatnikdocelowy) && rokzrodlowy.equals(rokdocelowy)) {
            Msg.msg("e", "Podatnik oraz rok źródłowy i docelowy jest ten sam");
        } else {
            List<Konto> plankontzrodlowy = kontoDAOfk.findWszystkieKontaPodatnikaPobierzRelacje(podatnikzrodlowy, rokzrodlowy);
            if (plankontzrodlowy.isEmpty()) {
                Msg.msg("e", "Nie ma planu kont w roku poprzednim, nie można automatycznie otworzyć roku "+wpisView.getRokWpisuSt());
            } else {
                List<Konto> macierzyste = PlanKontFKKopiujBean.skopiujlevel0(kontoDAOfk,podatnikdocelowy, plankontzrodlowy, rokdocelowy);
                int maxlevel = kontoDAOfk.findMaxLevelPodatnik(podatnikzrodlowy, Integer.parseInt(rokzrodlowy));
                for (int biezacylevel = 1; biezacylevel <= maxlevel; biezacylevel++) {
                    macierzyste = PlanKontFKKopiujBean.skopiujlevel(kontoDAOfk,podatnikzrodlowy, podatnikdocelowy, plankontzrodlowy, macierzyste, biezacylevel, rokdocelowy, true);
                }
                planKontView.init();
                planKontView.porzadkowanieKontPodatnika(podatnikdocelowy, rokdocelowy);
                Msg.msg("Skopiowano plan kont z firmy do firmy");
                zwrot = 0;
            }
        }
        return zwrot;
    }
    
    public int kopiujuklad() {
        int zwrot = 1;
        try {
            List<UkladBR> listaukladow = ukladBRDAO.findPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt());
            String ukladdocelowyrok = wpisView.getRokWpisuSt();
            if (listaukladow.isEmpty()) {
                Msg.msg("e", "Brak uładu w poprzednim roku. Nie można skopiować układu");
            } else {
                List<Konto> kontagrupa0 = kontoDAOfk.findKontaGrupa(wpisView, "0%");
                if (kontagrupa0 == null || kontagrupa0.isEmpty()) {
                    Msg.msg("e", "Brak planu kont w bieżacym roku. Nie można kopiować układu");
                } else if (!ukladdocelowyrok.equals(wpisView.getRokWpisuSt())) {
                    Msg.msg("e", "Rok docelowy jest inny od roku bieżącego. Nie można kopiować układu");
                } else {
                    for(UkladBR ukladzrodlowy :listaukladow) {
                        UkladBR ukladBR = serialclone.SerialClone.clone(ukladzrodlowy);
                        ukladBR.setPodatnik(wpisView.getPodatnikObiekt());
                        ukladBR.setRok(ukladdocelowyrok);
                        ukladBR.setImportowany(true);
                        ukladBRDAO.create(ukladBR);
                        PlanKontFKKopiujBean.implementujRZiS(pozycjaRZiSDAO, ukladzrodlowy, wpisView.getPodatnikWpisu(), ukladdocelowyrok, ukladzrodlowy.getUklad());
                        PlanKontFKKopiujBean.implementujBilans(pozycjaBilansDAO, ukladzrodlowy, wpisView.getPodatnikWpisu(), ukladdocelowyrok, ukladzrodlowy.getUklad());
                        Msg.msg("i", "Skopiowano układ podatnika "+ukladzrodlowy.getUklad());
                        pozycjaBRKontaView.init();
                        pozycjaBRKontaView.setUkladdocelowykonta(ukladBR);
                        pozycjaBRKontaView.setUkladzrodlowykonta(ukladzrodlowy);
                        pozycjaBRKontaView.kopiujprzyporzadkowaniekont("r", true);
                        pozycjaBRKontaView.kopiujprzyporzadkowaniekont("b", true);
                        Msg.msg("i", "Skopiowano przyporządkowanie kont z układu pierwotnego "+ukladzrodlowy.getUklad());
                        zwrot = 0;
                    };
                }
            }
        } catch (EJBException ejb) {
            Msg.msg("e", "Nieudana próba skopiowania układu. Układ za dany rok już istnieje " + ejb.getMessage());
        } catch (Exception e) {
            Msg.msg("e", "Nieudana próba skopiowania układu. " + e.getMessage());
        } finally {
          return zwrot;
        }
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public PlanKontView getPlanKontView() {
        return planKontView;
    }

    public void setPlanKontView(PlanKontView planKontView) {
        this.planKontView = planKontView;
    }

    public PozycjaBRKontaView getPozycjaBRKontaView() {
        return pozycjaBRKontaView;
    }

    public void setPozycjaBRKontaView(PozycjaBRKontaView pozycjaBRKontaView) {
        this.pozycjaBRKontaView = pozycjaBRKontaView;
    }


  
    
    
    
}
