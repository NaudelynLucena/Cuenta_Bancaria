package dev.naulu.cuenta_bancaria.modelo;

public class CuentaAhorros extends Cuenta {
    boolean activa;

    public CuentaAhorros(float saldo, float tasaAnual) {
        super(saldo, tasaAnual);
        this.activa = saldo >= 10000;
    }

    @Override
    public void consignar(float cantidad) {
        if (activa) {
            super.consignar(cantidad);
        } else {
            System.out.println("La cuenta está inactiva.");
        }
    }

    @Override
    public boolean retirar(float cantidad) {
        if (activa) {
            return super.retirar(cantidad);
        } else {
            System.out.println("La cuenta está inactiva.");
            return false;
        }
    }

    @Override
    public void extractoMensual() {
        if (super.getSaldo() < 10000) {
            activa = false;
        }
        if (numRetiros > 4) {
            comisionMensual += (numRetiros - 4) * 1000;
        }
        super.extractoMensual();
    }

    @Override
    public void imprimir() {
        System.out.println("Saldo: " + getSaldo());
        System.out.println("Comisión mensual: " + comisionMensual);
        System.out.println("Transacciones: " + (numConsignaciones + numRetiros));
    }
}
