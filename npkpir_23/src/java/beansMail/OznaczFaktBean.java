/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansMail;

import dao.FakturaDAO;
import entity.Faktura;
import entity.Klienci;
import java.util.List;
import msg.Msg;

/**
 *
 * @author Osito
 */
public class OznaczFaktBean {

    public static void oznaczonejakozaksiegowane(List<Faktura> fakturydomaila, FakturaDAO fakturaDAO) {
        if (fakturydomaila != null) {
            for (Faktura faktura : fakturydomaila) {
                Klienci klientf = faktura.getKontrahent();
                Msg.msg("i", "Oznaczono faktur\u0119 jako zaksi\u0119gowan\u0105 " + klientf.getNpelna());
                faktura.setZaksiegowana(true);
            }
            fakturaDAO.edit(fakturydomaila);
        }
    }

    public static void oznaczonejakowyslane(List<Faktura> fakturydomaila, FakturaDAO fakturaDAO) {
        if (fakturydomaila != null) {
            for (Faktura faktura : fakturydomaila) {
                Klienci klientf = faktura.getKontrahent();
                Msg.msg("i", "Oznaczono faktur\u0119 jako wys\u0142an\u0105 do klienta " + klientf.getNpelna());
                faktura.setWyslana(true);
            }
            fakturaDAO.edit(fakturydomaila);
        }
    }
    
}
