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
    
    private static final String LINIA = "Matylda jest; gupia; i;'Ma piecset; sera';";
    private static final char DZIELNIK = ';';
    private static final char CUDZY = '"';
    private static boolean szukamy;
    private static int miejsceod = -1;
    private static int miejscedo;
    
    public static void main(String[] args) {
        char[] array = LINIA.toCharArray();
        int i = 0;
        for (char ch : array) {
            if (ch==DZIELNIK) {
                szukamy = true;
                System.out.println ("średnik");
            } else if (szukamy && ch==DZIELNIK) {
                ch =' ';
            } else if (szukamy && ch==CUDZY) {
                miejsceod = i;
                System.out.println ("cudzysłów po średniku");
            } else if (miejsceod > -1  && ch==CUDZY) {
                miejscedo = i;
            } else {
                System.out.println (ch);
            }
        }
    }
}
