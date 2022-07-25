/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treasures;

/**
 *
 * @author Osito
 */
public class Filtrcsvbezsrednika {
    
    private static final String LINIA = "Matylda jest; 'gu;pia ;;xsx'; i;\"Ma piecset; sera\";";
    private static final char DZIELNIK = ';';
    private static final char CUDZY = '\"';
    private static boolean szukamy;
    private static boolean jestcudzy;
    
    
    public static String usunsrednik(String linia, char dzielnik, char cudzy) {
        char[] array = linia.toCharArray();
        boolean szukamy = false;
        boolean jestcudzy = false;
        for (int i = 0; i < array.length; i++) {
            char ch = array[i];
            if (!szukamy && ch==dzielnik) {
                szukamy = true;
            } else if (szukamy && !jestcudzy && ch==cudzy) {
                jestcudzy = true;
            } else if (jestcudzy && ch==dzielnik) {
                array[i] =' ';
            } else if (szukamy && jestcudzy && ch==cudzy) {
                szukamy= false;
                jestcudzy = false;
            } 
        }
        String zwrot = new String(array);
        return zwrot;
    }
    
     public static String usunprzecinek(String linia, char dzielnik, char cudzy) {
        char[] array = linia.toCharArray();
        boolean szukamy = false;
        boolean jestcudzy = false;
        for (int i = 0; i < array.length; i++) {
            char ch = array[i];
            if (!szukamy && ch==dzielnik) {
                szukamy = true;
            } else if (szukamy && !jestcudzy && ch==cudzy) {
                jestcudzy = true;
                array[i] =' ';
            } else if (jestcudzy && ch==dzielnik) {
                array[i] ='.';
            } else if (szukamy && jestcudzy && ch==cudzy) {
                szukamy= false;
                jestcudzy = false;
                array[i] =' ';
            } 
        }
        String zwrot = new String(array);
        return zwrot;
    }
    
//    public static void main(String[] args) {
//        char[] array = LINIA.toCharArray();
//        System.out.println(LINIA);
//        for (int i = 0; i < array.length; i++) {
//            char ch = array[i];
//            if (!szukamy && ch==DZIELNIK) {
//                szukamy = true;
//            } else if (szukamy && !jestcudzy && ch==CUDZY) {
//                jestcudzy = true;
//            } else if (jestcudzy && ch==DZIELNIK) {
//                array[i] =' ';
//            } else if (szukamy && jestcudzy && ch==CUDZY) {
//                szukamy= false;
//                jestcudzy = false;
//            } 
//        }
//        String zwrot = new String(array);
//        System.out.println(zwrot);
//    }
//    
    public static void main(String[] args) {
        System.out.println(LINIA);
        String zwrot = usunsrednik(LINIA, DZIELNIK, CUDZY);
        System.out.println(zwrot);
    }
}
