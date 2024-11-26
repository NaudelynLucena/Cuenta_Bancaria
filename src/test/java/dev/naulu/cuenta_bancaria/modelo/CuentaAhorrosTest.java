package dev.naulu.cuenta_bancaria.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CuentaAhorrosTest {
    private CuentaAhorros cuenta;

    @BeforeEach
    void setUp() {
        // Crear una cuenta con saldo inicial de 12,000 y tasa anual de 5%
        cuenta = new CuentaAhorros(12000, 0.05f);
    }

    @Test
    void testCrearCuenta() {
        // Verificar si la cuenta está activa con saldo mayor o igual a 10,000
        assertTrue(cuenta.activa, "La cuenta debe estar activa");
    }

    @Test
    void testConsignarCuentaActiva() {
        // Consignar 2000 en la cuenta
        cuenta.consignar(2000);
        // Verificar el saldo después de la consignación
        assertThat(cuenta.getSaldo(), is(equalTo(14000f)));
    }

    @Test
    void testConsignarCuentaInactiva() {
        // Crear una cuenta con saldo menor a 10,000
        cuenta = new CuentaAhorros(8000, 0.05f);
        
        // Intentar consignar dinero en una cuenta inactiva
        cuenta.consignar(2000);
        
        // Verificar que el saldo no cambió
        assertThat(cuenta.getSaldo(), is(equalTo(8000f)));
    }

    @Test
    void testRetirarCuentaActiva() {
        // Retirar 3000 de la cuenta activa
        boolean resultado = cuenta.retirar(3000);
        
        // Verificar que el saldo se actualizó correctamente
        assertTrue(resultado);
        assertThat(cuenta.getSaldo(), is(equalTo(9000f)));
    }

    @Test
    void testRetirarCuentaInactiva() {
        // Crear una cuenta con saldo menor a 10,000
        cuenta = new CuentaAhorros(8000, 0.05f);
        
        // Intentar retirar dinero en una cuenta inactiva
        boolean resultado = cuenta.retirar(2000);
        
        // Verificar que el saldo no cambió y que la operación fue rechazada
        assertFalse(resultado);
        assertThat(cuenta.getSaldo(), is(equalTo(8000f)));
    }

    @Test
    void testExtractoMensualCuentaActiva() {
        // Realizar 5 retiros (el quinto generará comisión)
        cuenta.retirar(1000);
        cuenta.retirar(1000);
        cuenta.retirar(1000);
        cuenta.retirar(1000);
        cuenta.retirar(1000);
        
        // Verificar la comisión mensual
        cuenta.extractoMensual();
        
        // Verificar si la comisión fue aplicada
        assertThat(cuenta.comisionMensual, is(equalTo(1000f)));
    }

    @Test
    void testExtractoMensualCuentaInactiva() {
        // Crear cuenta con saldo menor a 10,000
        cuenta = new CuentaAhorros(8000, 0.05f);
        
        // Verificar que la cuenta se desactiva por saldo bajo
        cuenta.extractoMensual();
        
        // Verificar que la cuenta ahora está inactiva
        assertFalse(cuenta.activa);
    }

    // Nueva prueba: Cuenta activa con saldo mayor o igual a 10,000
    @Test
    void testExtractoMensualCuentaActivaSaldoMayorQue10000() {
        // Crear cuenta con saldo inicial mayor o igual a 10,000
        cuenta = new CuentaAhorros(15000, 0.05f);

        // Verificar que la cuenta está activa antes de llamar al extracto mensual
        assertTrue(cuenta.activa);

        // Ejecutar extracto mensual
        cuenta.extractoMensual();

        // Verificar que la cuenta sigue activa después del extracto
        assertTrue(cuenta.activa, "La cuenta debe seguir activa con saldo mayor a 10,000");
    }

    // Nueva prueba: Cuenta inactiva con saldo menor a 10,000
    @Test
    void testExtractoMensualCuentaInactivaSaldoMenorQue10000() {
        // Crear cuenta con saldo menor a 10,000
        cuenta = new CuentaAhorros(8000, 0.05f);

        // Verificar que la cuenta está activa antes de llamar al extracto mensual
        assertFalse(cuenta.activa);

        // Ejecutar extracto mensual
        cuenta.extractoMensual();

        // Verificar que la cuenta se ha desactivado
        assertFalse(cuenta.activa, "La cuenta debe estar inactiva con saldo menor a 10,000");
    }

    @Test
    void testImprimirExtracto() {
        // Realizar algunas consignaciones y retiros
        cuenta.consignar(5000);
        cuenta.retirar(2000);
        
        // Comprobar si se imprime la información esperada
        // Este test solo verifica si no lanza excepciones
        assertDoesNotThrow(() -> cuenta.imprimir());
    }
}