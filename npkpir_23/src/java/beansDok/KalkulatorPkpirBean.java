/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beansDok;

import entity.KwotaKolumna1;
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
public class KalkulatorPkpirBean implements Serializable {

    private String wyrazenie;
    private String update;
    private double wynik;
    private KwotaKolumna1 kwotaKolumna1;

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

  
    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public KwotaKolumna1 getKwotaKolumna1() {
        return kwotaKolumna1;
    }

    public void setKwotaKolumna1(KwotaKolumna1 kwotaKolumna1) {
        this.kwotaKolumna1 = kwotaKolumna1;
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
            kwotaKolumna1.setNetto(wynik);
            
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
        update = "dodWiad:tabelapkpir:0:kwotaPkpir_input";
        String skrypt = "$(document.getElementById('"+update+"')).trigger('select')";
        PrimeFaces.current().executeScript(skrypt);
        wyrazenie = null;
    }
    
   
    
}
    

