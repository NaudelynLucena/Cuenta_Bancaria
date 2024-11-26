package dev.naulu.cuenta_bancaria.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        cuenta = new Cuenta(1000.0f, 12.0f);
    }

    @Test
    void testConsignar() {
        cuenta.consignar(500.0f);
        assertEquals(1500.0f, cuenta.getSaldo(), "El saldo debe ser 1500 después de consignar 500");
        assertEquals(1, cuenta.numConsignaciones, "El número de consignaciones debe ser 1");
    }

    @Test
    void testRetirar() {
        boolean resultado = cuenta.retirar(300.0f);
        assertTrue(resultado, "Debe poder retirar el dinero si hay suficiente saldo");
        assertEquals(700.0f, cuenta.getSaldo(), "El saldo debe ser 700 después de retirar 300");
        assertEquals(1, cuenta.numRetiros, "El número de retiros debe ser 1");
    }

    @Test
    void testRetirarConSaldoInsuficiente() {
        boolean resultado = cuenta.retirar(1500.0f);
        assertFalse(resultado, "No debe poder retirar más dinero del que tiene");
        assertEquals(1000.0f, cuenta.getSaldo(), "El saldo debe permanecer igual cuando no se puede retirar");
    }

    @Test
    void testCalcularInteresMensual() {
        cuenta.calcularInteresMensual();
        assertEquals(1010.0f, cuenta.getSaldo(), "El saldo debe ser 1010 después de calcular el interés mensual");
    }

    @Test
    void testExtractoMensual() {
        cuenta.extractoMensual();
        assertEquals(1010.0f, cuenta.getSaldo(), "El saldo debe ser 1010 después de calcular el interés mensual");
        assertEquals(0, cuenta.numRetiros, "El número de retiros no debe cambiar con el extracto mensual");
    }

    @Test
    void testImprimir() {
        cuenta.imprimir();
        assertDoesNotThrow(() -> cuenta.imprimir());
    }

    @Test
    void testComisionMensual() {
        cuenta.comisionMensual = 5.0f;
        cuenta.extractoMensual();
        assertEquals(1005.0f, cuenta.getSaldo(), "El saldo debe ser 1005 después de aplicar la comisión mensual de 5");
    }
}