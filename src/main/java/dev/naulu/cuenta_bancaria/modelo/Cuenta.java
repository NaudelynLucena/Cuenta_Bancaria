package dev.naulu.cuenta_bancaria.modelo;

public class Cuenta {
    protected float saldo;
    protected int numConsignaciones = 0;
    protected int numRetiros = 0;
    protected float tasaAnual;
    protected float comisionMensual = 0;

    public Cuenta(float saldo, float tasaAnual) {
        this.saldo = saldo;
        this.tasaAnual = tasaAnual;
    }

    public float getSaldo() {
        return saldo;
    }

    public void consignar(float cantidad) {
        saldo += cantidad;
        numConsignaciones++;
    }

    public boolean retirar(float cantidad) {
        if (cantidad <= saldo) {
            saldo -= cantidad;
            numRetiros++;
            return true;
        }
        return false;
    }

    public void calcularInteresMensual() {
        saldo += saldo * (tasaAnual / 12 / 100);
    }

    public void extractoMensual() {
        calcularInteresMensual();
        saldo -= comisionMensual;
    }

    public void imprimir() {
        System.out.println("Saldo: " + saldo);
        System.out.println("Consignaciones: " + numConsignaciones);
        System.out.println("Retiros: " + numRetiros);
    }
}