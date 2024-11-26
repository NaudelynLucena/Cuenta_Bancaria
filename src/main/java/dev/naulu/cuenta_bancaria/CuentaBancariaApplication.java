package dev.naulu.cuenta_bancaria;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.naulu.cuenta_bancaria.modelo.CuentaAhorros;
import dev.naulu.cuenta_bancaria.modelo.CuentaCorriente;

@SpringBootApplication
public class CuentaBancariaApplication {

	public static void main(String[] args) {
        CuentaAhorros cuentaAhorros = new CuentaAhorros(15000, 5);
        cuentaAhorros.consignar(5000);
        cuentaAhorros.retirar(2000);
        cuentaAhorros.extractoMensual();
        cuentaAhorros.imprimir();
        
        CuentaCorriente cuentaCorriente = new CuentaCorriente(5000, 5);
        cuentaCorriente.consignar(1000);
        cuentaCorriente.retirar(6000);
        cuentaCorriente.extractoMensual();
        cuentaCorriente.imprimir();
    }
}