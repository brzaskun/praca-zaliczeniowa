/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class WypowiedzeniePodstawa implements Serializable {
     private static final long serialVersionUID = 1L;
    
    private final static List<String> wypowiedzeniePodstawa;
    
    static {
        wypowiedzeniePodstawa = new ArrayList<>();
        wypowiedzeniePodstawa.add("art. 30 § 1 p. 1 K.p. - na mocy porozumienia stron");
        wypowiedzeniePodstawa.add("art. 30 § 1 p. 2 K.p. z upływem okresu próbnego - przez oświadczenie jednej ze stron z zachowaniem okresu wypowiedzenia (rozwiązanie umowy o pracę za wypowiedzeniem)");
        wypowiedzeniePodstawa.add("art. 30 § 1 p. 2 K.p. przez pracownika - przez oświadczenie pracownika z zachowaniem okresu wypowiedzenia (rozwiązanie umowy o pracę za wypowiedzeniem)");
        wypowiedzeniePodstawa.add("art. 30 § 1 p. 2 K.p. przez pracodawcę - przez oświadczenie pracodawcy z zachowaniem okresu wypowiedzenia (rozwiązanie umowy o pracę za wypowiedzeniem)");
        wypowiedzeniePodstawa.add("art. 30 § 1 p. 3 K.p. - przez pracownika - przez oświadczenie pracownika bez zachowania okresu wypowiedzenia (rozwiązanie umowy o pracę bez wypowiedzenia)");
        wypowiedzeniePodstawa.add("art. 30 § 1 p. 3 K.p. - przez pracodawcę - przez oświadczenie pracodawcy bez  zachowania okresu wypowiedzenia (rozwiązanie umowy o pracę bez wypowiedzenia)");
        wypowiedzeniePodstawa.add("art. 30 § 1 p. 4 K.p. - z upływem czasu, na który była zawarta");
        wypowiedzeniePodstawa.add("art. 30 § 1 p. 5 K.p. - z dniem ukończenia pracy, dla której wykoniania była zawarta");
        wypowiedzeniePodstawa.add("art. 55 § 1 i § 1(1) K.p. przez pracownika z winy pracodawcy - bez wypowiedzenia, rozwiązanie z winy pracodawcy");
        wypowiedzeniePodstawa.add("art. 52 §1 K.p. przez pracodawcę z winy pracownika - bez wypowiedzenia, rozwiązanie z winy pracownika");
        wypowiedzeniePodstawa.add("art. 177 § 1 K.p. - w okresie ciąży lub w okresie urlopu macierzyńskiego pracownicy, gdy zachodzą przyczyny uzasadniające rozwiązanie umowy bez wypowiedzenia z winy pracownicyi zakładowa organizacja związkowa wyraziła zgodę");
        wypowiedzeniePodstawa.add("art. 177 § 2 K.p. - w okresie ciąży, lub w okresie urlopy macierzyńskiego pracownicy, w okresie próbnym nie przekraczającym jednego miesiąca");
        wypowiedzeniePodstawa.add("art. 177 § 3 K.p. - umowa o pracę zawarta na czas określony lub na czas wykonania określonej pracy albo na okres próbny przekraczający jeden miesiąc, która uległaby rozwiązaniu po upływie trzeciego miesiąca ciąży, ulega przedłużeniu do dnia porodu");
        wypowiedzeniePodstawa.add("art. 23 § 4 lub § 5 K.p. - pracownik może bez wypowiedzenia, za 7-dniowym uprzedzeniem, rozwiązać stosunek pracy w terminie 2 miesięcy od przejścia zakładu pracy lub jego części na innego pracodawcę");
        wypowiedzeniePodstawa.add("art. 23 § 5 K.p. - w przypadku przejęcia zakładu pracy lub jego części, gdy w terminie, nie krótszym niż 7 dni, pracownik złożył oświadczenie o odmowie przyjęcia proponowanych warunków");
        wypowiedzeniePodstawa.add("art. 48 § 2 K.p. - pracownik, który przed przywróceniem do pracy podjął zatrudnienie u innego pracodawcy, może bez wypowiedzenia, za trzydniowym uprzedzeniem, rozwiązać umowę o pracę z tym pracodawcą w ciągu 7 dni od przywrócenia do pracy");
        wypowiedzeniePodstawa.add("art. 68 K.p. - jeżeli pracownik powołany na stanowisko w wyniku konkursu pozostaje w stosunku pracy z innym pracodawcą i obowiązuje go trzymiesięczny okres wypowiedzenia, może on rozwiązać ten stosunek za jednomiesięcznym wypowiedzeniem");
        wypowiedzeniePodstawa.add("art. 201 § 2 K.p. - jeżeli lekarz orzeknie, że dana praca zagraża zdrowiu młodocianego, a pracodawca nie ma możliwości zmianyrodzaju pracy, wówczas należy rozwiązać umowę o pracę i wpłacić odszkodowanie w wysokości wynagordzenia za okres");
        wypowiedzeniePodstawa.add("art. 63 K.p. - stosunek pracy wygasa z dniem śmierci pracownika");
        wypowiedzeniePodstawa.add("art. 66 K.p.- stosunek pracy wygasa z upływem 3 miesięcy nieobecności pracownika z powodu tymczasowego aresztowania, chyba że pracodawca rozwiązał wcześniej bez wypowiedzenia umowę o pracę z winy pracownika");
        wypowiedzeniePodstawa.add("art. 30 § 1 KP");
        wypowiedzeniePodstawa.add("Świadectwo");

    }

    public static List<String> getWypowiedzeniePodstawa() {
        return wypowiedzeniePodstawa;
    }
 
    
}
