/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import testobjects.Konto;
import testobjects.WierszBO;
import static testobjects.testobjects.getKlienci;
import static testobjects.testobjects.getPodatnik;
import static testobjects.testobjects.getRodzajedok;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class BilansGenerowanieView implements Serializable {
    
    public static void generujbilans() {
        String podatnik = "Lolo";
        List<testobjects.Konto> saldakont = pobierzkonta(podatnik);
        List<testobjects.WierszBO> wierszeBO = generujwierszeBO(saldakont);
        assert wierszeBO.size() == 10 : "za malo wierszy "+wierszeBO.size();
        assert wierszeBO.get(9).getKwotaWn() == 150.0 : "nieprawidlowa kwota "+wierszeBO.get(9).getKwotaWn();
        boolean dokumentusuniety = usunpoprzednidokumentBO(podatnik);
        assert dokumentusuniety == true : "blad nie usunieto dokumentu";
        if (dokumentusuniety) {
            Dokfk nowydokBO = generujNowyDokBO(wierszeBO, podatnik);
            assert nowydokBO.getNumerwlasnydokfk().equals("1/23/345/z") : "nie wygenerowano poprawnie";
        }
        System.out.println("koniec");
    }

    private static List<testobjects.Konto> pobierzkonta(String podatnik) {
        int i = 1;
        List<testobjects.Konto> konta = new ArrayList<>();
        konta.add(new testobjects.Konto(i++, podatnik, "Konto010", "010", 2016, 150, 0, "bilansowe"));
        konta.add(new testobjects.Konto(i++, podatnik, "Konto020", "020", 2016, 450, 0, "bilansowe"));
        konta.add(new testobjects.Konto(i++, podatnik, "Konto100", "100", 2016, 1250, 0, "bilansowe"));
        konta.add(new testobjects.Konto(i++, podatnik, "Konto201", "201", 2016, 1750, 0, "bilansowe"));
        konta.add(new testobjects.Konto(i++, podatnik, "Konto202", "202", 2016, 0, 1950, "bilansowe"));
        konta.add(new testobjects.Konto(i++, podatnik, "Konto300", "300", 2016, 400, 0, "bilansowe"));
        konta.add(new testobjects.Konto(i++, podatnik, "Konto645", "645", 2016, 0, 550, "bilansowe"));
        konta.add(new testobjects.Konto(i++, podatnik, "Konto821", "821", 2016, 0, 2000, "bilansowe"));
        konta.add(new testobjects.Konto(i++, podatnik, "Konto860", "860", 2016, 150, 0, "bilansowe"));
        konta.add(new testobjects.Konto(i++, podatnik, "Konto871", "871", 2016, 150, 0, "bilansowe"));
        return konta;
    }

    private static List<testobjects.WierszBO> generujwierszeBO(List<testobjects.Konto> saldakont) {
        List<testobjects.WierszBO> wierszeBO = new ArrayList<>();
        if (!saldakont.isEmpty()) {
            for (testobjects.Konto p : saldakont) {
                wierszeBO.add(new testobjects.WierszBO(p.getPodatnik(), p, p.getSaldoWn(), p.getSaldoMa()));
            }
        }
        return wierszeBO;
    }
    public static void main(String[] args){
         generujbilans();
    }

    private static boolean usunpoprzednidokumentBO(String podatnik) {
        return true;
    }

    private static Dokfk generujNowyDokBO(List<WierszBO> wierszeBO, String podatnik) {
        Dokfk nowydok = getDokfk("BO",podatnik);
        return nowydok;
    }
    
    public static Dokfk getDokfk(String rodzaj, String podatnik) {
        DokfkPK dp = new DokfkPK(rodzaj, 12, podatnik, "2015");
        Dokfk d = new Dokfk(dp);
        d.setDatadokumentu("2015-03-01");
        d.setDataoperacji("2015-03-02");
        d.setDatawplywu("2015-03-05");
        d.setDatawystawienia("2015-03-06");
        d.setRodzajedok(getRodzajedok(rodzaj));
        d.setPodatnikObj(getPodatnik());
        d.setKontr(getKlienci());
        d.setNumerwlasnydokfk("1/23/345/z");
        d.setMiesiac("02");
        return d;
    }
}
