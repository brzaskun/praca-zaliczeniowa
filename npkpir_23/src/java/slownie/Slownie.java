/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /**
 *
 * @author gmmobile.pl
 */
package slownie;

import java.text.DecimalFormat;

public class Slownie {


    // Tblica opisów wartości jednostek.
    static String[] Units =
    {
        "zero", "jeden", "dwa", "trzy", "cztery", "pięć", "sześć",
        "siedem", "osiem", "dziewięć", "dziesięć", "jedenaście",
        "dwanaście", "trzynaście", "czternaście", "piętnaście",
        "szesnaście", "siedemnaście", "osiemnaście", "dziewiętnaście"
    };

    //Tablica opisów dziesiątek
    static String[] Tens =
    {
        "dwadzieścia", "trzydzieści", "czterdzieści", "pięćdziesiąt",
        "sześćdziesiąt", "siedemdziesiąt", "osiemdziesiąt",
        "dziewięćdziesiąt"
    };

    //Tablica obisów setek
    static String[] Hundreds =
    {
        "", "sto", "dwieście", "trzysta", "czterysta", "pięćset",
        "sześćset", "siedemset", "osiemset", "dziewięćset"
    };

    //Dwuwymiarowa tablica tysięcy,milionów,miliarów ...
    static String[][] OtherUnits =
    {
        { "tysiąc", "tysiące", "tysięcy"     },
        { "milion", "miliony", "milionów"    },
        { "miliard", "miliardy", "miliardów" }
    };

    /**
     * @param args the command line arguments
     * Wartość obliczana
     */    public static String slownie(String param)
     {
         // TODO code application logic here
         try
         {
             {
                 String kwota = param;
                 double dkwota = 0;
                 //Odczyt parametrów plików
                 
                 int argumenty = param.length();
                 if (argumenty == 0)
                 {
                     return null;
                 }
                 try
                 {
                     dkwota =  Double.parseDouble(kwota);
                 }
                 catch (Exception ex)
                 {
                     return null;
                 }
                 String kwotaSlownie = "";
                 kwotaSlownie = procKwotaSlownie (dkwota, "zł");
                 return kwotaSlownie;
             }
         }
         catch (Exception ex)
         {
             //Wyjątek błędu aplikacji
             return "Bład "+ex.getMessage();
         }
     }
     
     public static String slownie(String param, String skrot)
     {
         // TODO code application logic here
         try
         {
             {
                 String kwota = param;
                 double dkwota = 0;
                 //Odczyt parametrów plików
                 
                 int argumenty = param.length();
                 if (argumenty == 0)
                 {
                     return null;
                 }
                 try
                 {
                     dkwota =  Double.parseDouble(kwota);
                 }
                 catch (Exception ex)
                 {
                     return null;
                 }
                 String kwotaSlownie = "";
                 kwotaSlownie = procKwotaSlownie (dkwota,skrot);
                 return kwotaSlownie;
             }
         }
         catch (Exception ex)
         {
             //Wyjątek błędu aplikacji
             return "Bład "+ex.getMessage();
         }
     }

    //Konwersja małych liczb ....
    static String SmallValueToWords(int n)
    {
        if (n == 0)
        {            
            return null;
        }

        StringBuilder valueInWords = new StringBuilder();

        // Konwertuj setki.

        int temp = n / 100;
        if (temp > 0)
        {
            valueInWords.append(Hundreds[temp]);
            n -= temp * 100;
        }

        // Konwertuj dziesiątki i jedności.

        if (n > 0)
        {
            if (valueInWords.length()> 0)
            {
                valueInWords.append(" ");
            }

            if (n < 20)
            {
                //  Liczby poniżej 20 przekonwertuj na podstawie
                //  tablicy jedności.

                valueInWords.append(Units[n]);
            }
            else
            {
                //  Większe liczby przekonwertuj łącząc nazwy
                //  krotności dziesiątek z nazwami jedności.
                valueInWords.append(Tens[(n / 10) - 2]);
                int lastDigit = n % 10;

                if (lastDigit > 0)
                {
                    valueInWords.append(" ");
                    valueInWords.append(Units[lastDigit]);
                }
            }
        }
        return valueInWords.toString();
    }

    //Obliczenia dla dużych liczb ... i odmiana prawidłowa ich wartości..
    static int GetBigUnitIndex(long n)
    {
        int lastDigit = (int)n % 10;

        if ((n >= 10 && (n <= 20 || lastDigit == 0)) ||
                (lastDigit > 4))
        {
            return 2;
        }
        return (lastDigit == 1) ? 0 : 1;
    }

    static long ToWords(StringBuilder valueInWords, long n, int level)
    {
        int smallValue = 0;
        //long divisor = (long)Math.pow(10000, (long)level + 1);
        long divisor = (long)Math.pow(1000, (long)level + 1);

        if (divisor <= n)
        {
            //  Jeśli liczbę da się podzielić przez najbliższą
            //  potęgę 1000, kontynuuj rekurencję.

            n = ToWords(valueInWords, n, level + 1);
            smallValue = (int)(n / divisor);

            if (valueInWords.length() > 0)
            {
                valueInWords.append(" ");
            }

            if (smallValue > 1)
            {
                valueInWords.append(SmallValueToWords(smallValue));
                valueInWords.append(" ");
            }
            valueInWords.append( OtherUnits[level][GetBigUnitIndex(smallValue)]);
        }

        return n - smallValue * divisor;
    }

 

    static String ToWords(long value) {
        if (value == 0)
        {
            // Zero.
            return Units[0];
        }
        StringBuilder valueInWords = new StringBuilder();
        int smallValue = (int)ToWords(valueInWords, value, 0);
        if (smallValue > 0)
        {
            if (valueInWords.length() > 0)
            {
                valueInWords.append(" ");
            }
            valueInWords.append(SmallValueToWords(smallValue));
        }
        return valueInWords.toString();
    }

    static long liczba_zlotych(double kwota) 
    {
        
        String kwotaString = "" + kwota;
        kwotaString = kwotaString.substring(0, kwotaString.indexOf('.'));
        Long dzlote = new Long(kwotaString);
        return dzlote;
    }

    static long liczba_groszy (double grosze)
    {
        //Tworzę format zmiennych aby uzyskać liczbę w frmie tekstowej
        DecimalFormat dfx =  new DecimalFormat("0.00");
        String szlote = dfx.format(grosze);
        //Odcinam grosze
        String bgzlote = szlote.substring(0,szlote.length()-3);        
        Double dzlote = new Double(bgzlote);
        //Od kowty z groszami odejmuję kwotę bez.
        Long groszy  = (long)(grosze*100 - dzlote*100);        
        return groszy;
    }

    static String procKwotaSlownie(double kwota, String skrot) {
        //Generalna funkcja ptzetworzenia zmiennej
        if (kwota < 0)
        {
            kwota = kwota * -1;
        }
        String strKwotaSl = "";
        if (skrot.equals("zł")) {
            strKwotaSl = ToWords(liczba_zlotych(kwota))+ " zł "+ ToWords(liczba_groszy(kwota))+" gr.";
        } else {
            strKwotaSl = ToWords(liczba_zlotych(kwota))+ " "+skrot+" "+ ToWords(liczba_groszy(kwota))+"";
        }
        return strKwotaSl;
    }
}

