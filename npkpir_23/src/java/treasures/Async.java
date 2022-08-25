/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treasures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.commons.math3.util.CombinatoricsUtils.factorial;

/**
 *
 * @author Osito
 */
public class Async {
    
    
    public static void main(String[] args) {
        try {
            int number = 4;
            int number1 = 7;
            int number2 = 10;
            CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(() -> factorial(number));
            CompletableFuture<Long> completableFuture1 = CompletableFuture.supplyAsync(() -> factorial(number1));
            CompletableFuture<Long> completableFuture2 = CompletableFuture.supplyAsync(() -> factorial(number2));
            while (!completableFuture.isDone()) {
                System.out.println("1 is not finished yet...");
            }
            if(completableFuture.isDone()) {
                long result1 = completableFuture.get();
                System.out.println("Result1 " + result1);
            }
            while (!completableFuture1.isDone()) {
                System.out.println("2 is not finished yet...");
            }
            if(completableFuture1.isDone()) {
                long result1 = completableFuture1.get();
                System.out.println("Result2 " + result1);
            }
            while (!completableFuture2.isDone()) {
                System.out.println("3 is not finished yet...");
            }
            if(completableFuture2.isDone()) {
                long result1 = completableFuture2.get();
                System.out.println("Result3 " + result1);
            }
//            Executor executor = Executors.newFixedThreadPool(10);
//            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//                try {
//                    TimeUnit.SECONDS.sleep(10);
//                } catch (InterruptedException e) {
//                    throw new IllegalStateException(e);
//                }
//                return "Result of the asynchronous computation";
//            }, executor);
        } catch (InterruptedException ex) {
            Logger.getLogger(Async.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(Async.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
