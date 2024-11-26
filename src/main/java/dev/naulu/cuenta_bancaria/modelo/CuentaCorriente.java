package dev.naulu.cuenta_bancaria.modelo;

public class CuentaCorriente extends Cuenta {
    float sobregiro = 0;

    public CuentaCorriente(float saldo, float tasaAnual) {
        super(saldo, tasaAnual);
    }

    @Override
    public boolean retirar(float cantidad) {
        if (cantidad <= getSaldo()) {
            return super.retirar(cantidad);
        } else {
            sobregiro += (cantidad - getSaldo());
            super.retirar(getSaldo());
            return true;
        }
    }

    @Override
    public void consignar(float cantidad) {
        super.consignar(cantidad);
        
        if (sobregiro > 0) {
            if (cantidad > sobregiro) { 
                sobregiro = 0;
            } else {
                sobregiro -= cantidad;
            }
        }
    }

    @Override
    public void extractoMensual() {
        super.extractoMensual();
    }

    @Override
    public void imprimir() {
        System.out.println("Saldo: " + getSaldo());
        System.out.println("Comisión mensual: " + comisionMensual);
        System.out.println("Transacciones: " + (numConsignaciones + numRetiros));
        System.out.println("Sobregiro: " + sobregiro);
    }
}