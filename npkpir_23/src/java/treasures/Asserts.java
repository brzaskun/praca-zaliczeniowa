/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package treasures;

/**
 *
 * @author Osito
 */
public class Asserts {
    public static void main(String[] args){
         Object obj = "kotek";
         assert obj instanceof Double  : "Tu powinien byc String";
         System.out.println("koniec");
    }
}
