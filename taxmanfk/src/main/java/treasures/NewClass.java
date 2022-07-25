/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package treasures;

import embeddable.Testowa;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author Osito
 */
public class NewClass {
    //jak wywolac metode z innego pliku gdzie jest duzo pol nozniacych sie koncowka
     private void Methodreflection() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
            Class[] paramString = new Class[1];	
            paramString[0] = String.class;
            Method met = Testowa.class.getDeclaredMethod("setPole"+"1", paramString);
            Testowa temp = new Testowa();
            met.invoke(temp, new String("mkyong"));
            Class[] noparams = {};
            met = Testowa.class.getDeclaredMethod("getPole"+"1", noparams);
            Object ob = met.invoke(temp, (Object[]) null);
            String wynik = ob.toString();
    }
}
