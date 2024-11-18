package mx.unam.fi.poo.g1.p12;

import mx.unam.fi.poo.g1.p12.*;

/**
 * Clase principal de Practica 12
 * @author Estrada Zacarias Aldo Axel
 * @version 12-Noviembre-2024
 */

public class Practica12 extends Thread {
    private static CuentaBanco cb = new CuentaBanco(0.0);
    
    /**
     * Metodo constructor: 
     * Para construir objetos de tipo Practica12
     * @param nombre -> Atributo que asigna un nombre al objeto.
     */
    public Practica12(String nombre) { 
        super(nombre);
    }
    
    /**
     * Metodo run: 
     * Metodo que le da comportamiento a cada hilo dependiendo de su nombre.
     */
    public void run() {
        if(getName().equals("Deposito")) cb.depositar(100.5);
        else if(getName().equals("Retiro")) cb.retirar(99.5);
    }
    
    /**
     * Método main
     * Se ejecuta toda la aplicacion.
     * @param args -> Arreglo por defecto del método main.
     */
    public static void main(String[] args) {
        for(int i = 0; i < 5; i++) {
            int aux = (int) (Math.random()*10);
            if(aux%2 == 0) new Practica12("Deposito").start();
            else new Practica12("Retiro").start();
        }
    }
}
