/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpk.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class PolaJPK_VAT2 implements Serializable {
    private static final long serialVersionUID = 1L;
    public static List<PoleJPK> polajpk;
    
    static {
        polajpk = new ArrayList<>();
        polajpk.add(new PoleJPK("K_10","Netto - Dostawa towarów oraz świadczenie usług na terytorium kraju, zwolnione od podatku"));
        polajpk.add(new PoleJPK("K_11","Netto - Dostawa towarów oraz świadczenie usług poza terytorium kraju"));
        polajpk.add(new PoleJPK("K_12","Netto - w tym świadczenie usług, o których mowa w art. 100 ust. 1 pkt 4 ustawy"));
        polajpk.add(new PoleJPK("K_13","Netto - Dostawa towarów oraz świadczenie usług na terytorium kraju, opodatkowane stawką 0%"));
        polajpk.add(new PoleJPK("K_14","Netto - w tym dostawa towarów, o której mowa w art. 129 ustawy"));
        polajpk.add(new PoleJPK("K_15","Netto - Dostawa towarów oraz świadczenie usług na terytorium kraju, opodatkowane stawką 5%"));
        polajpk.add(new PoleJPK("K_16","VAT należny - Dostawa towarów oraz świadczenie usług na terytorium kraju, opodatkowane stawką 5%", true));
        polajpk.add(new PoleJPK("K_17","Netto - Dostawa towarów oraz świadczenie usług na terytorium kraju, opodatkowane stawką 7% albo 8%"));
        polajpk.add(new PoleJPK("K_18","VAT należny - Dostawa towarów oraz świadczenie usług na terytorium kraju, opodatkowane stawką 7% albo 8%", true));
        polajpk.add(new PoleJPK("K_19","Netto - Dostawa towarów oraz świadczenie usług na terytorium kraju, opodatkowane stawką 22% albo 23%"));
        polajpk.add(new PoleJPK("K_20","VAT należny - Dostawa towarów oraz świadczenie usług na terytorium kraju, opodatkowane stawką 22% albo 23%", true));
        polajpk.add(new PoleJPK("K_21","Netto - Wewnątrzwspólnotowa dostawa towarów"));
        polajpk.add(new PoleJPK("K_22","Netto - Eksport towarów"));
        polajpk.add(new PoleJPK("K_23","Netto - Wewnątrzwspólnotowe nabycie towarów"));
        polajpk.add(new PoleJPK("K_24","VAT należny - Wewnątrzwspólnotowe nabycie towarów", true));
        polajpk.add(new PoleJPK("K_25","Netto - Import towarów podlegający rozliczeniu zgodnie z art. 33a ustawy"));
        polajpk.add(new PoleJPK("K_26","VAT należny - Import towarów podlegający rozliczeniu zgodnie z art. 33a ustawy", true));
        polajpk.add(new PoleJPK("K_27","Netto - Import usług z wyłączeniem usług nabywanych od podatników podatku od wartości dodanej, do których stosuje się art. 28b ustawy"));
        polajpk.add(new PoleJPK("K_28","VAT należny - Import usług z wyłączeniem usług nabywanych od podatników podatku od wartości dodanej, do których stosuje się art. 28b ustawy", true));
        polajpk.add(new PoleJPK("K_29","Netto - Import usług nabywanych od podatników podatku od wartości dodanej, do których stosuje się art. 28b ustawy"));
        polajpk.add(new PoleJPK("K_30","VAT należny - Import usług nabywanych od podatników podatku od wartości dodanej, do których stosuje się art. 28b ustawy", true));
        polajpk.add(new PoleJPK("K_31","Netto - Dostawa towarów oraz świadczenie usług, dla których podatnikiem jest nabywca zgodnie z art. 17 ust. 1 pkt 7 lub 8 ustawy (wypełnia dostawca)"));
        polajpk.add(new PoleJPK("K_32","Netto - Dostawa towarów, dla których podatnikiem jest nabywca zgodnie z art. 17 ust. 1 pkt 5 ustawy (wypełnia nabywca)"));
        polajpk.add(new PoleJPK("K_33","VAT należny - Dostawa towarów, dla których podatnikiem jest nabywca zgodnie z art. 17 ust. 1 pkt 5 ustawy (wypełnia nabywca)", true));
        polajpk.add(new PoleJPK("K_34","Netto - Dostawa towarów oraz świadczenie usług, dla których podatnikiem jest nabywca zgodnie z art. 17 ust. 1 pkt 7 lub 8 ustawy (wypełnia nabywca)"));
        polajpk.add(new PoleJPK("K_35","VAT należny - Dostawa towarów oraz świadczenie usług, dla których podatnikiem jest nabywca zgodnie z art. 17 ust. 1 pkt 7 lub 8 ustawy (wypełnia nabywca)", true));
        polajpk.add(new PoleJPK("K_36","VAT należny od towarów i usług objętych spisem z natury, o którym mowa w art. 14 ust. 5 ustawy", true));
        polajpk.add(new PoleJPK("K_37","Zwrot odliczonej lub zwróconej kwoty wydatkowanej na zakup kas rejestrujących, o którym mowa w art. 111 ust. 6 ustawy", true));
        polajpk.add(new PoleJPK("K_38","VAT należny od wewnątrzwspólnotowego nabycia środków transportu, wykazanego w elemencie K_24, podlegająca wpłacie w terminie, o którym mowa w art. 103 ust. 3, w związku z ust. 4 ustawy", true));
        polajpk.add(new PoleJPK("K_39","Kwota podatku od wewnątrzwspólnotowego nabycia paliw silnikowych, podlegająca wpłacie w terminach, o których mowa w art. 103 ust. 5a i 5b ustawy", true));
        polajpk.add(new PoleJPK("K_43","Netto - Nabycie towarów i usług zaliczanych u podatnika do środków trwałych"));
        polajpk.add(new PoleJPK("K_44","Kwota podatku naliczonego - Nabycie towarów i usług zaliczanych u podatnika do środków trwałych", true));
        polajpk.add(new PoleJPK("K_45","Netto - Nabycie towarów i usług pozostałych"));
        polajpk.add(new PoleJPK("K_46","Kwota podatku naliczonego - Nabycie towarów i usług pozostałych", true));
        polajpk.add(new PoleJPK("K_47","Korekta podatku naliczonego od nabycia środków trwałych", true));
        polajpk.add(new PoleJPK("K_48","Korekta podatku naliczonego od pozostałych nabyć", true));
        polajpk.add(new PoleJPK("K_49","Korekta podatku naliczonego, o której mowa w art. 89b ust. 1 ustawy", true));
        polajpk.add(new PoleJPK("K_50","Korekta podatku naliczonego, o której mowa w art. 89b ust. 4 ustawy", true));
    }

    public List<PoleJPK> getPolajpk() {
        return polajpk;
    }

    
    
}
