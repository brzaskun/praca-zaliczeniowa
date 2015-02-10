/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFaktura;

import dao.FakturaDAO;
import entity.Faktura;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.context.RequestContext;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class FakturaOkresowaGenNum {
    
    public static void wygenerujnumerfaktury(FakturaDAO fakturaDAO, Faktura selected, WpisView wpisView) {
         List<Faktura> wykazfaktur = fakturaDAO.findbyKontrahentNipRok(selected.getKontrahent().getNip(), wpisView.getPodatnikWpisu(), String.valueOf(wpisView.getRokWpisu()));
                int istniejafakturykontrahenta = 0;
                try {
                    if (wykazfaktur.size() > 0) {
                        istniejafakturykontrahenta = 1;
                    }
                } catch (Exception er) {
                }
                List<Faktura> wykazfakturogolem = fakturaDAO.findFakturyByRokPodatnik(wpisView.getRokWpisuSt(), wpisView.getPodatnikWpisu());
                int istniejafakturyrok = 0;
                try {
                    if (wykazfakturogolem.size() > 0) {
                        istniejafakturyrok = 1;
                    }
                } catch (Exception er) {
                }
         if (wpisView.getPodatnikObiekt().getSchematnumeracji() != null && !wpisView.getPodatnikObiekt().getSchematnumeracji().equals("")) {
                        if (istniejafakturyrok == 0) {
                            String numer = FakturaBean.uzyjwzorcagenerujpierwszynumerFaktura(wpisView.getPodatnikObiekt().getSchematnumeracji(), wpisView);
                            selected.getFakturaPK().setNumerkolejny(numer);
                            selected.setLp(1);
                            Msg.msg("i", "Generuje nową serie numerów faktury");
                        } else {
                            String numer = FakturaBean.uzyjwzorcagenerujnumerFaktura(wpisView.getPodatnikObiekt().getSchematnumeracji(), wpisView, fakturaDAO);
                            selected.getFakturaPK().setNumerkolejny(numer);
                            Faktura ostatnidokument = fakturaDAO.findOstatniaFakturaByRokPodatnik(wpisView.getRokWpisuSt(), wpisView.getPodatnikWpisu());
                            selected.setLp(ostatnidokument.getLp()+1);
                            Msg.msg("i", "Generuje kolejny numer faktury");
                        }
                        RequestContext.getCurrentInstance().update("akordeon:formstworz:nrfaktury");
                        RequestContext.getCurrentInstance().execute("przeskoczdoceny();");
                    } else {
                        if (istniejafakturykontrahenta == 0) {
                            int dlugoscnazwy = selected.getKontrahent().getNskrocona().length();
                            String nazwadofaktury = dlugoscnazwy > 4 ? selected.getKontrahent().getNskrocona().substring(0, 4) : selected.getKontrahent().getNskrocona();
                            String numer = "1/" + wpisView.getRokWpisu().toString() + "/" + nazwadofaktury;
                            selected.getFakturaPK().setNumerkolejny(numer);
                            Msg.msg("i", "Generuje nową serie numerów faktury");
                        } else {
                            String ostatniafaktura = wykazfaktur.get(wykazfaktur.size() - 1).getFakturaPK().getNumerkolejny();
                            String separator = "/";
                            String[] elementy;
                            elementy = ostatniafaktura.split(separator);
                            int starynumer = Integer.parseInt(elementy[0]);
                            starynumer++;
                            String numer = String.valueOf(starynumer);
                            int i = 0;
                            for (String p : elementy) {
                                if (i > 0) {
                                    numer += "/" + p;
                                }
                                i++;
                            }
                            selected.getFakturaPK().setNumerkolejny(numer);
                            Msg.msg("i", "Generuje kolejny numer faktury");
                        }
                        RequestContext.getCurrentInstance().update("akordeon:formstworz:nrfaktury");
                        RequestContext.getCurrentInstance().execute("przeskoczdoceny();");
                    }
    }
    
}
