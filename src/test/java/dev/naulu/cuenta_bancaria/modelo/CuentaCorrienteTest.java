package dev.naulu.cuenta_bancaria.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CuentaCorrienteTest {
    private CuentaCorriente cuenta;

    @BeforeEach
    void setUp() {
        // Crear una cuenta corriente con saldo inicial de 5,000 y tasa anual de 5%
        cuenta = new CuentaCorriente(5000, 0.05f);
    }

    @Test
    void testRetiroDentroDelSaldo() {
        // Realizar un retiro dentro del saldo disponible
        boolean resultado = cuenta.retirar(2000);
        
        // Verificar que el retiro se realizó correctamente
        assertTrue(resultado, "El retiro debe ser exitoso.");
        assertEquals(3000, cuenta.getSaldo(), "El saldo debe ser 3000.");
    }

    @Test
    void testRetiroConSobregiro() {
        // Intentar retirar más de lo que hay en el saldo (más de 5000)
        boolean resultado = cuenta.retirar(7000);
        
        // Verificar que el retiro se realizó correctamente
        assertTrue(resultado, "El retiro debe ser exitoso incluso con sobregiro.");
        assertEquals(0, cuenta.getSaldo(), "El saldo debe ser 0 después del retiro.");
        assertEquals(2000, cuenta.sobregiro, "El sobregiro debe ser 2000.");
    }

    @Test
    void testConsignarSinPagarTodoElSobregiro() {
        // Hacer un retiro que cause un sobregiro
        cuenta.retirar(7000);  // Sobregiro de 2000
        
        // Realizar una consignación parcial que no cubra todo el sobregiro
        cuenta.consignar(1000);
        
        // Verificar que el saldo haya aumentado y que el sobregiro no se haya eliminado completamente
        assertEquals(1000, cuenta.getSaldo(), "El saldo debe ser 1000 después de la consignación parcial.");
        assertEquals(1000, cuenta.sobregiro, "El sobregiro debe ser 1000 aún.");
    }

    @Test
    void testImprimirExtracto() {
        // Hacer un retiro y una consignación
        cuenta.retirar(2000);  // Sobregiro de 2000
        cuenta.consignar(1000); // Sobregiro se reduce a 1000

        // Verificar que la impresión no cause errores
        assertDoesNotThrow(() -> cuenta.imprimir());
    }

    @Test
    void testRetiroExcesivoSinSuficienteSaldo() {
        // Intentar realizar un retiro que exceda tanto el saldo como el sobregiro
        boolean resultado = cuenta.retirar(10000);
        
        // Verificar que el retiro no se puede hacer
        assertTrue(resultado, "El retiro debe ser exitoso, ya que se utiliza el sobregiro.");
        assertEquals(0, cuenta.getSaldo(), "El saldo debe ser 0 después del retiro.");
        assertEquals(5000, cuenta.sobregiro, "El sobregiro debe ser 5000.");
    }

    @Test
    void testConsignarExcesivoSinSobregiro() {
        // Hacer una consignación cuando no hay sobregiro
        cuenta.consignar(5000); // Consigna 5000
        
        // Verificar que el saldo se haya actualizado correctamente
        assertEquals(10000, cuenta.getSaldo(), "El saldo debe ser 10000 después de la consignación.");
        assertEquals(0, cuenta.sobregiro, "El sobregiro debe ser 0.");
    }
}
