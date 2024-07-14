/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interceptor;

import java.io.Serializable;
import javax.interceptor.Interceptor;

/**
 *
 * @author Osito
 */
@Interceptor
public class ConstructorInterceptor implements Serializable{
    private static final long serialVersionUID = 1L;
//    @AroundConstruct
//    public void interceptConstruction(InvocationContext ctx) {
//        System.out.println("Intercepting constructor call. "+ctx.getConstructor().getName());
//        try {
//            ctx.proceed(); // kontynuacja wywołania konstruktora
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @PostConstruct
//    public void postConstruct(InvocationContext ctx) {
//        System.out.println("PostConstruct method called.");
//    }
}
