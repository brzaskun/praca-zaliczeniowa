/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beansDok;

import embeddable.PodatnikRecord;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class DayUpdater {

    private static final Map<Integer, Consumer<PodatnikRecord>> daySetters = new HashMap<>();

    static {
        daySetters.put(1, recorda -> recorda.setDay1(recorda.getDay1() + 1));
        daySetters.put(2, recorda -> recorda.setDay2(recorda.getDay2() + 1));
        daySetters.put(3, recorda -> recorda.setDay3(recorda.getDay3() + 1));
        daySetters.put(4, recorda -> recorda.setDay4(recorda.getDay4() + 1));
        daySetters.put(5, recorda -> recorda.setDay5(recorda.getDay5() + 1));
        daySetters.put(6, recorda -> recorda.setDay6(recorda.getDay6() + 1));
        daySetters.put(7, recorda -> recorda.setDay7(recorda.getDay7() + 1));
        daySetters.put(8, recorda -> recorda.setDay8(recorda.getDay8() + 1));
        daySetters.put(9, recorda -> recorda.setDay9(recorda.getDay9() + 1));
        daySetters.put(10, recorda -> recorda.setDay10(recorda.getDay10() + 1));
        daySetters.put(11, recorda -> recorda.setDay11(recorda.getDay11() + 1));
        daySetters.put(12, recorda -> recorda.setDay12(recorda.getDay12() + 1));
        daySetters.put(13, recorda -> recorda.setDay13(recorda.getDay13() + 1));
        daySetters.put(14, recorda -> recorda.setDay14(recorda.getDay14() + 1));
        daySetters.put(15, recorda -> recorda.setDay15(recorda.getDay15() + 1));
        daySetters.put(16, recorda -> recorda.setDay16(recorda.getDay16() + 1));
        daySetters.put(17, recorda -> recorda.setDay17(recorda.getDay17() + 1));
        daySetters.put(18, recorda -> recorda.setDay18(recorda.getDay18() + 1));
        daySetters.put(19, recorda -> recorda.setDay19(recorda.getDay19() + 1));
        daySetters.put(20, recorda -> recorda.setDay20(recorda.getDay20() + 1));
        daySetters.put(21, recorda -> recorda.setDay21(recorda.getDay21() + 1));
        daySetters.put(22, recorda -> recorda.setDay22(recorda.getDay22() + 1));
        daySetters.put(23, recorda -> recorda.setDay23(recorda.getDay23() + 1));
        daySetters.put(24, recorda -> recorda.setDay24(recorda.getDay24() + 1));
        daySetters.put(25, recorda -> recorda.setDay25(recorda.getDay25() + 1));
        daySetters.put(26, recorda -> recorda.setDay26(recorda.getDay26() + 1));
        daySetters.put(27, recorda -> recorda.setDay27(recorda.getDay27() + 1));
        daySetters.put(28, recorda -> recorda.setDay28(recorda.getDay28() + 1));
        daySetters.put(29, recorda -> recorda.setDay29(recorda.getDay29() + 1));
        daySetters.put(30, recorda -> recorda.setDay30(recorda.getDay30() + 1));
        daySetters.put(31, recorda -> recorda.setDay31(recorda.getDay31() + 1));
    }
    
    // Prywatny konstruktor uniemożliwiający tworzenie instancji
    private DayUpdater() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    public static void incrementDay(PodatnikRecord recorda, int dayOfMonth) {
        Consumer<PodatnikRecord> setter = daySetters.get(dayOfMonth);
        if (setter != null) {
            setter.accept(recorda);
        }
    }
    

    public static void incrementDay(PodatnikRecord recorda, int day, int count) {
        switch (day) {
            case 1:
                recorda.setDay1(recorda.getDay1() + count);
                break;
            case 2:
                recorda.setDay2(recorda.getDay2() + count);
                break;
            case 3:
                recorda.setDay3(recorda.getDay3() + count);
                break;
            case 4:
                recorda.setDay4(recorda.getDay4() + count);
                break;
            case 5:
                recorda.setDay5(recorda.getDay5() + count);
                break;
            case 6:
                recorda.setDay6(recorda.getDay6() + count);
                break;
            case 7:
                recorda.setDay7(recorda.getDay7() + count);
                break;
            case 8:
                recorda.setDay8(recorda.getDay8() + count);
                break;
            case 9:
                recorda.setDay9(recorda.getDay9() + count);
                break;
            case 10:
                recorda.setDay10(recorda.getDay10() + count);
                break;
            case 11:
                recorda.setDay11(recorda.getDay11() + count);
                break;
            case 12:
                recorda.setDay12(recorda.getDay12() + count);
                break;
            case 13:
                recorda.setDay13(recorda.getDay13() + count);
                break;
            case 14:
                recorda.setDay14(recorda.getDay14() + count);
                break;
            case 15:
                recorda.setDay15(recorda.getDay15() + count);
                break;
            case 16:
                recorda.setDay16(recorda.getDay16() + count);
                break;
            case 17:
                recorda.setDay17(recorda.getDay17() + count);
                break;
            case 18:
                recorda.setDay18(recorda.getDay18() + count);
                break;
            case 19:
                recorda.setDay19(recorda.getDay19() + count);
                break;
            case 20:
                recorda.setDay20(recorda.getDay20() + count);
                break;
            case 21:
                recorda.setDay21(recorda.getDay21() + count);
                break;
            case 22:
                recorda.setDay22(recorda.getDay22() + count);
                break;
            case 23:
                recorda.setDay23(recorda.getDay23() + count);
                break;
            case 24:
                recorda.setDay24(recorda.getDay24() + count);
                break;
            case 25:
                recorda.setDay25(recorda.getDay25() + count);
                break;
            case 26:
                recorda.setDay26(recorda.getDay26() + count);
                break;
            case 27:
                recorda.setDay27(recorda.getDay27() + count);
                break;
            case 28:
                recorda.setDay28(recorda.getDay28() + count);
                break;
            case 29:
                recorda.setDay29(recorda.getDay29() + count);
                break;
            case 30:
                recorda.setDay30(recorda.getDay30() + count);
                break;
            case 31:
                recorda.setDay31(recorda.getDay31() + count);
                break;
            default:
                throw new IllegalArgumentException("Invalid day: " + day);
        }
    }


}
