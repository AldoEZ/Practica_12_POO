package mx.unam.fi.poo.g1.p12.e2;

import java.util.Arrays;

public class Ejercicio2 {
    private static final int ARRAY_SIZE = 400;
    private static final int NUM_HILOS = 4;
    
    public static void main(String[] args) {
        int[] array = crearArray();
        
        System.out.println("Antes de ordenar: " + Arrays.toString(array));
        
        Thread[] hilos = new Thread[NUM_HILOS];
        int tamSegmento = ARRAY_SIZE / NUM_HILOS;
        
        for(int i = 0; i < NUM_HILOS; i++) {
            int indiceInicio = i * tamSegmento;
            int indiceFin = (i == NUM_HILOS-1) ? ARRAY_SIZE -1 : (indiceInicio + tamSegmento - 1);
            
            hilos[i] = new Thread(new Ordena(array, indiceInicio, indiceFin));
            hilos[i].start();
        }
        for(Thread thread : hilos) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        mergeSort(array, 0, ARRAY_SIZE - 1);
        System.out.println("Despues de ordenar: " + Arrays.toString(array));
    } 
    private static int[] crearArray() {
        int[] array = new int[ARRAY_SIZE];
        
        for(int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = (int)(Math.random() * 400);
        }
        return array;
    }
    
    private static void mergeSort(int[] array, int left, int right) {
        if(left < right) {
            int mid = (left+right)/2;
            
            mergeSort(array, left, mid);
            mergeSort(array, mid+1, right);
            
            merge(array, left, mid, right);
        }
    }
    private static void merge(int[] array, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        
        while(i <= mid && j <= right) {
            if(array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        
        while(i <= mid) {
            temp[k++] = array[i++];
        }
        while(j <= right) {
            temp[k++] = array[j++];
        }
        
        System.arraycopy(temp, 0, array, left, temp.length);
    }
    
    static class Ordena implements Runnable {
        private int[] array;
        private int indiceInicio;
        private int indiceFin;
        
        public Ordena(int[] array, int indiceInicio, int indiceFin) {
            this.array = array;
            this.indiceInicio = indiceInicio;
            this.indiceFin = indiceFin;
        }
        
        @Override
        public void run() {
            Arrays.sort(array, indiceInicio, indiceFin+1);
        }
    }
}
