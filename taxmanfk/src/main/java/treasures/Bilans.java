/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treasures;

import entityfk.StronaWiersza;
import java.util.List;
import java.util.concurrent.atomic.DoubleAccumulator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class Bilans {
      public static void main(String[] args)  {
        EntityManagerFactory emfH2 = javax.persistence.Persistence.createEntityManagerFactory("JavaApplication4PU");
        EntityManager emH2 = emfH2.createEntityManager();
//       List<Podatnik> podatnicy = emH2.createQuery("SELECT o FROM Podatnik o ORDER BY o.printnazwa").getResultList();
//         List<Konto> konta = emH2.createQuery("SELECT o FROM Konto o WHERE o.podatnik.id=83 AND o.rok=2020 ORDER BY o.pelnynumer").getResultList();
        List<StronaWiersza> strony = emH2.createQuery("SELECT o FROM StronaWiersza o WHERE o.konto.id=263657").getResultList();
        DoubleAccumulator obrotywn = new DoubleAccumulator(Double::sum, 0.d);
        DoubleAccumulator obrotyma = new DoubleAccumulator(Double::sum, 0.d);
        DoubleAccumulator obrotywnwaluta = new DoubleAccumulator(Double::sum, 0.d);
        DoubleAccumulator obrotymawaluta = new DoubleAccumulator(Double::sum, 0.d);
        strony.parallelStream().forEach(p -> {
            if (p.isWn()) {
                obrotywn.accumulate(Z.z(p.getKwotaPLN()));
                if (!p.getSkrotSymbolWalutBOiSW().equals("PLN")) {
                    obrotywnwaluta.accumulate(Z.z(p.getKwota()));
                }
            } else {
                obrotyma.accumulate(Z.z(p.getKwotaPLN()));
                if (!p.getSkrotSymbolWalutBOiSW().equals("PLN")) {
                    obrotymawaluta.accumulate(Z.z(p.getKwota()));
                }
            }
        });
        double saldo = obrotywn.doubleValue()-obrotyma.doubleValue();
        double saldowaluta = obrotywnwaluta.doubleValue()-obrotymawaluta.doubleValue();
        double kurs = 0.0;
        if (saldowaluta!=0.0) {
            kurs = Math.abs(saldo/saldowaluta);
        }
        System.out.println(Z.z(saldo));
        System.out.println(Z.z(saldowaluta));
        System.out.println(Z.z4(kurs));
        System.out.println("");

    }

}
