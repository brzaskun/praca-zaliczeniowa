import entity.Pitpoz;
import java.io.Serializable;
import java.util.Comparator;

public class PitpozComparator implements Comparator<Pitpoz>, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(Pitpoz o1, Pitpoz o2) {
        // Porównanie pola pkpirR (rok)
        int year1 = parseIntSafe(o1.getPkpirR());
        int year2 = parseIntSafe(o2.getPkpirR());
        int yearComparison = Integer.compare(year1, year2);

        if (yearComparison != 0) {
            return yearComparison;
        }

        // Jeśli lata są równe, porównaj pole pkpirM (miesiąc)
        int month1 = parseIntSafe(o1.getPkpirM());
        int month2 = parseIntSafe(o2.getPkpirM());
        return Integer.compare(month1, month2);
    }

    // Metoda pomocnicza do bezpiecznego parsowania liczb całkowitych
    private int parseIntSafe(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0; // Lub inna wartość domyślna w zależności od kontekstu
        }
    }
}
