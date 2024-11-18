package mx.unam.fi.poo.g1.p12;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.management.RuntimeErrorException;

/**
 * Clase CuentaBanco
 * @author Aldo Axel Estrada Zacarias
 * @version 12-Noviembre-2024
 */

public class CuentaBanco {
    private double saldo;
    private ReentrantLock lock;
    private Condition condicion;
    
    /**
     * Metodo constructor: 
     * Para constuir objetos CuentaBanco
     * @param saldo -> Atributo para asignar un saldo a la cuenta
     */
    public CuentaBanco(double saldo) {
        setSaldo(saldo);
        setLock();
        setCondicion();
    }
    
    /**
     * Metodo set
     * @param saldo -> Atributo para asignar saldo
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    /**
     * Metodo get
     * @return saldo -> Regresa el atributo saldo.
     */
    public double getSaldo() {
        return this.saldo;
    }
    
    /**
     * Metodo set
     * Inicializa el atributo lock
     */
    public void setLock() {
        this.lock = new ReentrantLock();
    }
    /**
     * Metodo get
     * @return lock -> Regresa el atributo lock;
     */
    public ReentrantLock getLock() {
        return this.lock;
    }
    
    /**
     * Metodo set
     * Inicializa el atributo condicion
     */
    public void setCondicion() {
        this.condicion = this.getLock().newCondition();
    }
    /**
     * Metodo get
     * @return condicion -> Regresa el atributo condicion
     */
    public Condition getCondicion() {
        return this.condicion;
    }
    
    /**
     * Metodo depositar: 
     * Hace el deposito al saldo de una cuenta de banco.
     * @param deposito -> Atributo que indica la cantidad del deposito a realizar.
     */
    public void depositar(double deposito) {
        lock.lock();
        try {
            this.setSaldo(this.getSaldo() + deposito);
            System.out.println("El deposito se realizo.");
            System.out.println("Saldo restante: " + getSaldo());
            condicion.signalAll();
        } finally {
            lock.unlock();
        }
    }
    /**
     * Metodo retirar: 
     * Metodo que retira saldo a una cuenta de banco.
     * @param retiro -> Atributo que indica la cantidad del retiro a realizar.
     */
    public void retirar(double retiro) {
        lock.lock();
        try {
            try {
                while(this.getSaldo() < retiro) {
                    System.out.println("Esperando deposito...");
                    boolean con = condicion.await(5, TimeUnit.MILLISECONDS);
                    if(!con) {
                        throw new InterruptedException();
                    }
                }
                this.setSaldo(this.getSaldo() - retiro);
                System.out.println("El retiro se realizo.");
                System.out.println("Saldo restante: " + getSaldo());
                condicion.signalAll();
            } catch (InterruptedException e) {
                System.out.println("No se pudo realizar el deposito...");
            }
        } finally {
            lock.unlock();
        }
    }
}
