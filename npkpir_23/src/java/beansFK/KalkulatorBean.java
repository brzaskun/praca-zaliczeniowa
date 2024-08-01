/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beansFK;

import entityfk.StronaWiersza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class KalkulatorBean implements Serializable {

    private String wyrazenie;
    private String update;
    private double wynik;
    private StronaWiersza stronaWiersza;

    // Gettery i settery
    public String getWyrazenie() {
        return wyrazenie;
    }

    public void setWyrazenie(String wyrazenie) {
        this.wyrazenie = wyrazenie;
        przeliczWyrazenie();
    }

    public double getWynik() {
        return wynik;
    }

    public void setWynik(double wynik) {
        this.wynik = wynik;
    }

    public StronaWiersza getStronaWiersza() {
        return stronaWiersza;
    }

    public void setStronaWiersza(StronaWiersza stronaWiersza) {
        this.stronaWiersza = stronaWiersza;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
    

    // Przelicz wyrażenie matematyczne
    private void przeliczWyrazenie() {
        if (wyrazenie != null && !wyrazenie.isEmpty()) {
            try {
                wynik = eval(wyrazenie);
                //Msg.msg("nowy wynik "+wynik);
            } catch (Exception e) {
                wynik = 0.0; // lub obsłuż błąd odpowiednio
            }
            stronaWiersza.setKwota(wynik);
            
        } else {
            wynik = 0.0;
        }
    }

    // Funkcja do ewaluacji wyrażenia matematycznego
    private double eval(String wyrazenie) {
        List<String> tokens = tokenize(wyrazenie);
        Stack<Double> values = new Stack<>();
        Stack<String> ops = new Stack<>();

        for (String token : tokens) {
            token = token.replace(",", ".");
            if (isNumber(token)) {
                values.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                while (!ops.isEmpty() && hasPrecedence(token, ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(token);
            }
        }

        while (!ops.isEmpty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private List<String> tokenize(String wyrazenie) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (char c : wyrazenie.toCharArray()) {
            if (isOperator(Character.toString(c))) {
                if (sb.length() > 0) {
                    tokens.add(sb.toString());
                    sb.setLength(0);
                }
                tokens.add(Character.toString(c));
            } else {
                sb.append(c);
            }
        }
        if (sb.length() > 0) {
            tokens.add(sb.toString());
        }
        return tokens;
    }

    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private boolean hasPrecedence(String op1, String op2) {
        if ((op1.equals("*") || op1.equals("/")) && (op2.equals("+") || op2.equals("-"))) {
            return false;
        }
        return true;
    }

    private double applyOp(String op, double b, double a) {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    throw new UnsupportedOperationException("Nie można dzielić przez zero");
                }
                return a / b;
        }
        return 0;
    }
    
    public void close() {
        int lpWierszaWpisywanie = stronaWiersza.getWiersz().getIdporzadkowy() - 1;
        String wnma = stronaWiersza.getWnma().toLowerCase();
        update = "formwpisdokument:dataList:"+lpWierszaWpisywanie+":"+wnma+"_input";
        String skrypt = "$(document.getElementById('"+update+"')).trigger('select')";
        PrimeFaces.current().executeScript(skrypt);
        wyrazenie = null;
    }
    
    
}
    

