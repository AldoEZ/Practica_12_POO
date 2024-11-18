package mx.unam.fi.poo.g1.p12.e1;

public class Ejercicio1 {
    private static final int NUM_MAX = 20;
    private static Object lock = new Object();
    private static boolean esPar = true;
    
    public static void main(String[] args) {
        Thread parH = new Thread(() -> {
            for(int i = 2; i <= NUM_MAX; i+=2) {
                synchronized(lock) {
                    while(!esPar) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Numeros pares del hilo: " + i);
                    esPar = false;
                    lock.notify();
                }
            }
        });
        
        Thread imparH = new Thread(() -> {
            for(int i = 1; i <= NUM_MAX; i+=2) {
                synchronized(lock) {
                    while(esPar) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Numeros impares del Hilo: " + i);
                    esPar = true;
                    lock.notify();
                }
            }
        });
        
        parH.start();
        imparH.start();
    }
}
